package client.managers;

import client.classes.Server;

import java.net.ServerSocket;
import java.security.*;
import java.io.*;
import java.net.Socket;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;

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
	private final int ptpConnectionPort = 6001;

	boolean peerToPeerRunning = true;
	private Thread ptpServerThread;
	ServerSocket ptpServerSocket;
	ExecutorService ptpThreadPool;


	/* todo encryption definitions.
	public final String HashingType = "SHA-256";
	public final String keyAgreementType = "DiffieHellman";
	public final String keyPairType = "DH";
	public final String SymetricCipherType = "AES/CBC/PKCS5Padding";
	public final String SymetricKeyFactoryType = "PBKDF2WithHmacSHA1";
	*/

	public NetworkManager() {

	}

	/**
	 * getServerDetails
	 *
	 * this is the primary way of adding getting new Server instances
	 * gets the status of the server in question and builds a server object from that.
	 *
	 * @param ipAddress the address of the server
	 * @return new Server instance if the connection was successful.
	 * todo add key exchange and encryption.
	 */
	public Server getServerDetails(String ipAddress) {
		try {

			// setup connection
			Socket connection = new Socket(ipAddress, 6000);

			// get data streams
			DataInputStream in = new DataInputStream(connection.getInputStream());
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());

			// get data sent from the server.
			String request = in.readUTF();

			// check the server sent the request key word
			if (request.equals("?request:")) {

				// writing the command !info:
				out.writeUTF("!info:");
				out.flush();

				// getting the response back and iterating over it to get the kvp
				Iterator<Object> tokenizer = new StringTokenizer(in.readUTF()).asIterator();

				// getting the response code
				String a = (String) tokenizer.next();

				// check if successful
				if (a.equals("!success:")) {

					// parse key value pairs.
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

					// close the connection
					connection.close();

					// return the new server object.
					return serverBuilder.build();

				// if the response code is diffrent then close the connection.
				} else {
					connection.close();
					return null;
				}

			// if keyword is wrong close the connection
			} else {
				connection.close();
				return null;
			}

		// required for when the client cannot connect
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void run(){
		while(this.peerToPeerRunning){
			// System.out.println("Please help");
			Socket clientSocket = this.listenForClientConnection();
			if(clientSocket !=null){
				System.out.println("Client Connected");
				this.startInboundConnection(clientSocket);
			}
		}
	}

	public void shutdownServers() {
		this.ptpThreadPool.shutdownNow();
		this.peerToPeerRunning = false;
	}

// MARK: ptp functionality definitions.

	/**
	 * this listens for a new connection to the client.
	 * @return a new socket returned from the accept call
	 */
	private void ptpThreadFn() {

		for ( int i : new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10} ) {
			this.ptpThreadPool.execute(() -> {
				for ( int j : new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10} ) {
					System.out.println("thread " + Thread.currentThread().getId() + ": printing: " + i);
				}
			});

		}

		while (peerToPeerRunning) {
			try {
				System.out.println("main ptp thread");
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void ptpStart() {
		try {
			// create the server socket
			ptpServerSocket = new ServerSocket(ptpConnectionPort);

			// create the server thread
			ptpServerThread = new Thread(() -> this.ptpThreadFn());

			// creating a thread pool
			this.ptpThreadPool = newFixedThreadPool(8);

			// tell server thread wo run.
			this.peerToPeerRunning = true;
			this.ptpServerThread.start();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void ptpStopThreads() {
		this.peerToPeerRunning = false;
		this.ptpThreadPool = null;
	}

	public void shutdown() {
		this.peerToPeerRunning = false;

	}

	/**
	 * this listens for a new connection to the client.
	 * @return a new socket returned from the accept call
	 */
	private Socket listenForClientConnection() {
		try{
			System.out.println("listening for client connection...");
			ServerSocket listenSocket = new ServerSocket(this.ptpConnectionPort);
			return(listenSocket.accept());
		}catch(Exception e){
			System.out.println("Could not connect to incoming client!\nException Thrown:\n");

			e.printStackTrace();
			return null;
		}
	}

		/* todo find out what this does
	public void sendClientMessage(String message) {
		Socket clientSocket = this.createClientConnection();
		if(clientSocket != null) {
			System.out.println("Connection Successful!");
			this.startOutboundConnection(clientSocket, message);
		}
	}
	*/

	private void startInboundConnection(Socket clientSocket){
		System.out.println("Inbound Connection Started");
		Inbound inbound = new Inbound(clientSocket);
		inbound.start();
	}
	
	private void startOutboundConnection(Socket clientSocket, String message){
		Outbound outbound = new Outbound(clientSocket, message);
		outbound.start();
	}

// MARK: cryptography definitions.

	/**
	 * used to list all crypto providers
	 * (not used yet as this will a feature to be added later on).
	 */
	public static void getProviders() {
		for (Provider provider: Security.getProviders()) {
			System.out.println(provider.getName());
			for (String key: provider.stringPropertyNames())
				System.out.println("\t" + key + "\t" + provider.getProperty(key));
		}
	}
}
