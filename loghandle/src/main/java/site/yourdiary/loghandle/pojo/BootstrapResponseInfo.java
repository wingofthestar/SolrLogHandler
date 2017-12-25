package site.yourdiary.loghandle.pojo;

import java.util.List;

public class BootstrapResponseInfo {
    private Long total;
    private List rows;

    public BootstrapResponseInfo(Long total, List rows) {
        this.total = total;
        this.rows = rows;
    }

    public BootstrapResponseInfo() {
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "BootstrapResponseInfo{" +
                "total=" + total +
                ", rows=" + rows +
                '}';
    }
}
