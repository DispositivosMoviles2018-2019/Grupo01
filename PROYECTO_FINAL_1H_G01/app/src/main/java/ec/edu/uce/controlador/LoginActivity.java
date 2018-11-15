package ec.edu.uce.controlador;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ec.edu.uce.R;
import ec.edu.uce.modelo.Usuario;
import ec.edu.uce.servicio.UserService;

public class LoginActivity extends AppCompatActivity {

    private UserService userService = new UserService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void registerActivity(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void login(View view) {
        EditText txtUsername = findViewById(R.id.txtUsername);
        EditText txtPassword = findViewById(R.id.txtPassword);

        Usuario usuario = userService.find(new Usuario(txtUsername.getText().toString(), null));

        if (usuario != null) {
            if (usuario.getPassword().equals(txtPassword.getText().toString())) {
                Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "El usuario no existe por favor registrese", Toast.LENGTH_LONG).show();
        }
    }
}
