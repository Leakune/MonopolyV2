package src.game;

import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Board {
    private final String name;
    private final int size;
    //private Square[] squares;
    private Effect[] effect;
    private final ArrayList<Player> players;
    private static final Random RANDOM = new Random();
    private final static ResourceBundle bundle = ResourceBundle.getBundle("src.game.properties.config");
    private final static int MAX_BOARD_SIZE = Integer.parseInt(bundle.getString("MAX_BOARD_SIZE"));
    private final static int MIN_BOARD_SIZE = Integer.parseInt(bundle.getString("MIN_BOARD_SIZE"));
    private final static String[] BOARD_NAMES = {"Ice","Temple","Jungle","Fire"};
    private final static int MAX_PLAYERS = Integer.parseInt(bundle.getString("MAX_PLAYERS"));
    private final static int MIN_PLAYERS = Integer.parseInt(bundle.getString("MIN_PLAYERS"));
    private final static int START_COINS = Integer.parseInt(bundle.getString("START_COINS"));
    

    public Board(){
    	this.name = initName();
        this.size = initSize();
        this.players = initPlayers();
        this.effect = generateEffect();
    }

    public String getName(){
        return this.name;
    }

    public int getSize(){
        return this.size;
    }

    private String initName(){
        int boardName = 0;
        try ( Scanner scanner = new Scanner( System.in ) ) {
            do {
                int i = 1;
                for (String name : BOARD_NAMES) {
                    System.out.println("Map " + i + ": " + name);
                    i++;
                }
                System.out.println("Choose the number of map name:");
                boardName = scanner.nextInt();
            } while (boardName > BOARD_NAMES.length || boardName < BOARD_NAMES.length);
        }
        return BOARD_NAMES[boardName-1];
    }

    private int initSize(){
        int boardSize = 0;
        try ( Scanner scanner = new Scanner( System.in ) ) {
            do {
                System.out.println("Enter the size of map (" + MAX_BOARD_SIZE + "-" + MIN_BOARD_SIZE + "):");
                boardSize = scanner.nextInt();
            } while (boardSize > MAX_BOARD_SIZE || boardSize < MIN_BOARD_SIZE);
        }
        return boardSize;
    }
    
    private ArrayList<Player> initPlayers(){
        ArrayList<Player> players = new ArrayList<>();
        int arraySize = 0;
        try ( Scanner scanner = new Scanner( System.in ) ) {
            do {
                System.out.println("Enter the number of players (" + MIN_PLAYERS + "-" + MAX_PLAYERS + "):");
                arraySize = scanner.nextInt();
            } while (arraySize > MAX_PLAYERS || arraySize < MIN_PLAYERS);

            for (int i = 0; i < arraySize; i++) {
                System.out.println("Enter name of player " + (i + 1) + ":");
                Player player = new Player(START_COINS);
                players.add(player);
            }
        }
        return players;
    }
    
    private Effect[] generateEffect(){
    	Effect[] boardEffects = new Effect[size];
		boardEffects[0] = Effect.BEGIN;
		for (int i = 1; i < size; i++) {
			boardEffects[i] = Effect.values()[RANDOM.nextInt(Effect.values().length - 1) + 1];
		}
		return boardEffects;
	}
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("|-------------------------------|\n");
        sb.append("|---------");
        sb.append(getName());
        sb.append("----------|");
        sb.append("|---------");
        sb.append(getSize());
        sb.append("----------|");
        for(int i = 0; i < this.getSize(); i++) {
        	for (Player p : players) {
        		sb.append(p.toString());
            }
        	sb.append("----------|");
        	sb.append(effect[i].toString());
        }
        return sb.toString();
    }

}
