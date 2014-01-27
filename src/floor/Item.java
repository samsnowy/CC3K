package floor;

public class Item {
	protected int x, y, value;
	protected Floor floor;
	
	public Item(int x, int y, int value, Floor floor) {
		this.x = x;
		this.y = y;
		this.value = value;
		this.floor = floor;
	}
	
	public int getX() { return x; }
	public int getY() { return y; }
	public int getValue() { return value; }
	
	public void setX(int x) { this.x = x; }
	public void setY(int y) { this.y = y; }
	
	public void setDragonDead() {}
	public boolean getDragonAlive() { return false; }
	public boolean getEffect() { return false; }
	public char getType() { return '0'; }

}
