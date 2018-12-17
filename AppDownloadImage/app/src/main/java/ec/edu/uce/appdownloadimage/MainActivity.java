package ec.edu.uce.appdownloadimage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebImageView imageView = findViewById(R.id.webImage);
        imageView.setPlaceholderImage(R.drawable.ic_launcher_background);
        imageView.setImageUrl("https://picsum.photos/400/200");
    }
}
