public class CommandSystem {

	static void handleCommand(String s) {
		
		switch(s) {
		case "start":
			
			break;
		case "close":
			
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
		System.out.println("showplayers - Shows the players connected to the game.");
		System.out.println("start - Starts the game.");
		System.out.println("close - Closes the server.\n");
	}
	
	public static void showPlayers() {
		if(Server.numberOfPlayers > 0) {
			System.out.println("Player list: ");
			for(int i = 0; i < Server.numberOfPlayers; i++) {
				System.out.println(i+1 + " - " + Server.player.get(i).getPlayerName() + "\n");
			}
		} else {
			System.out.println("No players are connected\n");
		}
	}
}
