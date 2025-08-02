package com.alura.LiterAlura.service;


import com.alura.LiterAlura.model.Autor;
import com.alura.LiterAlura.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AutorService {
    @Autowired
    private AutorRepository autorRepository;

    // Metodo para buscar a los autores registrados en la base de datos.
    public List<String> listarAutoresRegistrados(){
        List<Autor> autores = autorRepository.findAll();

        //Ordenar la lista alfabeticamente y  convertir a string
        return autores.stream()
                .sorted((p1, p2) -> p1.getNombre().compareToIgnoreCase(p2.getNombre()))
                .map(Autor::toString)
                .collect(Collectors.toList());
    }

    //Este metodo detemrina si el autor estaba vivo en un año determinado.
    public List<Autor> getAutoresVivosEnFecha(int anio){
        return autorRepository.findAll().stream()
                .filter(autor -> autor.getFechaDeNacimiento() != null && autor.getFechaDeNacimiento() <= anio)
                .filter(autor -> autor.getFechaDeMuerte() == null && autor.getFechaDeMuerte() >= anio)
                .collect(Collectors.toList());

    }

    //Busca autores vivos en un determinado año.
    public void listarAutoresVivoEnUnaFecha(int anio){
        List<Autor> autoresVivos =  getAutoresVivosEnFecha(anio);

        if(autoresVivos.isEmpty()){
            System.out.println("\n/*/ EN LA BASE DE DATOS, NO HAY REGISTRO DE AUTORES VIVOS EN EL AÑO " + anio + ". /*/");
        } else {
            System.out.println("\nAUTORES VIVOS EN EL AÑO " + anio + ":\n────────────────────────────");
            autoresVivos.forEach(System.out::println);
        }
    }
    // METODO PARA BUSCAR AUTORES POR SU NOMBRE
    public List<Autor> listarAutoresPorNombre(String nombre) {
        return autorRepository.findByNombreContainingIgnoreCase(nombre);
    }

}



