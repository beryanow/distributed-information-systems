package ru.nsu.g.beryanov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ru.nsu.g.beryanov.entity.NodeEntity;

import java.util.List;

@Repository
public interface NodeRepository extends JpaRepository<NodeEntity, Long> {
    @Query(value = "select * from nodes " +
            "where (acos(sin(pi() * ?1 / 180.0) * sin(pi() * nodes.latitude / 180.0) " +
            "+ cos(pi()* ?1 / 180.0) * cos(pi() * nodes.latitude / 180.0) " +
            "* cos(pi() * nodes.longitude / 180.0 - pi() * ?2 / 180.0)) * 6371) " +
            "* 1000 < ?3", nativeQuery = true)
    List<NodeEntity> getNearNodes(double latitude, double longitude, double radius);
}
