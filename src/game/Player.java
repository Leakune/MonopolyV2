package src.game;

import java.util.Random;

public class Player {
    private String name;
    private int position;
    private int coins;
    
    private static final Random rand = new Random();
    private static final int MINIMUM_COINS = 0;

    public Player(int coins){
        this.name = initName();
        this.coins = coins;
        this.position = 0;
    }
    //initialize from loadGame
    public Player(String name, int coins, int position){
        this.name = name;
        this.coins = coins;
        this.position = position;
    }

    private String initName(){
        return Launcher.scanner.next();
    }

    public String getName(){
        return this.name;
    }
    public int getCoins(){
        return this.coins;
    }
    public void setCoins(int coins) {
    	this.coins = Math.max(this.coins + coins, MINIMUM_COINS);
    }
    public int getPosition(){
        return this.position;
    }
    public void setPosition(int position){
		this.position = position;
	}
    public String useDice(){
		StringBuilder sb = new StringBuilder();
		sb.append(getName()).append(" ..rolling a dice\n");
		int res = rand.nextInt(6)+1;
		sb.append("you got ").append(res).append(" !");
		setPosition(getPosition()+res);
		return sb.toString();
	}
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ");
        sb.append(this.getName());
        sb.append(" Position: ");
        sb.append(this.getPosition());
        sb.append(" Coins: ");
        sb.append(this.getCoins());
        sb.append("\n");
        return sb.toString();
    }
}
