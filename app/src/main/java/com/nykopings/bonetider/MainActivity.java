package com.nykopings.bonetider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.navigation.NavigationView;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    int year, month, date;
    SharedPreferences prefs = null;
    Toolbar toolbar;
    private String TAG = "MainActivity", monthname, weekday;
    private GetDataAsyncTask getDataAsyncTask;
    private InterstitialAd mInterstitialAd;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = getSharedPreferences("com.nykopings.bonetider", MODE_PRIVATE);

        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        date = c.get(Calendar.DATE);
        monthname = (String) android.text.format.DateFormat.format("MMMM", new Date());
        weekday = new DateFormatSymbols().getWeekdays()[c.get(Calendar.DAY_OF_WEEK)];
        title = findViewById(R.id.monthTitle);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        title.setText(weekday + ", " + date + " " + monthname);
        setSupportActionBar(toolbar);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-5889002374367451/3207720919");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d(TAG, "The interstitial wasn't loaded yet.");
                }
            }
        });
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new FragmentDailyPrayer()).commit();
            navigationView.setCheckedItem(R.id.nav_day);
        }
    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_day:
                title.setText(weekday + ", " + date + " " + monthname);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentDailyPrayer()).commit();
                break;
            case R.id.nav_month:
                title.setText(monthname);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentMonthly()).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @SuppressLint("StaticFieldLeak")
    class GetDataAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            DatabaseHelper dataBaseHelper = new DatabaseHelper(MainActivity.this);
            try {
                dataBaseHelper.createDataBase();
                SQLiteDatabase db = dataBaseHelper.openDataBase();
                Cursor cursor = db.rawQuery("SELECT * FROM prayer_time", null);
                Log.d(TAG, "onResume: " + cursor.getCount());
                if (cursor.getCount() == 0) {
                    Log.d(TAG, "onCreateView: null");
                } else {
                    while (cursor.moveToNext()) {
                        for (int i = 3; i <= 8; i++) {

                            String hour, minute, time, date, month;
                            time = cursor.getString(i);
                            hour = time.substring(0, 2);
                            minute = time.substring(3, 5);
                            date = cursor.getString(1);
                            month = cursor.getString(2);
                            setAlarm(Integer.parseInt(month), Integer.parseInt(date), Integer.parseInt(hour), Integer.parseInt(minute), cursor.getColumnName(i));


                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    private void setAlarm(int monthAlarm, int dateAlarm, int hourAlarm, int minuteAlarm, String timeAlarm) {
        monthAlarm = monthAlarm - 1;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.DATE, dateAlarm);
        calendar.set(Calendar.MONTH, monthAlarm);
        calendar.set(Calendar.HOUR_OF_DAY, hourAlarm);
        calendar.set(Calendar.MINUTE, minuteAlarm);
        calendar.set(Calendar.SECOND, 1);

        String req_code = "" + dateAlarm + monthAlarm + hourAlarm + minuteAlarm;

        if (calendar.getTimeInMillis() > System.currentTimeMillis()) {
            Intent intent = new Intent(this, NotificationReceiver.class);
            boolean isWorking = (PendingIntent.getBroadcast(this, Integer.parseInt(req_code), intent, PendingIntent.FLAG_NO_CREATE) != null);//just changed the flag
            if (!isWorking) {
                Log.d(TAG, "setAlarm: " + req_code);
                intent.putExtra("timeAlarm", timeAlarm);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, Integer.parseInt(req_code), intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmMgr = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    alarmMgr.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    alarmMgr.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//                } else {
//                    alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//                }
            } else {
                Log.d(TAG, "setAlarm: alam already setted" + req_code);
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (getDataAsyncTask != null) {
            getDataAsyncTask.cancel(true);
            getDataAsyncTask = null;
        }
        getDataAsyncTask = new GetDataAsyncTask();
        getDataAsyncTask.execute();
    }
}