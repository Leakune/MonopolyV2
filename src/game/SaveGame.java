package src.game;

import java.io.FileOutputStream;
import java.util.ArrayList;

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
	private static final String saveFile = "saveFile.xml";
	// create an XMLEventFactory -> allows create XMLEvents instances
	private static final XMLEventFactory eventFactory = XMLEventFactory.newInstance();
	// StartDocument XMLEvent = <?xml version="1.0" encoding="UTF-8"?>
    private static final StartDocument startDocument = eventFactory.createStartDocument();
    //characters of end-of-line and tabulation
	private static final XMLEvent end = eventFactory.createDTD("\n");
	private static final XMLEvent tab = eventFactory.createDTD("\t");
    

    public static void saveConfig(Game dataGame) throws Exception {
        // create an XMLOutputFactory -> class supporting XMLEventWriter
        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        
        // create XMLEventWriter -> interface to write content in XML file
        XMLEventWriter eventWriter = outputFactory.createXMLEventWriter(new FileOutputStream(saveFile));  
        
        eventWriter.add(startDocument);
        eventWriter.add(end);     
        createStartElement(eventWriter, "game", 0);       
        createNode(eventWriter, "id", String.format("%d",dataGame.getId()), 1);
        createBoardNode(eventWriter, dataGame.getBoard());      
        createEndElement(eventWriter, "game", 0);       
        eventWriter.add(eventFactory.createEndDocument());
        eventWriter.close();
    }
    
    private static void createEffectsNode(XMLEventWriter eventWriter, Board board) throws XMLStreamException {
    	createStartElement(eventWriter, "effects", 2);
        for(int i = 0; i < board.getSize(); i++) {
        	createStartElement(eventWriter, "effect", 3);
        	createNode(eventWriter, "name", board.getEffect(i).getName(), 4);
        	createNode(eventWriter, "message", board.getEffect(i).getMessage(), 4);
        	createNode(eventWriter, "value", String.format("%d",board.getEffect(i).getValue()), 4);
        	createNode(eventWriter, "cost", String.format("%d",board.getEffect(i).getCost()), 4);
            createEndElement(eventWriter, "effect", 3);
        }
        createEndElement(eventWriter, "effects", 2);
    }
    
    private static void createPlayersNode(XMLEventWriter eventWriter, ArrayList<Player> players) throws XMLStreamException {
    	createStartElement(eventWriter, "players", 2);
        for(Player player : players) {
        	createStartElement(eventWriter, "player", 3);
        	createNode(eventWriter, "name", player.getName(), 4);
        	createNode(eventWriter, "position", String.format("%d",player.getPosition()), 4);
        	createNode(eventWriter, "coins", String.format("%d",player.getCoins()), 4);
            createEndElement(eventWriter, "player", 3);
        }
        createEndElement(eventWriter, "players", 2);

    }
    
    private static void createBoardNode(XMLEventWriter eventWriter, Board board) throws XMLStreamException {
    	createStartElement(eventWriter, "board", 1);
        createNode(eventWriter, "nameBoard", board.getName(), 2);
        createNode(eventWriter, "sizeBoard", String.format("%d",board.getSize()), 2);
        
        //Players
        createPlayersNode(eventWriter, board.getPlayers());
        //Effects
        createEffectsNode(eventWriter, board);
        
        createEndElement(eventWriter, "board", 1);
        
    }
    
    // create a tag of an element
    private static void createStartElement(XMLEventWriter eventWriter, String name, int lvlTab) throws XMLStreamException {
    	for(int i = 0; i < lvlTab; i++) {
        	eventWriter.add(tab);
        }
    	StartElement boardStartElement = eventFactory.createStartElement("",
                "", name);
        eventWriter.add(boardStartElement);
        eventWriter.add(end);
    }
    
    //create tag end of an element
    private static void createEndElement(XMLEventWriter eventWriter, String name, int lvlTab) throws XMLStreamException {
    	for(int i = 0; i < lvlTab; i++) {
    		eventWriter.add(tab);
        }
    	eventWriter.add(eventFactory.createEndElement("", "", name));
        eventWriter.add(end);
    }
    
    //write a node with content
    private static void createNode(XMLEventWriter eventWriter, String name,
            String value, int lvlTab) throws XMLStreamException {
        // create Start node
        StartElement sElement = eventFactory.createStartElement("", "", name);
        for(int i = 0; i < lvlTab; i++) {
        	eventWriter.add(tab);
        }
        eventWriter.add(sElement);
        if(value != null) {
        	// create Content
            Characters characters = eventFactory.createCharacters(value);
            eventWriter.add(characters);
        }
        // create End node
        EndElement eElement = eventFactory.createEndElement("", "", name);
        eventWriter.add(eElement);
        eventWriter.add(end);
    } 
}
