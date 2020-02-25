public class ClientConnection extends Thread implements Node{
	private String recipentAddress;
	private int recipentPort;
	private Socket recipentSocket;
	private Outbound outbound;
	private Inbound inbound;
	boolean listenForRecipent, threadRunning = true;

	public ClientConnection(String recipentAddress, int recipentPort, boolean listenForRecipent){
		this.recipentAddress = recipentAddress;
		this.recipentPort = recipentPort;
		this.listenForRecipent = listenForRecipent;
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
		this.inbound.start();
		while(this.threadRunning){

		}
	}

	private void createRecipentSocket(){this.recipentSocket = new Socket(recipentAddress, recipentPort);}

	public void doStop(){this.threadRunning = false;}

	private class Outbound{
		private PrintWriter recipentSocketOutput;
	}

	private class Inbound extends Thread{
		private BufferedReader recipentSocketInput;
		private boolean chatting = true;

		public Inbound(){}

		@Override
		public void run(){
			while(chatting){}
		}

		public void doStop(){this.chatting = false;}

		public void listenForRecipentConnection(){
			ServerSocket listenSocket = new ServerSocket(recipentPort);
			System.out.println("Listening for Recipent Connection...");
			recipentSocket = listenSocket.accept();
			System.out.println("Recipent Connected");
			this.recipentSocketInput = (new BufferedReader(new InputStreamReader(recipentSocket.getInputStream())));
		}

		public void setRecipentSocketInput(){this.recipentSocketInput = (new BufferedReader(new InputStreamReader(recipentSocket.getInputStream())));}
	}
}
