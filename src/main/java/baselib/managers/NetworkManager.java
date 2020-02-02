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

public class NetworkManager extends Thread {
	private HashMap<> servers;
	private String currentServerAddress;
	private int currentServerPort;
	private Socket serverConnectionSocket, recipentSocket;
	private PrintWriter serverSocketOutput, recipentSocketOutput;

	public NetworkManager(HashMap<> servers){
		System.out.println("Details Stored");
		this.servers = servers;
	}
	
	@Override
	public void run(){
		//set a boolean to true
		//create a socket to the server - call connectToServer();
		//perform handshake - encryption (diffie stuff)
		//while(isConnected):
		//	retrieve all clients from server
		//	wait 10 secs before checking again
	}

	public void connectToServer() throws Exception{
		System.out.println("Client connection to server started");
		serverConnectionSocket = new Socket(currentServerAddress, currentServerPort);//ip address of server, and port of appliaction
		serverConnectionOutput = new PrintWriter(serverConnectionSocket.getOutputStream(), true);//true allows for immidete automatic flush of data eg gets sent.
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
		return false;
	}

	public boolean checkRecipentConnection(){
		return false;
	}

	public void sendMessage(String clientMessage){
		//if(this.checkRecipentConnection){
		socketOutput.println(clientMessage);
		//}
		//else{
		//	System.out.println("Message could not be sent as no connection has been Established.");
		//}
	}

	/*public void requestRecipentAddress(){

	}*/
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
