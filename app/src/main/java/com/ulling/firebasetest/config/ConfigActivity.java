package com.ulling.firebasetest.config;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.ulling.firebasetest.BuildConfig;
import com.ulling.firebasetest.R;

/**
 * Created by P100651 on 2016-09-29.
 */
public class ConfigActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    // Remote Config keys
    private static final String PRICE_CONFIG_KEY = "price";
    private static final String LOADING_PHRASE_CONFIG_KEY = "loading_phrase";
    private static final String PRICE_PREFIX_CONFIG_KEY = "price_prefix";
    private static final String DISCOUNT_CONFIG_KEY = "discount";
    private static final String IS_PROMOTION_CONFIG_KEY = "is_promotion_on";
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private TextView mPriceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_main);
        mPriceTextView = (TextView) findViewById(R.id.priceView);
        Button fetchButton = (Button) findViewById(R.id.fetchButton);
        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchDiscount();
            }
        });
        // Get Remote Config instance.
        // [START get_remote_config_instance]
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);
        // [END enable_dev_mode]
        // [START set_default_values]
        mFirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);
        // [END set_default_values]
        // Fetch discount config.
        fetchDiscount();
    }

    /**
     * Fetch discount from server.
     */
    private void fetchDiscount() {
        boolean remoteConfigTest = mFirebaseRemoteConfig.getBoolean("remoteConfigTest");
        Log.e(TAG, "remoteConfigTest========" + remoteConfigTest);
        mPriceTextView.setText(mFirebaseRemoteConfig.getString(LOADING_PHRASE_CONFIG_KEY));
        long cacheExpiration = 3600; // 1 hour in seconds.
        // If in developer mode cacheExpiration is set to 0 so each fetch will retrieve values from
        // the server.
        if (mFirebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            cacheExpiration = 0;
        }
        // [START fetch_config_with_callback]
        // cacheExpirationSeconds is set to cacheExpiration here, indicating that any previously
        // fetched and cached config would be considered expired because it would have been fetched
        // more than cacheExpiration seconds ago. Thus the next fetch would go to the server unless
        // throttling is in progress. The default expiration duration is 43200 (12 hours).
        mFirebaseRemoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ConfigActivity.this, "Fetch Succeeded",
                                    Toast.LENGTH_SHORT).show();
                            // Once the config is successfully fetched it must be activated before newly fetched
                            // values are returned.
                            mFirebaseRemoteConfig.activateFetched();
                        } else {
                            Toast.makeText(ConfigActivity.this, "Fetch Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                        displayPrice();
                    }
                });
        // [END fetch_config_with_callback]
    }

    /**
     * Display price with discount applied if promotion is on. Otherwise display original price.
     */
    // [START display_price]
    private void displayPrice() {
        long initialPrice = mFirebaseRemoteConfig.getLong(PRICE_CONFIG_KEY);
        long finalPrice = initialPrice;
        if (mFirebaseRemoteConfig.getBoolean(IS_PROMOTION_CONFIG_KEY)) {
            // [START get_config_values]
            finalPrice = initialPrice - mFirebaseRemoteConfig.getLong(DISCOUNT_CONFIG_KEY);
            // [END get_config_values]
        }
        mPriceTextView.setText(mFirebaseRemoteConfig.getString(PRICE_PREFIX_CONFIG_KEY) + finalPrice);
    }
    // [END display_price]
}
