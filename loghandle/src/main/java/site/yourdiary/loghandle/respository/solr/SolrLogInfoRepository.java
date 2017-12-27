package site.yourdiary.loghandle.respository.solr;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.repository.Highlight;
import org.springframework.data.solr.repository.SolrCrudRepository;
import site.yourdiary.loghandle.entity.solr.LogInfo;
import sun.rmi.runtime.Log;

import java.util.List;

public interface SolrLogInfoRepository extends SolrCrudRepository<LogInfo, String>{

    void deleteById(String id);

    LogInfo findById(String id);

    List<LogInfo> findByContent(String content);

    Long countByLevel(String Level);

    List<LogInfo> findByLevel(String Level);

    Page<LogInfo> findLogInfoByTimestamp(String timestamp, Pageable pageable);

    Page<LogInfo> findLogInfoPageByPid(String pid, Pageable pageable);

    Page<LogInfo> findLogInfoPageByLevel(String Level, Pageable pageable);

    Page<LogInfo> findLogInfoPageByContent(String content, Pageable pageable);

    Page<LogInfo> findLogInfoPageByTimestampAndPid(String timestamp, String pid, Pageable pageable);

    Page<LogInfo> findLogInfoPageByTimestampAndLevel(String timestamp, String level, Pageable pageable);

    Page<LogInfo> findLogInfoPageByTimestampAndContent(String timestamp, String level, Pageable pageable);

    Page<LogInfo> findLogInfoPageByPidAndLevel(String pid, String level, Pageable pageable);

    Page<LogInfo> findLogInfoPageByPidAndContent(String pid, String content, Pageable pageable);

    Page<LogInfo> findLogInfoPageByLevelAndContent(String level, String content, Pageable pageable);

    Page<LogInfo> findLogInfoPageByTimestampAndPidAndLevel(String timestamp, String pid, String level, Pageable pageable);

    Page<LogInfo> findLogInfoPageByTimestampAndPidAndContent(String timestamp, String pid, String content, Pageable pageable);

    Page<LogInfo> findLogInfoPageByTimestampAndLevelAndContent(String timestamp, String level, String content, Pageable pageable);

    Page<LogInfo> findLogInfoPageByPidAndLevelAndContent(String pid, String level, String content, Pageable pageable);

    Page<LogInfo> findLogInfoPageByTimestampAndPidAndLevelAndContent(String timestamp, String pid, String level, String content, Pageable pageable);

    @Highlight(prefix = "<b>", postfix = "</b>")
    HighlightPage<LogInfo> findLogInfoHighlightPageByContent(String content, Pageable pageable);

    List<LogInfo> findLogInfoByLevelAndContent(String level, String content);

    Long countLogInfoByLevelAndContent(String level, String content);


}
