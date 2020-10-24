package br.com.santander.icheffv1.model;

import java.util.ArrayList;
import java.util.List;

public class Message {
	private String message = "";
	private List<Usuario> usuarios = new ArrayList<Usuario>();
	private String error = "";
	
	public Message(String message, List<Usuario> usuarios, String error) {
		this.message = message;
		this.usuarios = usuarios;
		this.error = error;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public List<Usuario> getCustomers(){
		return this.usuarios;
	}
	
	public void setCustomers(ArrayList<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	public void setError(String error) {
		this.error = error;
	}
	
	public String getError() {
		return this.error;
	}
}