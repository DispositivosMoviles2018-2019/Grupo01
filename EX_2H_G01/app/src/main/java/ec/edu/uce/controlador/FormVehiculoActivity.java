package ec.edu.uce.controlador;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import ec.edu.uce.R;
import ec.edu.uce.componentes.CustomException;
import ec.edu.uce.modelo.Vehiculo;
import ec.edu.uce.servicios.VehiculoServicio;

public class FormVehiculoActivity extends AppCompatActivity {

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private VehiculoServicio vehiculoServicio = new VehiculoServicio(this);
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_PICK_IMAGE = 2;

    private TextInputEditText txtPlaca;
    private TextInputEditText txtMarca;
    private TextInputEditText txtFecha;
    private TextInputEditText txtCosto;
    private TextInputEditText txtColor;
    private Switch swMatriculado;
    private Switch swEstado;
    private RadioGroup rgTipo;
    private ImageView ivFoto;

    private BottomSheetDialog dialog;
    private Bitmap fotoBitmap;
    private Vehiculo vehiculo;
    private boolean nuevo;
    private String placaAnterior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_vehiculo);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initCampos();

        findViewById(R.id.btnFoto).setOnClickListener(btnFotoListener);
        findViewById(R.id.btnGuardar).setOnClickListener(btnGuardarListener);

        txtFecha.setOnClickListener(seleccionarFechaListener);

        placaAnterior = getIntent().getStringExtra("placa");
        nuevo = placaAnterior == null;
        if (nuevo) {
            vehiculo = new Vehiculo();
            swEstado.setChecked(true);
        } else {
            vehiculo = vehiculoServicio.buscarPorPlaca(placaAnterior);
            swEstado.setClickable(true);
            llenarCampos();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            fotoBitmap = (Bitmap) extras.get("data");
            ivFoto.setImageBitmap(fotoBitmap);
            ivFoto.setVisibility(View.VISIBLE);
        }
        if (requestCode == REQUEST_PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                fotoBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ivFoto.setImageBitmap(fotoBitmap);
                ivFoto.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private View.OnClickListener llTomarFotoListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog.cancel();

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    };

    private View.OnClickListener llAbrirGaleriaListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog.cancel();

            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_PICK_IMAGE);
        }
    };

    private View.OnClickListener btnFotoListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            View view = getLayoutInflater().inflate(R.layout.fragment_bottom_dialog, null);

            dialog = new BottomSheetDialog(v.getContext());
            dialog.setContentView(view);
            dialog.show();

            view.findViewById(R.id.llTomarFoto).setOnClickListener(llTomarFotoListener);
            view.findViewById(R.id.llAbrirGaleria).setOnClickListener(llAbrirGaleriaListener);
        }
    };

    private View.OnClickListener btnGuardarListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            boolean guardar = true;

            // Validaciones
            if (!txtPlaca.getText().toString().matches("([A-Za-z]{3}-[0-9]{3,4})")) {
                guardar = false;
                txtPlaca.setError("La palca debe tener el siguiente formato: ABC-1234");
            }
            if (TextUtils.isEmpty(txtMarca.getText())) {
                guardar = false;
                txtMarca.setError("Este campo es obligatorio");
            }
            if (TextUtils.isEmpty(txtFecha.getText())) {
                guardar = false;
                txtFecha.setError("Este campo es obligatorio");
            }
            if (TextUtils.isEmpty(txtCosto.getText())) {
                guardar = false;
                txtCosto.setError("Este campo es obligatorio");
            }
            if (TextUtils.isEmpty(txtColor.getText())) {
                guardar = false;
                txtColor.setError("Este campo es obligatorio");
            }
            if (fotoBitmap == null) {
                guardar = false;
                Snackbar.make(view, "Debe  seleccionar una foto de vehiculo", Snackbar.LENGTH_LONG).show();
            }


            if (guardar) {
                vehiculo.setPlaca(txtPlaca.getText().toString());
                vehiculo.setMarca(txtMarca.getText().toString());
                vehiculo.setColor(txtColor.getText().toString());
                vehiculo.setCosto(Double.parseDouble(txtCosto.getText().toString()));
                vehiculo.setMatriculado(swMatriculado.isChecked());
                vehiculo.setEstado(swEstado.isChecked());
                vehiculo.setFoto(fotoBitmap);
                try {
                    vehiculo.setFechaFabricacion(sdf.parse(txtFecha.getText().toString()));
                } catch (ParseException e) {
                    txtFecha.setError("El formato de fecha ingresado es incorrecto");
                }
                RadioButton rbTipo = findViewById(rgTipo.getCheckedRadioButtonId());
                vehiculo.setTipo(rbTipo.getText().toString());

                try {
                    if (nuevo) {
                        vehiculoServicio.crear(vehiculo);
                    } else {
                        vehiculoServicio.actualizar(placaAnterior, vehiculo);
                    }
                    Toast.makeText(view.getContext(), "Vehiculo " + vehiculo.getPlaca() + " guardado correctamente", Toast.LENGTH_LONG).show();
                    onBackPressed();
                } catch (CustomException e) {
                    Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }
    };

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

                    txtFecha.setText(sdf.format(myCalendar.getTime()));

                    mDay = selectedday;
                    mMonth = selectedmonth;
                    mYear = selectedyear;
                }
            }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    };

    private void initCampos() {
        txtPlaca = findViewById(R.id.txtPlaca);
        txtMarca = findViewById(R.id.txtMarca);
        txtFecha = findViewById(R.id.txtFecha);
        txtCosto = findViewById(R.id.txtCosto);
        txtColor = findViewById(R.id.txtColor);
        swMatriculado = findViewById(R.id.swMatriculado);
        swEstado = findViewById(R.id.swEstado);
        rgTipo = findViewById(R.id.rgTipo);
        ivFoto = findViewById(R.id.ivFoto);
    }

    private void llenarCampos() {
        txtPlaca.setText(vehiculo.getPlaca());
        txtMarca.setText(vehiculo.getMarca());
        txtFecha.setText(sdf.format(vehiculo.getFechaFabricacion()));
        txtCosto.setText(String.valueOf(vehiculo.getCosto()));
        swMatriculado.setChecked(vehiculo.getMatriculado());
        txtColor.setText(vehiculo.getColor());
        swEstado.setChecked(vehiculo.getEstado());
        ivFoto.setImageBitmap(vehiculo.getFoto());
        ivFoto.setVisibility(View.VISIBLE);
        fotoBitmap = vehiculo.getFoto();

        RadioButton rbTipo = rgTipo.findViewWithTag(vehiculo.getTipo());
        if (rbTipo != null) {
            rbTipo.setChecked(true);
        }
    }

    @Override
    protected void onDestroy() {
        vehiculoServicio.close();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, VehiculoActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}
