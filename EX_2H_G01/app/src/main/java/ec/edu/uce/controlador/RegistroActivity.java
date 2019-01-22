package ec.edu.uce.controlador;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ec.edu.uce.R;
import ec.edu.uce.componentes.CustomException;
import ec.edu.uce.modelo.Usuario;
import ec.edu.uce.servicios.UsuarioServicio;

public class RegistroActivity extends AppCompatActivity {

    private UsuarioServicio usuarioServicio = new UsuarioServicio(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.btnRegistro).setOnClickListener(this.btnRegistroListener);
    }

    private View.OnClickListener btnRegistroListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextInputEditText txtUsuario = findViewById(R.id.txtUsuario);
            TextInputEditText txtClave = findViewById(R.id.txtClave);

            boolean registrar = true;

            if (TextUtils.isEmpty(txtUsuario.getText())) {
                registrar = false;
                txtUsuario.setError("El campo usuario es obligatorio");
            }
            if (TextUtils.isEmpty(txtClave.getText())) {
                registrar = false;
                txtClave.setError("El campo clave es obligatorio");
            }
            if (!txtClave.getText().toString().matches("(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{5,}")) {
                registrar = false;
                txtClave.setError("La clave debe tener 5 caracteres minimo mayusculas minusculas un número y un caracter especial");
            }

            if(registrar) {
                Usuario usuario = new Usuario(txtUsuario.getText().toString(), txtClave.getText().toString());
                try {
                    usuarioServicio.crear(usuario);
                    Intent intent = new Intent(v.getContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                } catch (CustomException e) {
                    Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }
    };


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        usuarioServicio.close();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
