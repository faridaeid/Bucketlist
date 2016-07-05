package edu.aucegypt.bucketlist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookSdk;

public class Splash extends AppCompatActivity {

    private SharedPreferences shPref;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());     // imitiallizing the sdk
        setContentView(R.layout.activity_splash);

        shPref = getSharedPreferences("login_info", MODE_PRIVATE);

        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                updateWithToken(currentAccessToken);
            }
        };

        updateWithToken(AccessToken.getCurrentAccessToken());       // in case for logging in for the first time

    }

    public void updateWithToken(final AccessToken currentAccessToken) {

        new CountDownTimer(1500, 1000) {
            @Override
            public void onTick(long milliseconds) {
            }

            public void onFinish() {
                if(currentAccessToken == null)
                {
                    i = new Intent(getApplicationContext(), Login.class);
                    startActivity(i);
                }
                else
                {
                    i = new Intent(getApplicationContext(), BucketlistMain.class);
                    startActivity(i);
                }

            }

        }.start();
    }


}



