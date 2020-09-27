package com.google.track;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(CardFragment.newInstance());

    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_profile:
                        MainActivity.this.openFragment(ProfileFragment.newInstance());
                        return true;
                    case R.id.navigation_calendar:
                        MainActivity.this.openFragment(CalendarFragment.newInstance());
                        return true;
                    case R.id.navigation_history:
                        MainActivity.this.openFragment(HistoryFragment.newInstance());
                        return true;
                    case R.id.navigation_card:
                        MainActivity.this.openFragment(CardFragment.newInstance());
                        return true;
                }
                return false;
            };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
