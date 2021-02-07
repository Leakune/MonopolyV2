package src.game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class LoadGame {

	static final String GAME = "game";
    static final String ID = "id";
    static final String BOARD = "board";
    static final String NAME_BOARD = "nameBoard";
    static final String SIZE_BOARD = "sizeBoard";
    static final String PLAYERS = "players";
    static final String PLAYER = "player";
    static final String PLAYER_NAME = "name";
    static final String PLAYER_POSITION = "position";
    static final String PLAYER_COINS = "coins";
    static final String EFFECTS = "effects";
    static final String EFFECT = "effect";
    static final String EFFECT_NAME = "effectName";
    static final String EFFECT_MESSAGE = "message";
    static final String EFFECT_VALUE = "value";
    static final String EFFECT_COST = "cost";
    static final int NOT_FOUND_AT_THE_MOMENT = -100;

    private static final String SAVE_FILE = "saveFile.xml";
    

    @SuppressWarnings("resource")
	public static Game loadGame() throws FileNotFoundException, XMLStreamException {
    	int id = NOT_FOUND_AT_THE_MOMENT;
    	//board
        Board board = null;
        String nameBoard = null;
        int sizeBoard = NOT_FOUND_AT_THE_MOMENT;
        
        //effect
        ArrayList<Effect> effect = new ArrayList<>();
        String nameEffect = null;
        String message = null;
        int value = NOT_FOUND_AT_THE_MOMENT;
    	int cost = NOT_FOUND_AT_THE_MOMENT;
    	
    	//player
    	ArrayList<Player> players = new ArrayList<>();
    	String namePlayer = null;
        int position = NOT_FOUND_AT_THE_MOMENT;
        int coins = NOT_FOUND_AT_THE_MOMENT;
        
        //create a XMLInputFactory -> class supporting XMLEventReader
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        
        //XMLEvenReader -> interface to read content in XML file
        XMLEventReader eventReader = inputFactory.createXMLEventReader(new FileInputStream(SAVE_FILE));
               	
    	
        // read the XML document
        while (eventReader.hasNext()) {
            XMLEvent event = eventReader.nextEvent();

            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                // If we have an item element, we create a new item
                    String elementName = startElement.getName().getLocalPart();
                    switch (elementName) {
                    case ID:
                        event = eventReader.nextEvent();
                        id =  Integer.parseInt(event.asCharacters().getData());
                        break;
                    case NAME_BOARD:
                        event = eventReader.nextEvent();
                        nameBoard = event.asCharacters().getData();
                        break;
                    case SIZE_BOARD:
                        event = eventReader.nextEvent();
                        sizeBoard = Integer.parseInt(event.asCharacters().getData());
                        break;
                    case PLAYER_NAME:
                        event = eventReader.nextEvent();
                        namePlayer = event.asCharacters().getData();
                        break;
                    case PLAYER_POSITION:
                        event = eventReader.nextEvent();
                        position = Integer.parseInt(event.asCharacters().getData());
                        break;
                    case PLAYER_COINS:
                        event = eventReader.nextEvent();
                        coins = Integer.parseInt(event.asCharacters().getData());
                        break;
                    case EFFECT_NAME:
                        event = eventReader.nextEvent();
                        nameEffect = event.asCharacters().getData();
                        break;
                    case EFFECT_MESSAGE:
                        event = eventReader.nextEvent();
                        message = event.asCharacters().getData();
                        break;
                    case EFFECT_VALUE:
                        event = eventReader.nextEvent();
                        value = Integer.parseInt(event.asCharacters().getData());
                        break;
                    case EFFECT_COST:
                        event = eventReader.nextEvent();
                        cost = Integer.parseInt(event.asCharacters().getData());
                        break;
                    }
            }
                // reach the end of an element
            else if (event.isEndElement()) {
                EndElement endElement = event.asEndElement();
                String elementName = endElement.getName().getLocalPart();
                switch (elementName) {
                    case PLAYER:
                        if(!arePlayersPropsGood(namePlayer, position, coins)) {
                        	System.out.println("Error, not all props are good for a player");
                        	return null;
                        }
                        players.add(new Player(namePlayer, coins, position));
                        namePlayer = null;
                    	position = NOT_FOUND_AT_THE_MOMENT;
                    	coins = NOT_FOUND_AT_THE_MOMENT;
                    break;
                    case EFFECT:
                    	if(!areEffectsPropsGood(nameEffect, message, value, cost)) {
                        	System.out.println("Error, not all props are good for a effect");
                        	return null;
                        }
                    	Effect newEffect = addEffect(nameEffect);
                    	effect.add(newEffect);
                    	nameEffect = null;
                        message = null;
                        value = NOT_FOUND_AT_THE_MOMENT;
                    	cost = NOT_FOUND_AT_THE_MOMENT;
                    break;
                    case BOARD:
                    	if(!areBoardsPropsGood(nameBoard, sizeBoard)) {
                        	System.out.println("Error, not all props are good for a board");
                        	return null;
                        }
                    	board = new Board(nameBoard, sizeBoard, players, effect);
                    break;
                    case GAME:
                    	if(!areGamesPropsGood(board, id)) {
                        	System.out.println("Error, not all props are good for game");
                        	return null;
                        }
                    	board = new Board(nameBoard, sizeBoard, players, effect);
                    break;
                }
             }

        }
        return new Game(id, board);
    }
    
    private static Effect addEffect(String nameEffect) {
    	if (nameEffect.equals(Effect.BEGIN.getName())){
			return Effect.BEGIN;
		}else if(nameEffect.equals(Effect.EMPTY.getName())){
			return Effect.EMPTY;
		}else if(nameEffect.equals(Effect.LOOSE.getName())){
			return Effect.LOOSE;
		}else if(nameEffect.equals(Effect.BONUS.getName())){
			return Effect.BONUS;
		}else if(nameEffect.equals(Effect.PEN4A.getName())){
			return Effect.PEN4A;
		}else {
    		
			return null;
		}
    }
    private static Boolean areGamesPropsGood(Board board, int id) {
    	if(board == null || id == NOT_FOUND_AT_THE_MOMENT) {
    		return false;
    	}
    	else {
    		return true;
    	}
    }

    private static Boolean areBoardsPropsGood(String nameBoard, int sizeBoard) {
    	if(nameBoard == null || sizeBoard == NOT_FOUND_AT_THE_MOMENT) {
    		return false;
    	}
    	else {
    		return true;
    	}
    }

    private static Boolean areEffectsPropsGood(String nameEffect, String message, int value, int cost) {
    	if(nameEffect == null || message == null || value == NOT_FOUND_AT_THE_MOMENT || cost == NOT_FOUND_AT_THE_MOMENT) {
    		return false;
    	}
    	else {
    		return true;
    	}
    }

    private static Boolean arePlayersPropsGood(String namePlayer, int position, int coins) {
    	if(namePlayer == null || position == NOT_FOUND_AT_THE_MOMENT || coins == NOT_FOUND_AT_THE_MOMENT) {
    		return false;
    	}
    	else {
    		return true;
    	}
    }

}