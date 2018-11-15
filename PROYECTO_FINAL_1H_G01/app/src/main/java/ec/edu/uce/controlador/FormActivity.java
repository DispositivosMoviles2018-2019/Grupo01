package ec.edu.uce.controlador;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ec.edu.uce.R;
import ec.edu.uce.componentes.CustomException;
import ec.edu.uce.modelo.Vehiculo;
import ec.edu.uce.servicio.VehiculoService;
import ec.edu.uce.vista.DatePickerFragment;

public class FormActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private VehiculoService vehiculoService = new VehiculoService();

    private EditText txtPlaca;
    private EditText txtMarca;
    private EditText txtCosto;
    private EditText txtColor;
    private Switch wsEnrollment;
    private TextView txtDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void save(View view) {
        txtPlaca = findViewById(R.id.txt_license);
        txtMarca = findViewById(R.id.txt_brand);
        txtCosto = findViewById(R.id.txt_costo);
        txtColor = findViewById(R.id.txt_color);
        wsEnrollment = findViewById(R.id.sw_enrollment);
        txtDate = findViewById(R.id.txt_date);

        String datePattern = "dd MMMM yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(datePattern);

        // Todo eliminar
        System.out.println("Precios: " + txtCosto.getText().toString());

        Double cost = Double.parseDouble(txtCosto.getText().toString());
        Boolean isEnrollment = wsEnrollment.isChecked();
        Date date = new Date();
        try {
            date = sdf.parse(txtDate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setPlaca(txtPlaca.getText().toString());
        vehiculo.setMarca(txtMarca.getText().toString());
        vehiculo.setCosto(cost);
        vehiculo.setColor(txtColor.getText().toString());
        vehiculo.setMatriculado(isEnrollment);
        vehiculo.setFechaFabricacion(date);

        try {
            if (!WelcomeActivity.vehiculos.contains(vehiculo)) {
                WelcomeActivity.vehiculos.add(vehiculo);

                WelcomeActivity.adapter.notifyDataSetChanged();

                Toast.makeText(this, "Vehiculo con la placa " + vehiculo.getPlaca().toUpperCase() + " agregado correctamente", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(this, "Vehiculo con la placa " + vehiculo.getPlaca().toUpperCase() + " ya existe", Toast.LENGTH_LONG).show();
            }
        } catch (CustomException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void showDataPicker(View view) {
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(), "date picker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String datePattern = "dd MMMM yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(datePattern);

        String date = sdf.format(c.getTime());
        TextView txtFecha = findViewById(R.id.txt_date);
        txtFecha.setText(date);
    }
}
