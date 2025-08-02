package com.alura.LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import java.util.List;

@JsonIgnoreProperties (ignoreUnknown = true)
public record Datos(
        @JsonAlias("results")List<DatosLibros> resultados) {
}
