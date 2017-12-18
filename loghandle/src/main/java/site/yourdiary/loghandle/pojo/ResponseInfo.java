package site.yourdiary.loghandle.pojo;

/**
 * 自定义返回错误信息
 */
public class ResponseInfo {
    public static final Integer OK = 1;
    public static final Integer ERROR = -1;

    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResponseInfo(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseInfo() {
    }

    @Override
    public String toString() {
        return "ErrorInfo{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
