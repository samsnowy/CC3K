package floor;

public class Treasure extends Item {
	private boolean dragonAlive;

	public Treasure(int x, int y, int value, Floor floor, boolean dragonAlive) {
		super(x, y, value, floor);
		this.dragonAlive = dragonAlive;
		floor.setTile(x, y, 'G');
	}
	
	public void setDragonDead() { dragonAlive = false; }
	public boolean getDragonAlive() { return dragonAlive; }

}
