package ru.nsu.g.beryanov;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

import ru.nsu.g.beryanov.reader.XMLWrapperReader;

import java.io.FileInputStream;
import java.io.InputStream;

@Log4j2
public class XMLReaderUsingWrapperApplication {
    @SneakyThrows
    public static void main(String[] args) {
        String pathToArchiveFile = "src/main/resources/RU-NVS.osm.bz2";
        XMLWrapperReader XMLWrapperReader = new XMLWrapperReader();

        try (InputStream inputStream = new BZip2CompressorInputStream(new FileInputStream(pathToArchiveFile))) {
            XMLWrapperReader.process(inputStream);
        }
    }
}
