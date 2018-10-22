public class Player {

	private String playerName;
	private String playerClass;
	private int currentHealth;
	private String playerTeam;
	private boolean playerReady = false;
	
	public Player(String playerName, String playerClass, int currentHealth) {
		this.playerName = playerName;
		this.playerClass = playerClass;
		this.currentHealth = currentHealth;
	}
	
	public void setPLayerName(String s) {
		playerName = s;
	}
	
	public String getPlayerName() {
		return playerName;
	}
	
	public void setPlayerHealth(int i) {
		currentHealth = i;
	}
	
	public int getPLayerHealth() {
		return currentHealth;
	}
	
	public void setPlayerClass(String playerClass) {
		this.playerClass = playerClass;
	}

	public String getPlayerClass() {
		return playerClass;
	}
	
	public void setPlayerTeam(String s) {
		playerTeam = s;
	}
	
	public String getPLayerTeam() {
		return playerTeam;
	}

	public boolean isPlayerReady() {
		return playerReady;
	}

	public void setPlayerReady(boolean playerReady) {
		this.playerReady = playerReady;
	}
}
