package game;

import java.util.ResourceBundle;
import java.util.Scanner;

public class Board {
    private final String name;
    private final int size;
    //private Square[] squares;
    private final static ResourceBundle bundle = ResourceBundle.getBundle("game.properties.config");
    private final static int MAX_BOARD_SIZE = Integer.parseInt(bundle.getString("MAX_BOARD_SIZE"));
    private final static int MIN_BOARD_SIZE = Integer.parseInt(bundle.getString("MIN_BOARD_SIZE"));
    private final static String[] BOARD_NAMES = {"Ice","Temple","Jungle","Fire"};


    public Board(){
        this.name = initName();
        this.size = initSize();
        //this.squares = generateSquares();
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
}
