package ec.edu.uce.controlador;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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

        Button btnRegistro = findViewById(R.id.btnRegistro);
        btnRegistro.setOnClickListener(this.btnRegistroListener);
    }

    private void registrar(View v) {
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

    private View.OnClickListener btnRegistroListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            registrar(v);
        }
    };

    @Override
    protected void onDestroy() {
        usuarioServicio.close();
        super.onDestroy();
    }
}
