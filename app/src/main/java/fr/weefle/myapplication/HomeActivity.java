package fr.weefle.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    private Fragment fragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new ReceiverFragment();
                    break;
                case R.id.navigation_dashboard:
                    fragment = new SenderFragment();
                    break;
                case R.id.navigation_notifications:
                    // si le bluetooth n'est pas activé
                    Intent intent = new Intent(HomeActivity.this, BluetoothActivity.class);
                    startActivity(intent);
                    // sinon on lance le fragment
                    // fragment = new BluetoothFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
            BottomNavigationView navView = findViewById(R.id.nav_view);
            navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ReceiverFragment()).commit();


    }

}
