package com.kuqi.mall.web.core.handler;

import com.kuqi.mall.web.core.entity.vo.Response;
import com.kuqi.mall.web.core.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Objects;

/**
 * @Author iloveoverfly
 * @Date 2021/1/27 16:30
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    public static final String DATA_INVALID_MSG = "数据不合法";

    /**
     * 业务异常
     */
    @ExceptionHandler(value = {ServiceException.class})
    public Response businessException(ServiceException ex) {
        return Response.error(String.valueOf(ex.getCode()), ex.getMessage());
    }

    /**
     * 乐观锁异常
     */
    @ExceptionHandler(value = {OptimisticLockingFailureException.class})
    public Response optimisticLockingFailureException(OptimisticLockingFailureException ex) {
        return Response.error(ex.getMessage());
    }

    /**
     * 授权异常
     */
    @ExceptionHandler(value = {AccessDeniedException.class})
    public Response accessDeniedException(AccessDeniedException ex) {
        return Response.error(String.valueOf(HttpStatus.FORBIDDEN.value()), ex.getMessage());
    }

    /**
     * 未知异常
     */
    @ExceptionHandler(value = {Exception.class})
    public Response unKnownException(Exception ex) {
        log.error("ex {}", ex);
        return Response.error("系统异常");
    }

    /**
     * 参数校验异常
     */
    @ExceptionHandler(value = {IllegalArgumentException.class})
    public Response illegalArgumentException(IllegalArgumentException ex) {
        return Response.error(ex.getMessage());
    }

    /**
     * java validation校验异常
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public Response methodArgumentNotValidException(MethodArgumentNotValidException ex) {

        String errorMsg = buildObjectErrorMsg(ex.getBindingResult().getAllErrors());
        return Response.error(StringUtils.isNotBlank(errorMsg) ? errorMsg : DATA_INVALID_MSG);
    }

    private String buildObjectErrorMsg(List<ObjectError> objectErrorList) {

        String error = StringUtils.EMPTY;
        if (CollectionUtils.isNotEmpty(objectErrorList)) {

            int size = objectErrorList.size();
            for (int i = 0; i < size; i++) {

                ObjectError objectError;
                String defaultMessage;
                if (Objects.nonNull(objectError = objectErrorList.get(i))
                        && StringUtils.isNotBlank(defaultMessage = objectError.getDefaultMessage())) {

                    error += defaultMessage;
                    if (this.isNotLastIndex(size, i)) {
                        error += ";";
                    }
                }
            }
        }
        return error;
    }

    private boolean isNotLastIndex(int size, int i) {
        int lastIndex = size - 1;
        return lastIndex >= 0 && lastIndex != i;
    }

}
