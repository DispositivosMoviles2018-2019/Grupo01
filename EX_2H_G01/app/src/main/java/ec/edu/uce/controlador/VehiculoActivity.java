package ec.edu.uce.controlador;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import ec.edu.uce.R;
import ec.edu.uce.componentes.ComparadorVehiculo;
import ec.edu.uce.componentes.CustomException;
import ec.edu.uce.modelo.Vehiculo;
import ec.edu.uce.servicios.ArchivosServicio;
import ec.edu.uce.servicios.ConfiguracionServicio;
import ec.edu.uce.servicios.VehiculoServicio;
import ec.edu.uce.vista.ItemClickListener;
import ec.edu.uce.vista.VehiculoAdapter;

public class VehiculoActivity extends AppCompatActivity {

    private VehiculoAdapter adapter;
    private SearchView searchView;
    private VehiculoServicio vehiculoServicio = new VehiculoServicio(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculo);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.fab).setOnClickListener(floatingButtonListener);

        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView recyclerVehiculo = findViewById(R.id.rvVehiculo);
        recyclerVehiculo.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        ConfiguracionServicio conf = new ConfiguracionServicio();
        List<Vehiculo> vehiculos = vehiculoServicio.listarTodos();

        Collections.sort(vehiculos, ComparadorVehiculo.getCompPorCosto(conf.obtener().isAsd()));

        adapter = new VehiculoAdapter(vehiculos);
        adapter.setItemClickListener(btnOpcionesListener);
        recyclerVehiculo.setAdapter(adapter);
    }

    private View.OnClickListener floatingButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), FormVehiculoActivity.class);
            startActivity(intent);
            finish();
        }
    };

    private ItemClickListener btnOpcionesListener = new ItemClickListener() {
        @Override
        public void onClick(final View view, final int position) {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext(), R.style.AlertDialogCustom);
            String[] options = {"Editar", "Eliminar"};
            builder.setTitle("Seleccione una opción");

            final Vehiculo vehiculo = adapter.obtenerVehiculo(position);

            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    switch (item) {
                        case 0: // Editar
                            Intent intent = new Intent(view.getContext(), FormVehiculoActivity.class);
                            intent.putExtra("placa", vehiculo.getPlaca());
                            startActivity(intent);
                            break;
                        case 1: // Eliminar
                            final int original = adapter.eliminarVehiculo(position);

                            Snackbar snackbar = Snackbar.make(view, "Vehiculo con la placa " + vehiculo.getPlaca() + " eliminado de la lista", Snackbar.LENGTH_LONG);
                            snackbar.setAction("DESHACER", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    adapter.restaurarVehiculo(vehiculo, position, original);
                                }
                            });
                            snackbar.addCallback(new Snackbar.Callback() {
                                @Override
                                public void onDismissed(Snackbar transientBottomBar, int event) {
                                    if (event == DISMISS_EVENT_TIMEOUT) {
                                        System.out.println("BORRA");
                                        try {
                                            vehiculoServicio.borrar(vehiculo);
                                        } catch (CustomException e) {
                                            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
                            });
                            snackbar.setActionTextColor(getResources().getColor(R.color.colorAccent));
                            snackbar.show();
                            break;
                    }
                }
            });
            builder.show();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vehiculo, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                adapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

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
        vehiculoServicio.close();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }

        Intent intent = new Intent(this, InicioActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

        super.onBackPressed();
    }
}
