package ec.edu.uce.controlador;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ec.edu.uce.R;
import ec.edu.uce.componentes.CustomException;
import ec.edu.uce.modelo.Reserva;
import ec.edu.uce.modelo.Vehiculo;
import ec.edu.uce.servicios.ReservaServicio;
import ec.edu.uce.servicios.VehiculoServicio;

public class ReservaFormActivity extends AppCompatActivity {


    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private ReservaServicio reservaServicio = new ReservaServicio(this);
    private VehiculoServicio vehiculoServicio = new VehiculoServicio(this);

    private TextView txtNumReserva;
    private TextView txtCosto;
    private TextInputEditText txtEmail;
    private TextInputEditText txtCelular;
    private TextInputEditText txtFechaPrestamo;
    private TextInputEditText txtFechaEntrega;
    //    private TextInputEditText txtCosto;
    private Boolean bandera = true;
    private String numReservaNueva = "";
    private int a;
    private Reserva reserva;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_form);
        initCampos();

        txtNumReserva.setText(crearNumReserva());
        txtCosto.setText(String.valueOf(calcularCosto()));
        txtFechaPrestamo.setOnClickListener(seleccionarFechaListener);
        txtFechaEntrega.setOnClickListener(seleccionarFechaListener1);
        Button btnAgregarReserva = findViewById(R.id.btnGuardarReserva);
        btnAgregarReserva.setOnClickListener(btnAgregarReservaListener);
    }


    private View.OnClickListener btnAgregarReservaListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            guardar(v);
        }
    };

    private void refreshTextView() {
        txtCosto.setText(String.valueOf(calcularCosto()));
    }


    private View.OnClickListener seleccionarFechaListener = new View.OnClickListener() {

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

                    txtFechaPrestamo.setText(sdf.format(myCalendar.getTime()));

                    mDay = selectedday;
                    mMonth = selectedmonth;
                    mYear = selectedyear;
                }
            }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    };


    private View.OnClickListener seleccionarFechaListener1 = new View.OnClickListener() {
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

                    txtFechaEntrega.setText(sdf.format(myCalendar.getTime()));
                    refreshTextView();
                    mDay = selectedday;
                    mMonth = selectedmonth;
                    mYear = selectedyear;
                }
            }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    };


    private void initCampos() {
        txtNumReserva = findViewById(R.id.txtNumReserva);
        txtEmail = findViewById(R.id.txtEmail);
        txtCelular = findViewById(R.id.txtCelular);
        txtFechaPrestamo = findViewById(R.id.txtFechaPrestamo);
        txtFechaEntrega = findViewById(R.id.txtFechaEntrega);
        txtCosto = findViewById(R.id.txtCosto);

    }

    public String crearNumReserva() {

        while (bandera = true) {

            a = (int) Math.floor(Math.random() * 10000);
            numReservaNueva = String.valueOf(a);
            if (reservaServicio.buscarPorNumReserva(numReservaNueva) == null) {
                bandera = false;
            }
            break;
        }
        return numReservaNueva;
    }


    public int calcularCosto() {
        String fecha1 = txtFechaPrestamo.getText().toString();
        String fecha2 = txtFechaEntrega.getText().toString();
        Date fechaA = null;
        Date fechaB = null;
        int dias = 0;
        Bundle extras = getIntent().getExtras();
        String placaActual = extras.getString("placaReserva");
        if (!fecha1.isEmpty() && !fecha2.isEmpty()) {
            System.out.println("Entre");
            try {

                fechaA = sdf.parse(fecha1);
                fechaB = sdf.parse(fecha2);

            } catch (ParseException ex) {

                ex.printStackTrace();

            }
            dias = (int) ((fechaB.getTime() - fechaA.getTime()) / 86400000);
            Vehiculo actual = vehiculoServicio.buscarPorPlaca(placaActual);
            System.out.println("********************************************************************");
            System.out.println(actual.getTipo());
            System.out.println("********************************************************************");

            if(actual.getTipo().equalsIgnoreCase("Camioneta")){
                dias = dias * 75;
            }else{
                if(actual.getTipo().equalsIgnoreCase("Automovil")){
                    dias= dias * 60;
                }else{
                    dias= dias * 100;
                }
            }
            return dias;
        }


        return dias;
    }

    private void guardar(View view) {
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
        if (TextUtils.isEmpty(txtFechaPrestamo.getText())) {
            guardar = false;
            txtFechaPrestamo.setError("Este campo es obligatorio");
        }
        if (TextUtils.isEmpty(txtFechaEntrega.getText())) {
            guardar = false;
            txtFechaEntrega.setError("Este campo es obligatorio");
        }

        Bundle extras = getIntent().getExtras();
        System.out.println("-----------------------------------------------------------------------");
        System.out.println(extras.getString("placaReserva"));
        System.out.println("-----------------------------------------------------------------------");
        if (guardar) {
            reserva = new Reserva();
            String placaAGuardar = extras.getString("placaReserva");
            reserva.setPlaca(placaAGuardar);
            reserva.setNumReserva(Integer.parseInt(txtNumReserva.getText().toString()));
            reserva.setEmail(txtEmail.getText().toString());
            reserva.setCelular(txtCelular.getText().toString());

            try {
                reserva.setFechaPrestamo(sdf.parse(txtFechaPrestamo.getText().toString()));
            } catch (ParseException e) {
                txtFechaPrestamo.setError("El formato de fecha ingresado es incorrecto");
            }

            try {
                reserva.setFechaEntrega(sdf.parse(txtFechaEntrega.getText().toString()));
            } catch (ParseException e) {
                txtFechaEntrega.setError("El formato de fecha ingresado es incorrecto");
            }



            try {
                reservaServicio.crear(reserva);

                Toast.makeText(view.getContext(), "Reserva " + reserva.getNumReserva() + " guardada correctamente", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(view.getContext(), InicioActivity.class);
                startActivity(intent);
                finish();
            } catch (CustomException e) {
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

    }

}
