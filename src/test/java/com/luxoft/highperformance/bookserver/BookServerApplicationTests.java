package com.luxoft.highperformance.bookserver;

import com.luxoft.highperformance.bookserver.model.Book;
import com.luxoft.highperformance.bookserver.model.Word;
import com.luxoft.highperformance.bookserver.repositories.BookRepository;
import com.luxoft.highperformance.bookserver.repositories.WordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@SpringBootTest
@Commit
class BookServerApplicationTests {

    public final int BOOKS_AMOUNT = 100_000;

    @Autowired
    BookRepository bookRepository;
    @Autowired
    WordRepository wordRepository;

    @Test
    void contextLoads() {
    }

/*
    @Test
    public void removeAllBooks() {
        bookRepository.deleteAll();
    }
    @Test
    public void removeAllWords() {
        wordRepository.deleteAll();
    }
*/

    @Test
    @Transactional
    public void addBooks() {
        Random random = new Random();
        for (int i=0; i<BOOKS_AMOUNT; i++) {
            String title = "Book"+random.nextInt(BOOKS_AMOUNT);
            String authorName = "AuthorName"+random.nextInt(BOOKS_AMOUNT)+" ";
            String authorSurname = "AuthorSurname"+random.nextInt(BOOKS_AMOUNT);
            String bookName = title+" by "+authorName+authorSurname;

            Book book = new Book();
            book.setTitle(bookName);
            book.setId(i);
            bookRepository.save(book);

            String[] words = bookName.split(" ");
            for (String wordName: words) {
                Word word = new Word();
                word.setTitle(wordName);
                word.setBookId(i);
                wordRepository.save(word);
            }
        }
    }

/*
    @Test
    @Transactional
    public void addBooks() {
        Random random = new Random();
        for (int i=0; i<BOOKS_AMOUNT; i++) {
            String title = "Book"+random.nextInt(BOOKS_AMOUNT);
            String authorName = "AuthorName"+random.nextInt(BOOKS_AMOUNT)+" ";
            String authorSurname = "AuthorSurname"+random.nextInt(BOOKS_AMOUNT);
            Book book = new Book();
            book.setTitle(title+" by "+authorName+authorSurname);
            bookRepository.save(book);
        }
    }
 */
/*
    @Test
    public void showRandomBooks() {
        Random random = new Random();
        List<Book> all = bookRepository.findAll();
        System.out.println("Found "+all.size()+" books");
        for (int i=0;i<10;i++) {
            int index = random.nextInt(BOOKS_AMOUNT);
            System.out.println(all.get(index));
        }
    }
*/
}
