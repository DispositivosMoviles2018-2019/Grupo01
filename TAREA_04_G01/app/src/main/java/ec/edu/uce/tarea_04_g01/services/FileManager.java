package ec.edu.uce.tarea_04_g01.services;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import ec.edu.uce.tarea_04_g01.exceptions.StorageException;

public class FileManager {
    private final String FILE_NAME = "data.txt";
    private final String FOLDER_NAME = "registro";

    public File initFolder(String folderName) {
        File fodlerDir = new File(Environment.getExternalStorageDirectory(), folderName);

        if (!fodlerDir.exists()) {
            if (fodlerDir.mkdirs()) {
                return fodlerDir;
            } else {
                throw new StorageException("Error al crear la carpeta: " + folderName);
            }
        }
        return fodlerDir;
    }

    public File initFile(File path, String fileName) {
        File fileDir = new File(path, fileName);

        try {
            fileDir.createNewFile();
            return fileDir;
        } catch (IOException e) {
            throw new StorageException("Error al crear el archivo: " + fileName, e);
        }
    }

    public String readFile(File file) {
        String data = new String();
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
