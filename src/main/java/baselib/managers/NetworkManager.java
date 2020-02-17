package baselib.managers;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Iterator;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import baselib.interfaces.INetworkManagerController;

import javax.jmdns.*;

/**
 * Network Manager
 * this class provides a way of managing multiple ways 
 * data can be sent to other computers.
 * 
 * @author michael-bailey
 * @version 1.0
 * @since 1.0
 */

public class NetworkManager {
	private HashMap<> servers;
	private String currentServerAddress;
	private int currentServerPort;
	private Socket serverConnectionSocket, recipentSocket;
	private PrintWriter serverSocketOutput, recipentSocketOutput;
	private ServerConnection[] connectionThreads = new ServerConnection[2];
	private boolean appRunning = true, isConnected = false;

	public NetworkManager(HashMap<> servers){
		System.out.println("Details Stored");
		this.servers = servers;
	}

	public void startNetworkManager(){
		while(appRunning){
			if(connectionThreads[0]==null){
				primary = this.createNewConnection();
				primary.start();
			}
		}
	}

	public ServerConnection createNewConnection(){
		if(this.checkConnectionThreads){
			connectionThreads[0].stop();
		}
		ServerConnection primary = new ServerConnection();
		connectionThreads[0] = primary;
		return primary;
	}

	private boolean checkConnectionThreads(){
		if(connectionThreads[0]==null){
			return false;
		}
		return true;
	}

	public void shutdownThreads(){
		appRunning = false;
	}
	
	private class ServerConnection extends Thread {
		private boolean stopThread = false;
		private ArrayList<Packet> messageQueue = new ArrayList<Packet>();

		public ServerConnection(){
		}

		@Override
		public void run(){
			//set a boolean to true
			//create a socket to the server - call connectToServer();
			//perform handshake - encryption (diffie stuff)
			//while(isConnected):
			//	retrieve all clients from server
			//	wait 10 secs before checking again
			while(!stopThread){
				if(!isConnected){
					this.connectToServer();
				}
				if(messageQueue.size()>0){
					Packet message = messageQueue.remove(0);
					if(message.type==0){
						this.sendServerMessage(message);
					}else if(message.type==1){
						this.sendClientMessage(message);
					}else{System.out.println("Message is not in correct format");}
				}
			}
		}

		public void doStop(){
			this.stopThread = true;
		}

		public void connectToServer() throws Exception{
			System.out.println("Client connection to server started");
			serverConnectionSocket = new Socket(currentServerAddress, currentServerPort);//ip address of server, and port of appliaction
			serverConnectionOutput = new PrintWriter(serverConnectionSocket.getOutputStream(), true);//true allows for immidete automatic flush of data eg gets sent.
			isConnected = true;
		}

		public void requestRecipentAddress(){
			return;
		}

		public void createRecipentSocket() throws Exception{
			if(!recipentAddress.isEmpty()){
				recipentSocket = new Socket(recipentAddress,SERVER_PORT);
				recipentSocketOutput = new PrintWriter(recipentSocket.getOutputStream(), true);//true allows for immidete automatic flush of data eg gets sent.
			}else{
				System.out.println("There is currently no recipent address stored!");
			}
		}

		public boolean checkServerConnection(){
			return this.isConnected;
		}

		public boolean checkRecipentConnection(){
			return false;
		}

		public void sendServerMessage(String serverMessage){
			serverSocketOutput.println(serverMessage);
		}

		public void sendClientMessage(String clientMessage){
			//if(this.checkRecipentConnection){
			recipentSocketOutput.println(clientMessage);
			//}
			//else{
			//	System.out.println("Message could not be sent as no connection has been Established.");
			//}
		}

	}
}
// TODO SERVER CONNECTION THREAD - switch from one server to another
/* checks to see if thread is running (network manager thread)
 * if true:
 * 	signal to stop - change isConnected to false
 * 	terminate connection to server
 * start a new connection
 * set isConnected to true
 * tell thread to start running
 * stonks :]
 */
// TODO P2P CONNECTION
/*
 * create handshake with other client
 * send message
 * end connection
 */
