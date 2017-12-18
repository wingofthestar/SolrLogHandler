package site.yourdiary.solrlearn.repository;

import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.data.solr.repository.SolrRepository;
import site.yourdiary.solrlearn.document.TodoDocument;

public interface TodoDocumentRepository extends SolrCrudRepository<TodoDocument, String> {

}
