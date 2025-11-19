package com.salesianos.triana.dam.apibiblioteca.repository;

import com.salesianos.triana.dam.apibiblioteca.model.Biblioteca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BibliotecaRepository extends JpaRepository<Biblioteca,Long> {
}
