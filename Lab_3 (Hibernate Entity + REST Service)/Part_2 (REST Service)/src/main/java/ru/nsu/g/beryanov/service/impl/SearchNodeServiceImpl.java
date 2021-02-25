package ru.nsu.g.beryanov.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import ru.nsu.g.beryanov.dto.NodeDTO;
import ru.nsu.g.beryanov.mapper.NodeMapper;
import ru.nsu.g.beryanov.repository.NodeRepository;
import ru.nsu.g.beryanov.service.SearchNodeService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchNodeServiceImpl implements SearchNodeService {
    private final NodeRepository nodeRepository;
    private final NodeMapper nodeMapper;

    @Override
    public List<NodeDTO> search(Double latitude, Double longitude, Double radius) {
        return nodeMapper.toDtos(nodeRepository.getNearNodes(latitude, longitude, radius));
    }
}
