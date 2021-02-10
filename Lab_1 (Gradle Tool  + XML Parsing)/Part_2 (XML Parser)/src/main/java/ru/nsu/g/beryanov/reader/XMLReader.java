package ru.nsu.g.beryanov.reader;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import ru.nsu.g.beryanov.processor.StAXStreamProcessor;

import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
public class XMLReader {
    @SneakyThrows
    public void read(String path) {
        try (StAXStreamProcessor processor = new StAXStreamProcessor(Files.newInputStream(Paths.get(path)))) {
            XMLStreamReader reader = processor.getReader();

            HashMap<String, Integer> userEdits = new HashMap<>();
            HashMap<String, Integer> keyEntries = new HashMap<>();

            log.info("Происходит парсинг XML файла... ");

            while (reader.hasNext()) {
                int event = reader.next();
                if (event == XMLEvent.START_ELEMENT && reader.getLocalName().equals("node")) {
                    int attributesAmount = reader.getAttributeCount();

                    for (int i = 0; i < attributesAmount; i++) {
                        if (reader.getAttributeLocalName(i).equals("user")) {
                            String user = reader.getAttributeValue(i);

                            if (userEdits.containsKey(user)) {
                                userEdits.put(user, userEdits.get(user) + 1);
                            } else {
                                userEdits.put(user, 1);
                            }
                        }
                    }
                }

                if (event == XMLEvent.START_ELEMENT && reader.getLocalName().equals("tag")) {
                    int attributesAmount = reader.getAttributeCount();

                    for (int i = 0; i < attributesAmount; i++) {
                        if (reader.getAttributeLocalName(i).equals("k")) {
                            String key = reader.getAttributeValue(i);

                            if (keyEntries.containsKey(key)) {
                                keyEntries.put(key, keyEntries.get(key) + 1);
                            } else {
                                keyEntries.put(key, 1);
                            }
                        }
                    }
                }
            }

            LinkedHashMap<String, Integer> sortedUserEdits =
                    userEdits.entrySet()
                            .stream()
                            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (k1, k2) -> k1, LinkedHashMap::new));

            sortedUserEdits.forEach((user, amount) -> log.info("Пользователь: " + user + " -> " + "Количество правок: " + amount));
            keyEntries.forEach((key, amount) -> log.info("Ключ: " + key + " -> " + "Количество вхождений: " + amount));

            log.info("Парсинг XML файла завершен...");
        }
    }
}
