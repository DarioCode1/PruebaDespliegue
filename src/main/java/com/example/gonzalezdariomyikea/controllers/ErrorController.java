package com.example.gonzalezdariomyikea.controllers;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorController {
    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDeniedException() {
        return "error/403";
    }
}
