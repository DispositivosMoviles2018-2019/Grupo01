package ec.edu.uce.servicios;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

import ec.edu.uce.componentes.CustomException;
import ec.edu.uce.modelo.Ordenar;

public class ConfiguracionServicio {

    private ObjectMapper MAPPER = new ObjectMapper();
    private ArchivosServicio archivosServicio = new ArchivosServicio();
    private static final String FILE = "confg.txt";
    private File file;

    public ConfiguracionServicio() {
        if (!archivosServicio.existFolder()) {
            archivosServicio.createFolder();
        }
        file = archivosServicio.getFile(FILE);

        if (!archivosServicio.existFile(FILE)) {
            archivosServicio.createFile(FILE);

            Ordenar ordenar = new Ordenar();
            ordenar.setAtributo("costo");
            ordenar.setAsd(true);
            try {
                archivosServicio.writeFile(file, MAPPER.writeValueAsString(ordenar));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

    }

    public void guardar(Ordenar ordenar) {
        try {
            archivosServicio.writeFile(file, MAPPER.writeValueAsString(ordenar));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public Ordenar obtener() {
        try {
            return MAPPER.readValue(file, Ordenar.class);
        } catch (IOException e) {
            throw new CustomException("Error al leer el archivoi");
        }
    }
}
