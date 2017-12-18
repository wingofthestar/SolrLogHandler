package site.yourdiary.loghandle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.yourdiary.loghandle.entity.solr.LogInfo;
import site.yourdiary.loghandle.respository.solr.SolrLogInfoRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SolrQueryTest {
    @Autowired
    private SolrLogInfoRepository logInfoSolrRepository;

    @Test
    public void queryIndex(){
        LogInfo logInfo = logInfoSolrRepository.findById("8aea7904604e3ed101604e3f28e20004");
        System.out.println(logInfo.toString());
    }
}
