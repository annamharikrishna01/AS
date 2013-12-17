package com.hk.agentsphere;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;

public class NewListingActivity extends Activity implements ActionBar.TabListener {


    SectionsPagerAdapter mSectionsPagerAdapter;


    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_listing);


        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());


        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });


        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {

            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        

        getMenuInflater().inflate(R.menu.new_listing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
       switch (id)
       {
           case R.id.listing_save:
               Intent listing = new Intent(getApplicationContext(),ListingsActivity.class);
               startActivity(listing);
               break;
           case R.id.listing_cancel:
               finish();
               break;

       }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return "For Sale";
                case 1:
                    return "For Rent";
            }
            return null;
        }
    }


    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";


        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            int id=getArguments().getInt(ARG_SECTION_NUMBER);
            switch(id)
            {
                case 1:
                    View rootView = inflater.inflate(R.layout.fragment_new_salelisting, container, false);
                    // TextView textView = (TextView) rootView.findViewById(R.id.section_label);
                    // textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
                    return rootView;
                case 2:
                    View rootView1 = inflater.inflate(R.layout.fragment_new_rentlisting, container, false);
                    // TextView textView = (TextView) rootView.findViewById(R.id.section_label);
                    // textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
                    return rootView1;
                default:
                    View root = inflater.inflate(R.layout.fragment_new_salelisting, container, false);
                    // TextView textView = (TextView) rootView.findViewById(R.id.section_label);
                    // textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
                    return root;
            }

        }
    }

}
