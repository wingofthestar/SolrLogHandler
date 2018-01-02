package site.yourdiary.loghandle.entity.jpa;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "history_error")
public class HistoryError {
    @Id
    @GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid")
    @Column(columnDefinition = "char(32)")
    private String historyErrorId;
    private String historyErrorMessage;
    private int errorType;
    @ManyToOne
    @JoinColumn(name = "history_log_report_id")
    private HistoryLogReport historyLogReport;

    public String getHistoryErrorId() {
        return historyErrorId;
    }

    public void setHistoryErrorId(String historyErrorId) {
        this.historyErrorId = historyErrorId;
    }

    public String getHistoryErrorMessage() {
        return historyErrorMessage;
    }

    public void setHistoryErrorMessage(String historyErrorMessage) {
        this.historyErrorMessage = historyErrorMessage;
    }

    public HistoryLogReport getHistoryLogReport() {
        return historyLogReport;
    }

    public void setHistoryLogReport(HistoryLogReport historyLogReport) {
        this.historyLogReport = historyLogReport;
    }

    public int getErrorType() {
        return errorType;
    }

    public void setErrorType(int errorType) {
        this.errorType = errorType;
    }

    @Override
    public String toString() {
        return "HistoryError{" +
                "historyErrorId='" + historyErrorId + '\'' +
                ", historyErrorMessage='" + historyErrorMessage + '\'' +
                ", errorType=" + errorType +
                ", historyLogReport=" + historyLogReport +
                '}';
    }
}
