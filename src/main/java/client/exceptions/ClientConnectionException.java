package client.exceptions;

public class ClientConnectionException extends Exception{
	public ClientConnectionException(String errorMessage, Exception originalException){
		super(errorMessage);
		originalException.printStackTrace();
	}
}
