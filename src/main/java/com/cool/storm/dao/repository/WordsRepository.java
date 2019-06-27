package com.cool.storm.dao.repository;

import com.cool.storm.dao.entity.Words;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordsRepository extends JpaRepository<Words, Integer> {
}
