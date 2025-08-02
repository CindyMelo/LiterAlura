package com.alura.LiterAlura.repository;

import com.alura.LiterAlura.model.Idiomas;
import com.alura.LiterAlura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibrosRepository extends JpaRepository<Libro, Long> {
    List<Libro> findByIdioma(Idiomas idioma);
    List<Libro> findByTituloContainingIgnoreCase(String titulo);
}


