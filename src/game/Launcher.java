package src.game;

import java.util.Scanner;

public class Launcher {
	protected final static Scanner scanner = new Scanner( System.in );
    public static void main(String[] args) {
          
        while (true){
			System.out.println("_________Welcome to the game !_________");
			System.out.println("_________Main menu_____________________");
			System.out.println("_________1. New Game___________________");
			System.out.println("_________2. Continue___________________");
			System.out.println("_________3. Exit_______________________");
			System.out.print("_________Select : >");
			switch (scanner.nextInt()) {
				case 1 -> {
					System.out.println("Game is starting...");
					Game newGame = new Game();
					System.out.println(newGame.toString());
					newGame.startGame();
					
				}
				case 2 -> {
					System.out.println("Loading Game...");
					Game continueGame = LoadGame.loadGame();
					continueGame.startGame();
				}
				case 3 -> {
					System.out.println("Exiting the game.. See ya next time !");
					Launcher.scanner.close();
					return;
				}
				default -> System.out.println("not implemented yet");
			}
		}
    }
}
