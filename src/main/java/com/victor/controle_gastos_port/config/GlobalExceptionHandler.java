package com.victor.controle_gastos_port.config;

import com.victor.controle_gastos_port.admin.service.CredencialNaoEncontrado;
import com.victor.controle_gastos_port.admin.service.StatusDesativado;
import com.victor.controle_gastos_port.config.exception.CategoriaExistenteException;
import com.victor.controle_gastos_port.config.exception.CategoriaNaoEncontradaException;
import com.victor.controle_gastos_port.config.exception.CategoriaNaoExistenteException;
import com.victor.controle_gastos_port.config.exception.CategoriaPossuiGastosException;
import com.victor.controle_gastos_port.config.exception.IntervaloDataInvalidoException;
import com.victor.controle_gastos_port.config.exception.DataVencimentoInvalidaException;
import com.victor.controle_gastos_port.config.exception.GastoNaoEncontradoException;
import com.victor.controle_gastos_port.logs.dto.RegistrarLogsDto;
import com.victor.controle_gastos_port.logs.service.LogService;
import com.victor.controle_gastos_port.config.exception.AcessoNegadoException;
import com.victor.controle_gastos_port.config.exception.CredencialInvalida;
import com.victor.controle_gastos_port.config.exception.EmailJaCadastroException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final LogService logService;

    public GlobalExceptionHandler(LogService logService) {
        this.logService = logService;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(HttpServletRequest request, Exception ex) {
        String stackTraceString = getStackTraceAsString(ex);

        RegistrarLogsDto registrarLogs = new RegistrarLogsDto(
                ex.getMessage(),
                stackTraceString,
                request.getRequestURI(),
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );

        logService.logDetalhado(registrarLogs);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro interno.");
    }


    @ExceptionHandler(GastoNaoEncontradoException.class)
    public ResponseEntity<String> handleGastoNaoEncontrado(HttpServletRequest request, GastoNaoEncontradoException ex) {
        registrarLogNoBanco(request, ex, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(DataVencimentoInvalidaException.class)
    public ResponseEntity<String> handleDataVencimentoInvalida(HttpServletRequest request, DataVencimentoInvalidaException ex) {
        registrarLogNoBanco(request, ex, HttpStatus.UNPROCESSABLE_CONTENT);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_CONTENT);
    }

    @ExceptionHandler(IntervaloDataInvalidoException.class)
    public ResponseEntity<String> handleIntervaloDataInvalido(HttpServletRequest request, IntervaloDataInvalidoException ex) {
        // Registra o log no banco com o status 422
        registrarLogNoBanco(request, ex, HttpStatus.UNPROCESSABLE_CONTENT);

        // Retorna a mensagem com o status 422 Unprocessable Entity
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_CONTENT);
    }


    //exception categoria

    @ExceptionHandler(CategoriaNaoEncontradaException.class)
    public ResponseEntity<String> handleCategoriaNaoEncontrada(HttpServletRequest request, CategoriaNaoEncontradaException ex) {
        registrarLogNoBanco(request, ex, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoriaNaoExistenteException.class)
    public ResponseEntity<String> handleException(HttpServletRequest request, CategoriaNaoExistenteException ex) {
        registrarLogNoBanco(request, ex, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoriaExistenteException.class)
    public ResponseEntity<String> handleException(HttpServletRequest request, CategoriaExistenteException ex) {
        registrarLogNoBanco(request, ex, HttpStatus.CONFLICT);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CategoriaPossuiGastosException.class)
    public ResponseEntity<String> handleException(HttpServletRequest request, CategoriaPossuiGastosException ex) {
        registrarLogNoBanco(request,ex,HttpStatus.CONFLICT );
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.CONFLICT );
    }

    @ExceptionHandler(AcessoNegadoException.class)
    public ResponseEntity<String> handleAcessoNegado(HttpServletRequest request, AcessoNegadoException ex) {
        registrarLogNoBanco(request, ex, HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(EmailJaCadastroException.class)
    public ResponseEntity<String> handleException(HttpServletRequest request,EmailJaCadastroException ex)  {
        registrarLogNoBanco(request,ex,HttpStatus.CONFLICT);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(CredencialInvalida.class)
    public ResponseEntity<String> handleException(HttpServletRequest request,CredencialInvalida ex)  {
        registrarLogNoBanco(request,ex,HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(StatusDesativado.class)
    public ResponseEntity<String> handleException(HttpServletRequest request,  StatusDesativado ex)  {
        registrarLogNoBanco(request,ex,HttpStatus.CONFLICT);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(CredencialNaoEncontrado.class)
    public ResponseEntity<String> handleException(HttpServletRequest request,CredencialNaoEncontrado ex)  {
        registrarLogNoBanco(request,ex,HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }



    private String getStackTraceAsString (Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        return sw.toString();
    }

    private void registrarLogNoBanco(HttpServletRequest request, Exception ex, HttpStatus status) {
        String stackTraceString = getStackTraceAsString(ex);
        RegistrarLogsDto dto = new RegistrarLogsDto(
                ex.getMessage(),
                stackTraceString,
                request.getRequestURI(),
                LocalDateTime.now(),
                status.value()
        );
        logService.logDetalhado(dto);
    }
}
