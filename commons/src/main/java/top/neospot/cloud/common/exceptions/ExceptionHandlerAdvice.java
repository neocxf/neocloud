package top.neospot.cloud.common.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(CloudBizException.class)
    public void handleBizException(Exception ex) {

        ex.printStackTrace();
    }
}
