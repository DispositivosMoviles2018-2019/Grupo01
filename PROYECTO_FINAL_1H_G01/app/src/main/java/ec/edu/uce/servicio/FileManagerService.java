package ec.edu.uce.servicio;

import android.os.Environment;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import ec.edu.uce.componentes.StorageException;

public class FileManagerService {

    private final File ROOT_PATH = Environment.getExternalStorageDirectory();
    public static final String FOLDER_NAME = "optativa3";

    public File getBaseFolder() {
        return new File(ROOT_PATH, FOLDER_NAME);
    }

    public boolean existFolder() {
        return getBaseFolder().exists();
    }

    public void createFolder() {
        if (!getBaseFolder().mkdirs()) {
            throw new StorageException("Error al crear la carpeta: " + FOLDER_NAME);
        }
    }

    public boolean existFile(String fileName) {
        return new File(getBaseFolder(), fileName).exists();
    }

    public void createFile(String fileName) {
        File file = new File(getBaseFolder(), fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Error al crear el archivo: " + fileName, e);
        }
    }

    public File getFile(String fileName) {
        return new File(getBaseFolder(), fileName);
    }

    public String readFile(File file) {
        String data = "";
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String line;
            while ((line = br.readLine()) != null) {
                data += line;
            }
            br.close();
        } catch (FileNotFoundException e) {
            throw new StorageException("No existe el archivo: " + file.getName(), e);
        } catch (IOException e) {
            throw new StorageException("Ocurrio un error al leer el archivo: " + file.getName(), e);
        }
        return data;
    }

    public void writeFile(File file, String data) {
        try {
            FileWriter fw = new FileWriter(file);
            PrintWriter pw = new PrintWriter(fw);

            pw.print(data);
            pw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            throw new StorageException("Error al escribir en el archivo: " + file.getName());
        }
    }

}
