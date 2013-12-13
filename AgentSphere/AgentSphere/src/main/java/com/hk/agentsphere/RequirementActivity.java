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
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.hk.Components.AutoComplete;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class RequirementActivity extends Activity implements ActionBar.TabListener {


    SectionsPagerAdapter mSectionsPagerAdapter;
    static ViewPager mViewPager;
    static int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requirement);

        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);


        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());


        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
                page =position;
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
        

        getMenuInflater().inflate(R.menu.requirement, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id)
        {
            case R.id.requiremebt_save:

               int i =  mViewPager.getCurrentItem();
               if(i == 0)
               {
                   View v=getWindow().getDecorView().getRootView();
                   JSONObject requirements = GetRequirements(v,i);
                   Intent res = new Intent();
                   res.putExtra("Requirements",requirements.toString());
                   setResult(NewLeadActivity.GET_REQUIREMENTS, res);
                   Toast.makeText(getApplicationContext(), "Requirements Added", Toast.LENGTH_SHORT).show();
                   finish();

               }
                break;

            case R.id.requirement_cancel:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

        mViewPager.setCurrentItem(tab.getPosition());
        page = tab.getPosition();
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        page = tab.getPosition();
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
            page = sectionNumber;
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            int id= getArguments().getInt(ARG_SECTION_NUMBER);
            switch (id)
            {
                case 1:
                    View rootView = inflater.inflate(R.layout.fragment_req_forsale, container, false);
                    AutoComplete ac= new AutoComplete();
                    MultiAutoCompleteTextView act = (MultiAutoCompleteTextView)rootView.findViewById(R.id.location);
                    ac.populate(rootView,act,"Locations");
                    MultiAutoCompleteTextView am = (MultiAutoCompleteTextView)rootView.findViewById(R.id.Features);
                    ac.populate(rootView,am,"Features");
                    return rootView;
                default:
                    View View = inflater.inflate(R.layout.fragment_req_forrent, container, false);
                    AutoComplete ac1= new AutoComplete();
                    MultiAutoCompleteTextView act1 = (MultiAutoCompleteTextView)View.findViewById(R.id.location);
                    ac1.populate(View,act1,"Locations");
                    return View;
            }


        }
    }

    public static JSONObject GetRequirements(View view, int Section)
    {
        JSONObject req= new JSONObject();

        if(Section == 0)
        {
            MultiAutoCompleteTextView loc =(MultiAutoCompleteTextView)view.findViewById(R.id.location);
            EditText minPrice =(EditText)view.findViewById(R.id.MinPrice);
            EditText maxPrice= (EditText)view.findViewById(R.id.MaxPrice);
            Spinner beds =(Spinner)view.findViewById(R.id.Beds);
            Spinner baths =(Spinner)view.findViewById(R.id.Baths);
            Spinner area = (Spinner)view.findViewById(R.id.Area);
            Spinner age =(Spinner)view.findViewById(R.id.Age);
            Spinner proptype =(Spinner)view.findViewById(R.id.PropertyType);
            Spinner lotsize =(Spinner)view.findViewById(R.id.LotSize);
            MultiAutoCompleteTextView features = (MultiAutoCompleteTextView)view.findViewById(R.id.Features);

            try {
                req.put("Type","Sale");
                req.put("Location",loc.getText());
                req.put("MinPrice",minPrice.getText());
                req.put("MaxPrice",maxPrice.getText());
                req.put("Beds",beds.getSelectedItem().toString());
                req.put("Baths",baths.getSelectedItem().toString());
                req.put("Area",area.getSelectedItem().toString());
                req.put("Age",age.getSelectedItem().toString());
                req.put("PropertyType",proptype.getSelectedItem().toString());
                req.put("LotSize",lotsize.getSelectedItem().toString());
                req.put("Features",features.getText());
                return req;
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }else if(Section == 1)
        {
            MultiAutoCompleteTextView loc1 =(MultiAutoCompleteTextView)view.findViewById(R.id.location);
            EditText minPrice1 =(EditText)view.findViewById(R.id.MinPrice);
            EditText maxPrice1= (EditText)view.findViewById(R.id.MaxPrice);
            Spinner beds1 =(Spinner)view.findViewById(R.id.Beds);
            Spinner baths1 =(Spinner)view.findViewById(R.id.Baths);
            Spinner area1 = (Spinner)view.findViewById(R.id.Area);
            Spinner age1 =(Spinner)view.findViewById(R.id.Age);
            Spinner proptype1 =(Spinner)view.findViewById(R.id.PropertyType);
            Spinner petPolicy =(Spinner)view.findViewById(R.id.PetPolicy);
            try {
                req.put("Type","Rent");



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return req;

    }
}
