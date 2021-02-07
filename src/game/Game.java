package src.game;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;


public class Game {
    private int id;
    private final Board board;
    
    private final static ResourceBundle bundle = ResourceBundle.getBundle("src.game.properties.config");
    private final static int CDT_TO_WIN = Integer.parseInt(bundle.getString("CDT_TO_WIN"));

    
    public Game(){
        try {
        	this.id = initUniqId();
        } catch(NumberFormatException e) {
        	e.printStackTrace();
        }
        this.board = new Board();
    }
    //initialize from loadGame
    public Game(int id, Board board){     
        this.id = id;
        this.board = board;
    }

    public int getId(){
        return this.id;
    }
    public Board getBoard() {
    	return this.board;
    }
    
    private int initUniqId() throws NumberFormatException{
    	LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMddmmss");
        return Integer.parseInt(dtf.format(now));
    }
    public void startGame() {
    	while (true) {
			for (Player player : this.board.getPlayers()) {
				//player is moving
				boolean waiting = true;
				while(waiting){
					System.out.println(this.board.toString());
					System.out.println(player.getName() + " what do you want to do ?");
					System.out.println("1. Use dice 2. Use coins 3. Exit");
					System.out.print("Select : >");
					try {
						switch (Integer.parseInt(Launcher.scanner.nextLine())) {
							case 1 ->{
								System.out.println(player.useDice());
								waiting = false;
							}
							case 2 -> {
								coinsGui(player);						
							}
							case 3 -> {
								exitGame();
								return;
							}
							default -> System.out.println("Not implemented yet..");
						}
					} catch(NumberFormatException e){
			            System.out.println("Veuillez saisir un chiffre");
			            Launcher.scanner.nextLine();
			        }
				}
				if (player.getPosition() >= board.getSize()) {
					player.setPosition(player.getPosition() - board.getSize());
				}
				player.setCoins(this.board.getEffect(player.getPosition()).getValue());
				System.out.println(this.board.getEffect(player.getPosition()).getMessage());
				if (isWinner(player)) {
					System.out.println("Game Over, the winner is: "
							+player.getName()+" with: "+player.getCoins()+" coins.");
					return;
				}
				System.out.println("\n");
			}
		}
    }
    public Boolean isWinner(Player player) {
		if(player.getCoins() >= CDT_TO_WIN) {
			System.out.println("The winner is: " + player.getName());
			return true;
		}
    	return false;
    }
    public void exitGame() {
		while (true) {
			System.out.println("Do you want to save the game before exit ?");
			System.out.println("1. Yes 2. NO");
			try {
				int rep = Integer.parseInt(Launcher.scanner.nextLine());
				if(rep==1){
			        try {
			            SaveGame.saveConfig(this);
			        } catch (XMLStreamException e) {
			            e.printStackTrace();
			        }
			        catch (FileNotFoundException e) {
			            e.printStackTrace();
			        }
			        catch (FactoryConfigurationError e) {
			            e.printStackTrace();
			        }
			        catch (Exception e) {
			            e.printStackTrace();
			        }
				}
				return;
			} catch(NumberFormatException e){
	            System.out.println("Veuillez saisir un chiffre");
	            Launcher.scanner.nextLine();
	        }
		}
    }
    public void coinsGui(Player player) {
    	System.out.println("You have "+player.getCoins()+" coins");
		System.out.println("How do you want to spend coins ?");
		System.out.println("1. Set a case to BONUS effect for "+Math.abs(Effect.BONUS.getCost())+" coins");
		System.out.println("2. Set a case to LOOSE effect for "+Math.abs(Effect.LOOSE.getCost())+" coins");
		System.out.println("3. Set a case to EMPTY effect for "+Math.abs(Effect.EMPTY.getCost())+" coins");
		System.out.println("4. Set a case to PEN4A effect for "+Math.abs(Effect.PEN4A.getCost())+" coins");
		System.out.println("5. Go back");
		int res = Launcher.scanner.nextInt();
		if(res >= 5) {
			return;
		}
		Effect selectedEffect = Effect.chooseEffectToChange(res);
		System.out.println("Your effect:" + selectedEffect.toString());
		
		if (selectedEffect.getValue() > player.getCoins()){
			System.out.println("You don't have enough coins !");
			return;
		}
		System.out.println("What case do you want to set ?");
		System.out.println(board.toString());
		int selectedCase;
		do {
			System.out.println("Please select the case:");
			selectedCase = Launcher.scanner.nextInt();
			if (selectedCase < 0 || selectedCase >= board.getSize() || board.getEffect(selectedCase) == selectedEffect) {
				System.out.println("No such case or the case selected has already this effect !");
			}
		} while (selectedCase < 0 || selectedCase >= board.getSize() || board.getEffect(selectedCase) == selectedEffect);
		System.out.print("You have changed the case's effect " + board.getEffect(selectedCase).getName() +" into ");
		player.setCoins(selectedEffect.getCost());
		board.setEffect(selectedEffect, selectedCase);
		System.out.println(board.getEffect(selectedCase).getName());
    }
   

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("|------------");
        sb.append(getId());
        sb.append("------------|\n");
        sb.append("|-------------------------------|\n");
        sb.append("|--------- Name: ");
        sb.append(this.board.getName());
        sb.append("----------|\n");
        sb.append("|--------- Taille: ");
        sb.append(this.board.getSize());
        sb.append("----------|\n");
        return sb.toString();
    }

}
