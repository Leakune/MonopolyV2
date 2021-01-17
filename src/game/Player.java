package src.game;


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
        return Board.scanner.next();
    }

    public String getName(){
        return this.name;
    }
    public int getCoins(){
        return this.coins;
    }
    public int getPosition(){
        return this.position;
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
