package com.example.serviceexample1;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class SoundService extends Service {
    MediaPlayer mediaPlayer;

    long startTime;
    public SoundService() {
        startTime = System.currentTimeMillis();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.cat);
        mediaPlayer.setLooping(false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        long currentTime = System.currentTimeMillis();
        while (true){
            if (currentTime - startTime > 8000){
                mediaPlayer.start();
                break;
            }
            currentTime = System.currentTimeMillis();
        }
        Intent backIntent = new Intent(this, MainActivity.class);
        PendingIntent pNotificationIntent = PendingIntent.getActivity(this, 0,
                backIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationChannel nChannel = new NotificationChannel("MyNote","Note",
                notificationManager.IMPORTANCE_HIGH);
        notificationManager.createNotificationChannel(nChannel);
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this, "MyNote")
                .setSmallIcon(R.drawable.cathead)
                .setContentTitle("Подъем!!!")
                .setContentText("Работать!!!")
                .setContentIntent(pNotificationIntent)
                .setChannelId("MyNote");
        Notification note = nBuilder.build();
        notificationManager.notify(45786, note);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
