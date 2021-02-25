package ru.nsu.g.beryanov.reader;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Service;

import ru.nsu.g.beryanov.mapper.NodeMapper;
import ru.nsu.g.beryanov.model.Node;
import ru.nsu.g.beryanov.model.ObjectFactory;
import ru.nsu.g.beryanov.service.CRUDNodeService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

import java.io.InputStream;
import java.util.Objects;

@Log4j2
@Service
@RequiredArgsConstructor
public class XMLWrapperReader {
    private final CRUDNodeService CRUDNodeService;
    private final NodeMapper nodeMapper;

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
                    log.info("Объект '{}' сохранен в базу данных...", CRUDNodeService.create(nodeMapper.toDtoFromXMLWrapper(node)));
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
