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
import android.widget.ListView;

import com.cloudgust.CGCollection;
import com.cloudgust.CGException;
import com.cloudgust.CGFetchCallback;
import com.cloudgust.CGObject;
import com.cloudgust.CloudGust;

import java.util.ArrayList;
import java.util.Locale;

public class LeadsActivity extends Activity implements ActionBar.TabListener {


    SectionsPagerAdapter mSectionsPagerAdapter;


    ViewPager mViewPager;

    static  ArrayList<CGObject> Leads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leads);

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        CloudGust.initialize("52b02d2d75a43c192344de29", "db6c7594-7e1c-4ce2-a014-aa085c151f67");
        CloudGust.setUser("1","admin","admin");

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
        ActionBar ab= getActionBar();
        ab.setHomeButtonEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.leads, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.action_settings:
                return true;
            case R.id.action_new:
                Intent lead=new Intent(getApplicationContext(),NewLeadActivity.class);
                startActivity(lead);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }
    @Override
    public void onResume(){

        super.onResume();
        CloudGust.initialize("52b02d2d75a43c192344de29", "db6c7594-7e1c-4ce2-a014-aa085c151f67");
        CloudGust.setUser("1","admin","admin");
        CGCollection cInfo =new CGCollection("Leads");
        cInfo.fetch(new CGFetchCallback() {
            @Override
            public void done(ArrayList<CGObject> cgObjects, CGException e) {

                Leads =cgObjects;
            }
        });

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
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return "All";
                case 1:
                    return "New";
                case 2:
                    return "Closed";
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
        public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                Bundle savedInstanceState) {

            int id = getArguments().getInt(ARG_SECTION_NUMBER);

            switch (id)
            {
                case 1:
                    final View lall = inflater.inflate(R.layout.fragment_leads_all,container,false);
                    final ListView hotleads =(ListView)lall.findViewById(R.id.lead_list_all);
                    CGCollection cInfo =new CGCollection("Leads");
                    cInfo.fetch(new CGFetchCallback() {
                        @Override
                        public void done(ArrayList<CGObject> cgObjects, CGException e) {
                            com.hk.Components.ListViews adpter=new com.hk.Components.ListViews(
                                    lall.getContext(),
                                    R.layout.fragment_summury_lead_listitem,
                                    cgObjects,
                                    "LeadSummary"
                            );
                            hotleads.setAdapter(adpter);
                        }
                    });

                    return lall;
                case 2:
                    final View lnew= inflater.inflate(R.layout.fragment_leads_new,container,false);
                    final ListView newLead =(ListView)lnew.findViewById(R.id.lead_list_new);
                    CGCollection nInfo =new CGCollection("Leads");
                    nInfo.fetch(new CGFetchCallback() {
                        @Override
                        public void done(ArrayList<CGObject> cgObjects, CGException e) {
                            com.hk.Components.ListViews newadpter = new com.hk.Components.ListViews(
                                    lnew.getContext(),
                                    R.layout.fragment_summury_lead_listitem,
                                    cgObjects,
                                    "LeadSummary"
                            );
                            newLead.setAdapter(newadpter);
                        }
                    });
                    return lnew;
                case 3:
                    final View lclosed =inflater.inflate(R.layout.fragment_leads_closed,container,false);
                    final ListView closedLead =(ListView)lclosed.findViewById(R.id.lead_list_closed);
                    CGCollection clInfo =new CGCollection("Leads");
                    clInfo.fetch(new CGFetchCallback() {
                        @Override
                        public void done(ArrayList<CGObject> cgObjects, CGException e) {
                    com.hk.Components.ListViews closeadpter=new com.hk.Components.ListViews(
                            lclosed.getContext(),
                            R.layout.fragment_summury_lead_listitem,
                            Leads,
                            "LeadSummary"
                        );
                           closedLead.setAdapter(closeadpter);
                      }
                    });
                    return lclosed;
                default:
                    View ldft = inflater.inflate(R.layout.fragment_leads_all,container,false);
                    return ldft;
            }
        }
    }

}
