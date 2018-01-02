package site.yourdiary.loghandle.entity.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "history_log_report")
public class HistoryLogReport {

    @Id
    @GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid")
    @Column(columnDefinition = "char(32)")
    private String historyLogReportId;

    private Long errorNumber;

    private Long timeoutWarningNumber;

    private Long allLogInfoNumber;

    private Long logFileNumber;

    @Temporal(TemporalType.DATE)
    private Date historyLogDate;

    @OneToMany(mappedBy = "historyLogReport")
    @JsonIgnore //防止陷入死循环
    private Collection<HistoryError> historyErrors;

    public String getHistoryLogReportId() {
        return historyLogReportId;
    }

    public void setHistoryLogReportId(String historyLogReportId) {
        this.historyLogReportId = historyLogReportId;
    }

    public Long getErrorNumber() {
        return errorNumber;
    }

    public void setErrorNumber(Long errorNumber) {
        this.errorNumber = errorNumber;
    }

    public Long getTimeoutWarningNumber() {
        return timeoutWarningNumber;
    }

    public void setTimeoutWarningNumber(Long timeoutWarningNumber) {
        this.timeoutWarningNumber = timeoutWarningNumber;
    }

    public Long getAllLogInfoNumber() {
        return allLogInfoNumber;
    }

    public void setAllLogInfoNumber(Long allLogInfoNumber) {
        this.allLogInfoNumber = allLogInfoNumber;
    }

    public Long getLogFileNumber() {
        return logFileNumber;
    }

    public void setLogFileNumber(Long logFileNumber) {
        this.logFileNumber = logFileNumber;
    }

    public Date getHistoryLogDate() {
        return historyLogDate;
    }

    public void setHistoryLogDate(Date historyLogDate) {
        this.historyLogDate = historyLogDate;
    }

    public Collection<HistoryError> getHistoryErrors() {
        return historyErrors;
    }

    public void setHistoryErrors(Collection<HistoryError> historyErrors) {
        this.historyErrors = historyErrors;
    }

    @Override
    public String toString() {
        return "HistoryLogReport{" +
                "historyLogReportId='" + historyLogReportId + '\'' +
                ", errorNumber=" + errorNumber +
                ", timeoutWarningNumber=" + timeoutWarningNumber +
                ", allLogInfoNumber=" + allLogInfoNumber +
                ", logFileNumber=" + logFileNumber +
                ", historyLogDate=" + historyLogDate +
                '}';
    }
}
