package com.ifpe.project.resources.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ifpe.project.services.exception.ObjectNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

    // Tratamento para ObjectNotFoundException
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Não encontrado", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    // Tratamento para IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<StandardError> illegalArgument(IllegalArgumentException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Requisição inválida", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    // Tratamento para MethodArgumentNotValidException (Validação de argumentos)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validationException(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Erro de validação", message, request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    // Tratamento para HttpMessageNotReadableException (JSON malformado ou inválido)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<StandardError> messageNotReadable(HttpMessageNotReadableException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Requisição malformada", "Erro ao ler o corpo da requisição.", request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    // Tratamento para HttpRequestMethodNotSupportedException (Método HTTP não suportado)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<StandardError> methodNotSupported(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Método não suportado", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    // Tratamento para DataIntegrityViolationException (Erros de integridade de dados)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> dataIntegrityViolation(DataIntegrityViolationException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Erro de integridade de dados", "Operação não permitida por violar restrições de integridade.", request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    // Tratamento genérico para qualquer outra exceção
    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> globalException(Exception e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Erro interno do servidor", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
}
