package ec.edu.uce.controlador;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import ec.edu.uce.R;
import ec.edu.uce.modelo.Ordenar;
import ec.edu.uce.servicios.ConfiguracionServicio;

public class ConfiguracionActivity extends AppCompatActivity {

    private ConfiguracionServicio conf = new ConfiguracionServicio();

    private Switch swOrdenar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.btnGuardar).setOnClickListener(btnGuardarListener);

        swOrdenar = findViewById(R.id.swAsc);
        swOrdenar.setChecked(conf.obtener().isAsd());
    }

    View.OnClickListener btnGuardarListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Ordenar ordenar = new Ordenar("costo", swOrdenar.isChecked());
            conf.guardar(ordenar);

            onBackPressed();
        }
    };

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, InicioActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }


}
