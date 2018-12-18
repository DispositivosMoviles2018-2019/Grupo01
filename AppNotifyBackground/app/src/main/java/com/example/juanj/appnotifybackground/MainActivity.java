package com.example.juanj.appnotifybackground;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    private RadioGroup mOptionsGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mOptionsGroup = (RadioGroup) findViewById(R.id.options_group);
    }

    public void onPostClick(View v) {

        final int noteId = mOptionsGroup.getCheckedRadioButtonId();
        final Notification note;

        switch (noteId) {
            case R.id.option_basic:
            case R.id.option_bigtext:
            case R.id.option_bigpicture:
            case R.id.option_inbox:
                note = buildStyleNotification(noteId);
                break;
            case R.id.option_private:
            case R.id.option_secret:
            case R.id.option_headsup:
                note = buildSecuredNotification(noteId);
                break;
            default:
                throw new IllegalArgumentException("Unknown Type");
        }
        NotificationManager manger =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manger.notify(noteId, note);
    }

    private Notification buildStyleNotification(int type) {
        Intent launchIntent =
                new Intent(this, MainActivity.class);
        PendingIntent contentIntent =
                PendingIntent.getActivity(this, 0, launchIntent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                MainActivity.this);

        builder.setSmallIcon(R.drawable.ic_launcher_background)
                .setTicker("Something Happened")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentTitle("We're Finished!")
                .setContentText("Click Here")
                .setContentIntent(contentIntent);

        switch (type) {
            case R.id.option_basic:
                return builder.build();
            case R.id.option_bigtext:
                builder.addAction(android.R.drawable.ic_menu_call,
                        "Call", contentIntent);
                builder.addAction(android.R.drawable.ic_menu_recent_history,
                        "History", contentIntent);
                NotificationCompat.BigTextStyle textStyle =
                    new NotificationCompat.BigTextStyle(builder);
                textStyle.bigText(
                        "Here is some additional text to be displayed when the " +
                                "notification is " +
                                "in expanded mode. I can fit so much more content into this"
                                + "giant view!");
                return textStyle.build();
            case R.id.option_bigpicture:
                builder.addAction(android.R.drawable.ic_menu_compass,
                        "View Location", contentIntent);
                NotificationCompat.BigPictureStyle pictureStyle =
                        new NotificationCompat.BigPictureStyle(builder);
                pictureStyle.bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.dog));

                return pictureStyle.build();
            case R.id.option_inbox:
                NotificationCompat.InboxStyle inboxStyle =
                        new NotificationCompat.InboxStyle(builder);
                inboxStyle.setSummaryText("4 New Tasks");
                inboxStyle.addLine("Make Dinner");
                inboxStyle.addLine("Call Mom");
                inboxStyle.addLine("Call Wife First");
                inboxStyle.addLine("Pick up Kids");
                return inboxStyle.build();
            default:
                throw new IllegalArgumentException("Unknown Type");
        }
    }

    private Notification buildSecuredNotification(int type) {
        Intent launchIntent =
                new Intent(this, MainActivity.class);
        PendingIntent contentIntent =
                PendingIntent.getActivity(this, 0, launchIntent, 0);
//Construct the base notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Account Balance Update")
                .setContentText("Your account balance is -$250.00")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Your account balance is -$250.00; pay us please "
                                + "or we will be forced to take legal action!"))
                .setContentIntent(contentIntent);
        switch (type) {
            case R.id.option_private:
//Provide a unique version for secured lock screens
                Notification publicNote = new Notification.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("Account Notification")
                        .setContentText("An important message has arrived.")
                        .setContentIntent(contentIntent)
                        .build();

                return builder.setPublicVersion(publicNote)
                        .build();
            case R.id.option_secret:
//Hide the notification from a secured lock screen completely
                return builder.setVisibility(Notification.VISIBILITY_SECRET)
                        .build();
            case R.id.option_headsup:
//Show a heads-up notification when posted
                return builder.setDefaults(Notification.DEFAULT_SOUND)
                        .setPriority(Notification.PRIORITY_HIGH)
                        .build();
            default:
                throw new IllegalArgumentException("Unknown Type");
        }
    }

}
