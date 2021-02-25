package ru.nsu.g.beryanov.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import ru.nsu.g.beryanov.dto.NodeDTO;
import ru.nsu.g.beryanov.entity.NodeEntity;
import ru.nsu.g.beryanov.exception.NoNodeException;
import ru.nsu.g.beryanov.mapper.NodeMapper;
import ru.nsu.g.beryanov.repository.NodeRepository;
import ru.nsu.g.beryanov.repository.TagRepository;
import ru.nsu.g.beryanov.service.CRUDNodeService;

@Service
@RequiredArgsConstructor
public class CRUDNodeServiceImpl implements CRUDNodeService {
    private final NodeRepository nodeRepository;
    private final TagRepository tagRepository;
    private final NodeMapper nodeMapper;

    @Override
    public NodeDTO create(NodeDTO nodeDTO) {
        NodeEntity nodeEntity = nodeMapper.toEntity(nodeDTO);

        return nodeMapper.toDto(nodeRepository.save(nodeEntity));
    }

    @Override
    public NodeDTO get(Long nodeId) {
        return nodeMapper.toDto(nodeRepository.findById(nodeId).orElseThrow(() -> new NoNodeException(nodeId)));
    }

    @Override
    public NodeDTO update(Long nodeId, NodeDTO nodeDTO) {
        delete(nodeId);
        nodeDTO.setId(nodeId);

        return create(nodeDTO);
    }

    @Override
    public void delete(Long nodeId) {
        NodeEntity existingNodeEntity = nodeRepository.findById(nodeId).orElseThrow(() -> new NoNodeException(nodeId));

        nodeRepository.delete(existingNodeEntity);
    }
}
