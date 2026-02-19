package guru.springframework.spring_7_rest_mvc.controller;


/*
Created by Zsolt Melich (BT - IVR team)
*/

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomErrorController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity handleBindErrors(MethodArgumentNotValidException exception){
        return ResponseEntity.badRequest().body(exception.getBindingResult().getFieldErrors());
    }

}
