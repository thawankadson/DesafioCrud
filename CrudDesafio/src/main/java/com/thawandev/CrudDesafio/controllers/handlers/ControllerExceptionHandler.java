package com.thawandev.CrudDesafio.controllers.handlers;
import com.thawandev.CrudDesafio.dto.CustomError;
import com.thawandev.CrudDesafio.dto.ValidationError;
import com.thawandev.CrudDesafio.service.execption.ResourceNotFoundExecption;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
 @ControllerAdvice
 public class ControllerExceptionHandler{
     @ExceptionHandler(ResourceNotFoundExecption.class)
     public ResponseEntity<CustomError> resourceNotFound(ResourceNotFoundExecption e, HttpServletRequest request) {
         HttpStatus status = HttpStatus.NOT_FOUND;
         CustomError err = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
         return ResponseEntity.status(status).body(err);
     }

     @ExceptionHandler(MethodArgumentNotValidException.class)
     public ResponseEntity<CustomError> methodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request) {
         HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
         ValidationError err = new ValidationError(Instant.now(), status.value(), "data invalida", request.getRequestURI());
         for (FieldError f : e.getBindingResult().getFieldErrors()) {
             err.addError(f.getField(), f.getDefaultMessage());
         }
         return ResponseEntity.status(status).body(err);
     }
 }
