package character;
import floor.Floor;

public class Dragon extends Enemy {
	private int hoardX;
	private int hoardY;
	private boolean angry;

	public Dragon(int x, int y, Floor f, int hoardX, int hoardY) {
		super(x, y, 150, 20, 20, 0, f, false);
		this.hoardX = hoardX;
		this.hoardY = hoardY;
		angry = false;
		f.setTile(x, y, 'D');
	}

	public int getHoardX() { return hoardX; }
	public int getHoardY() { return hoardY; }
	public void setHoardX(int x) { hoardX = x; }
	public void setHoardY(int y) { hoardX = y; }
	
	public void move() {
		  boolean HoardDanger = false;
		  for(int tx = -1; tx < 2; tx++){
		    for(int ty = -1; ty < 2; ty++){
		      if(floor.getTile(hoardX + tx, hoardY + ty) == '@')
		        HoardDanger = true;
		    }
		  }
		  angry = HoardDanger;

		  boolean availablePlayer = false;

		  for(int tx = -1; tx < 2; tx++){
		    for(int ty = -1; ty < 2; ty++){
		      if(floor.getTile(x + tx, y + ty) == '@')
		        availablePlayer = true;
		    }
		  }
		  
		  if(hostile || angry){
		    if(availablePlayer){
		      int dmg = super.attack(floor.getPlayer());
		      if(dmg > 0)
		        floor.concatAction(" " + 'D' + " deals " + dmg + " dmg to Player.");
		      else
		        floor.concatAction(" " + 'D' + " misses the Player.");
		    }
		  }
		}
	
}
