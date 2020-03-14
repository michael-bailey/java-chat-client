public class Outbound extends Thread{
	private Socket clientSocket;
	private clientSocketOutput;
	private String message;

	public Outbound(Socket clientSocket, String message){
		this.clientSocket = clientSocket;
		this.clientSocketOutput = new Outbound(new PrintWriter(clientSocket.getOutputStream(), true));
		this.message = message;
	}

	@Override
	public void run(){
		this.sendMessage();
		clientSocket.close();
	}

	private void sendMessage(){
		this.clientSocketOutput.println(this.message);
	}
}
