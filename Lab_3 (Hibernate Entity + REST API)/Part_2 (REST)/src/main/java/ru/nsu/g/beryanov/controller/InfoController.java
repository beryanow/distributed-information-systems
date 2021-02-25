package ru.nsu.g.beryanov.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.nsu.g.beryanov.dto.NodeDTO;
import ru.nsu.g.beryanov.reader.XMLWrapperReader;
import ru.nsu.g.beryanov.service.CRUDNodeService;
import ru.nsu.g.beryanov.service.SearchNodeService;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

@Log4j2
@RestController
@RequestMapping("/info/")
@RequiredArgsConstructor
public class InfoController {
    private final XMLWrapperReader xmlWrapperReader;
    private final CRUDNodeService CRUDNodeService;
    private final SearchNodeService searchNodeService;

    @SneakyThrows
    @PostMapping("/initialize")
    public void initialize(@RequestBody HttpEntity<String> pathToArchiveFile) {
        try (InputStream inputStream = new BZip2CompressorInputStream(new FileInputStream(Objects.requireNonNull(pathToArchiveFile.getBody())))) {
            xmlWrapperReader.process(inputStream);
        }
    }

    @SneakyThrows
    @PostMapping(value = "/node/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NodeDTO> createNode(@RequestBody HttpEntity<NodeDTO> nodeDTORequestEntity) {
        log.info("Вызван метод {}", "/node/create");

        return ResponseEntity.ok(CRUDNodeService.create(nodeDTORequestEntity.getBody()));
    }

    @SneakyThrows
    @GetMapping(value = "/node/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NodeDTO> getNode(@PathVariable("id") Long nodeId) {
        log.info("Вызван метод {}", "/node/get");

        return ResponseEntity.ok(CRUDNodeService.get(nodeId));
    }

    @SneakyThrows
    @PutMapping(value = "/node/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NodeDTO> updateNode(@PathVariable("id") Long nodeId, @RequestBody HttpEntity<NodeDTO> nodeDTORequestEntity) {
        log.info("Вызван метод {}", "/node/update");

        return ResponseEntity.ok(CRUDNodeService.update(nodeId, nodeDTORequestEntity.getBody()));
    }

    @DeleteMapping("/node/delete/{id}")
    public void delete(@PathVariable("id") Long nodeId) {
        log.info("Вызван метод {}", "/node/delete");

        CRUDNodeService.delete(nodeId);
    }

    @GetMapping("/node/search")
    public ResponseEntity<List<NodeDTO>> search(@RequestParam("latitude") Double latitude, @RequestParam("longitude") Double longitude, @RequestParam("radius") Double radius) {
        log.info("Вызван метод {}", "/node/search");

        return ResponseEntity.ok(searchNodeService.search(latitude, longitude, radius));
    }
}
