package com.codekiller.retrofitwithfcm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    public static final String TEST_ID = "C041E2DF3EE9A35EE4DD880D8090B4EE";
    public static final String TAG = "MAIN ACTIVITY";

    RecyclerView recyclerView;
    ProgressBar progressBar;
    Retrofit retrofit;
    APIInterface apiInterface;
    ArrayList<JSONFile> arrayList;
    MainRecycler mainRecycler;
    AdView adView, adView2;
    InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progress_bar);
        adView = findViewById(R.id.ad_view);
        adView2 = findViewById(R.id.ad_view2);
        arrayList = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(APIInterface.class);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });
        MobileAds.setRequestConfiguration(new RequestConfiguration.Builder()
                .setTestDeviceIds(Arrays.asList(TEST_ID))
                .build());

        AdRequest adRequest = new AdRequest.Builder().build();
        if( adRequest != null ){
            Log.d(TAG, "onCreate: adrequest - "+adRequest);
            adView.loadAd(adRequest);
            adView2.loadAd(adRequest);
            InterstitialAd.load(this, "ca-app-pub-5943796115602553/5544647514", adRequest, new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    super.onAdLoaded(interstitialAd);
                    mInterstitialAd = interstitialAd;
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    Log.d(TAG, "onAdFailedToLoad: failed - "+loadAdError);
                }
            });
        }
        //Log.d(TAG, "onCreate: inter uint id - "+mInterstitialAd.getAdUnitId());

        Log.d(TAG, "onCreate: ad url - "+adRequest.getLocation());

        Call<ArrayList<JSONFile>> call = apiInterface.getJson();
        call.enqueue(new Callback<ArrayList<JSONFile>>() {
            @Override
            public void onResponse(Call<ArrayList<JSONFile>> call, Response<ArrayList<JSONFile>> response) {
                progressBar.setVisibility(View.GONE);
                arrayList = response.body();
                mainRecycler = new MainRecycler(MainActivity.this,arrayList);
                recyclerView.setAdapter(mainRecycler);
            }

            @Override
            public void onFailure(Call<ArrayList<JSONFile>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        adView.pause();
        if( mInterstitialAd != null ){
            mInterstitialAd.show(MainActivity.this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        adView.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adView.destroy();
    }
}