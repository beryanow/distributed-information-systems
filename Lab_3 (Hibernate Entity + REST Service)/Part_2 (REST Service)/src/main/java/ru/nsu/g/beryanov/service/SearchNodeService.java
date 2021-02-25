package ru.nsu.g.beryanov.service;

import ru.nsu.g.beryanov.dto.NodeDTO;

import java.util.List;

public interface SearchNodeService {
    List<NodeDTO> search(Double latitude, Double longitude, Double radius);
}
