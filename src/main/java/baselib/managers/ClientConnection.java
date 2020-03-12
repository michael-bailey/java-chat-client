public class ClientConnection extends Thread implements Node{
	private String recipentAddress;
	private int recipentPort;
	private Socket recipentSocket;
	private Outbound outbound;
	private Inbound inbound;
	private NetworkManager networkManager;
	private boolean listenForRecipent, threadRunning = true;
	private ArrayList<String> inboundMessageQueue = ArrayList<String>(), outboundMessageQueue = ArrayList<String>();

	public ClientConnection(String recipentAddress, int recipentPort, NetworkManager networkManager){
		this.recipentAddress = recipentAddress;
		this.recipentPort = recipentPort;
		this.networkManager = networkManager;
		this.listenForRecipent = networkManager.getServer().getListenForClient();
	}
	
	@Override
	public void run(){
		this.inbound = new Inbound();
		if(listenForRecipent){
			this.inbound.listenForRecipentConnection();
		}else{
			this.createRecipentSocket();
			this.inbound.setRecipentSocketInput();
		}
		this.networkManager.getCurrentServer().setListenForClient(false);
		this.networkManager.getCurrentServer().setRequestSuccessful(false);
		
		this.inbound.start();
		this.outbound.start();
		while(this.threadRunning){

		}
	}

	private void createRecipentSocket(){this.recipentSocket = new Socket(recipentAddress, recipentPort);}
	
	public void queueMessage(String message){
		this.outboundMessageQueue.add(message);
	}

	public void doStop(){this.threadRunning = false;}

	private class Outbound{
		private PrintWriter recipentSocketOutput;
		private boolean outboundChatting = true;

		public Outbound(){}

		@Override
		public void run(){
			while(this.outboundChatting){
				// send message from output queue
				if(outboundMessageQueue.size() > 0){
					this.sendMessage(outboundMessageQueue.get(0));
				}
			}
		}

		private sendMessage(String message){
			this.recipentSocketOutput.println(message);
		}
		
		public void setRecipentSocketOutput(){this.recipentSocketOutput = new PrintWriter(recipentSocket.getOutputStream());}

		public void doStop(){this.outboundChatting = false;}
	}

	private class Inbound extends Thread{
		private BufferedReader recipentSocketInput;
		private boolean inboundChatting = true;

		public Inbound(){}

		@Override
		public void run(){
			while(this.inboundChatting){
				// read from message input queue
				if(inboundMessageQueue.size() > 0){
					this.readMessage();
				}
			//}
		}

		public void doStop(){this.inboundChatting = false;}

		public void listenForRecipentConnection(){
			ServerSocket listenSocket = new ServerSocket(recipentPort);
			System.out.println("Listening for Recipent Connection...");
			recipentSocket = listenSocket.accept();
			System.out.println("Recipent Connected");
			this.recipentSocketInput = (new BufferedReader(new InputStreamReader(recipentSocket.getInputStream())));
		}

		public void setRecipentSocketInput(){this.recipentSocketInput = (new BufferedReader(new InputStreamReader(recipentSocket.getInputStream())));}

		public void readMessage(){}
	}
}
