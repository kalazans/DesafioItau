package br.com.desafioItau.Itau.infra.advice;

import br.com.desafioItau.Itau.infra.exceptions.ArgumentNotValid;
import br.com.desafioItau.Itau.infra.exceptions.CustomErrorApi;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class TransacaoAdvice {

    private static final Logger log = LogManager.getLogger(TransacaoAdvice.class);
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorApi> validationsIncorreta(MethodArgumentNotValidException ex){
        log.warn("exception lançada: {}",ex.getClass());
        //return ResponseEntity.unprocessableEntity().body(ex.getFieldErrors().stream().map(e->new ArgumentNotValid(e.getField(),e.getDefaultMessage())));
        String fieldErros = ex.getFieldErrors()
                .stream()
                .map(e->new ArgumentNotValid(e.getField(),e.getDefaultMessage())).toList().toString();
        System.out.println(fieldErros);
        CustomErrorApi apiError = new CustomErrorApi(HttpStatus.UNPROCESSABLE_ENTITY,"json invalido",fieldErros);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(apiError);

    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<CustomErrorApi>  handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        log.warn("exception lançada: {}",ex.getClass());

        String error = "variavel de caminho do Type errado";
        return ResponseEntity.badRequest().body(new CustomErrorApi(HttpStatus.BAD_REQUEST,error,ex.getMessage()));
    }
    @ExceptionHandler( HttpMessageNotReadableException.class)
    public ResponseEntity<CustomErrorApi> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.warn("exception lançada: {}",ex.getClass());

        return ResponseEntity.badRequest().body(new CustomErrorApi(HttpStatus.BAD_REQUEST,"json invalido",ex.getCause().toString()));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<String> handleNoResourceFoundException(NoResourceFoundException ex, HttpServletRequest request){
        log.warn("exception lançada: {}",ex.getClass());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("nao existe mapeamento ou metodo http errado para esta url: "+request.getRequestURL());
    }
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<CustomErrorApi> handlerNoHandlerFoundException(NoHandlerFoundException ex,HttpServletRequest request){
        log.warn("exception lançada: {}",ex.getClass());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomErrorApi(HttpStatus.NOT_FOUND,"nao existe mapeamento ou metodo http errado para esta url: "+request.getRequestURL(),ex.getMessage()));
    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<CustomErrorApi> handleMethodNotAllowedException(HttpRequestMethodNotSupportedException ex,HttpServletRequest request){
        log.warn("exception lançada: {}",ex.getClass());
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new CustomErrorApi(HttpStatus.METHOD_NOT_ALLOWED,"method %s nao valido para este endpoint: %s".formatted(request.getMethod(),request.getRequestURL(),ex.getMessage()),ex.getMessage()));
    }

    @ExceptionHandler(UnrecognizedPropertyException.class)
    public ResponseEntity<CustomErrorApi> handleUnrecognizedPropertyException(UnrecognizedPropertyException ex){
        log.warn("exception lançada {}",ex.getClass());
        return ResponseEntity.unprocessableEntity().body(new CustomErrorApi(HttpStatus.UNPROCESSABLE_ENTITY,"json invalido",ex.getLocalizedMessage()));
    }
    @ExceptionHandler(JsonParseException.class)
        public ResponseEntity<CustomErrorApi> handleJsonParseException(JsonParseException ex){
        log.warn("exception lançada {}",ex.getClass());
        return ResponseEntity.unprocessableEntity().body(new CustomErrorApi(HttpStatus.UNPROCESSABLE_ENTITY,"json invalido",ex.getLocalizedMessage()));

    }
    @ExceptionHandler(InvalidFormatException .class)
    public ResponseEntity<CustomErrorApi> handleInvalidFormatException(InvalidFormatException ex){
        log.warn("exception lançada {}",ex.getClass());
        return ResponseEntity.unprocessableEntity().body(new CustomErrorApi(HttpStatus.UNPROCESSABLE_ENTITY,"json invalido",ex.getLocalizedMessage()));



    }


}
