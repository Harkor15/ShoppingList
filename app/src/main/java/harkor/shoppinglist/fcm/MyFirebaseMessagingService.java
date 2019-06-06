package harkor.shoppinglist.fcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

import harkor.shoppinglist.R;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage message) {
        showNotification(message.getNotification().getTitle(),message.getNotification().getBody());
    }

    private void showNotification(String title, String body) {
        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID="harkor.shoppinglist.test";

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel notificationChannel=new NotificationChannel(NOTIFICATION_CHANNEL_ID,"Notification",
                    NotificationManager.IMPORTANCE_DEFAULT);

            notificationChannel.setDescription("Shopping list");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setVibrationPattern(new long[]{0,1000,500,1000});
            notificationChannel.enableLights(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_shopping_cart)
                .setContentTitle(title)
                .setContentText(body)
                .setContentInfo("Info");
        notificationManager.notify(new Random().nextInt(),notificationBuilder.build());
    }
}
