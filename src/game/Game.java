package game;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Game {
    private final int id;
    private final ArrayList<Player> players;
    private final Board board;
    private final static ResourceBundle bundle = ResourceBundle.getBundle("game.properties.config");
    private final static int MAX_PLAYERS = Integer.parseInt(bundle.getString("MAX_PLAYERS"));
    private final static int MIN_PLAYERS = Integer.parseInt(bundle.getString("MIN_PLAYERS"));
    private final static int START_COINS = Integer.parseInt(bundle.getString("START_COINS"));


    public Game(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMddmmss");
        this.id = Integer.parseInt(dtf.format(now));
        this.players = initPlayers();
        this.board = new Board();
    }

    public int getId(){
        return this.id;
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

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("|------------");
        sb.append(getId());
        sb.append("------------|\n");
        for (Player p : players) {
            sb.append("|__").append(p.getName()).append("__|");
        }
        sb.append("\n");
        sb.append("|-------------------------------|\n");
        sb.append("|---------");
        sb.append(board.getName());
        sb.append("----------|");
        sb.append("|---------");
        sb.append(board.getSize());
        sb.append("----------|");
        return sb.toString();
    }

}
