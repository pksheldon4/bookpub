package com.example.bookpub;

import com.example.bookpub.config.StartupRunner;
import com.example.bookpubstarter.dbcount.EnableDbCounting;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableDbCounting
public class BookpubApplication {

//	protected final Log logger = LogFactory.getLog(getClass());

	public static void main(String[] args) {
		SpringApplication.run(BookpubApplication.class, args);
	}

	@Bean
    public StartupRunner schedulerRunner() {
	    return new StartupRunner();
    }

//	@Bean
//	public DbCountRunner dbCountRunner(Collection<CrudRepository> repositories) {
//		return new DbCountRunner(repositories) {
//			@Override
//			public void run(String... args) throws Exception {
//				logger.info("Manually Declared DbCountRunner");
//			}
//		};
//	}
}
