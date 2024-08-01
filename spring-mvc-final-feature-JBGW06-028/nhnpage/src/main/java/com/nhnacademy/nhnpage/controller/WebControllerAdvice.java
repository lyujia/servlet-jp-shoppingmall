package com.nhnacademy.nhnpage.controller;

import com.nhnacademy.nhnpage.exception.NotLoginException;
import com.nhnacademy.nhnpage.exception.NotPersonException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WebControllerAdvice {
    @ExceptionHandler(NotLoginException.class)
    public String notLogin(NotLoginException e) {
        return "loginForm";
        //return "redirect:/cs/login";
    }
    @ExceptionHandler(NotPersonException.class)
    public String notPerson(NotPersonException e) {
        return "redirect:/login";
    }

}
