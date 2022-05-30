package com.ragaban.l2m;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String title = remoteMessage.getData().get("title");
        String message = remoteMessage.getData().get("message");
        String site = remoteMessage.getData().get("site");
        String image = remoteMessage.getData().get("image");
        String channel = remoteMessage.getData().get("channel");
        String server_id = remoteMessage.getData().get("server_id");
        sendNotification(title, message, site, image, channel, server_id);



    }



    private void sendNotification(String title, String message, String site, String image, String channel, String server_id) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("link", site);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Intent intent_view = new Intent(this, View_Server.class);
        intent_view.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent_view.putExtra("link", site);
        intent_view.putExtra("server_id", server_id);
        PendingIntent pendingIntent_view = PendingIntent.getActivity(this, 0 /* Request code */, intent_view,
                PendingIntent.FLAG_ONE_SHOT);



        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(site == null){
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "channel_1")
                    .setSmallIcon(R.drawable.icon_notify)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon_app3))
                    .setStyle(new NotificationCompat.BigTextStyle())
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setContentIntent(pendingIntent)
                    .setSound(defaultSoundUri);
            notificationManager.notify(1, notificationBuilder.build());
        }
        else{
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "channel_1")
                    .setSmallIcon(R.drawable.icon_notify)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon_app3))
                    .setContentTitle(title)
                    .setContentText(message)
                    .setStyle(new NotificationCompat.BigTextStyle())
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setContentIntent(pendingIntent)
                    .addAction(android.R.drawable.ic_menu_zoom, "Посмотреть", pendingIntent_view)
                    .addAction(android.R.drawable.ic_menu_zoom, "На Сайт", pendingIntent)
                    .setSound(defaultSoundUri);
            notificationManager.notify(1, notificationBuilder.build());
        }



    }
}