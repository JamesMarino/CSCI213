public class ThreadedMessage {
	
	public static void main(String[] args) {

		Messenger messenger = new Messenger(" Hello ");
		Thread thread = new Thread(messenger);
		thread.start();

		try {
			int random = (int) (Math.random()*200);
			//System.out.println("Main Thread Wait: " + random);
			Thread.sleep(random);
			//Thread.sleep((int) (500)); 
			
			thread.join(100);
			
			// Test if is alive
			if (thread.isAlive()) {
				
				// Kill the thread
				thread.interrupt();
				
				// Print out hi
				System.out.print(" Hi ");
			}
			
		} catch (InterruptedException e) {}
		
		System.out.print(" Threads ");
	}
}

class Messenger implements Runnable {

	String message;
	
	Messenger(String message){
		this.message=message;
	}
	
	public void run() {
		try {
			int random = (int) (Math.random()*200);
			//System.out.println("In Thread Wait: " + random);
			Thread.sleep(random);
			//Thread.sleep((int) (200)); 
		} catch (InterruptedException e) {
			return;
		}
		System.out.print(message);
	}
	
}
