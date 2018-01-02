package site.yourdiary.loghandle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "site.yourdiary.loghandle.respository.jpa")
@EnableSolrRepositories(basePackages = "site.yourdiary.loghandle.respository.solr")
@EnableScheduling
public class LoghandleApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoghandleApplication.class, args);
	}
}
