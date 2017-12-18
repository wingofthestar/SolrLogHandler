package site.yourdiary.solrlearn;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SolrJManager {
    private static final Logger logger = LoggerFactory.getLogger(SolrJManager.class);
    @Autowired
    private SolrClient solrClient;

    @Test
    public void testAdd() throws IOException, SolrServerException {
        SolrInputDocument document = new SolrInputDocument();
        document.setField("id", "5");
        document.setField("name", "good idea");

        //添加
        solrClient.add(document);
        solrClient.commit();
        logger.info("添加成功");

//        SolrDocument solrDocument = solrClient.getById("new_core", "3");
//        System.out.println(solrDocument);
    }

    @Test
    public void testDelete() throws IOException, SolrServerException {
        solrClient.deleteById("haha");
        solrClient.commit();

        SolrDocument solrDocument = solrClient.getById("haha");
        if (solrDocument != null){
            logger.error("deleteById(" + "haha" + ")" + "失败");
        }

    }

    @Test
    public void testGet() throws IOException, SolrServerException {
        SolrDocument document = solrClient.getById("1");
//        solrClient.commit();
        Assert.assertNotNull(document);
        System.out.println(document);

    }

    @Test
    public void testUpdate() throws IOException, SolrServerException {
        SolrInputDocument document = new SolrInputDocument();
        document.setField("id", "5");
        document.setField("name", "平板电脑ipad");
        document.setField("description", "ipad是非常美观的平板，搭载ios系统");
        solrClient.add(document);
        solrClient.commit();

        SolrDocument solrDocument =  solrClient.getById("5");
        Assert.assertNotNull(solrDocument);
        if (solrDocument != null){
            logger.info(solrDocument.toString());
        }else{
            logger.error("操作失败");
        }
    }

    @Test
    public void testSearch() throws IOException, SolrServerException {
        //查询 关键词
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.set("q", "name:手机");
//        solrQuery.set("fq", "description:苹果");
        solrQuery.addSort("id", SolrQuery.ORDER.desc);

        //分页
        solrQuery.setStart(0);
        solrQuery.setRows(3);

        //打开开关
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("name");
        solrQuery.setHighlightSimplePre("<span style='color:red'>");
        solrQuery.setHighlightSimplePost("</span>");


        //执行查询
        QueryResponse response = solrClient.query(solrQuery);
        //文档结果集
        SolrDocumentList docs = response.getResults();

        /**
         * Map k id V Map
         * Map k fild V List
         * List list.get(0)
         */
        Map<String, Map<String , List<String>>> highlighting = response.getHighlighting();
        //总条数
        long numFound = docs.getNumFound();
        System.out.println(numFound);

        for (SolrDocument doc:docs) {
            System.out.println(doc.get("id"));
            System.out.println(doc.get("name"));
            System.out.println(doc.get("description"));
            System.out.println("===========================");
             Map<String, List<String>> map =highlighting.get(doc.get("id"));
             List<String> list = map.get("name");
            System.out.println(list.get(0));
        }
    }
}
