package src.game;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class SaveGame {
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
    
	private static final String SAVE_FILE = "saveFile.xml";
	// create an XMLEVENT_FACTORY -> allows create XMLEvents instances
	private static final XMLEventFactory EVENT_FACTORY = XMLEventFactory.newInstance();
	
	// StartDocument XMLEvent = <?xml version="1.0" encoding="UTF-8"?>
    private static final StartDocument START_DOCUMENT = EVENT_FACTORY.createStartDocument();
    
    //characters of end-of-line and tabulation
	private static final XMLEvent END = EVENT_FACTORY.createDTD("\n");
	private static final XMLEvent TAB = EVENT_FACTORY.createDTD("\t");
    

    public static void saveConfig(Game dataGame) throws XMLStreamException, FileNotFoundException, FactoryConfigurationError {
        // create an XMLOutputFactory -> class supporting XMLEventWriter
        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        
        // create XMLEventWriter -> interface to write content in XML file
        XMLEventWriter eventWriter = outputFactory.createXMLEventWriter(new FileOutputStream(SAVE_FILE));  
        
        eventWriter.add(START_DOCUMENT);
        eventWriter.add(END);     
        createStartElement(eventWriter, GAME, 0);       
        createNode(eventWriter, ID, String.format("%d",dataGame.getId()), 1);
        createBoardNode(eventWriter, dataGame.getBoard());      
        createEndElement(eventWriter, GAME, 0);       
        eventWriter.add(EVENT_FACTORY.createEndDocument());
        eventWriter.close();
    }
    
    private static void createEffectsNode(XMLEventWriter eventWriter, Board board) throws XMLStreamException {
    	createStartElement(eventWriter, EFFECTS, 2);
        for(int i = 0; i < board.getSize(); i++) {
        	createStartElement(eventWriter, EFFECT, 3);
        	createNode(eventWriter, EFFECT_NAME, board.getEffect(i).getName(), 4);
        	createNode(eventWriter, EFFECT_MESSAGE, board.getEffect(i).getMessage(), 4);
        	createNode(eventWriter, EFFECT_VALUE, String.format("%d",board.getEffect(i).getValue()), 4);
        	createNode(eventWriter, EFFECT_COST, String.format("%d",board.getEffect(i).getCost()), 4);
            createEndElement(eventWriter, EFFECT, 3);
        }
        createEndElement(eventWriter, EFFECTS, 2);
    }
    
    private static void createPlayersNode(XMLEventWriter eventWriter, ArrayList<Player> players) throws XMLStreamException {
    	createStartElement(eventWriter, PLAYERS, 2);
        for(Player player : players) {
        	createStartElement(eventWriter, PLAYER, 3);
        	createNode(eventWriter, PLAYER_NAME, player.getName(), 4);
        	createNode(eventWriter, PLAYER_POSITION, String.format("%d",player.getPosition()), 4);
        	createNode(eventWriter, PLAYER_COINS, String.format("%d",player.getCoins()), 4);
            createEndElement(eventWriter, PLAYER, 3);
        }
        createEndElement(eventWriter, PLAYERS, 2);

    }
    
    private static void createBoardNode(XMLEventWriter eventWriter, Board board) throws XMLStreamException {
    	createStartElement(eventWriter, BOARD, 1);
        createNode(eventWriter, NAME_BOARD, board.getName(), 2);
        createNode(eventWriter, SIZE_BOARD, String.format("%d",board.getSize()), 2);
        
        //Players
        createPlayersNode(eventWriter, board.getPlayers());
        //Effects
        createEffectsNode(eventWriter, board);
        
        createEndElement(eventWriter, BOARD, 1);
        
    }
    
    // create a tag of an element
    private static void createStartElement(XMLEventWriter eventWriter, String name, int lvlTab) throws XMLStreamException {
    	for(int i = 0; i < lvlTab; i++) {
        	eventWriter.add(TAB);
        }
    	StartElement boardStartElement = EVENT_FACTORY.createStartElement("",
                "", name);
        eventWriter.add(boardStartElement);
        eventWriter.add(END);
    }
    
    //create tag end of an element
    private static void createEndElement(XMLEventWriter eventWriter, String name, int lvlTab) throws XMLStreamException {
    	for(int i = 0; i < lvlTab; i++) {
    		eventWriter.add(TAB);
        }
    	eventWriter.add(EVENT_FACTORY.createEndElement("", "", name));
        eventWriter.add(END);
    }
    
    //write a node with content
    private static void createNode(XMLEventWriter eventWriter, String name,
            String value, int lvlTab) throws XMLStreamException {
        // create Start node
        StartElement sElement = EVENT_FACTORY.createStartElement("", "", name);
        for(int i = 0; i < lvlTab; i++) {
        	eventWriter.add(TAB);
        }
        eventWriter.add(sElement);
        if(value != null) {
        	// create Content
            Characters characters = EVENT_FACTORY.createCharacters(value);
            eventWriter.add(characters);
        }
        // create End node
        EndElement eElement = EVENT_FACTORY.createEndElement("", "", name);
        eventWriter.add(eElement);
        eventWriter.add(END);
    } 
}
