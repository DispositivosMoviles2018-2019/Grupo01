package ec.edu.uce;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class Permission {

    private static final int REQUEST_STORAGE = 1;

    private Activity activity;

    public Permission(Activity activity) {
        this.activity = activity;
    }

    public boolean storage() {
        return ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    public void checkAll() {
        System.out.println("Entra aqui");
        if (!storage()) {
            System.out.println("Despues aqui");
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE);
        }
    }
}
