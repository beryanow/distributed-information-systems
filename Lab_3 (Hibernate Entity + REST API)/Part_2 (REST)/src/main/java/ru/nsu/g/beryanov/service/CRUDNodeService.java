package ru.nsu.g.beryanov.service;

import ru.nsu.g.beryanov.dto.NodeDTO;

public interface CRUDNodeService {
    NodeDTO create(NodeDTO nodeDTO);
    NodeDTO get(Long nodeId);
    NodeDTO update(Long nodeId, NodeDTO nodeDTO);
    void delete(Long nodeId);
}
