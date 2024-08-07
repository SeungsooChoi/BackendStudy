package com.example.demo.global.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // LoginService, UserDetailService
    @ExceptionHandler(value = {
            NonExistentUserException.class})
    public CommonResponse handle(NonExistentUserException e, WebRequest request) {
        exceptionLogging(e, request);

        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setError(HttpStatus.BAD_REQUEST, e.getMessage());
        return commonResponse;
    }

    // 모든 exception 발생시, 로깅
    private void exceptionLogging(Exception e, WebRequest request) {
        try {
            log.error("exception: {}", e.getClass().getName());

            String description = request.getDescription(true);
            if(StringUtils.isEmpty(description)) {
                return;
            }

            String[] descriptionSplit = description.split(";");
            for (String descriptionStr : descriptionSplit) {
                log.error("Request description: {}", descriptionStr);
            }
        } catch (Exception ex) { /* do nothing */ }
    }

}