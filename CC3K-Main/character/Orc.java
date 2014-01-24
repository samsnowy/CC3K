package character;
import floor.Floor;

public class Orc extends Player {

	public Orc(int x, int y, Floor f, int cc) {
		super(x, y, 180, 180, 30, 25, 0, f, cc, "Orc");
	}
	
	public String getRace() { return "Orc"; }

}
