package com.hk.agentsphere;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class ImageSlidActivty extends Activity {



    SectionsPagerAdapter mSectionsPagerAdapter;

    ViewPager mViewPager;
    static int count;
    static ArrayList<JSONObject> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_slid_activty);
        Bundle extras= getIntent().getExtras();
        if(extras != null)
        {
            String imageCol = extras.getString("Images");
            if(!imageCol.isEmpty())
            {
                images = GetImages(imageCol);
                count = images.size();
            }
        }
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        

        getMenuInflater().inflate(R.menu.image_slid_activty, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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

            return count;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
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
            View rootView = inflater.inflate(R.layout.fragment_image_slid_activty, container, false);
            ImageView image = (ImageView)rootView.findViewById(R.id.image_id);
            int id =getArguments().getInt(ARG_SECTION_NUMBER);
            JSONObject im =  images.get(id-1);
            try {
                image.setImageResource(Integer.parseInt(im.getString("Image")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return rootView;
        }
    }


    public ArrayList<JSONObject> GetImages(String imageString)
    {
        ArrayList<JSONObject> cImages = new ArrayList<JSONObject>();
        if (!imageString.isEmpty())
        {
            try {

                JSONArray temp = new JSONArray(imageString);
                for (int i =0;i< temp.length();i++)
                {
                    cImages.add(new JSONObject(temp.get(i).toString()));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return  cImages;

    }

}
