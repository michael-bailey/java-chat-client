package baselib.managers;

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

// Package Imports
import client.exceptions.ClientConnectionException;

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
	private int serverPort, tmpPort;
	boolean programRunning = true;

	public NetworkManager(){
		this.serverAddress = "127.0.0.1";
		this.serverPort = 6001;
		this.tmpPort = 6000;
	}

	@Override
	public void run(){
		while(this.programRunning){
			System.out.println("Please help");
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
}
