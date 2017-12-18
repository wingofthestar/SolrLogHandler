package site.yourdiary.loghandle.respository.solr;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.repository.Highlight;
import org.springframework.data.solr.repository.SolrCrudRepository;
import site.yourdiary.loghandle.entity.solr.LogInfo;

import java.util.List;

public interface SolrLogInfoRepository extends SolrCrudRepository<LogInfo, String> {

    void deleteById(String id);

    LogInfo findById(String id);

    List<LogInfo> findByContent(String content);

    Long countByLevel(String Level);

    List<LogInfo> findByLevel(String Level);

    Page<LogInfo> findLogInfoPageByLevel(String Level, Pageable pageable);

    Page<LogInfo> findLogInfoPageByContent(String content, Pageable pageable);

    @Highlight(prefix = "<b>", postfix = "</b>")
    HighlightPage<LogInfo> findLogInfoHighlightPageByContent(String content, Pageable pageable);

}
