import java.net.Socket;
import java.net.ServerSocket;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ServerConnection{
	private final int PORT = 9806;
	private boolean doStop = false;
	private ServerSocket ss;

	public ServerConnection(){
		try{
			this.ss = new ServerSocket(PORT);
			System.out.println("Listening for Connections...");
			while(this.keepRunning()){
				Socket socket = this.ss.accept();
				System.out.println("Connection Established");
				System.out.println("Creating new client thread...");
				createClientThread(socket);
			}
		}catch(Exception e){
			System.out.println("ServerConnection Constructor error!");
		}

	}
			
	public synchronized void doStop(){
		this.doStop = true;
	}

	private synchronized boolean keepRunning(){
		return this.doStop == false;
	}

	private void createClientThread(Socket socket){
		ClientThread client = new ClientThread(socket);
		client.start();
	}
	
	private class ClientThread extends Thread{
		private Socket socket;
		private boolean doStop = false;

		public ClientThread(Socket socket){
			this.socket = socket;
		}

		public synchronized void doStop(){
			this.doStop = true;
		}
	
		private synchronized boolean keepRunning(){
			return this.doStop == false;
		}

		@Override
		public void run(){
			try{
				System.out.println("Client Thread Created");
				BufferedReader dataInput = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
				String str = dataInput.readLine();
				System.out.println(str);
			}catch(Exception e){
				System.out.println("ClientThread Error!");
			}
		}
	}
}
