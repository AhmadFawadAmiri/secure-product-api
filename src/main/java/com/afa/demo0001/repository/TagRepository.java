package com.afa.demo0001.repository;

import com.afa.demo0001.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepositoy extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name);
}
