package character;
import floor.Floor;

public class Goblin extends Enemy {

	public Goblin(int x, int y, Floor f) {
		super(x, y, 70, 5, 10, 0, f, true);
		f.setTile(x, y, 'N');
	}

}
