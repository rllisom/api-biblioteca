package com.salesianos.triana.dam.apibiblioteca.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(LibraryNotFoundException.class)
    public ProblemDetail handleBibliotecaNotFound(LibraryNotFoundException ex){

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,ex.getMessage());

        problem.setTitle("Biblioteca no encontrada");
        problem.setDetail(ex.getMessage());
        problem.setType(URI.create("https://biblio.com/biblioteca-no-encontrada"));

        return problem;
    }

    @ExceptionHandler(InvalidLibraryDataException.class)
    public ProblemDetail handleInvalidData(InvalidLibraryDataException ex){

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,ex.getMessage());

        problem.setTitle("Datos inválidos en la solicitud");
        problem.setDetail(ex.getMessage());
        problem.setType(URI.create("https://biblio.com/datos-invalidos"));

        return problem;
    }
}
