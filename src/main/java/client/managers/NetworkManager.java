package client.managers;

import client.Delegates.Interfaces.INetworkManagerDelegate;
import client.Delegates.NetworkManagerDelegate;
import client.classes.Account;
import client.classes.Contact;
import client.classes.Server;
import client.managers.NetworkModules.PTPModule;
import client.managers.NetworkModules.ServerModule;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.Provider;
import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static client.enums.PROTOCOL_MESSAGES.*;

/**
 * Network Manager
 * this class provides a way of managing multiple ways 
 * data can be sent to other computers.
 * 
 * @author michael-bailey, Mitch161
 * @version 1.0
 * @since 1.0
 */

public class NetworkManager {

	INetworkManagerDelegate delegate = new NetworkManagerDelegate();
	Account account;


	private final int serverConnectionPort = 6000;
	private final int ptpConnectionPort = 6001;

	private final Pattern parser = Pattern.compile("([?!])([a-zA-z0-9]*):|([a-zA-z]*):([a-zA-Z0-9\\-+\\[\\]{}_=/]+|(\"(.*?)\")+)");

	private final PTPModule ptpServer = new PTPModule(this, ptpConnectionPort);
	private final ServerModule serverConnection = new ServerModule();



	/* todo encryption definitions.
	public final String HashingType = "SHA-256";
	public final String keyAgreementType = "DiffieHellman";
	public final String keyPairType = "DH";
	public final String SymetricCipherType = "AES/CBC/PKCS5Padding";
	public final String SymetricKeyFactoryType = "PBKDF2WithHmacSHA1";
	*/

	public NetworkManager(Account account) {
		this.account = account;
	}

	public NetworkManager(Account account, INetworkManagerDelegate delegate) {
		this.account = account;
		this.delegate = delegate;
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

			// setup connection and get data streams
			Socket connection = new Socket(ipAddress, 6000);
			DataInputStream in = new DataInputStream(connection.getInputStream());
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());

			// get data sent from the server.
			String request = in.readUTF();

			Matcher matcher = parser.matcher(request);

			if (!matcher.find()) {
				connection.close();
				return null;
			}

			// check the server sent the request key word
			if (matcher.group().equals(REQUEST)) {

				// writing the command !info:
				out.writeUTF(INFO);
				out.flush();

				// get the next part of the
				String response = in.readUTF();
				Matcher responseMatcher = parser.matcher(response);
				responseMatcher.reset();

				if (!responseMatcher.find() || !responseMatcher.group().equals(SUCCESS) ) {
					return null;
				}

				var serverBuilder = new Server.Builder();
				while (responseMatcher.find()) {
					String[] args = responseMatcher.group().split(":");
					switch (args[0]) {
						case "name":
							serverBuilder.name(args[1]);
							break;

						case "owner":
							serverBuilder.owner(args[1]);
							break;

						default:
							break;
					}
				}

				connection.close();
				return serverBuilder.build();

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

	/*  todo remove and separate
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
	*/

// MARK: server functionality definitions.


	public void connect(Server serverDetails) {
	}

	public void dissconnect() {

	}


// MARK: ptp functionality definitions.



// MARK: network manager control functions.

	public void start() {
		this.ptpServer.start();
	}

	public void stop() {
		ptpServer.stop();
		serverConnection.disconnect();
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

	public void ptpSendMessage() {

	}

// MARK: Delegate Methods

	public void ptpReceivedMessage(HashMap<String, String> data) {
		delegate.ptpReceivedMessage(data);
	}

	public void updateClientList(ArrayList<Contact> contacts) {
		delegate.updateClientList(contacts);
	}


	public void serverReceivedMessage() {

	}

// MARK: ptp stuff (old)

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
