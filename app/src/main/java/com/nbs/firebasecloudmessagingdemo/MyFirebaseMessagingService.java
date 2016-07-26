package com.nbs.firebasecloudmessagingdemo;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Sidiq on 26/07/2016.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFMService";

    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Handle data payload of FCM messages.
        Log.d(TAG, "FCM Message Id: " + remoteMessage.getMessageId());
        Log.d(TAG, "FCM Notification Message: " + remoteMessage.getNotification());
        Log.d(TAG, "FCM Data Message: " + remoteMessage.getData());

        PendingIntent mPendingIntent = PendingIntent.getActivities(getApplicationContext(), 100,
                new Intent[]{new Intent(getApplicationContext(), MainActivity.class)}, PendingIntent.FLAG_ONE_SHOT);
        createNotification(remoteMessage.getNotification().getTitle(),
                remoteMessage.getNotification().getBody(), mPendingIntent);
    }

    public void createNotification(String title, String content, @Nullable PendingIntent pendingIntent) {
        Uri soundUri = Uri.parse("android.resource://"
                + getPackageName() + "/" + R.raw.granules);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        builder.setAutoCancel(true);
        builder.setSound(soundUri);
        builder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
        builder.setContentTitle(title);
        builder.setContentText(content);
        if (pendingIntent != null) {
            builder.setContentIntent(pendingIntent);
        }
        builder.setSmallIcon(R.mipmap.ic_launcher);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(200, builder.build());
    }
}
