package character;
import floor.Floor;

public class Enemy extends Character {
	protected boolean hostile;

	public Enemy(int x, int y, int hp, int atk, int def, int gold, Floor floor, boolean hostile) {
		super(x, y, hp, atk, def, gold, floor);
		this.hostile = hostile;
	}
	
	public boolean getHostile() {return hostile;}
	public void setHostile(boolean hostile) {this.hostile = hostile;}
	public int getHoardX() { return 0; }
	public int getHoardY() { return 0; }

	public void setHoardX(int x){}
	public void setHoardY(int y){}

	public int attack(Player p){
		int hit = (int) (Math.random() * 2);
		if(hit == 0){
			int dmg, temphp;
			dmg = (int) Math.ceil((100.0/(100.0 + p.getDef())) * atk);
			temphp = p.getHP() - dmg;
			p.setHP(temphp);
			return dmg;
		}
		else
			return 0;
	}
	
	public void move(){
		  boolean availableMove = false;
		  boolean availablePlayer = false;

		  for(int tx = -1; tx < 2; tx++){
		    for(int ty = -1; ty < 2; ty++){
		      if(floor.getTile(x + tx, y + ty) == '@')
		        availablePlayer = true;
		      if(floor.getTile(x + tx, y + ty) == '.')
		        availableMove = true;
		    }
		  } 
		  
		  boolean moved = false;
		  if(hostile){
		    if(availablePlayer){
		      int dmg = attack(floor.getPlayer());
		      char enemyRace = floor.getTile(x,y); 
		  
		      if(dmg > 0)
		        floor.concatAction(" " + enemyRace + " deals " + dmg + " dmg to Player.");
		      else
		        floor.concatAction(" " + enemyRace + " misses the Player.");
		      moved = true;
		    }
		  }   
		    
		  while(!moved && availableMove) {
		    int tempx = x;
		    int tempy = y;
		    int randomX = -1 + (int) (Math.random() * 3);
		    int randomY = -1 + (int) (Math.random() * 3);
		     
		    tempx += randomX;
		    tempy += randomY;
		    
		    char race = floor.getTile(x, y);
		    if(floor.getTile(tempx, tempy) == '.'){
		      floor.setTile(x, y, '.');
		      x = tempx;
		      y = tempy;
		      moved = true;
		      floor.setTile(x, y, race);
		      break;
		    }
		  }
	}
	
}

