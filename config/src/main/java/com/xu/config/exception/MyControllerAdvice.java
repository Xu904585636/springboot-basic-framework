package com.xu.config.exception;

import com.xu.util.result.ResultInfo;
import com.xu.util.result.ResultStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 */
@ControllerAdvice
public class MyControllerAdvice {
    /**
     * 全局异常捕捉处理
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResultInfo errorHandler(Exception ex) {
        ResultInfo resultInfo = new ResultInfo(ResultStatus.SYSTEM_ERROR);
        resultInfo.setResult(ex.getMessage());
        ex.printStackTrace();
        return resultInfo;
    }

}
