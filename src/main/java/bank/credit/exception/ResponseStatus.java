package bank.credit.exception;

public class ResponseStatus extends RuntimeException{
	
	public ResponseStatus(String message)
	{
	  super(message);
	}
	
	public ResponseStatus(String message, Throwable cause)
	{
	  super(message, cause);
	}
	
}
