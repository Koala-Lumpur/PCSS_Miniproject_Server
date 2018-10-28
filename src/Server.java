import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;
import java.util.ArrayList;

//*********************************************************************************
//
//Project 		: Programming of complex software system mini-project
//
//Authors 		: Rasmus Eske Waage Nielsen (Nikoline Erreo Petersen and Nikita Hansen)
//
//Date created	: 23/10/2018
//
//Purpose		: A server to handle multiple clients joining and send/receive
//				  to/from the clients
//
//*********************************************************************************

public class Server implements Runnable {

	//ArrayList of sockets which keeps track of the clients connected
	public static ArrayList<Socket> clients = new ArrayList<Socket>();
	//ArrayList of players which has important info about each player
	public static ArrayList<Player> player = new ArrayList<Player>();
	public static ServerSocket serverSocket;
	public static int numberOfPlayers = 0;
	public static int numberOfPlayersOnTeam = 0;
	public static DataOutputStream out;
	public Scanner input = new Scanner(System.in);
	public Thread thread;
	public String threadName;
	public static int clientNoIndex = 1;
	public static boolean gameStarted;

	//Constructor
	public Server(String name) {
		threadName = name;
	}

	public static void main(String[] args) {

		//Creates and starts the server, then calls the connect method. Also creates the command thread
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
	
	//This thread handles commands, this is always running so you are able to enter commands at any time
	public void run() {
		while(true) {
			CommandSystem.handleCommand(input.nextLine());
		}
	}
	
	//Method creates a new thread
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
		new Thread (new ClientThread(connectToClient)).start();
	}

	//Writes a message to each client that has connected to the server
	public static void writeMessage(String message)  {
		System.out.println(message);
		try {
			for(int i = 0; i < numberOfPlayers; i++) {
				Socket output = clients.get(i);
				out = new DataOutputStream(output.getOutputStream());
				out.writeBytes(message+"\n");
			}
		}
		catch(IOException E) {
		}
	}

	//Writes an integer to each client
	public static void sendPlayerInfo(int message) {
		try {
			for(int i = 0; i < numberOfPlayers; i++) {
				Socket output = clients.get(i);
				out = new DataOutputStream(output.getOutputStream());
				out.writeInt(message);
			}
		}
		catch(IOException E) {
		}
	}
}