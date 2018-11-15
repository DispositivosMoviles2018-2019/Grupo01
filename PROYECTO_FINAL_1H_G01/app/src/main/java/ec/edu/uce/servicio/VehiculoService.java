package ec.edu.uce.servicio;

import android.content.Context;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ec.edu.uce.componentes.CustomException;
import ec.edu.uce.componentes.StorageException;
import ec.edu.uce.modelo.Usuario;
import ec.edu.uce.modelo.Vehiculo;

public class VehiculoService {

    private ObjectMapper MAPPER = new ObjectMapper();
    private final String VEHICULO_FILE_NAME = "vehiculos.txt";

    private FileManager fileManager = new FileManager();
    private File vehiculoFile;

    public VehiculoService() {
        this.vehiculoFile = fileManager.getFile(VEHICULO_FILE_NAME);
    }

    public void initResources(Context context) {
        try {
            if (!fileManager.existFile(VEHICULO_FILE_NAME)) {
                fileManager.createFile(VEHICULO_FILE_NAME);
                Toast.makeText(context, "Creando el archivo: " + VEHICULO_FILE_NAME, Toast.LENGTH_LONG).show();
            }
        } catch (StorageException e) {
            throw new CustomException(e.getMessage(), e);
        }
    }

    public List<Vehiculo> save(List<Vehiculo> vehiculos) {
        try {
            fileManager.writeFile(vehiculoFile, MAPPER.writeValueAsString(vehiculos));
            return getVehiculos();
        } catch (JsonProcessingException e) {
            throw new CustomException("Error al guardar los datos del vehiculo", e);
        }
    }

    public List<Vehiculo> getVehiculos() {
        String vehiculoData = fileManager.readFile(vehiculoFile);

        List<Vehiculo> vehiculos = new ArrayList<>();
        if (!vehiculoData.isEmpty()) {
            try {
                vehiculos = MAPPER.readValue(vehiculoData, new TypeReference<List<Vehiculo>>() {
                });
            } catch (IOException e) {
                throw new CustomException("Error al leer el archivo: " + VEHICULO_FILE_NAME, e);
            }
        }
        return vehiculos;
    }

    public boolean existFile() {
        return fileManager.existFile(VEHICULO_FILE_NAME);
    }
}
