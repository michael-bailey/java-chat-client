package io.github.michael_bailey.client.managers;

import io.github.michael_bailey.client.Delegates.Interfaces.INetworkManagerDelegate;
import io.github.michael_bailey.client.Delegates.Interfaces.IServerModuleDelegate;
import io.github.michael_bailey.client.Delegates.NetworkManagerDelegate;
import io.github.michael_bailey.client.StorageDataTypes.Account;
import io.github.michael_bailey.client.StorageDataTypes.Contact;
import io.github.michael_bailey.client.StorageDataTypes.Server;
import io.github.michael_bailey.client.managers.NetworkModules.PTPModule;
import io.github.michael_bailey.client.managers.NetworkModules.ServerModule;

import java.net.ServerSocket;
import java.net.Socket;
import java.security.Provider;
import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * Network Manager
 * this class provides a way of managing multiple ways 
 * data can be sent to other computers.
 * 
 * @author michael-bailey, Mitch161
 * @version 1.0
 * @since 1.0
 */

public class NetworkManager implements IServerModuleDelegate {

	INetworkManagerDelegate delegate = new NetworkManagerDelegate();
	Account account;


	private final int serverConnectionPort = 6000;
	private final int ptpConnectionPort = 6001;

	private final Pattern parser = Pattern.compile("([?!])([a-zA-z0-9]*):|([a-zA-z]*):([a-zA-Z0-9\\-+\\[\\]{}_=/]+|(\"(.*?)\")+)");

	private final PTPModule ptpServer = new PTPModule(this, ptpConnectionPort);
	private final ServerModule serverConnection = new ServerModule(account);



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
	 * this listens for a new connection to the io.github.michael_bailey.client.
	 * @return a new socket returned from the accept call
	 */
	private Socket listenForClientConnection() {
		try{
			System.out.println("listening for io.github.michael_bailey.client connection...");
			ServerSocket listenSocket = new ServerSocket(this.ptpConnectionPort);
			return(listenSocket.accept());
		}catch(Exception e){
			System.out.println("Could not connect to incoming io.github.michael_bailey.client!\nException Thrown:\n");

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
