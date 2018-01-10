package site.yourdiary.loghandle.pojo;

public enum ErrorType {
    ControlError("数据库状态控制更新异常"),
    DetachError("执行detach失败"),
    TSPError("TSP服务异常"),
    OtherError("其他异常");

    private String name;

    ErrorType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
