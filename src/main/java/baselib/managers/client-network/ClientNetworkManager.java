public class ClientNetworkManager{
	public static void main(String[] args){
		ClientConnection client = new ClientConnection("0","localhost",9806,"0");
		try{
			client.requestServerConnection();
		}catch(Exception e){System.out.println("Error occured when attempting to request a server connection:\n\n"+e);}
		
		client.sendMessage("This is a test to see if data gets sent");
	}
}
