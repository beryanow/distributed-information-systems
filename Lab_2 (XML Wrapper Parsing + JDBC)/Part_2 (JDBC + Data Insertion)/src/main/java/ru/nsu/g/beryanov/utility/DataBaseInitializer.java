package ru.nsu.g.beryanov.utility;

import lombok.Getter;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@Getter
public class DataBaseInitializer {
    private final Connection connection;

    @SneakyThrows
    public DataBaseInitializer(String dataBaseUrl, String dataBaseUername, String dataBasePassword) {
        connection = DriverManager.getConnection(dataBaseUrl, dataBaseUername, dataBasePassword);
    }

    @SneakyThrows
    public void initialize(String pathToDDLFile) {
        byte[] DDLFileByteArray = Files.readAllBytes(Paths.get(pathToDDLFile));
        int DDLFileByteArraySize = DDLFileByteArray.length;

        Statement statement = connection.createStatement();
        String SQLRequest = new String(DDLFileByteArray, 0, DDLFileByteArraySize);

        statement.execute(SQLRequest);
    }
}
