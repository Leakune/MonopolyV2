package src.game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GameFile {
	
	private static final String fileName = "save.txt";
	private static final Path path = Paths.get(fileName);
	
	public static void SaveGame(Game dataGame) {
		try(BufferedWriter writer = Files.newBufferedWriter(GameFile.path, Charset.forName("UTF-8"))){
			writer.write(dataGame.toString());
			writer.newLine();
			writer.write(dataGame.getBoard().toString());
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
	public static void loadGame() {
		try(BufferedReader reader = Files.newBufferedReader(GameFile.path)){
			String line = reader.readLine();
		    while(line != null) {
		        System.out.println(line);
		        line = reader.readLine();
		    }
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
}
