package ru.nsu.g.beryanov.processor;

import lombok.Getter;
import lombok.SneakyThrows;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;

@Getter
public class StAXStreamProcessor implements AutoCloseable {
    private static final XMLInputFactory FACTORY = XMLInputFactory.newInstance();
    private final XMLStreamReader reader;

    @SneakyThrows
    public StAXStreamProcessor(InputStream inputStream) {
        reader = FACTORY.createXMLStreamReader(inputStream);
    }

    @Override
    @SneakyThrows
    public void close() {
        if (reader != null) {
            reader.close();
        }
    }
}
