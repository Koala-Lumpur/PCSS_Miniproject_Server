import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.ArrayList;

public class Server {
	
	static ArrayList<Player> player = new ArrayList<Player>();
	static int numberOfPlayers = 0;

	public static void main(String[] args) {
		
		try {
			ServerSocket serverSocket = new ServerSocket(8000);
			System.out.println("Server started at " + new Date());
			while (true) {
				Socket connectToClient = serverSocket.accept();
				new Thread (new RunClient(connectToClient)).start();
				
				
			}
		}catch(IOException e) {
		      System.err.println(e);
		      }
		}
}

class RunClient implements Runnable {
	
	private Socket socket; 
	Server server = new Server();
	
	public RunClient (Socket socket) {
		this.socket = socket; 
	}
	
	public void run () {
		try {
			DataInputStream in = new DataInputStream(socket.getInputStream());
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			
			String playerName = in.readLine();
			Server.player.add(new Player(playerName));
			System.out.println("Player " + Server.player.get(Server.numberOfPlayers).getPlayerName() + " has joined the server");
		} catch (IOException e) {}
		
	}
	
}
		
	


