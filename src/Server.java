import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;
import java.util.ArrayList;

public class Server implements Runnable {
	
	//ArrayList of sockets which keeps track of the clients connected
	public static ArrayList<Socket> clients = new ArrayList<Socket>();
	//ArrayList of players which has important info about each player
	public static ArrayList<Player> player = new ArrayList<Player>();
	public static ServerSocket serverSocket;
	public static int numberOfPlayers = 0;
	public static DataOutputStream out;
	public Scanner input = new Scanner(System.in);
	public Thread thread;
	public String threadName;
	
	public Server(String name) {
		threadName = name;
	}

	public static void main(String[] args) {
		
		//Creates and starts the server, then calls the connect method 
		try {
			serverSocket = new ServerSocket(8000);
			System.out.println("Server started at " + new Date() + "\n");
			Server commandThread = new Server("Command_Thread");
			commandThread.start();
			CommandSystem.showCommands();
			while (true) {
				connect();
			}
		}catch(IOException e) {
		      System.err.println(e);
		      }
		}
	
	public void run() {
		while(true) {
			CommandSystem.handleCommand(input.nextLine());
		}
	}
	
	public void start() {
		if(thread == null) {
			thread = new Thread(this, threadName);
			thread.start();
		}
	}

	//Adds a client to the existing clients ArrayList 
	public synchronized static void addClient(Socket client) {
		clients.add(Server.numberOfPlayers, client);
		numberOfPlayers++;
	}
	
	//Waits for connection and creates a thread for each
	public synchronized static void connect() throws IOException {
		Socket connectToClient = serverSocket.accept();
		addClient(connectToClient);
		new Thread (new RunClient(connectToClient)).start();
	}
	
	//Writes a message to each client that has connected to the server
	public static void writeMessage(String message) throws IOException {
		for(int i = 0; i < numberOfPlayers; i++) {
			Socket output = clients.get(i);
			out = new DataOutputStream(output.getOutputStream());
			out.writeBytes(message+"\n");
			
		}
	}
}

class RunClient implements Runnable {
	
	private Socket socket; 
	String playerName;
	int playerHealth = 15;
	String playerClass = "Warrior";
	
	public RunClient (Socket socket) {
		this.socket = socket; 
	}
	
	public void run () {
		try {
			//Creates the input and output messaging devices
			DataInputStream in = new DataInputStream(socket.getInputStream());
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			
			//Reads the name the players entered
			playerName = in.readLine();
			//Adds that name to a "database"
			Server.player.add(new Player(playerName, playerClass, playerHealth));
			
			//Prints out the name if the player that connected as well as send that information to each player
			System.out.println("Player " + Server.player.get(Server.numberOfPlayers-1).getPlayerName() + " has joined the server");
			Server.writeMessage("Player " + Server.player.get(Server.numberOfPlayers-1).getPlayerName() + " has joined the server");
			
		} catch (IOException e) {}
		
	}
}
		
	


