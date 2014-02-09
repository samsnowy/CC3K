package floor;
import character.*;

import java.util.Random;

class Chamber {
	int chamberID;
	Floor floor;

	int width, height;
	int floorX, floorY;
	boolean[][] available;
	
	class Coord {
		int x, y;
		Coord(int X, int Y) {
			x = X;
			y = Y;
		}
	}
	
	public Coord randomCoord() {
		Random random = new Random();
		int x = random.nextInt(height);
		int y = random.nextInt(width);

		while (!available[x][y]) {
			x = random.nextInt(height);
			y = random.nextInt(width);
		}

		available[x][y] = false;

		Coord tmp = new Coord(x, y);
		return tmp;
	}

	public Chamber(int chamberID, Floor floor) {
		this.chamberID = chamberID;
		this.floor = floor;

		switch(chamberID) {
		case 0:
			width = 26;
			height = 4;
			floorX = 3;
			floorY = 3;
			break;
		case 1:
			width = 37;
			height = 10;
			floorX = 3;
			floorY = 39;
			break;
		case 2:
			width = 12;
			height = 3;
			floorX = 10;
			floorY = 38;
			break;
		case 3:
			width = 21;
			height = 7;
			floorX = 15;
			floorY = 4;
			break;
		case 4:
			width = 39;
			height = 6;
			floorX = 16;
			floorY = 37;
			break;
		}
		
		//initialize the boolean 2D array
		available = new boolean[height][width];
		
		for(int i=0; i<height; i++) {
			for(int j=0; j<width; j++) {
				available[i][j] = true;
			}
		}
		
		switch(chamberID) {
		case 1:
			for(int i=0; i<2; i++)
				for(int j=23; j<37; j++)
					available[i][j] = false;

			for(int i=31; i<37; i++)
				available[2][i] = false;

			for(int i=34; i<37; i++)
				available[3][i] = false;

			for(int i=4; i<10; i++)
				for(int j=0; j<22; j++)
					available[i][j] = false;
			break;
			
		case 4:
			for(int i=0; i<3; i++)
				for(int j=0; j<28; j++)
					available[i][j] = false;
			break;
		}
	}
		
	//randomly generate a player in the current chamber
	public void playerGenerator(char race) {
		//find a available coordinate
		Coord random = randomCoord();

		//create the character with the given race
		switch(race) {
		case 'h':
			floor.player = new Human(random.x + floorX,
					random.y + floorY,
					floor, chamberID);
			break;
		case 'e':
			floor.player = new Elves(random.x + floorX,
					random.y + floorY,
					floor, chamberID);
			break;
		case 'd':
			floor.player = new Dwarf(random.x + floorX,
					random.y + floorY,
					floor, chamberID);
			break;
		case 'o':
			floor.player = new Orc(random.x + floorX, 
					random.y + floorY, 
					floor, chamberID);
			break;
		}
	}
	
	public void stairwayGenerator() {
		//find a available coordinate
		Coord random = randomCoord();

		//set the stairway's tile to back slash
		floor.setTile(random.x + floorX, random.y + floorY, '\\');
	}
	
	public void enemyGenerator(boolean hostileMerchant) {
		  //find a available coordinate
		  Coord random = randomCoord();

		  Enemy e;

		  //generate a enemy with a random type
		  //based on the following probability:
		  // * Werewolf: 2/9
		  // * Vampire: 3/18
		  // * Goblin: 5/18
		  // * Troll: 1/9
		  // * Phoenix: 1/9
		  // * Merchant: 1/9
		  Random rand = new Random();
		  int type = rand.nextInt(18);
		  
		  if(type < 4)
		    e = new Werewolf(random.x + floorX, 
				     random.y + floorY, 
				     floor);
		  else if(type < 7)
		    e = new Vampire(random.x + floorX, 
				    random.y + floorY, 
				    floor);
		  else if(type < 12)
		    e = new Goblin(random.x + floorX,
				   random.y + floorY,
				   floor);
		  else if(type < 14)
		    e = new Troll(random.x + floorX,
				  random.y + floorY,
				  floor);
		  else if(type < 16)
		    e = new Phoenix(random.x + floorX,
				    random.y + floorY,
				    floor);
		  else
		    e = new Merchant(random.x + floorX,
				     random.y + floorY,
				     floor, hostileMerchant);
		  
		  //assign the enemy
		  floor.enemies[floor.numEnemies++] = e;
		}
	
	public void itemGenerator(boolean isPotion) {
		//find a available coordinate
		Coord random = randomCoord();
		Random ran = new Random();

		Item i = null;

		//create one of the following potions with equal possibility 
		// * restore HP up to 10
		// * increase ATK by 5
		// * increase DEF by 5
		// * lose HP up to 10
		// * decrease ATK by 5
		// * decrease DEF by 5
		if(isPotion) {
			int type = ran.nextInt(6);
			switch(type) {
			case 0:
				i = new Potion(random.x + floorX,
						random.y + floorY,
						10, floor, true, 'h');
				break;
			case 1:
				i = new Potion(random.x + floorX,
						random.y + floorY,
						10, floor, false, 'h');
				break;
			case 2:
				i = new Potion(random.x + floorX,
						random.y + floorY,
						5, floor, true, 'a');
				break;
			case 3:
				i = new Potion(random.x + floorX,
						random.y + floorY,
						5, floor, false, 'a');
				break;
			case 4:
				i = new Potion(random.x + floorX,
						random.y + floorY,
						5, floor, true, 'd');
				break;
			case 5:
				i = new Potion(random.x + floorX,
						random.y + floorY,
						5, floor, false, 'd');
				break;
			}

			//create a pile of treasure with the following probability
			// * normal: 5/8
			// * small hoard: 1/4
			// * dragon hoard: 1/8
		} else {
			int type = ran.nextInt(8);

			if(type < 5)
				i = new Treasure(random.x + floorX,
						random.y + floorY,
						1, floor, false);
			else if(type < 7)
				i = new Treasure(random.x + floorX,
						random.y + floorY,
						2, floor, false);
			else {      
				//check if the randomly generated coordinate
				//is surrounded by 8 other unavailable tiles
				boolean availableTiles = false;

				while(!availableTiles) {
					for(int j = -1; j < 2; j++) {
						for(int k = -1; k < 2; k++) {
							if(j == 0 && k == 0) continue;

							int tempX = random.x + j;
							int tempY = random.y + k;

							if(tempX >= 0 && tempY >= 0 &&
									tempX < height && tempY < width &&
									available[tempX][tempY]) {
								availableTiles = true;
								break;
							}
						}

						if(availableTiles) break;
					}

					if(availableTiles) break;

					//set the availability of the 
					//unqualified tile back to true
					available[random.x][random.y] = true;

					//get a new available tile
					random = randomCoord();	
				}

				//generate dragon hoard
				i = new Treasure(random.x + floorX,
						random.y + floorY,
						6, floor, true);

				//generate dragon
				Random k = new Random();
				int dragonX = random.x + k.nextInt(3) - 1;
				int dragonY = random.y + k.nextInt(3) - 1;

				while(dragonX < 0 ||
						dragonY < 0 ||
						dragonX >= height ||
						dragonY >= width ||
						(dragonX == random.x && dragonY == random.y) ||
						!available[dragonX][dragonY]) {
					dragonX = random.x + k.nextInt(3) - 1;
					dragonY = random.y + k.nextInt(3) - 1;
				}

				Enemy d = new Dragon(dragonX + floorX,
						dragonY + floorY, 
						floor,
						random.x + floorX,
						random.y + floorY);

				//assign the dragon
				floor.enemies[floor.numEnemies++] = d;

				//set the dragon's coordinate to unavailable
				available[dragonX][dragonY] = false;
			}
		}

		//assign the item
		floor.items[floor.numItems] = i;
		floor.numItems++;
	}

}
