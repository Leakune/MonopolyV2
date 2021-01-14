package game;

import java.util.ResourceBundle;
import java.util.Scanner;

public class Player {

    private String name;
    private int position;
    private int coins;

    public Player(int coins){
        this.name = initName();
        this.coins = coins;
        this.position = 0;
    }

    private String initName(){
        Scanner scanner = new Scanner( System.in );
        return scanner.next();
    }

    public String getName(){
        return this.name;
    }
    public int getCoins(){
        return this.coins;
    }
}
