package com.team02.boma;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

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

        // Change color of Status and App Bars
        // Define ActionBar object
        ActionBar actionBar;
        actionBar = getSupportActionBar();

        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#BD0101"));

        // Set BackgroundDrawable
        assert actionBar != null;
        actionBar.setBackgroundDrawable(colorDrawable);

        // Define and Design Status Bar
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.status_bar));

        // Call this method to create notification channel
        createNotificationChannel();

        // Establish total_launches value - used for initializing the alarmMethod function
        // that creates the Push Notification
        //  Referenced from: https://www.youtube.com/watch?v=tyVaPHv-RGo
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        int i = preferences.getInt("total_launches", 1);

        // If i (total_launches) has reached 2, forget about initializing alarmMethod again
        // Otherwise, perform the initialization
        if (i < 2) {
            alarmMethod();
            i++;
            editor.putInt("total_launches", i);
            editor.apply();
        }

        //sets quote
        quote();
    }

    private void createNotificationChannel() {
        // Check the API Build, then create the notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "BOMA!ReminderChannel";
            String description = "Channel for Weekly BOMA! Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("remindBOMA!", name, importance);
            channel.setDescription(description);

            // Pass the channel to NotificationManager, which passes the channel to alarmMethod
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void alarmMethod() {
        // Establish Intents and Pending Intents for Reminding Push Notifications
        Intent alarmIntent = new Intent(this, NotifyService.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);

        // Set weekly occurrences for Reminder Notification
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 60 * 60 * 24 * 7, pendingIntent);

        // Toast used for debugging - to see if alarm actually started
        Toast.makeText(MainActivity.this, "Weekly Reminder Activated", Toast.LENGTH_LONG).show();
    }

    // Code to display a different page
    public void createProfile(View view) {
        presenter.view.ShowSaveNewInfoActivity();
    }
    // Code to view previously saved User data
    public void viewPreviousProfile(View view) {
        presenter.view.ShowWelcomeBackActivity();
    }

    public void quote(){
        TextView tvQuote =  findViewById(R.id.quote);

        // Get the array of Strings from the strings.xml file
        String[] Quotes = getResources().getStringArray(R.array.quotes);

        // Initialize a Randomizer
        int randomNumber = new Random().nextInt(Quotes.length);

        // Set the TextView to the recommendation
        tvQuote.setText(Quotes[randomNumber]);

    }
}