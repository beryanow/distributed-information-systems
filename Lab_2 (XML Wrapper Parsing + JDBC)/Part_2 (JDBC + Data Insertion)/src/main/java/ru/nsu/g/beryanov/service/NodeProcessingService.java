package ru.nsu.g.beryanov.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import ru.nsu.g.beryanov.dao.NodeDAO;
import ru.nsu.g.beryanov.dao.TagDAO;
import ru.nsu.g.beryanov.dto.NodeDTO;
import ru.nsu.g.beryanov.dto.TagDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Setter
@Getter
@RequiredArgsConstructor
public class NodeProcessingService {
    private final int bufferThreshold;

    private final NodeDAO nodeDAO;
    private final TagDAO tagDAO;

    private final List<NodeDTO> nodeBuffer = new ArrayList<>();

    private long insertTime = 0;
    private long amount = 0;
    private int insertsAmount = 0;

    public void putNodeWithPreparedStatement(NodeDTO node) {
        long start = System.nanoTime();
        nodeDAO.insertPreparedNode(node);

        for (TagDTO tag : node.getTags()) {
            tagDAO.insertPreparedTag(tag);
        }

        insertTime += System.nanoTime() - start;

        insertsAmount++;
    }

    public void putNode(NodeDTO node) {
        long start = System.nanoTime();
        nodeDAO.insertNode(node);

        for (TagDTO tag : node.getTags()) {
            tagDAO.insertTag(tag);
        }

        insertTime += System.nanoTime() - start;

        insertsAmount++;
    }

    public void putNodeBuffered(NodeDTO node) {
        nodeBuffer.add(node);

        if (nodeBuffer.size() == bufferThreshold) {
            long start = System.nanoTime();
            flush();
            insertTime += System.nanoTime() - start;

            insertsAmount++;
        }

    }

    public void flush() {
        nodeDAO.batchInsertNodes(nodeBuffer);

        List<TagDTO> tags = nodeBuffer.stream()
                .flatMap(node -> node.getTags().stream())
                .collect(Collectors.toList());
        tagDAO.batchInsertTags(tags);

        nodeBuffer.clear();
    }

    public NodeDTO getNode(long id) {
        return nodeDAO.getNode(id);
    }
}
