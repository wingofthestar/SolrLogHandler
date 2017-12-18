package site.yourdiary.solrlearn.controller;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class SolrController {
    @Autowired
    private SolrClient client;

    @RequestMapping("/query/{id}")
    public String testSolrQuery(@PathVariable("id") String id) throws IOException, SolrServerException {
        SolrDocument document = client.getById("new_core", id);
        System.out.println(document);
        return document.toString();
    }

}
