package client.managers;

import client.classes.Server;
import javafx.beans.property.SimpleBooleanProperty;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.StringTokenizer;


/**
 * Network Manager
 * this class provides a way of managing multiple ways 
 * data can be sent to other computers.
 * 
 * @author michael-bailey, Mitch161
 * @version 1.0
 * @since 1.0
 */

public class NetworkManager extends Thread{

	private final int serverConnectionPort = 6000;
	private final int clientConnectionPort = 6001;

	private final String serverAddress;
	private final int serverPort;
	private final int tmpPort;
	boolean programRunning = true;




	public NetworkManager(){
		this.serverAddress = "127.0.0.1";
		this.serverPort = 6001;
		this.tmpPort = 6000;
	}

	/**
	 * addServer
	 *
	 * this is the primary way of adding getting new Server instances
	 * gets the status of the server in question and builds a server object from that.
	 *
	 * @param ipAddress the address of the server
	 * @return new Server instance if the connection was successful.
	 */
	public Server getServerDetails(String ipAddress) {
		try {
			Socket connection = new Socket(ipAddress, 6000);

			DataInputStream in = new DataInputStream(connection.getInputStream());
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());

			char[] inBuffer = new char[1024];

			String request = in.readUTF();


			if (request.equals("?request:")) {
				out.writeUTF("!info:");
				out.flush();

				Iterator<Object> tokenizer = new StringTokenizer(in.readUTF()).asIterator();
				String a = (String) tokenizer.next();

				if (a.equals("!success:")) {

					var serverBuilder = new Server.Builder();
					while (tokenizer.hasNext()) {
						String[] property = ((String) tokenizer.next()).split(":");
						switch (property[0]) {
							case "name":
								serverBuilder.name(property[1]);
								break;

							case "owner":
								serverBuilder.owner(property[1]);
								break;

							default:
								break;
						}
					}
					connection.close();
					return serverBuilder.build();
				} else {
					connection.close();
					return null;
				}
			} else {
				connection.close();
				return null;
			}

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void run(){
		/*
		while(this.programRunning){
			// System.out.println("Please help");
			Socket clientSocket = this.listenForClientConnection();
			if(clientSocket !=null){
				System.out.println("Client Connected");
				this.startInboundConnection(clientSocket);
			}
		}

		 */
	}

// --Commented out by Inspection START (17/05/2020, 16:44):
//	// --Commented out by Inspection (17/05/2020, 16:44):public void doStop(){this.programRunning = false;}
//
//	public void sendClientMessage(String message){
//		Socket clientSocket = this.createClientConnection();
//		if(clientSocket != null){
//			System.out.println("Connection Successful!");
//			this.startOutboundConnection(clientSocket, message);
// --Commented out by Inspection STOP (17/05/2020, 16:44)
		//}
	//}
	
// --Commented out by Inspection START (17/05/2020, 16:44):
//	private Socket listenForClientConnection(){
//		try{
// --Commented out by Inspection START (17/05/2020, 16:44):
////			System.out.println("listening for client connection...");
////			ServerSocket listenSocket = new ServerSocket(this.tmpPort);
////			return(listenSocket.accept());
////		}catch(Exception e){
////			System.out.println("Could not connect to incoming client!\nException Thrown:\n");
// --Commented out by Inspection STOP (17/05/2020, 16:44)
//			e.printStackTrace();
//			return null;
//		}
//	}
// --Commented out by Inspection STOP (17/05/2020, 16:44)

	/*
	private void startInboundConnection(Socket clientSocket){
		System.out.println("Inbound Connection Started");
		Inbound inbound = new Inbound(clientSocket);
		inbound.start();
	}
	
	private void startOutboundConnection(Socket clientSocket, String message){
		Outbound outbound = new Outbound(clientSocket, message);
		outbound.start();
	}

	public SimpleBooleanProperty loggedInProperty() {
		return loggedIn;
	}
*/
}
