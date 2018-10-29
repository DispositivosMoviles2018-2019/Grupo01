package ec.edu.uce.tarea_04_g01;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ec.edu.uce.tarea_04_g01.services.FileManager;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText txtUsername;
    private EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin = (Button) findViewById(R.id.btnLogin);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation(txtUsername.getText().toString(), txtPassword.getText().toString())) {
                    Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private boolean validation(String username, String password) {
        FileManager fileManager = new FileManager();

        fileManager.createFolder();
        return true;
    }
}
