package ru.nsu.g.beryanov.dao;

import ru.nsu.g.beryanov.dto.NodeDTO;

import java.util.List;

public interface NodeDAO {
    NodeDTO getNode(long nodeId);

    void createInsertNode(NodeDTO nodeDTO);
    void createPreparedStatementNode(NodeDTO nodeDTO);
    void createBatchPreparedStatementNodes(List<NodeDTO> nodeDTOs);
}
