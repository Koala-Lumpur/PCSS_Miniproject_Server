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
	boolean clientGameStarted = false;
	
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
			playerHealth = in.readInt();

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
			
			try { 
			if(Server.player.get(0).isPlayerReady() && Server.player.get(1).isPlayerReady() &&
					Server.player.get(2).isPlayerReady() && Server.player.get(3).isPlayerReady()) {
				Server.gameStarted = true; 
				Server.writeMessage("Game is starting!");
			} 
			} catch (IndexOutOfBoundsException e) {
				
			}
			
			while (Server.gameStarted) {
				if(!clientGameStarted) {
					for(int i = 5; i > 0; i-- ) {
						Server.writeMessage("Game is starting in... " + i);
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {}
					}
					clientGameStarted = true;
					}
					
					for(int i = 0; i < 4; i++) {
						Server.writeMessage(Server.player.get(i).getPlayerName());
						Server.writeMessage(Server.player.get(i).getPlayerClass());
						Server.writeMessage( Server.player.get(i).getPlayerTeam());
						Server.sendPlayerInfo(Server.player.get(i).getPlayerHealth());
					}
					
				}
				
			
			
			} catch (IOException e) {
		}		
	}
}