package com.alura.LiterAlura.service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

// Implementa una interfaz IConiverteDatos
@Component
public class ConvierteDatos implements IConvierteDatos {
    // Crea un objeto ObjectMapper para trabajar con JSON
    private ObjectMapper objectMapper = new ObjectMapper();
    // Metodo genérico que recibe un JSON como String y una clase T para mapearlo
    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            // Convierte el JSON recibido a un objeto de la clase pasada como parámetro
            return objectMapper.readValue(json,clase);
        } catch (JsonProcessingException e) {
            // En caso de error en el parseo, lanza RuntimeException
            throw new RuntimeException(e);
        }
    }

}
