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
 * @author michael-bailey, Mitch161
 * @version 1.0
 * @since 1.0
 */

public class NetworkManager extends Thread{
	private String serverAddress;
	private int serverPort;
	boolean programRunning = true;

	public NetworkManager(){
		this.serverAddress = "127.0.0.1";
		this.serverPort = 6000;
	}

	@Override
	public void run(){
		while(this.programRunning){
			Socket clientSocket = this.listenForClientConnection();
			System.out.println("Client Connected");
			this.startInboundConnection(clientSocket);
		}
	}

	public void doStop(){this.threadRunning = false;}
	
	public void sendClientMessage(String message){
		Socket clientSocket = this.createClientConnection();
		this.startOutboundConnection(clientSocket, message);
	}

	private void startOutboundConnection(Socket clientSocket, String message){
		Outbound outbound = new Outbound(clientSocket, message);
		outbound.start();
	}

	private Socket createClientConnection(){
		System.out.println("Connecting to Client...");
		return(new Socket(this.serverAddress, this.serverPort));
	}
	
	private Socket listenForClientConnection(){
		ServerSocket listenSocket = new ServerSocket(serverPort);
		System.out.println("Listening for Client Connection...");
		return(listenSocket.accept());
	}

	private void startInboundConnection(Socket clientSocket){
		System.out.println("Inbound Connection Started");
		Inbound inbound = new Inbound(clientSocket);
		inbound.start();
	}
}
