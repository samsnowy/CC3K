package character;
import floor.Floor;

public class Merchant extends Enemy {

	public Merchant(int x, int y, Floor f, boolean hostile) {
		super(x, y, 30, 70, 5, 4, f, hostile);
		f.setTile(x, y, 'M');
	}

}
