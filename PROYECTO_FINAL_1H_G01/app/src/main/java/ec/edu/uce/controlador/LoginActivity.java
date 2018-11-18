package ec.edu.uce.controlador;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ec.edu.uce.R;
import ec.edu.uce.componentes.StorageException;
import ec.edu.uce.modelo.Usuario;
import ec.edu.uce.servicio.FileManagerService;
import ec.edu.uce.servicio.UserService;

public class LoginActivity extends AppCompatActivity {

    private static final int CODIGO_DE_SOLICITUD_DE_ALMACENAMIENTO = 1;

    private FileManagerService fileManagerService = new FileManagerService();
    private UserService userService = new UserService();

    private FloatingActionButton fab;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);
        btnLogin = findViewById(R.id.btnLogin);

        // Desabilita los botones por defecto
        enableButtons(false);

        checkPermissions();
    }

    public void login(View view) {
        EditText txtUsername = findViewById(R.id.txtUsername);
        EditText txtPassword = findViewById(R.id.txtPassword);

        Usuario usuario = userService.findByUsername(txtUsername.getText().toString());

        if (usuario != null) {
            if (usuario.getPassword().equals(txtPassword.getText().toString())) {
                Intent intent = new Intent(this, WelcomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "El usuario no existe por favor registrese.", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Verifica si tiene el permiso de escribir en la memoria del dispositivo, si los tiene habilita
     * los botones y crea la carpeta en donde se guardaran los documentos caso contrario solicita
     * el permiso necesario
     */
    public void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODIGO_DE_SOLICITUD_DE_ALMACENAMIENTO);
        } else {
            try {
                if (!fileManagerService.existFolder()) {
                    Toast.makeText(this, "Creando la carpeta " + fileManagerService.FOLDER_NAME + " en la memoria del dispositivo.", Toast.LENGTH_LONG).show();
                    fileManagerService.createFolder();
                }
                enableButtons(true);
            } catch (StorageException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            enableButtons(true);
        } else {
            enableButtons(false);
            Toast.makeText(this, "Para usar esta aplicacion es necesario aceptar los permisos solicitados", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Cambia el estado de los botones
     * @param status true o false para activar los botones
     */
    public void enableButtons(boolean status) {
        fab.setEnabled(status);
        btnLogin.setEnabled(status);
    }


    public void redirectToRegisterActivity(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

}
