/*import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class ClientConnection{
	private String clientAddress, recipentAddress;
	private final String SERVER_ADDRESS;
	private final int SERVER_PORT;
	private Socket serverSocket, recipentSocket;
	private PrintWriter serverSocketOutput, recipentSocketOutput;

	public ClientConnection(String clientAddress, String serverAddress, int serverPort, String recipentAddress){
		System.out.println("Details Stored");
		this.clientAddress = clientAddress;
		this.SERVER_ADDRESS = serverAddress;
		this.recipentAddress = recipentAddress;
		this.SERVER_PORT = serverPort;
	}

	public void requestServerConnection() throws Exception{
		System.out.println("Client connection to server started");
		serverSocket = new Socket(SERVER_ADDRESS,SERVER_PORT);//ip address of server, and port of appliaction
		serverSocketOutput = new PrintWriter(serverSocket.getOutputStream(), true);//true allows for immidete automatic flush of data eg gets sent.
	}

	public void requestRecipentAddress(){
		return;
	}

	/*
	 * Create recipent socket needs to be in a seperate class - a class for p2p

	public void createRecipentSocket() throws Exception{
		if(!recipentAddress.isEmpty()){
			recipentSocket = new Socket(recipentAddress,SERVER_PORT);
			recipentSocketOutput = new PrintWriter(recipentSocket.getOutputStream(), true);//true allows for immidete automatic flush of data eg gets sent.
		}else{
			System.out.println("There is currently no recipent address stored!");
		}
	}

	public boolean checkServerConnection(){
		return false;	
	}

	public boolean checkRecipentConnection(){
		return false;
	}

	public void sendMessage(String clientMessage){
		//if(this.checkRecipentConnection){
		socketOutput.println(clientMessage);
		//}
		//else{
		//	System.out.println("Message could not be sent as no connection has been Established.");
		//}
	}

	/*public void requestRecipentAddress(){

	}
}
*/
