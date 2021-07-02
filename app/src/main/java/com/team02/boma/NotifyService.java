package com.team02.boma;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotifyService extends BroadcastReceiver {

    // Implement the onReceive method to receive intent from MainActivity
    //  Referenced from: https://www.youtube.com/watch?v=nl-dheVpt8o
    @Override
    public void onReceive(Context context, Intent intent) {

        // Establish PendingIntent for notification - ability to open app after being tapped
        //  Referenced from: https://pushy.me/docs/android/setup-broadcastreceiver
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        // Construct Details of Notifications
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "remindBOMA!")
                .setSmallIcon(R.drawable.ic_stat_b)
                .setContentTitle("BOMA! Weekly Reminder")
                .setContentText("Remember to update your BMI for the week.\nTap here to do so.")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Remember to update your BMI for the week.\nTap here to do so."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                // Can also display app, but resets Weekly Reminder occurrence counter
                .addAction(0, "Update BMI", pendingIntent)
                .setAutoCancel(true);

        // Set the Notification from the Context
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(200, builder.build());
    }

}
