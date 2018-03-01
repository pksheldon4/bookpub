package com.example.bookpub.controller;

import com.example.bookpub.editor.IsbnEditor;
import com.example.bookpub.entity.Author;
import com.example.bookpub.entity.Book;
import com.example.bookpub.entity.Reviewer;
import com.example.bookpub.model.Isbn;
import com.example.bookpub.repository.BookRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/books")
public class BookController {

    private Log logger = LogFactory.getLog(BookController.class);

    @Autowired
    private BookRepository bookRepository;


    @RequestMapping(value = "", method = RequestMethod.GET)
    public Iterable<Book> getAllBooks() {
        Iterable<Book> books = bookRepository.findAll();
        logger.info("returning books");
        return books;
    }

    @RequestMapping(value = "/{isbn}", method = RequestMethod.GET)
    public Book getBook(@PathVariable Isbn isbn) {
        Assert.notNull(isbn, "isbn cannot be null.");
        logger.info("Find Book for ISBN: " + isbn.toString());
        return bookRepository.findBookByIsbn(isbn.toString());
    }

    @RequestMapping(value = "/{isbn}/reviewers", method = RequestMethod.GET)
    public Set<Reviewer> getReviewers(@PathVariable("isbn") Book book) {
        logger.info("Find reviewers for Book with ISBN: " + book.getIsbn());
        logger.info("Book reviewer count = " + book.getReviewers().size());
        return book.getReviewers();
    }


    @RequestMapping(value = "/{isbn}/author", method = RequestMethod.GET)
    public Author getAuthor(@PathVariable("isbn") Book book) {
        logger.info("Find reviewers for Book with ISBN: " + book.getIsbn());
        return book.getAuthor();
    }



    @InitBinder
    public void initBinder(WebDataBinder binder) {
        logger.info("initializing WebDataBinder - IsbnEditor");
        binder.registerCustomEditor(Isbn.class, new IsbnEditor());
    }
}
