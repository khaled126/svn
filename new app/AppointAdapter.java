package com.example.mohsher.drsdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import static com.example.mohsher.drsdemo.AppointmentsFragment.int_items;

/**
 * Created by mohsher on 1/24/2017.
 */

   public class AppointAdapter extends FragmentPagerAdapter {

        public AppointAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position)
        {
            switch (position){
                case 0 : return new UpcomingAppointFragment();
                case 1 : return new PastAppointFragment();
            }
            return null;
        }

        @Override
        public int getCount() {

            return int_items;

        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0 :
                    return "Upcoming";
                case 1 :
                    return "Past";
            }
            return null;
        }
    }


