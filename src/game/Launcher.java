package src.game;

import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.xml.stream.XMLStreamException;

public class Launcher {
	protected final static Scanner scanner = new Scanner( System.in );
    public static void main(String[] args){
          
        while (true){
			System.out.println("_________Welcome to the game !_________");
			System.out.println("_________Main menu_____________________");
			System.out.println("_________1. New Game___________________");
			System.out.println("_________2. Continue___________________");
			System.out.println("_________3. Exit_______________________");
			System.out.print("_________Select : >");
			try {
				switch (Integer.parseInt(scanner.nextLine())) {
					case 1 -> {
						System.out.println("Game is starting...");
						Game newGame = new Game();
						System.out.println(newGame.toString());
						newGame.startGame();
						
					}
					case 2 -> {
						System.out.println("Loading Game...");
						try {
							Game continueGame = LoadGame.loadGame();
							continueGame.startGame();
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (XMLStreamException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					case 3 -> {
						System.out.println("Exiting the game.. See ya next time !");
						Launcher.scanner.close();
						return;
					}
					default -> System.out.println("not implemented yet");
				}
			} catch(NumberFormatException e){
	            System.out.println("Veuillez saisir un chiffre");
	            scanner.nextLine();
	        }
				
		}
    }
}
