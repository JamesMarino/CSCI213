import java.util.Vector;

/**
 * This program will set up a StringWorkshop and recruit builders 
 *   and a printer to work in the StringWorkshop.
 *   
 */
public class CharThreads {

	private static final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static void main( String [] args ) {

		StringWorkshop sWorkshop = new StringWorkshop();

		for ( int i = 0 ; i < 26 ; i++ ) {
			Builder aBuilder = new Builder(sWorkshop,  alphabet.charAt(i) ) ;
			new Thread(aBuilder,  Character.toString(alphabet.charAt(i)) ).start() ;
			

			
		}

		new Thread(new Printer(sWorkshop), "Printer").start();

	}
}

/**
 * This is the place to build and print a string
 * 
 * This version builds a string with characters in random order
 *
 */
class StringWorkshop {

	private Vector<Character> charBuffer =new Vector<Character>();

	private boolean done =false;
	
	public synchronized boolean isDone(){
		return done;
	}
	
	public synchronized void printString(){
		
		
			
		if(charBuffer.size()==26){  // all threads are finished; time to print
			System.out.println( charBuffer ) ;
			done=true;
		}
		
	}

	public synchronized void buildString(char aChar){
		
		System.out.println("Passed in: " + aChar);
		
		if (aChar != 'A') {
			try {
							
				while (charBuffer.isEmpty()
						|| charBuffer.lastElement() + 1 != aChar) {
					wait();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
			
		charBuffer.add(aChar);
		System.out.println("Inserted: " + aChar);
			
		notifyAll();
	}
}	

/**
 * This task class prints the string built in the StringWorkshop
 *
 */
class Printer implements Runnable{

	private final int DELAY = 5;
	
	private StringWorkshop sWorkshop;

	Printer(StringWorkshop sWorkshop){
		this.sWorkshop = sWorkshop;
	}

	public void run() {

		while( ! sWorkshop.isDone()){
			try {
				Thread.sleep( DELAY );
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			sWorkshop.printString();
		}
	}

}

/**
 * This task class brings a character to the StringWorkshop to build
 *
 */
class Builder implements Runnable{

	private final int DELAY_SEED = 20;
	
	private StringWorkshop sWorkshop;
	private char myChar;
	
	Builder(StringWorkshop sWorkshop, char myChar){
		this.sWorkshop = sWorkshop;
		this.myChar = myChar;
	}

	public void run() {

		try {   // introducing some chaos 
			Thread.sleep( (int) (Math.random()*DELAY_SEED) );
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		sWorkshop.buildString(myChar);

	}

}



