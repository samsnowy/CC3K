package character;
import floor.Floor;

public class Werewolf extends Enemy {

	public Werewolf(int x, int y, Floor f) {
		super(x, y, 120, 30, 5, 0, f, true);
		f.setTile(x, y, 'V');
	}

}
