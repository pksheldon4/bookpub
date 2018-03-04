package com.example.bookpub

import com.example.bookpub.repository.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator
import org.springframework.test.web.servlet.MockMvc
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.context.ConfigurableWebApplicationContext
import spock.lang.Specification

import javax.sql.DataSource

import static org.hamcrest.CoreMatchers.containsString
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class SpockBookRepositorySpecification extends Specification {

    @Autowired
    private ConfigurableWebApplicationContext context

    @Autowired
    private DataSource ds;

    @Autowired
    private BookRepository repository;

    @Autowired
    private MockMvc mockMvc;

    void setup() {
        ResourceDatabasePopulator populator =
                new ResourceDatabasePopulator(
                        context.getResource("classpath:/packt-books.sql"));
        DatabasePopulatorUtils.execute(populator, ds);
    }

    @Transactional
    def "Test RESTful GET"() {
        when:
        def result = mockMvc.perform(get("/books/${isbn}"));

        then:
        result.andExpect(status().isOk())
        result.andExpect(
                content().string(containsString(title))
        );

        where:
        isbn               | title
        "978-1-78398-478-7"|"Orchestrating Docker"
        "978-1-78528-415-1"|"Spring Boot Recipes 2.0"
        "978-1-23456-789-1"|"Romeo and Juliet"
    }

//    @Transactional
//    def "Insert another book"() {
//        setup:
//        def existingBook = repository.findBookByIsbn("978-1-78528-415-1")
//        def newBook = new Book("978-1-12345-678-9", "Some Future Book", existingBook.getAuthor(), existingBook.getPublisher())
//
//        expect:
//        repository.count() == 3
//
//        when:
//        def savedBook = repository.save(newBook)
//
//        then:
//        repository.count() == 4
//        savedBook.id > -1
//    }
}