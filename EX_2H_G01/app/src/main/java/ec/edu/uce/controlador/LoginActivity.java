package ec.edu.uce.controlador;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ec.edu.uce.R;
import ec.edu.uce.modelo.Usuario;
import ec.edu.uce.servicios.UsuarioServicio;

public class LoginActivity extends AppCompatActivity {

    private UsuarioServicio usuarioServicio = new UsuarioServicio(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title_activity_login);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this.floatingButtonListener);

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(btnLoginListener);
    }

    private void login(View v) {
        EditText txtUsuario = findViewById(R.id.txtUsuario);
        EditText txtClave = findViewById(R.id.txtClave);

        boolean validado = usuarioServicio.validar(txtUsuario.getText().toString(), txtClave.getText().toString());

        if (!validado) {
            Intent intent = new Intent(v.getContext(), InicioActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(v.getContext(), "Usuario o clave incorrectos.", Toast.LENGTH_SHORT).show();
        }
    }

    private View.OnClickListener btnLoginListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            login(v);
        }
    };

    private View.OnClickListener floatingButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), RegistroActivity.class);
            startActivity(intent);
        }
    };

    @Override
    protected void onDestroy() {
        usuarioServicio.close();
        super.onDestroy();
    }
}
