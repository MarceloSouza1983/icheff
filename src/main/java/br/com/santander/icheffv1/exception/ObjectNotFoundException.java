package br.com.santander.icheffv1.exception;

public class ObjectNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ObjectNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ObjectNotFoundException(String msg) {
		super(msg);
	}
	
}