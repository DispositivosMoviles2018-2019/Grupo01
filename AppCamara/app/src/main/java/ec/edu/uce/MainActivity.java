package ec.edu.uce;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE = 100;
    private static final int REQUEST_VIDEO = 200;
    private Permission permission;

    Button captureImageButton;
    Button captureVideoButton;
    ImageView imageView;
    File imageDestination;
    File videoDestination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        permission = new Permission(this);

        captureImageButton = findViewById(R.id.capture_image);
        captureImageButton.setOnClickListener(listenerTakeImage);

        captureVideoButton = findViewById(R.id.capture_video);
        captureVideoButton.setOnClickListener(listenerTakeVideo);

        imageView = findViewById(R.id.image);

        imageDestination = new File(Environment.getExternalStorageDirectory(), "image.jpg");
        videoDestination = new File(Environment.getExternalStorageDirectory(), "myVideo.mp4");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE && resultCode == Activity.RESULT_OK) {
            try {
                FileInputStream in = new FileInputStream(imageDestination);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2; //Downsample by 10x
                Bitmap userImage = BitmapFactory.decodeStream(in, null, options);
                imageView.setImageBitmap(userImage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (requestCode == REQUEST_VIDEO && resultCode == Activity.RESULT_OK) {
            String location = data.getData().toString();
            Toast.makeText(this, "Video guardado: " + location, Toast.LENGTH_LONG).show();
        }
    }

    private View.OnClickListener listenerTakeImage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            permission.checkAll();
            if (permission.storage()) {
                try {
                    Uri photoURI = FileProvider.getUriForFile(MainActivity.this,
                            BuildConfig.APPLICATION_ID + ".provider", imageDestination);

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(intent, REQUEST_IMAGE);
                } catch (ActivityNotFoundException e) {
                    //Handle if no application exists
                }
            }
        }
    };

    private View.OnClickListener listenerTakeVideo = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            permission.checkAll();
            if (permission.storage()) {
                try {
                    Uri videoURI = FileProvider.getUriForFile(MainActivity.this,
                            BuildConfig.APPLICATION_ID + ".provider", videoDestination);

                    Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    //Add (optional) extra to save video to our file
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, videoURI);
                    //Optional extra to set video quality
                    intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                    startActivityForResult(intent, REQUEST_VIDEO);
                } catch (ActivityNotFoundException e) {
                    //Handle if no application exists
                }
            }
        }
    };

}
