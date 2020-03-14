public class Inbound extends Thread{
	private Socket clientSocket;
	private BufferedReader clientSocketInput;
	
	public Inbound(Socket clientSocket){
		this.clientSocket = clientSocket;
		this.clientSocketInput = (new BufferedReader(new InputStreamReader(clientSocket.getInputStream())));
	}

	@Override
	public void run(){
		String messageObject = this.readMessage();
		System.out.println(messageObject);
		this.clientSocket.close();
	}

	private String readMessage(){
		return(this.clientSocketInput.read());
	}
}
