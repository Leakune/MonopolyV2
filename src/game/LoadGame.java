package src.game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class LoadGame {
    static final String DATE = "date";
    static final String ITEM = "item";
    static final String MODE = "mode";
    static final String UNIT = "unit";
    static final String CURRENT = "current";
    static final String INTERACTIVE = "interactive";

    @SuppressWarnings({ "unchecked", "null" })
    public List<Item> readConfig(String configFile) {
        List<Item> items = new ArrayList<Item>();
        try {
            // First, create a new XMLInputFactory
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            // Setup a new eventReader
            InputStream in = new FileInputStream(configFile);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            // read the XML document
            Item item = null;

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    // If we have an item element, we create a new item
                        String elementName = startElement.getName().getLocalPart();
                        switch (elementName) {
                        case ITEM:
                            item = new Item();
                            // We read the attributes from this tag and add the date
                            // attribute to our object
                            Iterator<Attribute> attributes = startElement.getAttributes();
                            while (attributes.hasNext()) {
                                Attribute attribute = attributes.next();
                                if (attribute.getName().toString().equals(DATE)) {
                                    item.setDate(attribute.getValue());
                                }
                            }
                            break;
                        case MODE:
                            event = eventReader.nextEvent();
                            item.setMode(event.asCharacters().getData());
                            break;
                        case UNIT:
                            event = eventReader.nextEvent();
                            item.setUnit(event.asCharacters().getData());
                            break;
                        case CURRENT:
                            event = eventReader.nextEvent();
                            item.setCurrent(event.asCharacters().getData());
                            break;
                        case INTERACTIVE:
                            event = eventReader.nextEvent();
                            item.setInteractive(event.asCharacters().getData());
                            break;
                        }
                }
                    // If we reach the end of an item element, we add it to the list
                    if (event.isEndElement()) {
                        EndElement endElement = event.asEndElement();
                        if (endElement.getName().getLocalPart().equals(ITEM)) {
                            items.add(item);
                        }
                    }

            }
        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
        return items;
    }

}