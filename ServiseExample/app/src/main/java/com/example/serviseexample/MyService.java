package com.example.serviseexample;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {
    MediaPlayer mediaPlayer;
    long startTime;

    public MyService() {
        startTime = System.currentTimeMillis();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this,"Service is greate", Toast.LENGTH_SHORT).show();
        mediaPlayer = MediaPlayer.create(this, R.raw.cat);
        mediaPlayer.setLooping(false);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this,"Service is work", Toast.LENGTH_SHORT).show();
        long currentTime = System.currentTimeMillis();
        while (true) {
            if (currentTime - startTime > 5000) {
                mediaPlayer.start();
                break;
            }
            currentTime = System.currentTimeMillis();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this,"Service is stoped", Toast.LENGTH_SHORT).show();
        mediaPlayer.stop();
    }

}
