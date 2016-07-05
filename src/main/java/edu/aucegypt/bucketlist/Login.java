package edu.aucegypt.bucketlist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class Login extends AppCompatActivity {

    private CallbackManager callbackManager;        // manages the call backs from the app
    private SharedPreferences shPref;
    private SharedPreferences.Editor editor;
    private LoginButton loginButton;
    private ProfileTracker profileTracker;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());     // imitiallizing the sdk
        setContentView(R.layout.activity_login);

        shPref = getSharedPreferences("login_info",MODE_PRIVATE);       //get the shared preference
        editor = shPref.edit();

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton)findViewById(R.id.login_button);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                    profileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                            //editor.putString("Name", currentProfile.getName());

                            editor.putString("Name", currentProfile.getName());
                            editor.putString("userID", currentProfile.getId());
                            editor.putBoolean("loggedIn", true);
                            editor.commit();

                            profileTracker.stopTracking();

                        }
                    };
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.e("Error", error.toString());
            }

        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
