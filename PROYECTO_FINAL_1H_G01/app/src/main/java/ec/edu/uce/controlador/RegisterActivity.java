package ec.edu.uce.controlador;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ec.edu.uce.R;
import ec.edu.uce.componentes.CustomException;
import ec.edu.uce.modelo.Usuario;
import ec.edu.uce.servicio.UserService;

public class RegisterActivity extends AppCompatActivity {

    private UserService userService = new UserService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void register(View view) {
        EditText txtUsername = findViewById(R.id.txtUsername);
        EditText txtPassword = findViewById(R.id.txtPassword);

        try {
            userService.createUsersFileIfNotExist(this);

            Usuario usuario = new Usuario(txtUsername.getText().toString(), txtPassword.getText().toString());
            if (userService.existUsuario(usuario)) {
                Toast.makeText(this, "El nombre de usaurio " + usuario.getUsername() + " ya existe ingrese otro", Toast.LENGTH_LONG).show();
            } else {
                userService.save(usuario);
                Toast.makeText(this, "Usuario " + usuario.getUsername() + " registrado correctamente", Toast.LENGTH_LONG).show();
                finish();
            }
        }catch (CustomException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
