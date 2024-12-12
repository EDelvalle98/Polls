package com.pollhw.springrestpoll.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

//    @Autowired
//    private MessageSource messageSource;
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ResponseEntity<?> handleValidationError(MethodArgumentNotValidException manve, HttpServletRequest request) {
//        ErrorDetail errorDetail = new ErrorDetail();
//// Populate errorDetail instance
//        errorDetail.setTimeStamp(new Date().getTime());
//        errorDetail.setStatus(HttpStatus.BAD_REQUEST.value());
//        String requestPath = (String) request.getAttribute("javax.servlet.error. request_uri");
//        if(requestPath == null) {
//            requestPath = request.getRequestURI();
//        }
//        errorDetail.setTitle("Validation Failed");
//        errorDetail.setDetail("Input validation failed");
//        errorDetail.setDeveloperMessage(manve.getClass().getName());
//// Create ValidationError instances
//        List<FieldError> fieldErrors = manve.getBindingResult().getFieldErrors();
//        for(FieldError fe : fieldErrors) {
//            List<ValidationError> validationErrorList = errorDetail.getErrors().
//                    get(fe.getField());
//            if(validationErrorList == null) {
//                validationErrorList = new ArrayList<ValidationError>();
//                errorDetail.getErrors().put(fe.getField(),
//                        validationErrorList);
//            }
//            ValidationError validationError = new ValidationError();
//            validationError.setCode(fe.getCode());
//            validationError.setMessage(fe.getDefaultMessage());
//            validationErrorList.add(validationError);
//        }
////        return new ResponseEntity<>(errorDetail, null, HttpStatus. BAD_REQUEST);
//        return errorDetail;
//    }
///** handleResourceNotFoundException method removed **/

//@Override
protected ResponseEntity<Object> handleHttpMessageNotReadable(
        HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

    ErrorDetail errorDetail = new ErrorDetail();
    errorDetail.setTimeStamp(new Date().getTime());
    errorDetail.setStatus(status.value());
    errorDetail.setTitle("Message Not Readable");
    errorDetail.setDetail(ex.getMessage());
    errorDetail.setDeveloperMessage(ex.getClass().getName());
    return handleExceptionInternal(ex, errorDetail, headers, status, request);
}

    //@Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimeStamp(new Date().getTime());
        errorDetail.setStatus(status.value());
        errorDetail.setTitle("Validation Failed");
        errorDetail.setDetail("Input validation failed");
        errorDetail.setDeveloperMessage(ex.getClass().getName());

        // Create ValidationError instances
        Map<String, List<ValidationError>> fieldErrors = new HashMap<>();
        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            List<ValidationError> validationErrorList = fieldErrors.computeIfAbsent(fe.getField(), k -> new ArrayList<>());
            ValidationError validationError = new ValidationError();
            validationError.setCode(fe.getCode());
            validationError.setMessage(fe.getDefaultMessage());
            validationErrorList.add(validationError);
        }

        errorDetail.setErrors(fieldErrors);
        return handleExceptionInternal(ex, errorDetail, headers, status, request);
    }
}