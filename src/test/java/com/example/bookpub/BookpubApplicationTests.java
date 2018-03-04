package com.example.bookpub;

import com.example.bookpub.entity.Book;
import com.example.bookpub.repository.BookRepository;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@Sql(scripts = "classpath:/test-data.sql")
public class BookpubApplicationTests {

	@Autowired
	private WebApplicationContext context;
	@Autowired
	private TestRestTemplate restTemplate;
	@Autowired
	private BookRepository repository;

    @Autowired
    private DataSource ds;

    @LocalServerPort
	private int port;

	private MockMvc mockMvc;

	private static boolean loadDataFixtures = true;

	@Before
	public void setupMockMvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

//    @Before
//    public void loadDataFixtures() {
//        if (loadDataFixtures) {
//            ResourceDatabasePopulator populator =
//                    new ResourceDatabasePopulator(context.getResource("classpath:/test-data.sql"));
//            DatabasePopulatorUtils.execute(populator, ds);
//            loadDataFixtures = false;
//        }
//    }


    @Test
	public void contextLoads() {
		Assert.assertEquals(3, repository.count());
	}

	@Test
	public void webappBookIsbnApi() {
		Book book = restTemplate.getForObject("/books/978-1-78528-415-1", Book.class);
		Assert.assertNotNull(book);
		Assert.assertEquals("Packt", book.getPublisher().getName());
	}

    @Test
    public void webappBookApi() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books/978-1-78528-415-1")).
                andExpect(status().isOk()).andExpect(content().
                contentType(MediaType.parseMediaType
                        ("application/json;charset=UTF-8"))).
                andExpect(content().
                        string(CoreMatchers.containsString("Packt"))).
                andExpect(jsonPath("$.title").value("Spring Boot Recipes 2.0"));
    }

//	@Test
//	public void webappPublisherApi() throws Exception {
//		mockMvc.perform(MockMvcRequestBuilders.get("/publishers/1")).
//				andExpect(status().isOk()).andExpect(content().
//				contentType(MediaType.parseMediaType
//						("application/hal+json;charset=UTF-8"))).
//				andExpect(content().
//						string(CoreMatchers.containsString("Packt"))).
//				andExpect(jsonPath("$.name").value("Packt"));
//	}
}