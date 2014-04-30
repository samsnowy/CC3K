package floor;
import character.*;

public class Floor {
	int floorID;
	Player player;
	boolean hostileMerchant;
	int numEnemies, numItems;
	String action;

	char[][] layout = new char[25][79];
	Chamber[] chambers = new Chamber[5];
	Enemy[] enemies = new Enemy[30];
	Item[] items = new Item[40];

	public Floor(int floorID, boolean hostileMerchant) {
		this.floorID = floorID;
		this.hostileMerchant = hostileMerchant;
		numEnemies = 0;
		numItems = 0;

		//initialize chambers
		for(int i = 0; i < 5; i++) {
			chambers[i] = new Chamber(i, this);
		}

		//initialize the message
		action = "Player character has spawned.";
	}

	public void playerGenerator(char race) {
		int index = (int) (Math.random() * 5);
		chambers[index].playerGenerator(race);
	}

	public void contentGenerator() {
		int random = player.getCurrentChamber();

		//generate stairway
		while(random == player.getCurrentChamber()) {
			random = (int) (Math.random() * 5);
		}
		chambers[random].stairwayGenerator();

		//generate potions and gold
		//10 potions are spawned on every floor
		//10 piles of gold are spawned on every floor

		//generate potions                                             
		//1/5 chance to spawn a potion in any chamber
		int tmp, tmp2, tmp3;
		for(int i = 0; i < 10; i++) {
			tmp = (int) (Math.random() * 5);
			chambers[tmp].itemGenerator(true);
		}

		//generate gold                                                
		//1/5 chance to spawn a pile of gold in any chamber
		for(int i = 0; i < 10; i++) {
			tmp2 = (int) (Math.random() * 5);
			chambers[tmp2].itemGenerator(false);
		}

		//generate enemies
		//20 enemies are spawned every floor
		//1/5 chance to spawn a enemy in any chamber
		for(int i=0; i<20; i++) {
			tmp3 = (int) (Math.random() * 5);
			chambers[tmp3].enemyGenerator(hostileMerchant);
		}
	}
	
	public void printFloor() {
		for(int i = 0; i < 25; i++) {
			for(int j = 0; j < 79; j++)
				System.out.print(layout[i][j]);
				System.out.println();
		}
		System.out.println();
				
		System.out.printf("Race: " + player.getRace());
		System.out.printf(" Gold " + (int) player.getGold());
		System.out.println("\t\t\t\t\t\t      Floor " + (floorID + 1));
		System.out.println("HP: " + player.getHP());
		System.out.println("Atk: " + player.getAtk());
		System.out.println("Def: " + player.getDef());
		System.out.println("Action: " + action);
		System.out.println("Job: " + player.getJob());

	}
	
	//return the content of tile with the given coordinate
	public char getTile(int x, int y) {
		return layout[x][y];
	}
	
	//set the given character to the given coordinate
	public void setTile(int x, int y, char tile) {
	  layout[x][y] = tile;
	}

	//hostileMerchant setter
	public void setHostileMerchant(boolean hostileMerchant) {
	  this.hostileMerchant = hostileMerchant;
	}

	//hostileMerchant getter
	public boolean getHostileMerchant() {
	  return hostileMerchant;
	}

	//numEnemies getter
	public int getNumEnemies() {
	  return numEnemies;
	}

	//numItems getter
	public int getNumItems() {
	  return numItems;
	}

	//player getter
	public Player getPlayer() {
	  return player;
	}

	//add a new player
	public void addPlayer(Player p) {
	  player = p;
	}

	//add a new item into the item array
	public void addItem(Item i) {
	  items[numItems++] = i;
	}

	//add a new enemy into the enemy array
	public void addEnemy(Enemy e) {
	  enemies[numEnemies++] = e;
	}

	//return the enemy based on the given enemy ID
	public Enemy getEnemy(int enemyID) {
	  return enemies[enemyID];
	}

	//return the item based on the given item ID
	public Item getItem(int itemID) {
	  return items[itemID];
	}

	//action setter
	public void setAction(String action) {
	  this.action = action;
	}

	//concatenate the new action to the end
	//of the current action
	public void concatAction(String action) {
	  this.action += action;
	}
	
}

