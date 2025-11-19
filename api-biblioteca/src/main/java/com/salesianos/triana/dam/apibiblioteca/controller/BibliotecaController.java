package com.salesianos.triana.dam.apibiblioteca.controller;

import com.salesianos.triana.dam.apibiblioteca.dto.BibliotecaResponse;
import com.salesianos.triana.dam.apibiblioteca.dto.CreateBibliotecaCmd;
import com.salesianos.triana.dam.apibiblioteca.service.BibliotecaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/biblioteca")
@RestController
@Tag(name = "Gestión de bibioteca", description = "Vamos comprobar los endopoints del servicio de bibliotecas")
public class BibliotecaController {

    private final BibliotecaService bibliotecaService;

    //Obtener todos
    @GetMapping
    @Operation(summary = "Obtiene todas las bibliotecas registrada")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Productos encontrados correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = BibliotecaResponse.class)),
                            examples = @ExampleObject(
                                    value = """
                                                [
                                                    {
                                                      "id": 1,
                                                      "nombreCiudad": "Sevilla",
                                                      "nombreBiblio": "San Julián",
                                                      "anioFundacion": 2007,
                                                      "descripcion": "Biblioteca amplia para todo tipo de personas",
                                                      "url": "https://www.biblio.com"
                                                    }
                                                ]
                                            """
                            )

                    )),
            @ApiResponse(responseCode = "404", description = "No se ha encontrado ninguna respuesta",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = @ExampleObject( value = """
                            {
                                 "type": "https://example.com/probs/biblioteca-no-encontrada",
                                 "title": "Biblioteca no encontrada",
                                 "status": 404,
                                 "detail": "No se encontraron bibliotecas"
                             }
                            """)
            ))
    })
    public ResponseEntity<List<BibliotecaResponse>> getAll(){
        return ResponseEntity.ok(
                bibliotecaService.getAll().stream().map(BibliotecaResponse::of).toList()
        );
    }

    //Obtener una biblioteca por su identificador
    @GetMapping("/{id}")
    @Operation(summary = "Obtener una biblioteca por su id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Biblioteca encontrada",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema( implementation = BibliotecaResponse.class),
                    examples = @ExampleObject( value = """
                                      {
                                          "id": 1,
                                          "nombreCiudad": "Sevilla",
                                          "nombreBiblio": "San Julián",
                                          "anioFundacion": 2007,
                                          "descripcion": "Biblioteca amplia para todo tipo de personas",
                                          "url": "https://www.biblio.com"
                                        }
                                    """
                    )
            )),
            @ApiResponse(responseCode = "404", description = "Biblioteca no encontrada",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema( implementation = ProblemDetail.class),
                            examples = @ExampleObject( value = """
                                      {
                                         "type": "https://example.com/probs/biblioteca-no-encontrada",
                                         "title": "Biblioteca no encontrada",
                                         "status": 404,
                                         "detail": "No se encontró la biblioteca con ese id"
                                        }
                                    """
                            )
                    ))
    })
    public ResponseEntity<BibliotecaResponse> getById(
            @Parameter(name = "id", description = "Id de la biblioteca", example = "1")
            @PathVariable Long id){
        return ResponseEntity.ok(BibliotecaResponse.of(bibliotecaService.getById(id)));
    }

    //Editar una biblioteca
    @PutMapping("/{id}")
    @Operation(summary = "Edición de una biblioteca")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Biblioteca editada correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BibliotecaResponse.class),
                            examples = @ExampleObject(value = """
                {
                  "id": 1,
                  "nombreCiudad": "Sevilla",
                  "nombreBiblio": "San Julián",
                  "anioFundacion": 2007,
                  "descripcion": "Biblioteca amplia para todo tipo de personas",
                  "url": "https://www.biblio.com"
                }
            """)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(value = """
                {
                  "type": "https://biblio.com/datos-invalidos",
                  "title": "Datos inválidos en la solicitud",
                  "status": 400,
                  "detail": "El campo nombreBiblio no puede estar vacío"
                }
            """)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Biblioteca no encontrada",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(value = """
                {
                  "type": "https://example.com/probs/biblioteca-no-encontrada",
                  "title": "Biblioteca no encontrada",
                  "status": 404,
                  "detail": "No se encontró la biblioteca con id 123"
                }
            """)
                    )
            )
    })
    public ResponseEntity<BibliotecaResponse> edit(
            @Parameter(name = "id", description = "Id de la biblioteca", example = "1")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la biblioteca para editar",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CreateBibliotecaCmd.class),
                    examples = @ExampleObject(value = """
                                                    {
                                                      "id": 1,
                                                      "nombreCiudad": "Sevilla",
                                                      "nombreBiblio": "San Julián",
                                                      "anioFundacion": "2007",
                                                      "descripcion": "Biblioteca amplia para todo tipo de personas",
                                                      "url": "https://www.biblio.com"
                                                    }
                            """))
            )
            @RequestBody CreateBibliotecaCmd cmd
    ){
        return ResponseEntity.ok(BibliotecaResponse.of(bibliotecaService.edit(id,cmd)));
    }


    //Eliminar biblioteca
    @DeleteMapping("/{id}")
    public  ResponseEntity<?> delete(
            @Parameter(name = "id", description = "Id de la biblioteca", example = "1")
            @PathVariable Long id){
        bibliotecaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    //Crear biblioteca
    @PostMapping
    @Operation(summary = "Crear una nueva biblioteca")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Biblioteca creada correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BibliotecaResponse.class),
                            examples = @ExampleObject(value = """
                {
                  "id": 1,
                  "nombreCiudad": "Sevilla",
                  "nombreBiblio": "San Julián",
                  "anioFundacion": 2007,
                  "descripcion": "Biblioteca amplia para todo tipo de personas",
                  "url": "https://www.biblio.com"
                }
            """)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(value = """
                {
                  "type": "https://biblio.com/datos-invalidos",
                  "title": "Datos inválidos en la solicitud",
                  "status": 400,
                  "detail": "El campo nombreBiblio no puede estar vacío",
                  "errors": {
                    "nombreBiblio": "no puede estar vacío"
                  }
                }
            """)
                    )
            )
    })
    public ResponseEntity<BibliotecaResponse> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la biblioteca a crear",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CreateBibliotecaCmd.class),
                            examples = @ExampleObject(value = """
                {
                  "id": 1,
                  "nombreCiudad": "Sevilla",
                  "nombreBiblio": "San Julián",
                  "anioFundacion": "2007",
                  "descripcion": "Biblioteca amplia para todo tipo de personas",
                  "url": "https://www.biblio.com"
                }
            """))
            )
            @RequestBody CreateBibliotecaCmd cmd
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BibliotecaResponse.of(bibliotecaService.create(cmd)));
    }
}
