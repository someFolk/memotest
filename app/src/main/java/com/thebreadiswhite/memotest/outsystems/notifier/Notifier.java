package com.thebreadiswhite.memotest.outsystems.notifier;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import com.thebreadiswhite.memotest.R;
import com.thebreadiswhite.memotest.activities.Splash;


// IMPORTANT !!
// After modifing this class you should uninstall and
// reinstall the app in order for the changes to take affect;

public class Notifier
{

    public final String CHANNEL_ID;
    private Context context;
    public Notifier(Context context, String channelID, String textTitle, String textContent)
    {
        this.context = context;
        CHANNEL_ID = channelID;
        sendMyNotification(textTitle, textContent);
    }

    private void sendMyNotification(String title, String message)
    {
        Intent intent = new Intent(context, Splash.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        // This for the use of custom sounds on notifications
        // In order to use this you need a setting utility and a db for configuring
        // Until then -> you should use the defaults so the user will
        // think he has a new message and it will attract him to look upon
        //Uri soundUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.hiccup);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setDefaults(Notification.DEFAULT_LIGHTS| Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                // Vibrate -> sleep - > vibrate -> sleep
                //.setVibrate(new long[] {1000,100,100,1})
                //.setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setTimeoutAfter(3600000)
                .setPriority(NotificationManager.IMPORTANCE_HIGH);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Look above for the comment for the sound URI
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//
//            if(soundUri != null){
//                // Changing Default mode of notification
//                notificationBuilder.setDefaults(Notification.DEFAULT_VIBRATE);
//                // Creating an Audio Attribute
//                AudioAttributes audioAttributes = new AudioAttributes.Builder()
//                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
//                        .setUsage(AudioAttributes.USAGE_ALARM)
//                        .build();
//
//                // Creating Channel
//                NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,"Testing_Audio",NotificationManager.IMPORTANCE_HIGH);
//                notificationChannel.setSound(soundUri,audioAttributes);
//                mNotificationManager.createNotificationChannel(notificationChannel);
//            }
//        }

        mNotificationManager.notify(0, notificationBuilder.build());
    }
}
