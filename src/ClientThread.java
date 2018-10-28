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
	boolean clientGameStarted;
	boolean playerInfoSent; 

	//Constructor
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

			//Gets and prints the joined teams
			index = in.readInt();
			playerTeam = in.readLine();
			Server.player.get(index-1).setPlayerTeam(playerTeam);
			Server.writeMessage(Server.player.get(index-1).getPlayerName() + 
					" has joined the server and selected " + playerTeam + " and the " + Server.player.get(index-1).getPlayerClass() + 
					" class");

			Server.numberOfPlayersOnTeam++;
			
			//Read the player's index number and set their status to ready, if received a ready status
			index = in.readInt();
			boolean ready = in.readBoolean();
			Server.player.get(index-1).setPlayerReady(ready);
			Server.writeMessage(Server.player.get(index-1).getPlayerName() + " is ready");

			//Checks if each players is ready, and if so starts the game
			try { 
				if(Server.player.get(0).isPlayerReady() && Server.player.get(1).isPlayerReady() &&
						Server.player.get(2).isPlayerReady() && Server.player.get(3).isPlayerReady()) {
					Server.gameStarted = true; 
					Server.writeMessage("Game is starting!");
					for(int i = 0; i < 4; i++) {
						Server.player.get(i).setPlayerReady(false);
					}
				} 
			} catch (IndexOutOfBoundsException e) {

			}

			
			while (Server.gameStarted) {
				//Countdown from 5 before the game actually starts
				if(!clientGameStarted) {
					for(int i = 5; i > 0; i-- ) {
						Server.writeMessage("Game is starting in... " + i);
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {}
					}
					clientGameStarted = true;
				}

				//Sends player information to each client
				if(!playerInfoSent) {	

					for(int i = 0; i < 4; i++) {
						Server.writeMessage(Server.player.get(i).getPlayerName());
						Server.writeMessage(Server.player.get(i).getPlayerClass());
						Server.writeMessage( Server.player.get(i).getPlayerTeam());
						Server.sendPlayerInfo(Server.player.get(i).getPlayerHealth());
					}
					playerInfoSent = true;  	
				}
			}
			
			//Gets damage information from clients, calculates it and sends information back to the clients
			String dmgMsg1 = " ";
			String dmgMsg2 = " ";
			String dmgMsg3 = " ";
			String dmgMsg4 = " ";
			int playerTarget1 = in.readInt();
			System.out.println("Target is : " + playerTarget1);
			int damageDealt1 = in.readInt();
			System.out.println("Damage is : " + damageDealt1);
			index = in.readInt();
			System.out.println("Index is: " + index);
			Server.player.get(index-1).setPlayerReady(in.readBoolean());
			Server.player.get(playerTarget1).setPlayerHealth(Server.player.get(playerTarget1).getPlayerHealth() + damageDealt1);
			System.out.println("First it");
			
			//Checks which player sent information, and sets them to ready
			if(index == 1) {
				dmgMsg1 = Server.player.get(playerTarget1).getPlayerName() + " has taken " + damageDealt1 + " damage";
				System.out.println(dmgMsg1);
				System.out.println("Player 1 is ready?" + Server.player.get(0).isPlayerReady());
			} else if (index == 2) {
				dmgMsg2 = Server.player.get(playerTarget1).getPlayerName() + " has taken " + damageDealt1 + " damage";
				System.out.println(dmgMsg2);
				System.out.println("Player 2 is ready?" + Server.player.get(1).isPlayerReady());
			} else if (index == 3) {
				dmgMsg3 = Server.player.get(playerTarget1).getPlayerName() + " has taken " + damageDealt1 + " damage";
				System.out.println(dmgMsg3);
				System.out.println("Player 3 is ready?" + Server.player.get(2).isPlayerReady());
			} else if (index == 4) {
				dmgMsg4 = Server.player.get(playerTarget1).getPlayerName() + " has taken " + damageDealt1 + " damage";
				System.out.println(dmgMsg4);
				System.out.println("Player 4 is ready?" + Server.player.get(3).isPlayerReady());
			}

			//If everyone is ready, send damage messages to each client
			if(Server.player.get(0).isPlayerReady() && Server.player.get(1).isPlayerReady() &&
					Server.player.get(2).isPlayerReady() && Server.player.get(3).isPlayerReady()) {
				Server.writeMessage(dmgMsg1);
				Server.writeMessage(dmgMsg2);
				Server.writeMessage(dmgMsg3);
				Server.writeMessage(dmgMsg4);
				for(int i = 0; i < 4; i++) {
					Server.player.get(i).setPlayerReady(false);
				}
			}
		} catch (IOException e) {
		}		
	}
}