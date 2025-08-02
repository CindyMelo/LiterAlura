package com.alura.LiterAlura.service;

import com.alura.LiterAlura.model.Datos;
import com.alura.LiterAlura.model.DatosLibros;
import com.alura.LiterAlura.model.Idiomas;
import com.alura.LiterAlura.model.Libro;
import com.alura.LiterAlura.repository.LibrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class LibroService {

    @Autowired
    private LibrosRepository librosRepository;

    @Autowired
    private ConvierteDatos conversor;

    public void buscarLibroPorTitulo(String tituloLibro, String json){
        try {
            Datos datos = conversor.obtenerDatos(json, Datos.class);
            Optional<DatosLibros> libroBuscado = datos.resultados().stream()
                    .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                    .findFirst();

            if (libroBuscado.isPresent()){
                DatosLibros datosLibros = libroBuscado.get();

                Libro libro = new Libro(datosLibros);

                System.out.println("\nDATOS DEL LIBRO:\n───────────────\n" + libro.toString());
                librosRepository.save(libro);
                System.out.println("\n /*/ LIBRO GUARDADO EN LA BASE DE DATOS /*/");
            } else {
                System.out.println("\n /*/ LIBRO NO ENCONTRADO /*/");
            }

        } catch (Exception e) {
            // Manejo de cualquier otro error inesperado
            System.err.println("Ocurrió un error inesperado: " + e.getMessage());
        }

    }
    public static String eliminarTildes(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("");
    }

    // METODO PARA BUSCAR LIBROS DE ACUERDO AL IDIOMA
    public List<Libro> listarLibrosPorIdioma(Idiomas idioma) {
        List<Libro> libros = librosRepository.findByIdioma(idioma);
        if (libros.isEmpty()) {
            System.out.println("/*/ EN LA BASE DE DATOS NO HAY REGISTRO DE LIBROS DISPONIBLES EN EL IDIOMA " + idioma);
        } else {
            System.out.println("\nLISTADO DE LIBROS EN IDIOMA " + idioma + ":\n──────────────────────────────");
            libros.forEach(System.out::println);
        }
        return libros;
    }

    public int obtenerNumeroDescargas(Long id) {
        Optional<Libro> libroOptional = librosRepository.findById(id);
        return libroOptional.map(Libro::getNumeroDescargas).orElse(0);
    }

    public int obtenerNumeroDescargas(String titulo) {
        List<Libro> libros = librosRepository.findByTituloContainingIgnoreCase(titulo);
        if (!libros.isEmpty()) {
            return libros.get(0).getNumeroDescargas(); // podés mostrar todos si querés
        }
        return 0;
    }

}
