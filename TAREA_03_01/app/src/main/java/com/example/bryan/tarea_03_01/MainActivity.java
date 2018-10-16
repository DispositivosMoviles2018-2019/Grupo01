package com.example.bryan.tarea_03_01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText usuario;
    private EditText contrasenia;
    private Button ingresar;
    private TextView info;
    private int contador = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuario = (EditText) findViewById(R.id.txtUsuario);
        contrasenia = (EditText) findViewById(R.id.txtContrasenia);
        info = (TextView)findViewById(R.id.txtInformacion);
        ingresar = (Button)findViewById(R.id.btnIngresar);

        info.setText("Numero de intentos: 5");
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validacion(usuario.getText().toString(),contrasenia.getText().toString());
            }
        });
    }



    private void validacion(String nombreUsuario, String contraseniaUsuario){
    if (((nombreUsuario.trim().equalsIgnoreCase("Bryan") ) && (contraseniaUsuario.equalsIgnoreCase("Acosta")))
            || ((nombreUsuario.trim().equalsIgnoreCase("Juan") ) && (contraseniaUsuario.equalsIgnoreCase("Jativa")))
            || ((nombreUsuario.trim().equalsIgnoreCase("Bryan") ) && (contraseniaUsuario.equalsIgnoreCase("Saltos")))
            ||((nombreUsuario.trim().equalsIgnoreCase("Monica") ) && (contraseniaUsuario.equalsIgnoreCase("Alvear")))){
        Intent intent = new Intent(MainActivity.this , SecondActivity.class);
        startActivity(intent);
    }else{
        contador--;
        info.setText("Intentos restantes: "+String.valueOf(contador));
        if (contador==0){
            ingresar.setEnabled(false);
        }
    }
    }
}
