package ru.nsu.g.beryanov.reader;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import ru.nsu.g.beryanov.model.Node;
import ru.nsu.g.beryanov.model.ObjectFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

import java.io.InputStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Log4j2
public class XMLWrapperReader {
    @SneakyThrows
    public void process(InputStream inputStream) {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = null;
        JAXBContext jaxbContext = JAXBContext.newInstance(Node.class, ObjectFactory.class, Node.Tag.class);

        HashMap<String, Integer> userEdits = new HashMap<>();
        HashMap<String, Integer> keyEntries = new HashMap<>();

        log.info("Происходит парсинг XML файла из архива... ");

        try {
            reader = factory.createXMLStreamReader(inputStream);
            while (reader.hasNext()) {
                int event = reader.next();
                if (XMLStreamConstants.START_ELEMENT == event && "node".equals(reader.getLocalName())) {
                    Node node = revealNode(jaxbContext, reader);
                    String user = node.getUser();

                    if (userEdits.containsKey(user)) {
                        userEdits.put(user, userEdits.get(user) + 1);
                    } else {
                        userEdits.put(user, 1);
                    }

                    for (Node.Tag tag: node.getTag()) {
                        String key = tag.getV();

                        if (keyEntries.containsKey(key)) {
                            keyEntries.put(key, keyEntries.get(key) + 1);
                        } else {
                            keyEntries.put(key, 1);
                        }
                    }
                }
            }
        } finally {
            Objects.requireNonNull(reader).close();
        }

        LinkedHashMap<String, Integer> sortedUserEdits =
                userEdits.entrySet()
                        .stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (k1, k2) -> k1, LinkedHashMap::new));

        sortedUserEdits.forEach((user, amount) -> log.info("Пользователь: " + user + " -> " + "Количество правок: " + amount));
        keyEntries.forEach((key, amount) -> log.info("Ключ: " + key + " -> " + "Количество вхождений: " + amount));

        log.info("Парсинг XML файла из архива завершен...");
    }

    @SneakyThrows
    private Node revealNode(JAXBContext jaxbContext, XMLStreamReader reader) {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        return (Node) unmarshaller.unmarshal(reader);
    }
}
