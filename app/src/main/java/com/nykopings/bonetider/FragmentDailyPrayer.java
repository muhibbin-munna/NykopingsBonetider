package com.nykopings.bonetider;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

public class FragmentDailyPrayer extends Fragment {
    private String TAG = "FragmentDailyPrayer";
    int yearNow,monthNow,dateNow;
    TextView fajr,shorook,zuhr,asr,maghrib,isha;
    private AdView mAdView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_daily_prayer, container, false);

        fajr = view.findViewById(R.id.fajr);
        shorook = view.findViewById(R.id.shorook);
        zuhr = view.findViewById(R.id.duhur);
        asr = view.findViewById(R.id.asr);
        maghrib = view.findViewById(R.id.maghrib);
        isha = view.findViewById(R.id.isha);
        Calendar c = Calendar.getInstance();
        yearNow = c.get(Calendar.YEAR);
        monthNow = c.get(Calendar.MONTH)+1;
        dateNow = c.get(Calendar.DATE);
        DatabaseHelper dataBaseHelper = new DatabaseHelper(getActivity());
        try {
            dataBaseHelper.createDataBase();
            SQLiteDatabase db = dataBaseHelper.openDataBase();
            Cursor cursor = db.rawQuery("SELECT * FROM prayer_time WHERE DATE= "+dateNow+" AND MONTH= "+monthNow+"", null);
            if(cursor.getCount()==0){
                Log.d(TAG, "onCreateView: null");
            }
            else {
                while (cursor.moveToNext())
                {
                    fajr.setText(cursor.getString(3));
                    shorook.setText(cursor.getString(4));
                    zuhr.setText(cursor.getString(5));
                    asr.setText(cursor.getString(6));
                    maghrib.setText(cursor.getString(7));
                    isha.setText(cursor.getString(8));
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = view.findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        return view;
    }

}