package site.yourdiary.loghandle.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import site.yourdiary.loghandle.exception.DataBaseCurdException;
import site.yourdiary.loghandle.exception.SolrCurdException;
import site.yourdiary.loghandle.pojo.ResponseInfo;

/**
 * 全局异常捕获处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     *对Solr的索引操作的异常同意处理
     */
    @ExceptionHandler(value = SolrCurdException.class)
    @ResponseBody
    public ResponseInfo SolrCurdOperatorErrorHandler(SolrCurdException e){
        ResponseInfo r = new ResponseInfo();
        r.setMessage(e.getMessage());
        r.setCode(ResponseInfo.ERROR);
        return r;
    }

    /**
     *对数据库的增删改查操作的异常同意处理
     */
    @ExceptionHandler(value = DataBaseCurdException.class)
    @ResponseBody
    public ResponseInfo DataBaseCurdOperatorErrorHandler(SolrCurdException e){
        ResponseInfo r = new ResponseInfo();
        r.setMessage(e.getMessage());
        r.setCode(ResponseInfo.ERROR);
        return r;
    }

}
