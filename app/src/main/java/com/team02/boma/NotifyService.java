package com.team02.boma;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotifyService extends BroadcastReceiver {

    // Implement the onReceive method to receive intent from MainActivity
    @Override
    public void onReceive(Context context, Intent intent) {

        // Construct Details of Notifications
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "remindBOMA!")
                .setSmallIcon(R.drawable.ic_stat_fitness_center)
                .setContentTitle("BOMA! Weekly Reminder")
                .setContentText("Remember to update your BMI for the week.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Set the Notification from the Context
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(200, builder.build());
    }

}
