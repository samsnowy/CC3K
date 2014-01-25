package character;
import floor.Floor;
import floor.Item;
import floor.Treasure;

public class Player extends Character {
	
	private boolean potionKnowledge[] = new boolean[6];
	private int currentChamber = 0;
	private int defaultHP = 0;
	private char currentTile;
	private String race;
	
	public Player(int x, int y, int hp, int dhp, int atk, int def, float gold, Floor floor, int cc, String Race) {
		super(x, y, hp, atk, def, gold, floor);
		currentChamber = cc;
		defaultHP = dhp;
		currentTile = '.';
		race = Race;
		floor.setTile(x, y, '@');
	}
	
	public boolean getPK(int index) { 
		return potionKnowledge[index]; 
	}
	
	public void knowPK(int index) {
		potionKnowledge[index] = true;
	}
	
	public int getCurrentChamber() {
		return currentChamber;
	}
	
	public void setCurrentChamber(int cc) {
		currentChamber = cc;
	}
	
	public int getdefaultHP() { return defaultHP; }
	
	public int attack(Enemy e) {
		int dmg, temphp;
		dmg = (int) Math.ceil((100.0/(100.0 + e.getDef()))*this.getAtk());
		temphp = e.getHP() - dmg;
		e.setHP(temphp);
		return dmg;
	}
	
	public String getRace() { return race; }
	
	public boolean move(char dir, char action) {
		int tempx = x;
		int tempy = y;
		String direct = null;
		
	    switch (dir){
	      case '7':
	        tempx-=1;
	        tempy-=1;
	        direct = "Northwest";
	        break;
	      case '8':
	        tempx-=1;
	        direct = "North";
	        break;
	      case '9': 
	        tempy+=1;
	        tempx-=1;
	        direct = "Northeast";
	        break;
	      case '4':
	        tempy-=1;
	        direct = "West";
	        break;
	      case '6':
	        tempy+=1;
	        direct = "East";
	        break;
	      case '1':
	        tempy-=1;
	       tempx+=1;
	        direct = "Southwest";
	        break;
	      case '2':
	        tempx+=1;
	        direct = "South";
	        break;
	      case '3':
	        tempx+=1;
	        tempy+=1;
	        direct = "Southeast";
	        break;
	    }
	    
	    
	    if(action == 'm'){

	        // Checks if the player interacts with a stairway.
	        if(floor.getTile(tempx, tempy) == '\\')
	          return true;
	    
	        // Checks if the player wants to move
	        if(floor.getTile(tempx, tempy) == '.' ||
	          floor.getTile(tempx, tempy) == '+' ||
	          floor.getTile(tempx, tempy) == '#') {
	          
	          floor.setTile(x, y, currentTile);
	   
	          currentTile = floor.getTile(tempx, tempy);
	    
	          x = tempx;
	          y = tempy;
	          floor.setTile(x, y, '@');
	          floor.setAction("Player moves " + direct + ".");
	        }
	       
	        //checks if the player is interacting with a wall. 
	        else if(floor.getTile(tempx, tempy) == '-'||
	            floor.getTile(tempx, tempy) == ' ' ||
	            floor.getTile(tempx, tempy) == '|') {
	            floor.setAction("Player is banging a wall with his head.");
	        }
	        //checks if the player is interacting with a pile of gold.
	        else if(floor.getTile(tempx, tempy) == 'G'){
	          // checks which gold pile is interacted on the floor
	          for(int i = 0; i < floor.getNumItems(); i++){
	            if((tempx == floor.getItem(i).getX()) && (tempy == floor.getItem(i).getY())){
	              //checks if the Player can pick the gold or not 
	              if(!(floor.getItem(i).getDragonAlive())){
	                //Player picks the gold up
	                float value = (float)floor.getItem(i).getValue();

	                if(getRace() == "Dwarf")
	                  value *= 2;
	                else if(getRace() == "Orc")
	                  value /= 2;

	                gold += value;
	                //Set the action of Player's turn. 
	                floor.setAction("Player moves " + direct + ".");
	                floor.concatAction(" Player picks up " + (int) value +" gold.");

	                floor.setTile(x,y, currentTile);
	                currentTile = '.';
	    
	  	      
	                floor.getItem(i).setX(1);
	                floor.getItem(i).setY(1);

	  		
	                x = tempx;
	                y = tempy;
	                floor.setTile(x,y, '@');
	              }
		          else
		          {
		            floor.setAction("There's a dragon guarding it!");
		          }	              
	            }
	          }
	        }
	        else{
	          floor.setAction("Something is blocking Player's way.");
	        }
	        //Checks if the player sees a potion.
	        for(int tx = -1; tx < 2; tx++){
	          for(int ty = -1; ty < 2; ty++){
	              if(floor.getTile(x + tx, y + ty) == 'P'){
	                  //Check which potion he sees on the floor
	                  for(int i = 0;i < floor.getNumItems(); i++){
	                      if((x+tx == floor.getItem(i).getX()) && (y+ty == floor.getItem(i).getY())){
	                          Item potion;
	                          potion = floor.getItem(i);
	                          boolean effect = potion.getEffect(); 
	                          String know = "";
	                          
	                          //Checks if the player knows the potion he sees.
	                          switch(potion.getType()) {
	                              case 'h':
	                                  if(effect){
	                                      if(potionKnowledge[0] == true)
	                                          know = "RH";
	                                  }
	                                  else{
	                                      if(potionKnowledge[1] == true)
	                                          know = "PH";
	                                  }
	                                  break;
	                              case 'a':
	                                  if(effect){
	                                      if(potionKnowledge[2] == true)
	                                          know = "BA";
	                                  }
	                                  else{
	                                      if(potionKnowledge[3] == true)
	                                          know = "WA";
	                                  }
	                                  break;
	                              case 'd':
	                                  if(effect){
	                                      if(potionKnowledge[4] == true)
	                                          know = "BD";      
	                                  }
	                                  else{
	                                      if(potionKnowledge[5] == true)
	                                          know = "WD"; 
	                                  }
	                                  break;
	                          }
	                          
	                          //Adds the action of Player seeing a potion
	                          if(know != "")
	                              floor.concatAction(" Player sees a " + know + ".");
	                          else
	                              floor.concatAction(" Player sees a unknown potion.");
	                              
	                      }
	                  }
	              }
	          }
	      }          
	  }
	      //checks if the player is interacting with a potion.
	      if(action == 'u'){
	          if(floor.getTile(tempx, tempy) == 'P'){
	              //checks which potion is interacted on the floor
	              for(int i = 0;i < floor.getNumItems(); i++){
	                  if((tempx == floor.getItem(i).getX()) && (tempy == floor.getItem(i).getY())){
	                      int bonus, result;
	                      //checks if the potion is a positive or negative one
	                      if(floor.getItem(i).getEffect()) { bonus = 1; }
	                      else bonus = -1;
	                      
	                      //calculates the change in attribute
	                      int change = floor.getItem(i).getValue() * bonus;
	                      
	                      //checks if the player is an Elf
	                      //if player is an Elf, change the
	                      //potion effect to a postive one.
	                      if(getRace() == "Elves")
	                          if(bonus == -1)
	                              change *= -1;
	                      
	                      //Checks which attribute the potion affects
	                      switch(floor.getItem(i).getType()) {
	                              //checks if the potion affects health
	                          case 'h':
	                              result = hp + change;
	                              //adds the action.
	                              //changes the player's potion knowledge 
	                              if(bonus==1){
	                                  floor.setAction("You drank a RH.");
	                                  potionKnowledge[0] = true;
	                              }
	                              else{
	                                  floor.setAction("You drank a PH.");
	                                  potionKnowledge[1] = true;
	                              }
	                              //checks if end result of HP exceeds the limit or 
	                              //is beneath 0 and change accordingly.
	                              if(result > defaultHP) {
	                                  hp = defaultHP;
	                                  break;
	                              }
	                              if(result < 0){
	                                  hp = 0;
	                                  break;
	                              }
	                              hp = result;
	                              break;
	                              //checks if the potion affects attack
	                          case 'a':
	                              result = atk + change;
	                              //adds the action and
	                              //changes the player's potion knowledge 
	                              if(bonus==1){
	                                  floor.setAction("You drank a BA.");
	                                  potionKnowledge[2] = true;
	                              }
	                              else{
	                                  floor.setAction("You drank a WA.");
	                                  potionKnowledge[3] = true;
	                              }
	                              //checks if the end result is below 0,
	                              //and changes accordingly
	                              if(result < 0) {
	                                  atk = 0;
	                                  break;
	                              }
	                              atk = result;
	                              break;
	                              //checks if the potion affects defense
	                          case 'd':
	                              result = def + change;
	                              //adds the action and changes
	                              // the player's potion knowledge
	                              if(bonus==1){
	                                  floor.setAction("You drank a BD.");
	                                  potionKnowledge[4] = true;
	                              }
	                              else{
	                                  floor.setAction("You drank a WD.");
	                                  potionKnowledge[5] = true;
	                              }  
	                              //checks if the end result is below 0,
	                              //and changes accordingly
	                              if(result < 0) {
	                                  def = 0;
	                                  break;
	                              }
	                              def = result;
	                              break;
	                      }
	                      //update's the potion's tile to a floor tile
	                      
	          	    floor.getItem(i).setX(1);
	  	            floor.getItem(i).setY(1);
	  	            floor.setTile(tempx, tempy, '.');
	                  }
	              }
	          }
	          else{
	               floor.setAction("Player sees nothing to use at that direction.");
	          }
	              
	       }
	      if(action == 'a'){
	          char enemyRace = floor.getTile(tempx, tempy);
	          if(enemyRace == 'V' || enemyRace == 'W' ||
	             enemyRace == 'T' || enemyRace == 'N' ||
	             enemyRace == 'M' || enemyRace == 'D' || enemyRace == 'X'){
	             
	              //checks which enemy is interacted
	              for(int i = 0; i < floor.getNumEnemies(); i++){
	                  if((tempx == floor.getEnemy(i).getX()) && (tempy == floor.getEnemy(i).getY())){
	                      Enemy e = floor.getEnemy(i);
	                      //calculates the damage and the enemy's remaining HP
	                      int dmg = attack(e);
	                      int enemyHP = floor.getEnemy(i).getHP();
	                      
	                      //set the action that the player did in this turn.
	                      floor.setAction("Player deals " + dmg + " dmg to " + floor.getTile(tempx, tempy) + "("+ enemyHP +").");
	                      //checks if the player is attacking a dragon, if so, 
	                      //change the dragon's hostility
	                      if(floor.getTile(tempx, tempy) == 'D')
	                          e.setHostile(true);
	                      //checks if the player is attacking a merchant, if so,
	                      // change every merchant's hostility
	                      if(floor.getTile(tempx, tempy) == 'M')
	                      {
	                          for(int k = 0; k < floor.getNumEnemies(); k++) {
	                              if(floor.getEnemy(k).getAtk() == 70)
	                                  floor.getEnemy(k).setHostile(true);
	                          }
	                          floor.setHostileMerchant(true);
	                      } 
	                      //checks if the enemy is dead after attack
	                      if(e.getHP()<1){
	                          // add the action of the kill
	                          floor.concatAction(" Player kills " + enemyRace  + ".");
	                          // remove the enemy from the floor
	                          floor.setTile(tempx, tempy, '.');
	                          e.setCoord(1,1);
	                       
	                          // if a dragon died, changes the dragon hoard availability
	                          if(enemyRace == 'D'){
	                              int tx = e.getHoardX();
	                              int ty = e.getHoardY();
	                              for(int n = 0;n < floor.getNumItems(); n++){
	                                  if((tx == floor.getItem(n).getX()) && (ty == floor.getItem(n).getY())){
	                                      floor.getItem(n).setDragonDead();
	                                  }
	                              }
	                          }
	                          // if a merchant die, a merchant hoard will spawn at the same spot.
	                          else if(enemyRace == 'M'){
	                              Item t = new Treasure(tempx, tempy, 4, floor, false);
	                              floor.addItem(t);
	                              floor.setTile(tempx,tempy,'G');
	                          }
	                          // add one gold if anything else dies
	                          else {
	                            float loot = 1;
	  	                  //Apply gold modifier for different races
	                            if(getRace() == "Dwarf")
	                            {
	                            	loot *= 2;
	                            }
	                            else if(getRace() == "Orc")
	                            {
	                            	loot /= 2;
	                            }
	                            gold += loot;
	                          }
	                             
	                      }
	                  }
	              }

	          }
	          else{
	            floor.setAction("Player sees nothing to attack at that direction.");
	          }
	      }
	      //returns false if the player has not interact with a stairway
	      return false;
		
	}
	

}
