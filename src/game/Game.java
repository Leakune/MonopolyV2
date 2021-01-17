package src.game;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Game {
    private final int id;
    private final Board board;
    
    public Game(){     
        this.id = initUniqId();
        this.board = new Board();
    }

    public int getId(){
        return this.id;
    }
    
    private int initUniqId() {
    	LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMddmmss");
        return Integer.parseInt(dtf.format(now));
    }

   

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("|------------");
        sb.append(getId());
        sb.append("------------|\n");
        sb.append(this.board.toString());
        return sb.toString();
    }

}
