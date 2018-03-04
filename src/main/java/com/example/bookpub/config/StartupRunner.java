package com.example.bookpub.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Order(Ordered.LOWEST_PRECEDENCE - 1)
public class StartupRunner implements CommandLineRunner {
    protected final Log logger = LogFactory.getLog(getClass());

//    @Autowired
//    private BookRepository bookRepository;
//    @Autowired
//    private AuthorRepository authorRepository;
//    @Autowired
//    private PublisherRepository publisherRepository;
//    @Autowired
//    private ReviewerRepository reviewerRepository;

    @Override
    public void run(String... args) throws Exception {

//        Author author = new Author("Alex", "Antonov");
//        author = authorRepository.save(author);
//        Publisher publisher = new Publisher("Packt");
//        publisher = publisherRepository.save(publisher);
//        final String isbn = "978-1-78528-415-1";
//        Book book = new Book(isbn, "Spring Boot Recipes 2.0", author, publisher);
//        bookRepository.save(book);
//        Reviewer reviewer = new Reviewer("Preston", "Sheldon");
//        reviewerRepository.save(reviewer);
//        book.getReviewers().add(reviewer);
//        bookRepository.save(book);
//        logger.debug("Number of books: " + bookRepository.count());
//        Book savedBook = bookRepository.findBookByIsbn(isbn);
//        Assert.notNull(savedBook, "Saved book should not be null.");
//        Assert.notNull(savedBook.getAuthor(), "Saved book should have an Author");
//        Assert.notNull(savedBook.getPublisher(), "Saved book should have a Publisher");
//        logger.debug("Saved Book publisher = " + savedBook.getPublisher().getName());
//        logger.debug("Saved Book reviewers = " + savedBook.getReviewers().size());
    }


//    @Scheduled(initialDelay = 1000, fixedRate = 10000)
//    public void run() {
//        logger.info("Number of books: " + bookRepository.count());
//    }
}