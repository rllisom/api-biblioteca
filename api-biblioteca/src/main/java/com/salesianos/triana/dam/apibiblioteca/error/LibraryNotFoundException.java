package com.salesianos.triana.dam.apibiblioteca.error;

public class LibraryNotFoundException extends RuntimeException {
    public LibraryNotFoundException(String message) {
        super(message);
    }

    public LibraryNotFoundException(){
        super("No se han encontrado bibliotecas en nuestra base de datos");
    }

    public LibraryNotFoundException(Long id){
        super("No se ha encontrado una biblioteca con ese id %d".formatted(id));
    }
}
