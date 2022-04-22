package com.nykopings.bonetider;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FragmentMonthly extends Fragment {
    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private LinearLayoutManager layoutManager;
    int year, month, date;
    String TAG = "FragmentMonthly", monthname;
    String[] daysArray;
    private List<Upload> mUploads;

    private AdView mAdView;
    HorizontalScrollView horizontalScrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_monthly, container, false);
        recyclerView = view.findViewById(R.id.my_recycler_view);

        horizontalScrollView = view.findViewById(R.id.hrScroll);
        mUploads = new ArrayList<>();
        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        monthname = (String) android.text.format.DateFormat.format("MMMM", new Date());
        date = Calendar.getInstance().get(Calendar.DATE);
//        daysArray = new String[] {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
        daysArray = new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        DatabaseHelper dataBaseHelper = new DatabaseHelper(getContext());
        try {
            dataBaseHelper.createDataBase();
            SQLiteDatabase db = dataBaseHelper.openDataBase();
            Cursor cursor = db.rawQuery("SELECT * FROM prayer_time WHERE MONTH = " + month + "", null);
            Log.d(TAG, "onResume: " + cursor.getCount());
            if (cursor.getCount() == 0) {
                Log.d(TAG, "onCreateView: null");
            } else {

                Log.d(TAG, "onCreateView: "+cursor.getCount());
                while (cursor.moveToNext()) {
                    Calendar calendar = Calendar.getInstance();
                    String hour, minute, time, date, day,fazr = "",shorook = "",zuhur = "",asr = "",maghrib = "",isha = "";
                    date = cursor.getString(1);
                    calendar.set(year, (month - 1), Integer.parseInt(date), 0, 1, 1);
                    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
                    if (dayOfWeek < 0) {
                        dayOfWeek += 7;
                    }
                    day = daysArray[dayOfWeek];
                    for (int j = 3; j <= 8; j++) {
                        switch (j) {
                            case 3:
                                time = cursor.getString(3);
                                hour = time.substring(0, 2);
                                minute = time.substring(3, 5);
                                fazr= hour+":"+minute ;
                                break;
                            case 4:
                                time = cursor.getString(4);
                                hour = time.substring(0, 2);
                                minute = time.substring(3, 5);
                                shorook= hour+":"+minute ;

                                break;
                            case 5:
                                time = cursor.getString(5);
                                hour = time.substring(0, 2);
                                minute = time.substring(3, 5);
                                zuhur=hour+":"+minute;
                                break;
                            case 6:
                                time = cursor.getString(6);
                                hour = time.substring(0, 2);
                                minute = time.substring(3, 5);
                                asr=hour+":"+minute;
                                break;
                            case 7:
                                time = cursor.getString(7);
                                hour = time.substring(0, 2);
                                minute = time.substring(3, 5);
                                maghrib= hour+":"+minute;
                                break;
                            case 8:
                                time = cursor.getString(8);
                                hour = time.substring(0, 2);
                                minute = time.substring(3, 5);
                                isha = hour+":"+minute;
                                break;
                        }
                }
                    Upload upload;
                    upload = new Upload(date,day,fazr,shorook,zuhur,asr,maghrib,isha);
                    mUploads.add(upload);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(getContext(),mUploads);
        recyclerView.setAdapter(mAdapter);

        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = view.findViewById(R.id.adView3);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                mAdView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
                mAdView.setVisibility(View.GONE);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);
            }

        });

        return view;
    }
}