package ec.edu.uce.controlador;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import ec.edu.uce.R;
import ec.edu.uce.componentes.CustomException;
import ec.edu.uce.modelo.Vehiculo;
import ec.edu.uce.servicio.VehiculoService;
import ec.edu.uce.vista.DataAdapter;
import ec.edu.uce.vista.ItemClickListener;

public class WelcomeActivity extends AppCompatActivity {

    private VehiculoService vehiculoService = new VehiculoService();

    public static List<Vehiculo> vehiculos = new ArrayList<>();
    public static DataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Inicializa los vehiculos
        initVehiculos();

        // Inicializa el RecyclerView
        RecyclerView recyclerStdudents = findViewById(R.id.RecyclerID);
        recyclerStdudents.setLayoutManager(new LinearLayoutManager(this));

        adapter = new DataAdapter(vehiculos, this);
        recyclerStdudents.setAdapter(adapter);

        // Define el ClickListener
        adapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, final int position, boolean isLongClick) {
                if (isLongClick) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(WelcomeActivity.this);
                    builder.setTitle("Seleccione una opción");

                    String[] options = {"Editar", "Eliminar"};
                    builder.setItems(options, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int item) {
                            switch (item) {
                                case 0: // Editar
                                    EditFormActivity.vehiculo = vehiculos.get(position);

                                    Intent intent = new Intent(WelcomeActivity.this, EditFormActivity.class);
                                    startActivity(intent);
                                    break;
                                case 1: // Eliminar
                                    vehiculos.remove(position);
                                    System.out.println("Size: " + vehiculos.size());
                                    adapter.notifyDataSetChanged();
                                    break;
                            }
                        }
                    });
                    builder.show();
                } else {
                    Toast.makeText(WelcomeActivity.this, "Manten presionado para ver las opciones", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void onStop () {
        super.onStop();
        persistData(vehiculos);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_persist) {
            persistData(vehiculos);
            return true;
        } else if (id == R.id.action_close) {
            persistData(vehiculos);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void persistData(List<Vehiculo> vehiculos) {
        try {
            if (!vehiculoService.existFile()) {
                vehiculoService.initResources(this);
            }
            this.vehiculos = vehiculoService.save(vehiculos);
            Toast.makeText(this, "Datos persistidos correctamente", Toast.LENGTH_LONG).show();
        } catch (CustomException e) {

        }
    }


    public void redirectFormActivity(View view) {
        Intent intent = new Intent(this, FormActivity.class);
        startActivity(intent);
    }

    public void initVehiculos() {
        if (vehiculoService.existFile()) {
            vehiculos = vehiculoService.getVehiculos();
        } else {
            vehiculos.add(new Vehiculo("XTR-9784", "Audi", new GregorianCalendar(2015, 11, 13).getTime(), 79990.0, true, "Negro"));
            vehiculos.add(new Vehiculo("CCD-0789", "Honda", new GregorianCalendar(1998, 3, 5).getTime(), 15340.0, false, "Blanco"));
        }
    }
}
