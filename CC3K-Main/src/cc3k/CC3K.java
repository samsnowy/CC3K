package cc3k;
import character.*;
import floor.*;

import java.io.*;
import java.util.*;

public class CC3K {

	//global variables stores player status
	static boolean[] potionKnowledge = new boolean[6];
	static int currentHp;
	static float currentGold = 0;
	static boolean hostileMerchant = false;

	//save player status for next floor
	static void save(Player player) {
		for(int i = 0; i < 6; i++)
			if(player.getPK(i))
				potionKnowledge[i] = true;

		currentHp = player.getHP();
		currentGold = player.getGold();
	}

	//load player status from previous floor
	static void load(Player player) {
		for(int i = 0; i < 6; i++)
			if(potionKnowledge[i])
				player.knowPK(i);

		player.setHP(currentHp);
		player.setGold(currentGold);
	}

	//reset player status
	static void reset() {
		hostileMerchant = false;
		for(int i = 0; i < 6; i++)
			potionKnowledge[i] = false;

		currentGold = 0;
	}

	//help menu
	private static void help(){
		System.out.println("Move: ");
		System.out.println("no = move north");
		System.out.println("ea = move east");
		System.out.println("so = move south");
		System.out.println("we = move west");
		System.out.println("nw = move northwest");
		System.out.println("ne = move northeast");
		System.out.println("se = move southeast");
		System.out.println("sw = move southwest");
		System.out.println();
		System.out.println("Attack: ");
		System.out.println("input 'a' followed by direction");
		System.out.println("e.g.: ano = attack north");
		System.out.println("");
		System.out.println("Use potion: ");
		System.out.println("input 'u' followed by direction");
	}
	
	public static void main(String[] args) {

		int tempchar;
		char instruction, playerRace;
		boolean nextFloor;
		boolean newGame = true;

		File file = new File("map.txt");
		Reader reader = null;	
		Scanner input = new Scanner(System.in);




		while(newGame) {
			Player player = null;
			newGame = false;

			System.out.println("Welcome to ChamberCrawler 3000!");
			System.out.println("Please choose a race:");
			System.out.println("Human(h), Elves(e), Dwarf(d), Orc(o)");
			System.out.println();
			System.out.println("Quit(q) Restart(r) Help(?)");

			instruction = input.next().charAt(0);

			while(instruction != 'q' &&
					instruction != 'r' &&
					instruction != 'h' &&
					instruction != 'e' &&
					instruction != 'd' &&
					instruction != 'o' &&
					instruction != '?') {
				System.out.println("Invalid command!");
				instruction = input.next().charAt(0);
			}

			if (instruction == 'q') {
				//clear the screen and display "Game over"
				System.out.println("Game over");
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				input.close();
				continue;
			} else if(instruction == 'r') {
				newGame = true;
				continue;
			} else if(instruction == '?'){
				help();
				newGame = true;
				continue;
			}

			//if the instruction is player's race
			playerRace = instruction;

			//initialize the player's hp for future save/load
			switch(playerRace) {
			case 'h':
			case 'e':
				currentHp = 140;
				break;
			case 'd':
				currentHp = 100;
				break;
			case 'o':
				currentHp = 180;
				break;
			}

			//start the game
			for(int i = 0; i < 10; i++) {

				Floor currentFloor = new Floor(i, hostileMerchant);
				
				try {
					reader = new InputStreamReader(new FileInputStream(file));
				} catch (Exception e) {
					e.printStackTrace();
				}


				//read in the map
				try {
					for(int j=0; j<25; j++) {
						
						if (j > 0) { tempchar = reader.read(); }
						
						for(int k=0; k<79; k++) {
							tempchar = reader.read();
							if (((char) tempchar) != '\n') 
							{
								currentFloor.setTile(j, k, (char) tempchar);
							}
						}
					}

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}



				//generate the player based on the player's race
				currentFloor.playerGenerator(playerRace);
				player = currentFloor.getPlayer();

				//generate the contents of the current floor
				currentFloor.contentGenerator();

				//load the player's status form previous floor
				//(if applicable)
				load(player);

				//print the layout of the current floor
				currentFloor.printFloor();
				

				//while the player haven't reach the stairway
				nextFloor = false;
				

				while(!nextFloor) {
							
					
					String a = input.next();
					char[] b = a.toCharArray();
					instruction = b[0];
					boolean helped = false;
					

					//if the instruction is restart
					if(instruction == 'r') {
						//reset the player's status
						reset();
						newGame = true;
						break;
						//if the instruction is quit
					} else if(instruction == 'q') {
						//clear the screen and display "Game over"
						System.out.println("Game over");
						break;
						//if the instruction is to move
					} else if (instruction == '?'){
						help();
						helped = true;
					} else if (b.length < 2 || b.length > 3) {
						System.out.println("Invalid command!");
						continue;
					} else if(instruction == 'n' ||
							instruction == 's' ||
							instruction == 'e' ||
							instruction == 'w' ||
							instruction == 'u' ||
							instruction == 'a') {

						char command;

						//set the command to "use", "attack" or "move"
						if(instruction == 'u' ||
								instruction == 'a') {
							command = instruction;
							
							if (b.length < 3) 
							{
								System.out.println("Invalid command!");
								continue;
							}
							else
							{
								instruction = b[1];
							}
							
						} else
							command = 'm';

						//read in the direction instructions
						//and let the player use, attack or move
						//according to the command
						if(instruction == 'n') {
							
							if (b.length == 2)
							{
								instruction = b[1];
							}
							else
							{
								instruction = b[2];
							}

							if(instruction == 'o')
								nextFloor = player.move('8', command);
							else if(instruction == 'e')
								nextFloor = player.move('9', command);
							else if(instruction == 'w')
								nextFloor = player.move('7', command);
							else {
								System.out.println("Invalid command!");
								continue;
							}
						} else if(instruction == 's') {
							
							if (b.length == 2)
							{
								instruction = b[1];
							}
							else
							{
								instruction = b[2];
							}

							if(instruction == 'o')
								nextFloor = player.move('2', command);
							else if(instruction == 'e')
								nextFloor = player.move('3', command);
							else if(instruction == 'w')
								nextFloor = player.move('1', command);
							else {
								System.out.println("Invalid command!");
								continue;
							}
						} else if(instruction == 'e') {
							
							if (b.length == 2)
							{
								instruction = b[1];
							}
							else
							{
								instruction = b[2];
							}

							if(instruction == 'a')
								nextFloor = player.move('6', command);
							else {
								System.out.println("Invalid command!");
								continue;
							}
						} else {
							
							if (b.length == 2)
							{
								instruction = b[1];
							}
							else if (b[1] == 'w' || b[0] == 'w')
							{
								instruction = b[2];
							}
							
							if(instruction == 'e')
								nextFloor = player.move('4', command);

							
							else {
								System.out.println("Invalid command!");
								continue;
							}
						}

						//move all the enemies
						for(int i1=0; i1<currentFloor.getNumEnemies(); i1++)
							currentFloor.getEnemy(i1).move();

						//if any one of the merchants got hit
						//set the global merchant hostility to true
						hostileMerchant = currentFloor.getHostileMerchant();

						//if the player is dead
						if(player.getHP() <= 0) {
							nextFloor = false;
							//clear the screen
							//and display "You are dead! Game over!"
							//display score based on the player's 
							//race special ability:
							// * Human gets 1.5 times more score
							System.out.println("You are dead! Game over!");

							if(player.getRace() == "Human")
								System.out.println("Score: " + (int) (player.getGold()* 1.5));
							else
								System.out.println("Score: " + (int) player.getGold());
							System.out.println("Quit(q) Restart(r)");

							//read in the instruction
							instruction = input.next().charAt(0);

							//keep reading in the instruction until it is valid
							while(instruction != 'q' && instruction != 'r')
								instruction = input.next().charAt(0);

							//if the instruction is restart
							if(instruction == 'r') {
								//reset the player's status
								reset();
								newGame = true;
							}

							break;
						}
					}
					
					else {
						System.out.println("Invalid command!");
						continue;
					}

					//print the floor layout, if have not seeked for help
					if (!helped)
					 currentFloor.printFloor();
				}

				//if the player defeated the game
				if(i == 9 && nextFloor && !newGame) {
					//clear the screen
					//display ""Congratulations! You defeated the game!"
					//display score based on the player's                  
					//race special ability:                                
					// * Human gets 1.5 times more score                   

					System.out.println("Congratulations! You defeated the game!");

					if(player.getRace() == "Human")	
						System.out.println("Score: " + (int) (player.getGold() * 1.5));
					else
						System.out.println("Score: " + (int) player.getGold());

					System.out.println("Quit(q) Restart(r)");

					//read in the instruction
					instruction = input.next().charAt(0);

					//keep reading in the instruction until it is valid
					while(instruction != 'q' && instruction != 'r')
						instruction = input.next().charAt(0);

					//if the instruction is restart
					if(instruction == 'r') {
						reset();
						newGame = true;
					}

					//if the quit or restart the game
				} else if (!nextFloor) {
					break;
				}

				//if the current floor is finished 
				//save the player's status
				save(player);
			}
		}

	}

}

