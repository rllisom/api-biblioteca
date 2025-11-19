package com.salesianos.triana.dam.apibiblioteca.dto;

import com.salesianos.triana.dam.apibiblioteca.model.Biblioteca;
import io.swagger.v3.oas.annotations.media.Schema;

public record BibliotecaResponse(
        @Schema(description = "Id del producto", example = "1") Long id,
        @Schema(description = "Nombre de la ciudad donde se encuentra la biblioteca", example = "Sevilla")String nombreCiudad,
        @Schema(description = "Nombre de la biblioteca", example = "San Julián")String nombreBiblio,
        @Schema(description = "Año en que se fundó la biblioteca", example = "2007")int anioFundacion,
        @Schema(description = "Descripción de la biblioteca", example = "Biblioteca amplia para todo tipo de personas")String descripcion,
        @Schema(description = "Sitio web de la biblioteca", example = "https://www.biblio.com")String url
) {
    public static BibliotecaResponse of(Biblioteca b){
        return new BibliotecaResponse(b.getId(),
        b.getNombreCiudad(),
        b.getNombreBiblio(),
        b.getAnioFundacion(),
        b.getDescripcion(),b.getUrl()
        );
    }
}
