package src.game;

import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Board {
    private final String name;
    private final int size;
    private ArrayList<Effect> effect;
    private final ArrayList<Player> players;
    
    private static final Random RANDOM = new Random();
    private final static ResourceBundle bundle = ResourceBundle.getBundle("src.game.properties.config");
    private final static int MAX_BOARD_SIZE = Integer.parseInt(bundle.getString("MAX_BOARD_SIZE"));
    private final static int MIN_BOARD_SIZE = Integer.parseInt(bundle.getString("MIN_BOARD_SIZE"));
    private final static String[] BOARD_NAMES = {"Ice","Temple","Jungle","Fire"};
    private final static int MAX_PLAYERS = Integer.parseInt(bundle.getString("MAX_PLAYERS"));
    private final static int MIN_PLAYERS = Integer.parseInt(bundle.getString("MIN_PLAYERS"));
    //private final static int START_COINS = Integer.parseInt(bundle.getString("START_COINS"));
    

    public Board(){
    	this.name = initName();
        this.size = initSize();
        this.players = initPlayers();
        this.effect = generateEffect();
    }
    //initialize from loadGame
    public Board(String name, int size, ArrayList<Player> players, ArrayList<Effect> effect){
    	this.name = name;
        this.size = size;
        this.players = players;
        this.effect = effect;
    }
    public String getName(){
        return this.name;
    }

    public int getSize(){
        return this.size;
    }
    public ArrayList<Player> getPlayers(){
    	return this.players;
    }
    public Effect getEffect(int position) {
    	return this.effect.get(position);
    }
    public void setEffect(Effect newEffect, int position) {
    	this.effect.set(position, newEffect);
    }
    private String initName(){
        int boardName = 0;
            do {
                int i = 1;
                for (String name : BOARD_NAMES) {
                    System.out.println("Map " + i + ": " + name);
                    i++;
                }
                System.out.println("Choose the number of map name:");
                boardName = Launcher.scanner.nextInt();
            } while (boardName > BOARD_NAMES.length || boardName <= 0);
        return BOARD_NAMES[boardName-1];
    }

    private int initSize(){
        int boardSize = 0;
            do {
                System.out.println("Enter the size of map (" + MIN_BOARD_SIZE + "-" + MAX_BOARD_SIZE + "):");
                boardSize = Launcher.scanner.nextInt();
            } while (boardSize > MAX_BOARD_SIZE || boardSize < MIN_BOARD_SIZE);
        return boardSize;
    }
    
    private ArrayList<Player> initPlayers(){
        ArrayList<Player> players = new ArrayList<>();
        int arraySize = 0;
            do {
                System.out.println("Enter the number of players (" + MIN_PLAYERS + "-" + MAX_PLAYERS + "):");
                arraySize = Launcher.scanner.nextInt();
            } while (arraySize > MAX_PLAYERS || arraySize < MIN_PLAYERS);

            for (int i = 0; i < arraySize; i++) {
                System.out.println("Enter name of player " + (i + 1) + ":");
                Player player = new Player(Effect.BEGIN.getValue());
                players.add(player);
            }
        return players;
    }
    
    private ArrayList<Effect> generateEffect(){
    	ArrayList<Effect> boardEffects = new ArrayList<>();
		boardEffects.add(Effect.BEGIN);
		for (int i = 1; i < size; i++) {
			boardEffects.add(Effect.values()[RANDOM.nextInt(Effect.values().length - 1) + 1]);
		}
		return boardEffects;
	}
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        for (Player p : players) {
    		sb.append(p.toString());
        }
        for(int i = 0; i < this.getSize(); i++) {
        	sb.append("|------");
        	//sb.append(effect[i].toString());
        	sb.append(i + " ");
        	sb.append(effect.get(i).getName());
        	for (Player p : players) {
        		if(p.getPosition() == i) {
        			sb.append(" " + p.getName());
        		}
            }
        	sb.append("------|");
        }
        return sb.toString();
    }

}
