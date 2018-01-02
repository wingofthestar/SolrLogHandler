package site.yourdiary.loghandle.entity.jpa;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "flush_info")
public class FlushInfo {
    @Id
    @Column(name = "flush_id")
    private String flushId;
    @Temporal(TemporalType.TIMESTAMP)
    private Date flushDate;

    public String getFlushId() {
        return flushId;
    }

    public void setFlushId(String flushId) {
        this.flushId = flushId;
    }

    public Date getFlushDate() {
        return flushDate;
    }

    public void setFlushDate(Date flushDate) {
        this.flushDate = flushDate;
    }

    @Override
    public String toString() {
        return "FlushInfo{" +
                "flushId='" + flushId + '\'' +
                ", flushDate=" + flushDate +
                '}';
    }
}
