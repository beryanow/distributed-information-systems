package ru.nsu.g.beryanov;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ru.nsu.g.beryanov.reader.XMLWrapperReader;

import java.io.FileInputStream;
import java.io.InputStream;

@Log4j2
@RequiredArgsConstructor
@SpringBootApplication
public class XMLReaderUsingWrapperApplication implements CommandLineRunner {
    private final XMLWrapperReader xmlWrapperReader;

    @Value("${path.to.archive.file}")
    private String pathToArchiveFile;

    public static void main(String[] args) {
        SpringApplication.run(XMLReaderUsingWrapperApplication.class);
    }

    @SneakyThrows
    @Override
    public void run(String... args) {
        try (InputStream inputStream = new BZip2CompressorInputStream(new FileInputStream(pathToArchiveFile))) {
            xmlWrapperReader.process(inputStream);
        }
    }
}
