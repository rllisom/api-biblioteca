package com.salesianos.triana.dam.apibiblioteca.service;

import com.salesianos.triana.dam.apibiblioteca.dto.CreateBibliotecaCmd;
import com.salesianos.triana.dam.apibiblioteca.error.InvalidLibraryDataException;
import com.salesianos.triana.dam.apibiblioteca.error.LibraryNotFoundException;
import com.salesianos.triana.dam.apibiblioteca.model.Biblioteca;
import com.salesianos.triana.dam.apibiblioteca.repository.BibliotecaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BibliotecaService {

    private final BibliotecaRepository bibliotecaRepository;


    public List<Biblioteca> getAll(){
        List<Biblioteca> results = bibliotecaRepository.findAll();

        if(results.isEmpty()) throw new LibraryNotFoundException();

        return results;
    }

    public Biblioteca getById(Long id){
        return bibliotecaRepository.findById(id).orElseThrow(
                () -> new LibraryNotFoundException(id)
        );
    }

    public Biblioteca edit(Long id, CreateBibliotecaCmd cmd){
        return bibliotecaRepository.findById(id)
                .map(
                        m -> {
                            m.setNombreCiudad(cmd.nombreCiudad());
                            m.setNombreBiblio(cmd.nombreBiblio());
                            m.setAnioFundacion(cmd.anioFundacion());
                            m.setDescripcion(cmd.descripcion());
                            m.setUrl(cmd.url());

                            return bibliotecaRepository.save(m);
                        }).orElseThrow( () -> new InvalidLibraryDataException("No se ha podido modificar los datos de la biblioteca"));

    }

    public Biblioteca create(CreateBibliotecaCmd cmd){
        if(!StringUtils.hasText(cmd.nombreBiblio())) throw new InvalidLibraryDataException("No se ha podido crear la biblioteca, faltan datos");
        return bibliotecaRepository.save(cmd.toEntity());
    }

    public void delete(Long id){
        bibliotecaRepository.deleteById(id);
    }


}
