package character;
import floor.Floor;

public class Vampire extends Enemy {

	public Vampire(int x, int y, Floor f) {
		super(x, y, 50, 25, 25, 0, f, true);
		f.setTile(x, y, 'V');
	}

}
