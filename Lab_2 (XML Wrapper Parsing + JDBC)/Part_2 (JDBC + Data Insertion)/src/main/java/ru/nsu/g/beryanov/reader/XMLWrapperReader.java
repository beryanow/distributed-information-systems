package ru.nsu.g.beryanov.reader;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import ru.nsu.g.beryanov.model.Node;
import ru.nsu.g.beryanov.model.ObjectFactory;
import ru.nsu.g.beryanov.service.NodeProcessingService;
import ru.nsu.g.beryanov.utility.Converter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

import java.io.InputStream;
import java.util.Objects;

@Log4j2
@RequiredArgsConstructor
public class XMLWrapperReader {
    private final NodeProcessingService nodeProcessingService;

    @SneakyThrows
    public void process(InputStream inputStream) {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = null;
        JAXBContext jaxbContext = JAXBContext.newInstance(Node.class, ObjectFactory.class, Node.Tag.class);

        log.info("Происходит парсинг XML файла из архива... ");

        try {
            reader = factory.createXMLStreamReader(inputStream);
            while (reader.hasNext()) {
                int event = reader.next();
                if (XMLStreamConstants.START_ELEMENT == event && "node".equals(reader.getLocalName())) {
                    Node node = revealNode(jaxbContext, reader);
                    nodeProcessingService.addBatchPreparedStatementNode(Converter.convertToNodeDTO(node));

                    // в зависимости от типа
                    if (nodeProcessingService.getInsertsAmount() == 3) {
                        log.info("Время вставки данных используя INSERT: {} записей в секунду", 300000.0 / ((double) nodeProcessingService.getInsertTime() / 1000000 / 1000));
                        System.exit(0);
                    }
                }
            }
        } finally {
            Objects.requireNonNull(reader).close();
        }

        log.info("Парсинг XML файла из архива завершен...");
    }

    @SneakyThrows
    private Node revealNode(JAXBContext jaxbContext, XMLStreamReader reader) {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        return (Node) unmarshaller.unmarshal(reader);
    }
}
