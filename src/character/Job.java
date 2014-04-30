package character;

abstract class Job {
	private String name;
	private String s1;
	private String s2;
	
	public Job(String choice, String first, String second) {
		name = choice;
		s1 = first;
		s2 = second;
	}
	
	abstract public void skill1(Character c);
	abstract public void skill2(Character c);
	public String getname() { return name; }
	public String gets1() { return s1; }
	public String gets2() { return s2; }
	
}
