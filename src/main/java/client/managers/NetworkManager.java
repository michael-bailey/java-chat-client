package client.managers;

import client.classes.Server;
import client.ChatWindow.ListCells.ServerListCellModel;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;


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
	private String serverAddress;
	private int serverPort, tmpPort;
	boolean programRunning = true;

	// login properties
	SimpleBooleanProperty loggedIn = new SimpleBooleanProperty(false);

	// server properties
	SimpleMapProperty<UUID, Server> serverStore = new SimpleMapProperty<UUID, Server>();
	SimpleListProperty<ServerListCellModel> serverList = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<>()));
	SimpleObjectProperty<Server> currentServer = new SimpleObjectProperty<>();

	//server change Listeners
	private ChangeListener<Server> currentServerChange = (observable, oldValue, newValue) -> {
		System.out.println("Server changed");
	};

	/**
	 * defining the thread runnable that will,
	 * loop through all the server uuids.
	*/
	Runnable serverPingRunnable = () -> {
		System.out.println("Started...");

		// loop through the server keys
		for (UUID uuid : serverStore.keySet()) {
			try {
				// getting the server object
				System.out.println("testing: " + uuid.getMostSignificantBits());
				Server server = serverStore.get(uuid);

				// creating a socket for the server object along with
				// the same IO streams
				Socket serverSock = new Socket(server.getIpAddress(), 6001);
				InputStream sockIN = serverSock.getInputStream();
				OutputStream sockOUT = serverSock.getOutputStream();

				// get the message sent from the server and test it is part of the protocol
				if (new String(sockIN.readAllBytes()).equals(":Request?")) {
					// request the status from the server
					sockOUT.write(":status?".getBytes());

					// todo read server data to get
					// print out the server status just for debug and close the socket
					System.out.println(sockIN.readAllBytes());
					serverSock.close();

					var serverView = new ServerListCellModel();
					// the server is online
					serverView.ServerName = server.getServerName();
					serverView.uuid = uuid;

					serverList.add(serverView);
				}


			// the host can't be found
			} catch (UnknownHostException e) {
				e.printStackTrace();

			// other io exception occured.
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	};

	public boolean requestAddServer(String ipAddress) {
		return false;
	}

	private class serverPingTimerTask extends TimerTask {
		@Override
		public void run() {
			try {
				if (loggedIn.get()) {
					System.out.println("creating thread");
					var a = new Thread(serverPingRunnable);
					a.setDaemon(true);
					a.setName("server_ping_thread");
					a.start();
				} else {
					System.out.println("not logged in");
				}
			} catch (IllegalThreadStateException e) {
				e.printStackTrace();
			}
		}
	}
	Timer serverPingTimer = new Timer();


	public NetworkManager(){
		this.serverAddress = "127.0.0.1";
		this.serverPort = 6001;
		this.tmpPort = 6000;

		serverPingTimer.scheduleAtFixedRate(new serverPingTimerTask(), 0, 10000);
	}

	@Override
	public void run(){
		while(this.programRunning){
			// System.out.println("Please help");
			Socket clientSocket = this.listenForClientConnection();
			if(clientSocket !=null){
				System.out.println("Client Connected");
				this.startInboundConnection(clientSocket);
			}
		}
	}

	public void doStop(){this.programRunning = false;}
	
	public void sendClientMessage(String message){
		Socket clientSocket = this.createClientConnection();
		if(clientSocket != null){
			System.out.println("Connection Successful!");
			this.startOutboundConnection(clientSocket, message);
		}
	}

	private Socket createClientConnection(){
		try{
			System.out.println("Connecting to Client...");
			return(new Socket(this.serverAddress, this.serverPort));
		}catch(Exception e){
			//throw new ClientConnectionException
			System.out.println("Could not connect to requested client!\nException Thrown:\n");
			e.printStackTrace();
			return null;
		}
	}
	
	private Socket listenForClientConnection(){
		try{
			System.out.println("listening for client connection...");
			ServerSocket listenSocket = new ServerSocket(this.tmpPort);
			return(listenSocket.accept());
		}catch(Exception e){
			System.out.println("Could not connect to incoming client!\nException Thrown:\n");
			e.printStackTrace();
			return null;
		}
	}

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

	public SimpleMapProperty<UUID, Server> serverStoreProperty() {
		return serverStore;
	}

	public SimpleListProperty<ServerListCellModel> serverListProperty() {
		return serverList;
	}

	public SimpleObjectProperty<Server> currentServerProperty() {
		return currentServer;
	}
}
