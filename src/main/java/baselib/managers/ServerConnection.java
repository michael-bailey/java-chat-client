//test

public class ServerConnection extends Thread implements Node{
	private String serverAddress, recipentAddress;
	private int serverPort;
	private Socket serverConnectionSocket;
	private Outbound outbound;
	private Inbound inbound;
	//private List<Short> outboundMessageQueue = new ArrayList<>();
	//private List<Short> inboundMessageQueue = new ArrayList<>();
	private boolean threadRunning = true, isConnected= false, requestToChat = false, listenForClient = false, requestSuccessful = false;;

	public ServerConnection(String serverAddress, int serverPort){
		this.serverAddress = serverAddress;
		this.serverPort = serverPort;
	}

	@Override
	public void run(){
		while(this.threadRunning){
			if(!this.isConnected){
				this.connectToServer();
				this.inbound.start();
			}
			if(this.requestToChat && this.isConnected){
				if(recipentAddress.isEmpty()){
					this.outbound.sendMessage(1);
					//wait timer
				}else if(!recipentAddress.isEmpty()){
					this.outbound.sendMessage(2);
					//wait timer
					if(this.requestSuccessful){
						this.requestToChat = false;
					}
				}else{
					System.err.println("Waiting for address...");
				}
			}
		}
	}

	private void connectToServer(){
		System.out.println("Connecting to server...");
		this.serverConnectionSocket = new Socket(this.serverAddress, this.serverPort);
		this.outbound = new Outbound(new PrintWriter(serverConnectionSocket.getOutputStream(), true));
		this.inbound = new Inbound(new BufferedReader(new InputStreamReader(serverConnectionSocket.getInputStream())));
		this.isConnected = true;
	}
	public boolean setRequestToChat(boolean requestToChat){this.requestToChat = requestToChat;}
	
	public boolean getRequestToChat(){return this.requestToChat;}

	public boolean getListenForClient(){return this.listenForClient;}
	
	public boolean setListenForClient(boolean state){this.listenForClient = state;}
	
	public boolean getRequestSuccessful(){return this.requestSuccessful;}
	
	public boolean setRequestSuccessful(boolean state){this.requestSuccessful = state;}

	public String getRecipentAddress(){return this.recipentAddress;}

	public void doStop(){this.threadRunning = false;}
	
	private class Outbound{
		private PrintWriter serverSocketOutput;

		public Outbound(PrintWriter serverSocketOutput){
			this.serverSocketOutput = serverSocketOutput;
		}

		public void sendMessage(short message){
			serverSocketOutput.println(message);
		}
	}
	
	private class Inbound extends Thread{
		private BufferedReader serverSocketInput;
		private boolean listening = true;

		public Inbound(BufferedReader serverSocketInput){
			this.serverSocketInput = serverSocketInput;
		}

		@Override
		public void run(){
			while(this.listening){
				//This needs to be gson format
				short response = serverSocketInput.ReadShort();
				switch(response){
					case 2:
						listenForClient = true;
					case 10:
						requestSuccessful = true;
					default:
						System.err.println("Response Failed");
				}
			}
		}

		public void doStop(){this.listening = false;}
	}
}
