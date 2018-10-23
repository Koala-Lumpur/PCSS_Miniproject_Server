import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

class ClientThread implements Runnable {
	
	private Socket socket; 
	String playerName;
	String playerTeam;
	int playerHealth = 15;
	String playerClass = "Warrior";
	int index;
	
	public ClientThread (Socket socket) {
		this.socket = socket; 
	}
	
	public void run () {
		try {
			//Creates the input and output messaging devices
			DataInputStream in = new DataInputStream(socket.getInputStream());
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			
			out.writeInt(Server.clientNoIndex);
			Server.clientNoIndex++;
			
			//Reads the name and class the players entered and chose
			playerName = in.readLine();
			playerClass = in.readLine();

			//Adds that name to a "database"
			Server.player.add(new Player(playerName, playerClass, playerHealth));
			
			//Prints out the name if the player that connected as well as send that information to each player
			Server.writeMessage(Server.player.get(Server.numberOfPlayers-1).getPlayerClass() + " " + 
					Server.player.get(Server.numberOfPlayers-1).getPlayerName() + " has joined the server");
			//Gets and prints the joined teams
			index = in.readInt();
			playerTeam = in.readLine();
			Server.player.get(index-1).setPlayerTeam(playerTeam);
			Server.writeMessage(Server.player.get(index-1).getPlayerName() + 
					 " has joined the server and selected " + playerTeam + " and the " + Server.player.get(index-1).getPlayerClass() + 
					 " class");
			
			Server.numberOfPlayersOnTeam++;
			
			index = in.readInt();
			boolean ready = in.readBoolean();
			Server.player.get(index-1).setPlayerReady(ready);
			Server.writeMessage(Server.player.get(index-1).getPlayerName() + " is ready");
			
			while (Server.gameStarted) {
				
			}
			
			} catch (IOException e) {
		}		
	}
}