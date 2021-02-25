package ru.nsu.g.beryanov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.nsu.g.beryanov.entity.TagEntity;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, Long> {}
