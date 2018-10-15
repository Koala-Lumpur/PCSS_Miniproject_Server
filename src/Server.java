import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;



public class Server {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
		     
		      ServerSocket serverSocket = new ServerSocket(8000);
		     System.out.println("Loan Server started at " + new Date() + '\n');

		     
		     while (true) {
		    	
		
		
		      Socket connectToClient = serverSocket.accept();
		      
		      new Thread (new RunClient(connectToClient)).start();

		     
		      System.out.println("Connected to a client " + " at " + new Date() + '\n');

		     
		    } 
		      
		  }
		    catch(IOException e) {
		      System.err.println(e);
		    }
		  }
}

class RunClient implements Runnable {
	
	private Socket socket; 
	
	public RunClient (Socket socket) {
		this.socket = socket; 
		
	}
	
	public void run () {
		
		
	}
	
}
		
	


