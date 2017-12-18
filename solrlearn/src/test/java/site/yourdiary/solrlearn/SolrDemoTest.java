package site.yourdiary.solrlearn;

import org.apache.solr.common.SolrDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.yourdiary.solrlearn.document.TodoDocument;
import site.yourdiary.solrlearn.repository.TodoDocumentRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SolrDemoTest {
    @Autowired
    private TodoDocumentRepository todoDocumentRepository;

    @Test
    public void testTodo(){
        TodoDocument todoDocument = new TodoDocument();
        todoDocument.setId("6");
        todoDocument.setName("三星手机");
        todoDocument.setDescription("三星手机在韩国销量领先");
        todoDocumentRepository.save(todoDocument);
        System.out.println("保存成功");
    }
}
