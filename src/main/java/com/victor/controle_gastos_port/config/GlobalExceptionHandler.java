package com.victor.controle_gastos_port.config;

import com.victor.controle_gastos_port.admin.service.CredencialNaoEncontrado;
import com.victor.controle_gastos_port.admin.service.StatusDesativado;
import com.victor.controle_gastos_port.usuario.service.CredencialInvalida;
import com.victor.controle_gastos_port.usuario.service.EmailJaCadastroException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailJaCadastroException.class)
    public ResponseEntity<String> handleException(EmailJaCadastroException ex)  {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(CredencialInvalida.class)
    public ResponseEntity<String> handleException(CredencialInvalida ex)  {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(StatusDesativado.class)
    public ResponseEntity<String> handleException(StatusDesativado ex)  {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(CredencialNaoEncontrado.class)
    public ResponseEntity<String> handleException(CredencialNaoEncontrado ex)  {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
