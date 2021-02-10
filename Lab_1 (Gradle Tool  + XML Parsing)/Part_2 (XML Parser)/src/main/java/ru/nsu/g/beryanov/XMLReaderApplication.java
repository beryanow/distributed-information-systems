package ru.nsu.g.beryanov;

import lombok.extern.log4j.Log4j2;
import ru.nsu.g.beryanov.reader.XMLReader;

@Log4j2
public class XMLReaderApplication {
    public static void main(String[] args) {
        XMLReader xmlReader = new XMLReader();
        xmlReader.read("src/main/resources/RU-NVS.osm");
    }
}
