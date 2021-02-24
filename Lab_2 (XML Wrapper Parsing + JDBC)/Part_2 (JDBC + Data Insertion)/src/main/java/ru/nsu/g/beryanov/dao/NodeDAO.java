package ru.nsu.g.beryanov.dao;

import ru.nsu.g.beryanov.dto.NodeDTO;

import java.util.List;

public interface NodeDAO {
    NodeDTO getNode(long nodeId);
    void insertNode(NodeDTO nodeDTO);
    void insertPreparedNode(NodeDTO nodeDTO);
    void batchInsertNodes(List<NodeDTO> nodeDTOs);
}
