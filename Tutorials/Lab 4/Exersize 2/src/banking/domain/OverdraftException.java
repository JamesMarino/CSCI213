package banking.domain;

public class OverdraftException extends Exception {

	private double deficit;
	
	public OverdraftException(String mes, double def){
		super(mes);	
		this.deficit = def;
	}

	public double getDeficit(){
	return deficit;
	}
}
	

