//package baselib.managers;

import java.util.Scanner;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Iterator;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
//import baselib.interfaces.INetworkManagerController;

//import javax.jmdns.*;

/**
 * Network Manager
 * this class provides a way of managing multiple ways 
 * data can be sent to other computers.
 * 
 * @author michael-bailey
 * @version 1.0
 * @since 1.0
 */

public class NetworkManager extends Thread{
	//private HashMap<> servers;
	private String currentServerAddress = "localhost", recipentAddress = "";
	private int currentServerPort = 9806;
	private Socket serverConnectionSocket, recipentSocket;
	private PrintWriter serverSocketOutput, recipentSocketOutput;
	private BufferedReader serverSocketInput, recipentSocketInput;
	private ServerConnection[] connectionThreads = new ServerConnection[2];
	private boolean appRunning = true, isConnected = false, stopConnection = false;

	public NetworkManager(/*HashMap<> servers*/){
		System.out.println("Details Stored");
		//this.servers = servers;
	}

	@Override
	public void run(){
		while(appRunning){
			if(connectionThreads[0]==null){
				System.out.println("primary connection created");
				this.createNewConnection();
				connectionThreads[0].start();
				ClientConnection client = new ClientConnection
			}
		}
	}

	public void createNewConnection(){
		if(this.checkConnectionThreads()){
			connectionThreads[0].doStop();
		}
		ServerConnection primary = new ServerConnection();
		connectionThreads[0] = primary;
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
		private ArrayList<String> messageQueue = new ArrayList<String>();//<Packet>

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
			while(!stopConnection){
				if(!isConnected){
					try{
						this.connectToServer();
					}catch(Exception e){}
				}
				/*if(messageQueue.size()>0){
					String message = messageQueue.remove(0);
					//if(message.type==0){
					this.sendServerMessage(message);
					//}else if(message.type==1){
					//	this.sendClientMessage(message);
					//}else{System.out.println("Message is not in correct format");}
					try{
					System.out.println(serverSocketInput.readLine());
					}catch(Exception e){}
				}*/
				short sh = 0;
				if(recipentAddress.isEmpty()){
					Scanner scan = new Scanner(System.in);
					System.out.println("enter message > ");
					sh = scan.nextShort();
					//messageQueue.add(str);
				}

				if(sh==1){
					this.sendServerMessage(sh);
					recipentAddress = serverSocketInput.readLine()
					try{
						this.createRecipentSocket();
					}catch(Exception e){
						e.printTraceStack();
					}
				}
			}
		}

		public void doStop(){
			stopConnection = true;
		}

		public void connectToServer() throws Exception{
			System.out.println("Client connection to server started");
			serverConnectionSocket = new Socket(currentServerAddress, currentServerPort);//ip address of server, and port of appliaction
			serverSocketOutput = new PrintWriter(serverConnectionSocket.getOutputStream(), true);//true allows for immidete automatic flush of data eg gets sent.
			serverSocketInput = new BufferedReader(new InputStreamReader(serverConnectionSocket.getInputStream()));
			isConnected = true;
		}

		public void createRecipentSocket() throws Exception{
			if(!recipentAddress.isEmpty()){
				System.out.println("recipent address recieved");
				recipentSocket = new Socket(recipentAddress, currentServerPort);
				recipentSocketOutput = new PrintWriter(recipentSocket.getOutputStream(), true);//true allows for immidete automatic flush of data eg gets sent.
				recipentSocketInput = new BufferedReader(new InputStreamReader(recipentSocket.getInputStream()));
			}else{
				System.out.println("There is currently no recipent address stored!");
			}
		}

		public void sendServerMessage(short message){
			serverSocketOutput.println(message);
			if(message == 0){
				appRunning = false;
				this.doStop();
			}
		}
		
		public boolean checkServerConnection(){
			return isConnected;
		}
	}

	private class ClientConnection extends Thread {
		private boolean isConnected = false;
		private ServerSocket ss;
		private Socket socket;

		public ClientConnection(){
			System.out.println("Client Thread Started");
		}
		
		@Override
		public void run(){
			this.ss = new ServerSocket(currentServerPort);
			System.out.println("Listening to client peer...");
			while(!stopClientConnection){
				if(!this.isConnected){
					this.socket = this.ss.accept();
					System.out.println("Connection Established");
					this.isConnected = true;
				}
			}
		}

		public void doStop(){
			stopClientConnection = true;
		}
	}

	/*private class InboundMessages extends Thread {
		public InboundMessages(){System.out.println("Inbound created...");}
		
		@Override
		public void run(){
			while(!stopThread){
				try{
					System.out.println("waiting for message...");
					//System.out.println(serverSocketInput.readLine());
				}catch(Exception e){
					System.out.println("error in inbound messages thread\n");
					e.printStackTrace();
				}
			}
		}
	}*/
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
