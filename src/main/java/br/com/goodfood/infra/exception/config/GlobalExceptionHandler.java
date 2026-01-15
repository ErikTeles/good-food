package br.com.goodfood.infra.exception.config;

import br.com.goodfood.infra.exception.ObjectNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public StandardError objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
        return new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(), "Não encontrado", e.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public StandardError genericException(Exception e, HttpServletRequest request) {
        return new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Erro desconhecido", e.getMessage(), request.getRequestURI());
    }
}
