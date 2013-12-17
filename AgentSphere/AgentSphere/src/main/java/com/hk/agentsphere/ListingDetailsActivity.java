package com.hk.agentsphere;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudgust.CGCollection;
import com.cloudgust.CGException;
import com.cloudgust.CGFetchCallback;
import com.cloudgust.CGObject;
import com.cloudgust.CloudGust;
import com.hk.Components.JsonObjects;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class ListingDetailsActivity extends Activity implements ActionBar.TabListener {

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    static JSONObject listing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_details);
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        Bundle extras= getIntent().getExtras();
        if(extras != null)
        {
            String list = extras.getString("Listing");
            try {
                listing = new JSONObject(list);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

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
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.listing_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id)
        {
            case R.id.listing_new_email:
                AlertDialog.Builder alert = new AlertDialog.Builder(ListingDetailsActivity.this);
                View contentView = getLayoutInflater().inflate(R.layout.listing_mail_dialogue, null);
                alert.setPositiveButton("Send",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"Listing Mailed",Toast.LENGTH_SHORT).show();
                    }
                });
                alert.setTitle("Mail Listing To");
                alert.setView(contentView);
                alert.show();
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

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
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
                    return "Basic Info";
                case 1:
                    return "Leads";
                case 2:
                    return "Notes";
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
            int id =getArguments().getInt(ARG_SECTION_NUMBER);
            switch (id)
            {
                case 1:
                    View rootView = inflater.inflate(R.layout.fragment_listing_basic, container, false);
                    TextView textView = (TextView) rootView.findViewById(R.id.listing_name);
                    TextView com =(TextView)rootView.findViewById(R.id.listing_community);
                    TextView price =(TextView)rootView.findViewById(R.id.listing_price);
                    TextView type =(TextView)rootView.findViewById(R.id.listing_type);
                    TextView desc =(TextView)rootView.findViewById(R.id.listing_desc);
                    TextView proptype=(TextView)rootView.findViewById(R.id.listing_propType);
                    TextView dprice =(TextView)rootView.findViewById(R.id.listing_details_price);
                    TextView dbeds=(TextView)rootView.findViewById(R.id.listing_details_beds);
                    TextView dbaths =(TextView)rootView.findViewById(R.id.listing_details_baths);
                    TextView tArea =(TextView)rootView.findViewById(R.id.listing_details_Area);
                    TextView cArea =(TextView)rootView.findViewById(R.id.listing_details_carpetArea);
                    TextView lSize =(TextView)rootView.findViewById(R.id.listing_details_lotSize);
                    TextView age =(TextView)rootView.findViewById(R.id.listing_details_age);
                    TextView address =(TextView)rootView.findViewById(R.id.listing_details_address);
                    TextView city =(TextView)rootView.findViewById(R.id.listing_details_city);
                    TextView ame =(TextView)rootView.findViewById(R.id.listing_details_amenities);
                    Button btnsh=(Button)rootView.findViewById(R.id.listing_shortlist);
                    btnsh.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getActivity().getApplicationContext(),"Listing Shortlisted",Toast.LENGTH_SHORT).show();
                        }
                    });
                    try {
                        textView.setText(listing.getString("Name"));
                        com.setText(listing.getString("Community"));
                        price.setText(listing.getString("Price"));
                        type.setText(listing.getString("Listing Type"));
                        desc.setText(listing.getString("Description"));
                        proptype.setText(listing.getString("Property Type"));
                        dprice.setText(listing.getString("Price"));
                        dbeds.setText(listing.getString("Beds"));
                        dbaths.setText(listing.getString("Baths"));
                        tArea.setText(listing.getString("Total Area")+" Sqft");
                        cArea.setText(listing.getString("Carpet Area")+" Sqft");
                        lSize.setText(listing.getString("LotSize"));
                        age.setText(listing.getString("Age")+" Years");
                        address.setText(listing.getString("Street Address"));
                        city.setText(listing.getString("City"));
                        ame.setText(listing.getString("Amenities"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ImageView image=(ImageView)rootView.findViewById(R.id.listing_image);
                    image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                String Images = listing.getString("Images");
                                Intent slideIntent = new Intent(getActivity().getApplicationContext(),ImageSlidActivty.class);
                                slideIntent.putExtra("Images",Images);
                                startActivity(slideIntent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    return rootView;
                case 2:
                    final View addView = inflater.inflate(R.layout.fragment_listing_additional, container, false);
                    CloudGust.initialize("52b02d2d75a43c192344de29", "db6c7594-7e1c-4ce2-a014-aa085c151f67");
                    CloudGust.setUser("1","admin","admin");
                    final ListView hotleads =(ListView)addView.findViewById(R.id.lead_list_all);
                    CGCollection cInfo =new CGCollection("Leads");
                    cInfo.fetch(new CGFetchCallback() {
                        @Override
                        public void done(ArrayList<CGObject> cgObjects, CGException e) {
                            com.hk.Components.ListViews adpter=new com.hk.Components.ListViews(
                                    addView.getContext(),
                                    R.layout.fragment_summury_lead_listitem,
                                    cgObjects,
                                    "LeadSummary"
                            );
                            hotleads.setAdapter(adpter);
                        }
                    });
                    return addView;
                case 3:
                    View notesView =inflater.inflate(R.layout.fragment_listing_notes, container, false);
                    JsonObjects nob =new JsonObjects();
                    final ListView notelist = (ListView)notesView.findViewById(R.id.listing_notes);
                    com.hk.Components.ListViews nadpter=new com.hk.Components.ListViews(
                            notesView.getContext(),
                            R.layout.fragment_note_listitem,
                            nob.GetNotes(),
                            "Notes"
                    );
                    notelist.setAdapter(nadpter);
                    return notesView;
                default:
                    View noteView =inflater.inflate(R.layout.fragment_listing_notes, container, false);
                    return noteView;
            }

        }
    }

}
