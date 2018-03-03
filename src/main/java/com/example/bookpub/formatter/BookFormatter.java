package com.example.bookpub.formatter;

import com.example.bookpub.entity.Book;
import com.example.bookpub.repository.BookRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class BookFormatter implements Formatter<Book> {

    private Log logger = LogFactory.getLog(BookFormatter.class);

    private BookRepository bookRepository;

    public BookFormatter(BookRepository repository) {
        this.bookRepository = repository;
    }

    @Override
    public Book parse(String bookIdentifier, Locale locale) throws ParseException {

        logger.info("BootFormatter.parse(" + bookIdentifier + ")");
        Book book = bookRepository.findBookByIsbn(bookIdentifier);
        if (book == null) {
            try {
                Long id = Long.valueOf(bookIdentifier);
                book = bookRepository.findById(id).get();
            } catch (Exception e) {
                logger.error(e.getLocalizedMessage(), e);
            }
        }
        return book;
    }

    @Override
    public String print(Book book, Locale locale) {

        logger.info("BootFormatter.print(" + book.getIsbn() + ")");
        return book.getIsbn();
    }
}
