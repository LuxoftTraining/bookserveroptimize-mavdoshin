package com.luxoft.highperformance.bookserver.repositories;


import com.luxoft.highperformance.bookserver.model.Book;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.function.Function;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findAllByTitleContaining(String keyword);
    List<Book> findAllByTitleContainingAndTitleContaining(String keyword1, String keyword2);
    List<Book> findAllByTitleContainingAndTitleContainingAndTitleContaining(
        String keyword1, String keyword2, String keyword3);


    List<Book> findByTitleContaining(String keyword);
    List<Book> findByTitleContainingAndTitleContaining(String keyword1, String keyword2);
    List<Book> findByTitleContainingAndTitleContainingAndTitleContaining(String keyword1, String keyword2, String keyword3);

    @Query(value = "  SELECT * FROM Book WHERE id in \n" +
            "   (\n" +
            "    (SELECT book_id FROM Word WHERE title=:word1) \n" +
            "    INTERSECT \n" +
            "    (SELECT book_id FROM Word WHERE title=:word2) \n" +
            "    INTERSECT \n" +
            "    (SELECT book_id FROM Word WHERE title=:word3) \n" +
            "   )", nativeQuery = true)
    List<Book> findThreeWord(@Param("word1") String word1, @Param("word2") String word2, @Param("word3") String word3);

    @Query(value = "  SELECT * FROM Book WHERE id in \n" +
            "   (\n" +
            "    (SELECT book_id FROM Word WHERE title=:word1) \n" +
            "    INTERSECT \n" +
            "    (SELECT book_id FROM Word WHERE title=:word2) \n" +
            "   )", nativeQuery = true)
    List<Book> findTwoWord(@Param("word1") String word1, @Param("word2") String word2);

    @Query(value = "  SELECT * FROM Book WHERE id in \n" +
            "(SELECT book_id FROM Word WHERE title=:word1)", nativeQuery = true)
    List<Book> findOneWord(@Param("word1") String word1);
}
