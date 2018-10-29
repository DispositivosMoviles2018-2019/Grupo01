package ec.edu.uce.tarea_04_g01;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import ec.edu.uce.tarea_04_g01.adapters.StudentAdapter;
import ec.edu.uce.tarea_04_g01.models.Student;

public class WelcomeActivity extends AppCompatActivity {

    ArrayList<Student> students;
    RecyclerView recyclerStdudents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        students = new ArrayList<>();
        recyclerStdudents = (RecyclerView) findViewById(R.id.RecyclerID);
        recyclerStdudents.setLayoutManager(new LinearLayoutManager(this));

        fillStudents();

        StudentAdapter adapter = new StudentAdapter(students);
        recyclerStdudents.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_back) {
            goToLogin();
            return true;
        } else if (id == R.id.action_close) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void goToLogin(){
        Intent intent = new Intent(this , LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void fillStudents() {

        students.add(new Student("Bryan Acosta", "Username: bacosta, Email: acosta@hotmail.com, Telefono: 0923423424, Sexo: hombre, Asignaturas: [Programación], Beca: No", R.drawable.acosta));
        students.add(new Student("Monica Alvear", "Username: malvear, Email: alvear@hotmail.com, Telefono: 0923734859, Sexo: mujer, Asignaturas: [Matemáticas, Física], Beca: Si", R.drawable.alvear));
        students.add(new Student("Bryan Saltos", "Username: bsaltos, Email: saltos@hotmail.com, Telefono: 0923876534, Sexo: hombre, Asignaturas: [Química, Programación], Beca: Si", R.drawable.saltos));
        students.add(new Student("Juan Jativa", "Username: jativa, Email: jativa@hotmail.com, Telefono: 0923474534, Sexo: hombre, Asignaturas: [Física], Beca: No", R.drawable.jativa));

    }
}
