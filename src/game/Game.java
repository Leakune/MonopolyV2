package src.game;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


public class Game {
    private final int id;
    private final Board board;
    
    private final static ResourceBundle bundle = ResourceBundle.getBundle("src.game.properties.config");
    private final static int CDT_TO_WIN = Integer.parseInt(bundle.getString("CDT_TO_WIN"));

    
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
    public void startGame() {
    	while (true) {
			for (Player player : this.board.getPlayers()) {
				//player is moving
				boolean waiting = true;
				while(waiting){
					System.out.println(player.getName() + " what do you want to do ?");
					System.out.println("1. Use dice 2. Use coins 3. Exit");
					System.out.print("Select : >");
					switch (Launcher.scanner.nextInt()) {
						case 1 ->{
							System.out.println(player.useDice());
							waiting = false;
						}
						case 2 -> {
							System.out.println("You have "+player.getCoins()+" coins");
							System.out.println("How do you want to spend coins ?");
							System.out.println("1. Set a case to BONUS effect for "+Effect.BONUS.getValue()+" coins");
							System.out.println("2. Set a case to LOOSE effect for "+Math.abs(Effect.LOOSE.getValue())+" coins");
							System.out.println("3. Set a case to EMPTY effect for "+(Effect.EMPTY.getValue()+1)+" coins");
							System.out.println("4. Set a case to PEN4A effect for "+Math.abs(Effect.PEN4A.getValue())+" coins");
							System.out.println("5. Go back");
							int res = Launcher.scanner.nextInt();
							if(res >= 5) {
								break;
							}
							Effect selectedEffect = Effect.chooseEffectToChange(res);
							System.out.println("Your effect:" + selectedEffect.toString());
							
							if (selectedEffect.getValue() > player.getCoins()){
								System.out.println("You don't have enough coins !");
								break;
							}
							System.out.println("What case do you want to set ?");
							System.out.println(board.toString());
							int selectedCase;
							do {
								System.out.println("Please select the case:");
								selectedCase = Launcher.scanner.nextInt();
								if (selectedCase < 0 || selectedCase > board.getSize() || board.getEffect(selectedCase) == selectedEffect) {
									System.out.println("No such case or the case selected has already this effect !");
								}
							} while (selectedCase < 0 || selectedCase > board.getSize() || board.getEffect(selectedCase) == selectedEffect);
							System.out.println("This case :");
							System.out.println(board.getEffect(selectedCase).toString());
							System.out.println("Becomes :");
							player.setCoins(selectedEffect.getValue());
							board.setEffect(selectedEffect, selectedCase);
							System.out.println(board.getEffect(selectedCase).toString());
						}
						case 3 -> {
							System.out.println("Do you want to save the game before exit ?");
							System.out.println("1. Yes 2. NO");
							int rep = Launcher.scanner.nextInt();
							if(rep==1){
								//ici on enregistre la game dans la bdd et on lui retourne un code de la game
								return;
							}else{
								return;
							}
						}
						default -> System.out.println("Not implemented yet..");
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
			}
			System.out.println(this.toString());
		}
    }
    public Boolean isWinner(Player player) {
		if(player.getCoins() >= CDT_TO_WIN) {
			System.out.println("The winner is: " + player.getName());
			return true;
		}
    	return false;
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
