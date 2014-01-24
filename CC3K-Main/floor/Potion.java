package floor;

public class Potion extends Item {
	boolean effect;
	char type;

	public Potion(int x, int y, int value, Floor floor, boolean effect, char type) {
		super(x, y, value, floor);
		this.effect = effect;
		this.type = type;
		floor.setTile(x, y, 'P');
	}
	
	public boolean getEffect() { return effect; }
	public char getType() { return type; }

}
