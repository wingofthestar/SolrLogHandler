package site.yourdiary.solrdemo.config;

import org.apache.solr.client.solrj.SolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrOperations;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

@Configuration
@EnableSolrRepositories(basePackages = { "site.yourdiary.solrdemo.respository" }, multicoreSupport = false)
public class ApplicationConfig {

    @Autowired
    private SolrClient solrClient;

    @Bean
    public SolrOperations solrTemplate(){
        return new SolrTemplate(solrClient);
    }
}
