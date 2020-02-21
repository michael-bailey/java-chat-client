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
	private Socket serverConnectionSocket;
	private PrintWriter serverSocketOutput;
	private BufferedReader serverSocketInput;
	private ServerConnection[] connectionThreads = new ServerConnection[2];
	private boolean appRunning = true, isConnected = false, stopServerConnection = false, stopClientConnection=false, responseSuccessful = false;

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
				//ClientConnection client = new ClientConnection();
			}
			if(responseSuccessful){
				ClientConnection client = new ClientConnection();
				client.start();
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
			boolean tmp = true;
			//set a boolean to true
			//create a socket to the server - call connectToServer();
			//perform handshake - encryption (diffie stuff)
			//while(isConnected):
			//	retrieve all clients from server
			//	wait 10 secs before checking again
			while(!stopServerConnection){
				if(!isConnected){
					try{
						this.connectToServer();
					}catch(Exception e){}
				}

				short sh = 0;
				if(recipentAddress.isEmpty()&&tmp){
					Scanner test = new Scanner(System.in);
					System.out.println("Do you want to talk to server(y/n)? ");
					String ans = test.nextLine();
					if(ans.equals("y")){
						Scanner scan = new Scanner(System.in);
						System.out.println("enter message > ");
						sh = scan.nextShort();
						//messageQueue.add(str);
					}else{
						tmp = false;
					}
				}

				if(sh==1){
					try{
						this.sendServerMessage(sh);
						recipentAddress = serverSocketInput.readLine();
						//this.createRecipentSocket();
						this.sendServerMessage(2);
						short response = serverSocketInput.readShort();
						if(response == 10){
							responseSuccessful = true;
						}else{
							System.out.println("Failed to request chat")
						}
					}catch(Exception e){}
				}
				if(tmp==false){
					short response = serverSocketInput.readShort();
					if(response == 2){
						this.sendServerMessage(10);
						responseSuccessful = true;
					}
				}
			}
		}

		public void doStop(){
			stopServerConnection = true;
		}

		public void connectToServer() throws Exception{
			System.out.println("Client connection to server started");
			serverConnectionSocket = new Socket(currentServerAddress, currentServerPort);//ip address of server, and port of appliaction
			serverSocketOutput = new PrintWriter(serverConnectionSocket.getOutputStream(), true);//true allows for immidete automatic flush of data eg gets sent.
			serverSocketInput = new BufferedReader(new InputStreamReader(serverConnectionSocket.getInputStream()));
			isConnected = true;
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
		private Socket recipentSocket;
		private PrintWriter recipentSocketOutput;
		private BufferedReader recipentSocketInput;
		private Scanner scan = new Scanner(System.in);

		public ClientConnection(){
			System.out.println("Client Thread Started");
		}
		
		@Override
		public void run(){
			do{
				if(!this.isConnected){
					if(recipentAddress.isEmpty()){
						this.listenForConnection();
					}else{
						this.connectToRecipent();
					}
				}
				//ask for user input message
				System.out.print("Enter message to other client:\n>");
				String message = scan.nextLine();
				this.sendMessage(message);
			}while(this.isConnected);
		}

		public void doStop(){
			stopClientConnection = true;
		}

		public void listenForConnection(){
			try{
				this.ss = new ServerSocket(currentServerPort);
				System.out.println("Listening to client peer...");
				this.recipentSocket = this.ss.accept();
				System.out.println("Connection Established");
				this.setConnectionState(true);
			}catch(Exception e){}
		}
		
		public void connectToRecipent() throws Exception{
			System.out.println("creating recipent socket...");
			this.recipentSocket = new Socket(recipentAddress, currentServerPort);
			this.recipentSocketOutput = new PrintWriter(this.recipentSocket.getOutputStream(), true);//true allows for immidete automatic flush of data eg gets sent.
			this.recipentSocketInput = new BufferedReader(new InputStreamReader(this.recipentSocket.getInputStream()));
			this.setConnectionState(true);
		}

		private void sendMessage(String message){
			recipentSocketOutput.println(message);
		}

		private void setConnectionState(boolean state){this.isConnected = state;}
		public boolean endConnection(){return false;}
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
