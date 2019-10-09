package top.neospot.cloud.user.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import top.neospot.cloud.user.pojo.ResponseBo;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/10/9.
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandleAdvice {
    @ExceptionHandler(UnauthenticatedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public @ResponseBody
    ResponseBo handleException(UnauthenticatedException e) {
        log.debug("{} was thrown", e.getClass(), e);
        return ResponseBo.error(401, "you have no privileges, try login or apply more privileges", e.getMessage());
    }

    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public @ResponseBody ResponseBo handleException(AuthorizationException e) {
        log.debug("{} was thrown", e.getClass(), e);
        return ResponseBo.error(401, "you have no privileges, try login or apply more privileges", e.getMessage());
    }

}
