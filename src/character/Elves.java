package character;
import floor.Floor;

public class Elves extends Player {

	public Elves(int x, int y, Floor f, int cc) {
		super(x, y, 140, 140, 30, 10, 0, f, cc, "Elves");
	}
	
	public String getRace() { return "Elves"; }

}
