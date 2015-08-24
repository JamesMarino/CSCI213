package domain;

public class OverdraftException extends Exception {

	private double deficit;
	
	public OverdraftException(String message, double def){
		super(message);	
		this.deficit = def;
	}

	public double getDeficit(){
	return deficit;
	}
}
	

