package ec.edu.uce.controlador;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import ec.edu.uce.R;
import ec.edu.uce.modelo.Vehiculo;
import ec.edu.uce.servicios.ReservaServicio;
import ec.edu.uce.servicios.VehiculoServicio;

public class Reserva_Activity extends AppCompatActivity {

    private ReservaServicio reservaServicio = new ReservaServicio(this);
    private VehiculoServicio vehiculoServicio = new VehiculoServicio(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_activity);


//        Button btnVehiculo = findViewById(R.id.btnVehiculo);
//        btnVehiculo.setOnClickListener(btnVehiculoListener);
//
//        Button btnReserva = findViewById(R.id.btnReserva);
//        btnVehiculo.setOnClickListener(btnReservaListener);

        Button btnBuscar = findViewById(R.id.btnBuscarPlaca);
        btnBuscar.setOnClickListener(btnBuscarListener);
        Button btnListarReservas = findViewById(R.id.btnListarReservas);
        btnListarReservas.setOnClickListener(btnListarReservasListener);
    }


    private View.OnClickListener btnBuscarListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            existeCarro(v);
        }
    };


    private View.OnClickListener btnListarReservasListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), ListarReservas.class);
            startActivity(intent);
        }
    };

    private void existeCarro(View v) {
        TextInputEditText txtPlaca = findViewById(R.id.txtPlacaBuscar);
        boolean bandera = true;

        if (TextUtils.isEmpty(txtPlaca.getText())) {
            bandera = false;
            txtPlaca.setError("El campo placa es obligatorio");
        }
        if (!txtPlaca.getText().toString().matches("([A-Za-z]{3}-[0-9]{3,4})")) {
            bandera = false;
            txtPlaca.setError("La placa debe tener el siguiente formato: ABC-1234");
        }

        if (bandera) {


            if (vehiculoServicio.buscarPorPlaca(txtPlaca.getText().toString()) != null) {
                Vehiculo temporal = vehiculoServicio.buscarPorPlaca(txtPlaca.getText().toString());
                if (temporal.getEstado()) {
//                Toast.makeText(v.getContext(), "Si funca we :v", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(v.getContext(), ReservaFormActivity.class);
                    intent.putExtra("placaReserva", txtPlaca.getText().toString());
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(v.getContext(), "Vehiculo no disponible para alquilar", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(v.getContext(), "No existe esa placa", Toast.LENGTH_SHORT).show();

            }
        }
    }
}
