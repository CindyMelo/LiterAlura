// Paquete donde se encuentra esta clase

package com.alura.LiterAlura.service;
// Importaciones necesarias para trabajar con HTTP y manejo de errores
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
// Clase que se encarga de consumir una API (en este caso, Gutendex por ejemplo)
public class ConsumoApi {
    // Metodo que recibe una URL como parámetro y devuelve la respuesta en formato String (JSON)
    public String obtenerDatos(String url) {
        // Se crea una instancia de HttpClient (el cliente HTTP que envía la solicitud)
        HttpClient client = HttpClient.newHttpClient();
        // Se construye una solicitud HTTP con la URL que recibimos como parámetro
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url)) // Se convierte la URL en un objeto URI
                .build(); // Se construye el objeto request
        // Se declara una variable para guardar la respuesta
        HttpResponse<String> response = null;

        try {
            // Se envia la solicitud al servidor y se guarda la respuesta como texto

            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            // Si hay un problema de entrada/salida (por ejemplo, no hay internet), lanza una excepción

            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            // Si el hilo se interrumpe mientras espera la respuesta, también lanza una excepción

            throw new RuntimeException(e);
        }
        // Se obtiene el cuerpo de la respuesta (lo que devuelve la API en formato JSON)

        String json = response.body();
        // Se devuelve el JSON al que llamó este metodo

        return json;

    }
}
