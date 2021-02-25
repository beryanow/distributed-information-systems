package ru.nsu.g.beryanov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.g.beryanov.entity.NodeEntity;

@Repository
public interface NodeRepository extends JpaRepository<NodeEntity, Long> {}
