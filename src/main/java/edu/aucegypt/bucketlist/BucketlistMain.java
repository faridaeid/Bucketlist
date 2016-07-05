package edu.aucegypt.bucketlist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.firebase.client.Firebase;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import org.w3c.dom.Text;

public class BucketlistMain extends AppCompatActivity implements OnMenuTabClickListener {

    private Toolbar toolbar;
    private BottomBar bottomBar;
    private SharedPreferences shPref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bucketlist_main);

        shPref = getSharedPreferences("login_info",MODE_PRIVATE);
        editor = shPref.edit();

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomBar = BottomBar.attach(this, savedInstanceState);
        bottomBar.setItems(R.menu.bottom_bar_menu);
        bottomBar.setOnMenuTabClickListener(this);

        addAccount();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.bucketlist_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.logout:
                FacebookSdk.sdkInitialize(getApplicationContext());
                LoginManager.getInstance().logOut();
                editor.putBoolean("loggedIn", false);
                editor.commit();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addAccount()
    {
        User myAccount = new User(shPref.getString("Name", ""), shPref.getString("userID", ""));

        Firebase.setAndroidContext(this);

        Firebase ref = new Firebase("https://bucketlist-15d3c.firebaseio.com/");
        Firebase userRef = ref.child("Users").child(shPref.getString("userID", ""));
        userRef.setValue(myAccount);
    }

    @Override
    public void onMenuTabSelected(@IdRes int menuItemId) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (menuItemId)
        {
            case R.id.add_new_post:
                ft.replace(R.id.Frame, new add_new_post());
                ft.commit();
                break;

            case R.id.uncompleted:
                ft.replace(R.id.Frame, new uncompleted());
                ft.commit();
                break;

        }

    }

    @Override
    public void onMenuTabReSelected(@IdRes int menuItemId) {

    }
}
