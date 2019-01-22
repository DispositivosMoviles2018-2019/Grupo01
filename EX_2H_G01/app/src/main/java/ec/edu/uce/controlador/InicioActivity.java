package ec.edu.uce.controlador;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import ec.edu.uce.R;
import ec.edu.uce.modelo.Ordenar;
import ec.edu.uce.servicios.ArchivosServicio;

public class InicioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.btnVehiculo).setOnClickListener(btnVehiculoListener);

        findViewById(R.id.btnReserva).setOnClickListener(btnReservaListener);

        ArchivosServicio as = new ArchivosServicio();
    }

    private View.OnClickListener btnVehiculoListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), VehiculoActivity.class);
            startActivity(intent);
            finish();
        }
    };

    private View.OnClickListener btnReservaListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), ReservaActivity.class);
            startActivity(intent);
            finish();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_configurar) {
            Intent intent = new Intent(this, ConfiguracionActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_salir) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
