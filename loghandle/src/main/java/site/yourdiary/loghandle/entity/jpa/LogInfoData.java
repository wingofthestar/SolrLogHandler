package site.yourdiary.loghandle.entity.jpa;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "loginfo")
public class LogInfoData {

    @Id
    @GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid")
    @Column(name = "uuid")
    private String id;
    private String timestamp;
    private String pid;
    private String level;
    private String content;

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
        return "LogInfoData{" +
                "id='" + id + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", pid='" + pid + '\'' +
                ", level='" + level + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
