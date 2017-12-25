package site.yourdiary.loghandle.entity.jpa;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "flushinfo")
public class FlushInfo {
    @Id
    @Column(name = "flush_id")
    private String flushId;
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    public String getFlushId() {
        return flushId;
    }

    public void setFlushId(String flushId) {
        this.flushId = flushId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "FlushTime{" +
                "flushId='" + flushId + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
