package com.compassuol.sp.msusers.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    public void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    public void testHandleDuplicateCpfException() {
        DuplicateCpfException exception = new DuplicateCpfException("CPF duplicado");
        WebRequest webRequest = mock(WebRequest.class);

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleDuplicateCpfException(exception, webRequest);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("CPF duplicado", response.getBody().getMessage());
    }

    @Test
    public void testHandleDuplicateEmailException() {
        DuplicateEmailException exception = new DuplicateEmailException("Email duplicado");
        WebRequest webRequest = mock(WebRequest.class);

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleDuplicateEmailException(exception, webRequest);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Email duplicado", response.getBody().getMessage());
    }

    @Test
    public void testHandleInvalidActiveValueException() {
        InvalidActiveValueException exception = new InvalidActiveValueException("Valor de ativação inválido");
        WebRequest webRequest = mock(WebRequest.class);

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleInvalidActiveValueException(exception, webRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Valor de ativação inválido", response.getBody().getMessage());
    }

    @Test
    public void testHandleInvalidNameLengthException() {
        InvalidNameLengthException exception = new InvalidNameLengthException("Comprimento do nome inválido");
        WebRequest webRequest = mock(WebRequest.class);

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleInvalidNameLengthException(exception, webRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Comprimento do nome inválido", response.getBody().getMessage());
    }

    @Test
    public void testHandleInvalidCpfFormatException() {
        InvalidCpfFormatException exception = new InvalidCpfFormatException("Formato de CPF inválido");
        WebRequest webRequest = mock(WebRequest.class);

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleInvalidCpfFormatException(exception, webRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Formato de CPF inválido", response.getBody().getMessage());
    }

    @Test
    public void testHandleInvalidPasswordLengthException() {
        InvalidPasswordLengthException exception = new InvalidPasswordLengthException("Comprimento da senha inválido");
        WebRequest webRequest = mock(WebRequest.class);

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleInvalidPasswordLengthException(exception, webRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Comprimento da senha inválido", response.getBody().getMessage());
    }

    @Test
    public void testHandleInvalidUserDTOException() {
        InvalidUserDTOException exception = new InvalidUserDTOException("Erro na validação do usuário");
        ResponseEntity<Object> response = globalExceptionHandler.handleInvalidUserDTOException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Validation failed: Erro na validação do usuário", ((ErrorResponse) response.getBody()).getMessage());
    }

    @Test
    public void testHandleGlobalException() {
        Exception exception = new Exception("Erro interno");
        WebRequest webRequest = mock(WebRequest.class);

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleGlobalException(exception, webRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Ocorreu um erro interno no servidor. Verifique suas credenciais e tente novamente.", response.getBody().getMessage());
    }
}

