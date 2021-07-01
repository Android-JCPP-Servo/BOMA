package com.team02.boma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    // Every activity that needs access to the MVP structure needs a reference
    // to the global MVPPresenter
    MVPPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // set presenter if it is null
        if (presenter == null) {
            presenter = new MVPPresenter(this.getApplication());
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannel();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        int i = preferences.getInt("total_launches", 1);

        if (i < 2) {
            alarmMethod();
            i++;
            editor.putInt("total_launches", i);
            editor.commit();
        }
        else {
            i = 1;
            editor.putInt("total_launches", i);
        }

    }

    private void createNotificationChannel() {
        // Check the API Build, then run the timer
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "BOMA!ReminderChannel";
            String description = "Channel for Weekly BOMA! Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("remindBOMA!", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void alarmMethod() {
        Intent alarmIntent = new Intent(this, NotifyService.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() , 1000 * 10, pendingIntent);

        // Toast used for debugging - to see if alarm actually started
        Toast.makeText(MainActivity.this, "Alarm System Activated", Toast.LENGTH_LONG).show();
    }

    // Code to display a different page
    public void createProfile(View view) {
        presenter.view.ShowSaveNewInfoActivity();
    }
    // Code to view previously saved User data
    public void viewPreviousProfile(View view) {
        presenter.view.ShowWelcomeBackActivity();
    }
}