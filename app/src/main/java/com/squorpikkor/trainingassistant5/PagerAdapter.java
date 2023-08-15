package com.squorpikkor.trainingassistant5;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

class PagerAdapter extends FragmentStateAdapter {

   private final ArrayList<Fragment> fragmentList = new ArrayList<>();

   public PagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
      super(fragmentManager, lifecycle);
   }

   @NonNull
   @Override
   public Fragment createFragment(int position) {
      return fragmentList.get(position);
   }

   @Override
   public int getItemCount() {
      return fragmentList.size();
   }

   public void addFragment(Fragment fragment) {
      fragmentList.add(fragment);
   }

}
