package ec.edu.uce.tarea_04_g01.Services;

import android.os.Environment;
import android.util.Log;

import java.io.File;

public class FileManager {
    private final String FILE_NAME = "data.txt";
    private final String FOLDER_NAME = "registro";

    public void createFolder() {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), FOLDER_NAME);

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("App", "failed to create directory");
            }
        }
    }

}
