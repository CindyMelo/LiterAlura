package com.alura.LiterAlura.principal;


import com.alura.LiterAlura.model.Idiomas;
import com.alura.LiterAlura.model.Libro;
import com.alura.LiterAlura.repository.AutorRepository;
import com.alura.LiterAlura.repository.LibrosRepository;
import com.alura.LiterAlura.service.AutorService;
import com.alura.LiterAlura.service.ConsumoApi;
import com.alura.LiterAlura.service.ConvierteDatos;
import com.alura.LiterAlura.service.LibroService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Principal {
    private Scanner teclado;
    private ConsumoApi consumoApi;
    private ConvierteDatos conversor;
    private static final String URL_BASE = "https://gutendex.com/books/";
    private List<Libro> libro;

    @Autowired
    private LibrosRepository librosRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LibroService libroService;

    @Autowired
    private AutorService autorService;

    @PostConstruct
    public void init(){
        teclado = new Scanner(System.in);
        consumoApi = new ConsumoApi();
        conversor = new ConvierteDatos();

    }
    public void muestraMenu(){
        var opcion = -1;
        while (opcion != 0){
            var menu = """
                    \\nElija el número de la opción deseada:\\n
                    1 - Buscar libro por título 
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año 
                    5 - Listar libros por idiomas
                    6 - Listar libros por título
                    0 - Salir
                    """;

            System.out.println( menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion){
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosEnAnio();
                    break;
                case 5:
                    listarLibrosPorIdiomas();
                    break;
                case 6:
                    listarLibrosPorTitulo();
                case 0:
                    System.out.println("Programa finalizado. ");
                    break;
                default:
                    System.out.println("Opción invalidad. Intente nuevamente");
            }
        }
    }


    private void buscarLibroPorTitulo() {
        System.out.println("Ingrese el nombre del libro:");
        String tituloDelLibro = teclado.nextLine();
        String json = consumoApi.obtenerDatos(URL_BASE + "?search=" + tituloDelLibro.replace(" ", "+").toLowerCase());
        libroService.buscarLibroPorTitulo(tituloDelLibro, json);

    }
    private void listarLibrosRegistrados() {
        List<Libro> libros = librosRepository.findAll();
        libros.forEach(System.out::println);
    }

    private void listarAutoresRegistrados() {
        List<String> listaAutoresOrdenada = autorService.listarAutoresRegistrados();
        System.out.println("\nLISTADO DE AUTORES REGISTRADOS:\n");
        listaAutoresOrdenada.forEach(System.out::println);
    }
    private void listarAutoresVivosEnAnio() {
        System.out.println("En esta opción podrá buscar autores vivos en un determinado año." +
                "\n¿Autores vivos en qué año desea encontrar?");

        int anio = teclado.nextInt();
        // Llama al servicio para listar los autores vivos en el año especificado
        autorService.listarAutoresVivoEnUnaFecha(anio);
    }
    private void listarLibrosPorIdiomas() {
        System.out.println("En esta opción podras buscar libros escritos en un determinado idioma. \n" +
                "¿En qué idioma deseas buscar?");
        String idiomaStr = teclado.nextLine().toLowerCase();

        idiomaStr = LibroService.eliminarTildes(idiomaStr);

        // Manejar el caso especial para "español"
        if ("español".equalsIgnoreCase(idiomaStr)) {
            idiomaStr = "CASTELLANO";
        }

        try {
            Idiomas idioma = Idiomas.valueOf(idiomaStr.toUpperCase()); // Convertir a objeto Idiomas
            libroService.listarLibrosPorIdioma(idioma);
        } catch (IllegalArgumentException e) {
            System.out.println("El idioma ingresado no es válido.");
        }
    }
    private void listarLibrosPorTitulo() {
        System.out.println("En esta opción podras buscar libros registrados en la base de datos. \n" +
                "¿Qué titulo deseas buscar?");
        String titulo = teclado.nextLine();
        List<Libro> libro = librosRepository.findByTituloContainingIgnoreCase(titulo);

        if (libro.isEmpty()) {
            System.out.println("No se encontraron libros con el titulo: " + titulo);
        } else {
            System.out.println("\nEl libro fue encontrado:\n────────────────");
            libro.forEach(System.out::println);
        }
    }






}
