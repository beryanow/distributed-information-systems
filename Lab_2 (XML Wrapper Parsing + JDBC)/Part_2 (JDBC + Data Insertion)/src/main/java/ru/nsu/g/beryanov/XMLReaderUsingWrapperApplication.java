package ru.nsu.g.beryanov;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

import ru.nsu.g.beryanov.dao.NodeDAO;
import ru.nsu.g.beryanov.dao.TagDAO;
import ru.nsu.g.beryanov.dao.impl.NodeDAOImpl;
import ru.nsu.g.beryanov.dao.impl.TagDAOImpl;
import ru.nsu.g.beryanov.reader.XMLWrapperReader;
import ru.nsu.g.beryanov.service.NodeProcessingService;
import ru.nsu.g.beryanov.utility.DataBaseInitializer;
import ru.nsu.g.beryanov.utility.SQLStatements;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;

@Log4j2
public class XMLReaderUsingWrapperApplication {
    @SneakyThrows
    public static void main(String[] args) {
        String pathToArchiveFile = "src/main/resources/RU-NVS.osm.bz2";
        String pathToDDLFile = "src/main/resources/ddl/database.sql";

        DataBaseInitializer dataBaseInitializer = new DataBaseInitializer(
                "jdbc:postgresql://localhost:5432/info",
                "postgres", "postgres");

        dataBaseInitializer.initialize(pathToDDLFile);

        Connection connection = dataBaseInitializer.getConnection();
        NodeDAO nodeDAO = new NodeDAOImpl(connection.createStatement(),
                connection.prepareStatement(SQLStatements.GET_NODE_EXPRESSION),
                connection.prepareStatement(SQLStatements.CREATE_NODE_EXPRESSION),
                connection.prepareStatement(SQLStatements.CREATE_NODE_EXPRESSION));

        TagDAO tagDAO = new TagDAOImpl(connection.createStatement(),
                connection.prepareStatement(SQLStatements.GET_TAG_EXPRESSION),
                connection.prepareStatement(SQLStatements.CREATE_TAG_EXPRESSION),
                connection.prepareStatement(SQLStatements.CREATE_TAG_EXPRESSION));

        NodeProcessingService nodeProcessingService = new NodeProcessingService(100000, nodeDAO, tagDAO);
        XMLWrapperReader XMLWrapperReader = new XMLWrapperReader(nodeProcessingService);

        try (InputStream inputStream = new BZip2CompressorInputStream(new FileInputStream(pathToArchiveFile))) {
            XMLWrapperReader.process(inputStream);
        }

        log.info("Время выполнения вставки данных в БД : {} мс", nodeProcessingService.getInsertTime() / 1000000);
    }
}
