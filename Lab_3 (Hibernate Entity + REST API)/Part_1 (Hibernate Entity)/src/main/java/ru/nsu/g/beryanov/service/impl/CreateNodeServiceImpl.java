package ru.nsu.g.beryanov.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import ru.nsu.g.beryanov.dto.NodeDTO;
import ru.nsu.g.beryanov.entity.NodeEntity;
import ru.nsu.g.beryanov.mapper.NodeMapper;
import ru.nsu.g.beryanov.repository.NodeRepository;
import ru.nsu.g.beryanov.service.CreateNodeService;

@Service
@RequiredArgsConstructor
public class CreateNodeServiceImpl implements CreateNodeService {
    private final NodeRepository nodeRepository;
    private final NodeMapper nodeMapper;

    @Override
    public NodeDTO create(NodeDTO nodeDTO) {
        NodeEntity nodeEntity = nodeMapper.toEntity(nodeDTO);

        return nodeMapper.toDto(nodeRepository.save(nodeEntity));
    }
}
