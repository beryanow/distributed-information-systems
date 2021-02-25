package ru.nsu.g.beryanov;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Log4j2
@RequiredArgsConstructor
@SpringBootApplication
public class XMLReaderRESTApplication {
    public static void main(String[] args) {
        SpringApplication.run(XMLReaderRESTApplication.class);
    }
}
