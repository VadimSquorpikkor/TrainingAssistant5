package com.squorpikkor.trainingassistant5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.squorpikkor.trainingassistant5.fragment.ExerciseFragment;
import com.squorpikkor.trainingassistant5.fragment.ExerciseListFragment;
import com.squorpikkor.trainingassistant5.fragment.TrainingListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_main);
        MainViewModel mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        PagerAdapter sectionsPagerAdapter = new PagerAdapter(getSupportFragmentManager(), getLifecycle());
        sectionsPagerAdapter.addFragment(new TrainingListFragment());
        sectionsPagerAdapter.addFragment(new ExerciseListFragment());
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

    }

}