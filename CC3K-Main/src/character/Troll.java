package character;
import floor.Floor;

public class Troll extends Enemy {

	public Troll(int x, int y, Floor f) {
		super(x, y, 120, 25, 15, 0, f, true);
		f.setTile(x, y, 'T');
	}
	
}
