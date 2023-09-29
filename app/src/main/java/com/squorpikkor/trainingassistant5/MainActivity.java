package com.squorpikkor.trainingassistant5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.squorpikkor.trainingassistant5.dialog.SignInDialog;
import com.squorpikkor.trainingassistant5.fragment.ExerciseFragment;
import com.squorpikkor.trainingassistant5.fragment.EventListFragment;
import com.squorpikkor.trainingassistant5.fragment.TrainingListFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_main);
        MainViewModel mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        PagerAdapter sectionsPagerAdapter = new PagerAdapter(getSupportFragmentManager(), getLifecycle());
        sectionsPagerAdapter.addFragment(new TrainingListFragment());
        sectionsPagerAdapter.addFragment(new EventListFragment());
        sectionsPagerAdapter.addFragment(new ExerciseFragment());

        ViewPager2 viewPager = findViewById(R.id.view_pager);
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabs = findViewById(R.id.tabs);
        new TabLayoutMediator(tabs, viewPager, (tab, position) -> {
            //tab.setText("OBJECT " + (position + 1));
        }).attach();

        //CustomView
        tabs.getTabAt(0).setCustomView(R.layout.tab_view_0);
        tabs.getTabAt(1).setCustomView(R.layout.tab_view_1);
        tabs.getTabAt(2).setCustomView(R.layout.tab_view_2);

        //переключение на выбранную программно вкладку
        mViewModel.getSelectedPage().observe(this, viewPager::setCurrentItem);

        DrawerLayout drawer_layout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            item.setCheckable(false);
            int id = item.getItemId();
            if (id == R.id.first) {
                Log.e("", "onNavigationItemSelected: FIRST");
            } else if (id == R.id.second) {
                Log.e("", "onNavigationItemSelected: SECOND");
            } else if (id == R.id.third) {
                Log.e("", "onNavigationItemSelected: THIRD");
            }
            drawer_layout.closeDrawer(GravityCompat.START);
            return true;
        });


        View headerView = navigationView.getHeaderView(0);
//        TextView version = headerView.findViewById(R.id.version);
//        String appName = getString(R.string.app_name);
//        version.setText(String.format("%s %s", appName, VERSION_NAME));
        TextView emailText = headerView.findViewById(R.id.accountEmail);
//        accountImage = headerView.findViewById(R.id.account_image);

        headerView.findViewById(R.id.accountImage).setOnClickListener(v-> {
            SignInDialog.newInstance().show(getSupportFragmentManager(), null);
            drawer_layout.closeDrawer(GravityCompat.START);
        });

        mViewModel.getSignedLogin().observe(this, emailText::setText);





        ArrayList<www> list = new ArrayList<>();
        list.add(new www(1));
        list.add(new www(2));
        list.add(new www(3));
        list.add(new www(4));
        SLog.e(list.get(2).i);
        changeArray(list);
        SLog.e(list.get(2).i);

    }

    class www {
        int i;
        public www(int i) {this.i = i;}
    }

    void changeArray(ArrayList<www> list) {
        list.get(2).i=5;
    }











}