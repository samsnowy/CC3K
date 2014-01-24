package character;
import floor.Floor;

class Character {
	protected int x, y, hp, atk, def;
	protected float gold;
	protected Floor floor;
	
	public Character(int x, int y, int hp, int atk, int def, float gold, Floor floor) {
		this.x = x;
		this.y = y;
		this.hp = hp;
		this.atk = atk;
		this.def = def;
		this.gold = gold;
		this.floor = floor;
	}
	
	public int getX() { return x;}
	public int getY() { return y;}
	public int getHP() { return hp; }
	public int getAtk() { return atk; }
	public int getDef() { return def; }
	public float getGold() { return gold; }
	public Floor getFloor() { return floor; }
	
	public void setHP(int HP) { hp = HP; }
	public void setAtk(int ATK) { atk = ATK; }
	public void setDef(int DEF) { def = DEF; }
	public void setGold (float Gold) { gold = Gold; }
	public void setCoord (int xcord, int ycord) { x = xcord; y = ycord; }
	public void setFloor (Floor cfloor) { floor = cfloor; }
}