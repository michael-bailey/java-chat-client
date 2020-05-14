package client.managers;

import java.util.Scanner;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Iterator;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Inbound extends Thread{
	private Socket clientSocket;
	private BufferedReader clientSocketInput;
	
	public Inbound(Socket clientSocket){
		this.clientSocket = clientSocket;
		try{
			this.clientSocketInput = (new BufferedReader(new InputStreamReader(clientSocket.getInputStream())));
		}catch(Exception e){
			this.clientSocketInput = null;
			System.out.println("Failed to create BufferedReader!\nException Thrown:");
			e.printStackTrace();
		}
	}

	@Override
	public void run(){
		if(this.clientSocketInput != null){
			String messageObject = this.readMessage();
			if(messageObject != null){
				System.out.println(messageObject);
			}
		}
		try{
			this.clientSocket.close();
			this.clientSocketInput.close();
		}catch(Exception e){
			System.out.println("Failed to close streams!\nException Thrown:");
			e.printStackTrace();
		}
	}

	private String readMessage(){
		try{
			return(this.clientSocketInput.readLine());
		}catch(Exception e){
			System.out.println("Failed to read data from input stream!\nException Thrown:");
			e.printStackTrace();
			return null;
		}
	}
}
