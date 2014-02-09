package character;
import floor.Floor;

public class Phoenix extends Enemy {

	public Phoenix(int x, int y, Floor f) {
		super(x, y, 50, 35, 20, 0, f, true);
		f.setTile(x, y, 'X');
	}

}
