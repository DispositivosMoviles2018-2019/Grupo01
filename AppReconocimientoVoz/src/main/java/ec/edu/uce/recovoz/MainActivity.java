package ec.edu.uce.recovoz;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void getLectura(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        }else{
            Toast.makeText(this,"Error ",Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle = getIntent().getExtras();
        System.out.println("******************************************");
        System.out.println(requestCode);
        System.out.println("******************************************");
        System.out.println(resultCode);
        System.out.println("******************************************");
        System.out.println(bundle);

        switch (requestCode){
            case 10:
                try {
                    if (resultCode == RESULT_OK && data != null) {
                        ArrayList<String> result= data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                        textView.setText(result.get(2));
                    }
                }catch(NullPointerException e){
                    System.out.println("Error"+e);
                    Toast.makeText(this,data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0).toString(),Toast.LENGTH_LONG).show();
                }
             break;
        }
    }
}
