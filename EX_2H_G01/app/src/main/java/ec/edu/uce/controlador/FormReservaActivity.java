package ec.edu.uce.controlador;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import ec.edu.uce.R;
import ec.edu.uce.componentes.CustomException;
import ec.edu.uce.modelo.Reserva;
import ec.edu.uce.modelo.Vehiculo;
import ec.edu.uce.servicios.ReservaServicio;
import ec.edu.uce.servicios.VehiculoServicio;

public class FormReservaActivity extends AppCompatActivity {

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private VehiculoServicio vehiculoServicio = new VehiculoServicio(this);
    private ReservaServicio reservaServicio = new ReservaServicio(this);
    private Vehiculo vehiculo;

    private LinearLayout llForm;
    private TextInputEditText txtPlaca;
    private TextInputEditText txtEmail;
    private TextInputEditText txtCelular;
    private TextInputEditText txtPrestamo;
    private TextInputEditText txtEntrega;
    private TextView txtValor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_reserva);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initCampos();

        findViewById(R.id.btnBuscar).setOnClickListener(btnBuscarListener);
        findViewById(R.id.btnGuardar).setOnClickListener(btnGuardarListener);

        txtPrestamo.setOnClickListener(seleccionarFechaListener(txtPrestamo));
        txtEntrega.setOnClickListener(seleccionarFechaListener(txtEntrega));
    }

    private View.OnClickListener btnBuscarListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            boolean buscar = true;
            if (!txtPlaca.getText().toString().matches("([A-Za-z]{3}-[0-9]{3,4})")) {
                buscar = false;
                txtPlaca.setError("La palca debe tener el siguiente formato: ABC-1234");
            }

            if (buscar) {
                String placa = txtPlaca.getText().toString();
                vehiculo = vehiculoServicio.buscarPorPlaca(placa);
                boolean existe = vehiculo != null;
                if (existe) {
                    if (vehiculo.getEstado()) {
                        txtPlaca.setFocusable(false);
                        llForm.setVisibility(View.VISIBLE);
                    } else {
                        Snackbar.make(view, "El vehiculo " + placa + " no esta disponible", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(view, "El vehiculo " + placa + " no existe", Snackbar.LENGTH_LONG).show();
                }
            }
        }
    };

    private View.OnClickListener btnGuardarListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            boolean guardar = true;

            // Validaciones
            if (TextUtils.isEmpty(txtEmail.getText())) {
                guardar = false;
                txtEmail.setError("Este campo es obligatorio");
            }
            if (TextUtils.isEmpty(txtCelular.getText())) {
                guardar = false;
                txtCelular.setError("Este campo es obligatorio");
            }
            if (TextUtils.isEmpty(txtPrestamo.getText())) {
                guardar = false;
                txtPrestamo.setError("Este campo es obligatorio");
            }
            if (TextUtils.isEmpty(txtEntrega.getText())) {
                guardar = false;
                txtEntrega.setError("Este campo es obligatorio");
            }


            if (guardar) {
                Reserva reserva = new Reserva();

                reserva.setNumero(reservaServicio.getNuevoNumero());
                reserva.setEmail(txtEmail.getText().toString());
                reserva.setCelular(txtCelular.getText().toString());
                reserva.setValor(Double.parseDouble(txtValor.getText().toString()));
                reserva.setVehiculo(vehiculo);
                try {
                    reserva.setFechaPrestamo(sdf.parse(txtPrestamo.getText().toString()));
                    reserva.setFechaEntrega(sdf.parse(txtEntrega.getText().toString()));
                } catch (ParseException e) {
                    txtPrestamo.setError("El formato de fecha ingresado es incorrecto");
                    txtEntrega.setError("El formato de fecha ingresado es incorrecto");
                }

                try {
                    reservaServicio.crear(reserva);
                    Toast.makeText(view.getContext(), "Reserva " + reserva.getNumero() + " guardado correctamente", Toast.LENGTH_LONG).show();
                    onBackPressed();
                } catch (CustomException e) {
                    Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }
    };

    private View.OnClickListener seleccionarFechaListener(final TextInputEditText inputEditText) {
        return new View.OnClickListener() {
            int mYear, mMonth, mDay;

            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                mYear = mcurrentDate.get(Calendar.YEAR);
                mMonth = mcurrentDate.get(Calendar.MONTH);
                mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(), R.style.TimePickerTheme, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        Calendar myCalendar = Calendar.getInstance();
                        myCalendar.set(Calendar.YEAR, selectedyear);
                        myCalendar.set(Calendar.MONTH, selectedmonth);
                        myCalendar.set(Calendar.DAY_OF_MONTH, selectedday);

                        inputEditText.setText(sdf.format(myCalendar.getTime()));
                        calcularValor();
                        mDay = selectedday;
                        mMonth = selectedmonth;
                        mYear = selectedyear;
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        };
    }

    private void calcularValor() {
        boolean calcular = true;

        if (TextUtils.isEmpty(txtPrestamo.getText()) || TextUtils.isEmpty(txtEntrega.getText())) {
            calcular = false;
        }

        if (calcular) {
            Date start, end;
            Double valor = 0.0;
            try {
                start = sdf.parse(txtPrestamo.getText().toString());
                end = sdf.parse(txtEntrega.getText().toString());

                long dias = TimeUnit.DAYS.convert(start.getTime() - end.getTime(), TimeUnit.MILLISECONDS);
                System.out.println("DIAS: " + dias);
                if (dias >= 0) {
                    valor = vehiculo.getCosto() * dias;
                }
            } catch (ParseException e) {
                txtPrestamo.setError("El formato de fecha ingresado es incorrecto");
                txtEntrega.setError("El formato de fecha ingresado es incorrecto");
            }
            txtValor.setText(String.valueOf(valor));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    private void initCampos() {
        llForm = findViewById(R.id.llForm);

        txtPlaca = findViewById(R.id.txtPlaca);
        txtEmail = findViewById(R.id.txtEmail);
        txtCelular = findViewById(R.id.txtCelular);
        txtPrestamo = findViewById(R.id.txtPrestamo);
        txtEntrega = findViewById(R.id.txtEntrega);
        txtValor = findViewById(R.id.txtValor);
        txtValor.setText("0.00");
    }

    @Override
    protected void onDestroy() {
        vehiculoServicio.close();
        reservaServicio.close();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ReservaActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

        super.onBackPressed();
    }

}
