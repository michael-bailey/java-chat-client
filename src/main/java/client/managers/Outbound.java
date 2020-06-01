package client.managers;

import java.io.DataOutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Outbound
 *
 * this class is used to handle sending messages to other clients
 * using the peer to peer model
 *
 * @author mitch161
 */
public class Outbound extends Thread {

	private final Socket clientSocket;
//	private PrintWriter clientSocketOutput;
	//private OutputStream clientSocketOutput;
	private DataOutputStream clientSocketOutput;
	private final String message;

	/**
	 *
	 * @param clientSocket the socket that will be connected to
	 * @param message
	 */
	public Outbound(Socket clientSocket, String message){
		this.clientSocket = clientSocket;
		try{
			//this.clientSocketOutput = new PrintWriter(clientSocket.getOutputStream(), true);
			this.clientSocketOutput = new DataOutputStream(clientSocket.getOutputStream());
			System.out.println("Socket Output Successful!");
		}catch(Exception e){
			this.clientSocketOutput = null;
			System.out.println("Failed to create PrintWriter!\nException Thrown:");
			e.printStackTrace();
		}
		this.message = message;
	}

	@Override
	public void run(){
		if(this.clientSocketOutput != null){
			this.sendMessage();
			try{
				//TimeUnit.SECONDS.sleep(3);
				Thread.sleep(3000);
			}catch(Exception e){}
		}
		try{
			this.clientSocket.close();
			this.clientSocketOutput.close();
			System.out.println("Output Streams Successfully Closed!");
		}catch(Exception e){
			System.out.println("Failed to close streams!\nException Thrown:");
			e.printStackTrace();
		}
	}

	private void sendMessage(){
		try{
			byte[] messageBytes = this.message.getBytes(StandardCharsets.UTF_8);
			//this.clientSocketOutput.println(messageBytes)
			System.out.println("Sending message: "+this.message);
			this.clientSocketOutput.write(messageBytes);
			this.clientSocketOutput.flush();
			System.out.println("Message Sent!");
		}catch(Exception e){
			System.out.println("Failed to send message!");
		}
	}
}
