package com.luxoft.highperformance.bookserver;

import com.luxoft.highperformance.bookserver.model.Book;
import com.luxoft.highperformance.bookserver.model.Word;
import com.luxoft.highperformance.bookserver.repositories.BookRepository;
import com.luxoft.highperformance.bookserver.repositories.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("books")
@EnableCaching
public class BookController {

    public final int BOOKS_AMOUNT=100_000;

    @Autowired
    BookRepository bookRepository;
    @Autowired
    WordRepository wordRepository;

    @GetMapping("keywords/{keywordsString}")
    public List<Book> getBookByTitle(@PathVariable String keywordsString) {
        String[] keywords = keywordsString.split(" ");
        if (keywords.length == 1) {
            return bookRepository.findAllByTitleContaining(keywords[0]);
        } else if (keywords.length == 2) {
            return bookRepository.findAllByTitleContainingAndTitleContaining(
                keywords[0], keywords[1]);
        } else if (keywords.length == 3) {
            return bookRepository.findAllByTitleContainingAndTitleContainingAndTitleContaining(
                keywords[0], keywords[1], keywords[2]);
        }
        return null;
    }

    @GetMapping("/find/{keywordsString}")
//    @Cacheable(cacheNames="books")
    public List<Book> findByTitle(@PathVariable String keywordsString) {

        List<Book> books;

        String[] keywords = keywordsString.split(" ");
        if (keywords.length == 1) {
            //books = bookRepository.findByTitleContaining(keywords[0]);
            books = bookRepository.findOneWord(keywords[0]);
            return books;
        } else if (keywords.length == 2) {
            /*
            books = bookRepository.findByTitleContainingAndTitleContaining(
                    keywords[0], keywords[1]);

             */
            books = bookRepository.findTwoWord(keywords[0], keywords[1]);
            return books;
        } else if (keywords.length == 3) {
            /*
            books = bookRepository.findByTitleContainingAndTitleContainingAndTitleContaining(
                    keywords[0], keywords[1], keywords[2]);
             */
            books = bookRepository.findThreeWord(keywords[0], keywords[1], keywords[2]);
            return books;
        }
        return null;
    }

    @GetMapping("/init")
    @Transactional
    public void initDB() {
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

    @GetMapping("/random")
    public Book getBookRandom() {
        Random random = new Random();
        int index = random.nextInt(BOOKS_AMOUNT);
        List<Book> all = bookRepository.findAll();
        return all.get(index);
    }

    @GetMapping
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @PostMapping
    public Book addBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

}
