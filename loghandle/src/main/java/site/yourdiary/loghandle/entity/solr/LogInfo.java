package site.yourdiary.loghandle.entity.solr;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(solrCoreName = "solrlogcore")
public class LogInfo{

    @Id
    @Field
    private String id;
    @Field
    private String timestamp;
    @Field
    private String pid;
    @Field
    private String level;
    @Field
    private String content;

    public LogInfo() {
    }

    public LogInfo(String timestamp, String pid, String level, String content) {
        this.timestamp = timestamp;
        this.pid = pid;
        this.level = level;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "LogInfo{" +
                "id='" + id + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", pid='" + pid + '\'' +
                ", level='" + level + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
