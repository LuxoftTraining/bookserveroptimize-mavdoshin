package com.luxoft.highperformance.bookserver.repositories;


import com.luxoft.highperformance.bookserver.model.Word;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.function.Function;

public interface WordRepository extends JpaRepository<Word, Integer> {
    List<Word> findAllByTitleContaining(String keyword);
    List<Word> findAllByTitleContainingAndTitleContaining(String keyword1, String keyword2);
    List<Word> findAllByTitleContainingAndTitleContainingAndTitleContaining(
        String keyword1, String keyword2, String keyword3);


    List<Word> findByTitleContaining(String keyword);
    List<Word> findByTitleContainingAndTitleContaining(String keyword1, String keyword2);
    List<Word> findByTitleContainingAndTitleContainingAndTitleContaining(String keyword1, String keyword2, String keyword3);
}
