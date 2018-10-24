import java.io.IOException;

public class CommandSystem {

	static void handleCommand(String s) {
		
		switch(s) {
		case "start":
			start();
			break;
		case "close":
			close();
			break;
		case "showplayers":
			showPlayers();
			break;
		case "showteams":
			
			break;
		case "help":
			showCommands();
		default:
			System.out.println("The string entered is not a valid command.");
		}
	}
	
	public static void showCommands() {
		System.out.println("These are the valid server commands. Be aware that it is case-sensitive.");
		System.out.println("showplayers - Shows the players connected to the game, what class they are and their team.");
		System.out.println("help - Shows the commands for the game.");
		System.out.println("start - Starts the game.");
		System.out.println("close - Closes the server.\n");
	}
	
	public static void showPlayers() {
		if(Server.numberOfPlayersOnTeam > 0) {
			System.out.println("\n------Team list------");
			for(int i = 0; i < Server.numberOfPlayersOnTeam; i++) {
				System.out.println(i+1 + " - Player name: " + Server.player.get(i).getPlayerName() 
						+ " - Class: " + Server.player.get(i).getPlayerClass() 
						+ " - " + Server.player.get(i).getPlayerTeam());
			}
		} else {
			System.out.println("No players are connected\n");
		}
	}
	
	
	public static void close() {
		Server.writeMessage("Closing the game!");
		System.exit(0); 
		
	}
	
	public static void start() {
		
		Server.gameStarted = true;
		Server.writeMessage("The game have started");
	}
	
	
}
