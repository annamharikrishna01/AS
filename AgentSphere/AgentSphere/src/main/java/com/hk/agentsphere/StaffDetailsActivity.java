package com.hk.agentsphere;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

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

public class StaffDetailsActivity extends Activity implements ActionBar.TabListener {


    private static JSONObject StaffObj;
    private String Phone;
    private String Email;
    static String Staff;
    SectionsPagerAdapter mSectionsPagerAdapter;

    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_details);


        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        CloudGust.initialize("52b02d2d75a43c192344de29", "db6c7594-7e1c-4ce2-a014-aa085c151f67");
        CloudGust.setUser("1","admin","admin");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        Bundle extras= getIntent().getExtras();
        if(extras != null)
        {
            String staff = extras.getString("Staff");
            if(staff != null)
            {
                try {
                    StaffObj = new JSONObject(staff);
                    JSONObject cInfo =StaffObj.getJSONObject("Contact");
                    Phone = cInfo.getString("Phone");
                    Email =cInfo.getString("Email");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Staff=staff;
            }
        }
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
        

        getMenuInflater().inflate(R.menu.staff_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id  =item.getItemId();
        switch (id)
        {
            case R.id.staff_call:
                Intent call = new Intent(Intent.ACTION_CALL);
                call.setData(Uri.parse("tel:" + Phone));
                startActivity(call);
                break;
            case R.id.staff_new_email:
                Intent mail = new Intent(Intent.ACTION_SEND);
                mail.setType("text/plain");
                mail.putExtra(Intent.EXTRA_EMAIL, new String[]{Email});
                mail.putExtra(Intent.EXTRA_SUBJECT,"");
                startActivity(mail);
                break;
            default:
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
                    View baseView =inflater.inflate(R.layout.fragment_staff_basic, container, false);
                    TextView name =(TextView)baseView.findViewById(R.id.staff_name);
                    TextView mobile =(TextView)baseView.findViewById(R.id.staff_mobile);
                    TextView phone =(TextView)baseView.findViewById(R.id.staff_phone);
                    TextView email =(TextView)baseView.findViewById(R.id.staff_email);
                    TextView dob =(TextView)baseView.findViewById(R.id.staff_dob);
                    TextView address =(TextView)baseView.findViewById(R.id.staff_addressline);
                    TextView city =(TextView)baseView.findViewById(R.id.staff_city);
                    TextView facebook =(TextView)baseView.findViewById(R.id.staff_facebook);
                    TextView twitter =(TextView)baseView.findViewById(R.id.staff_twitter);
                    TextView linkedin =(TextView)baseView.findViewById(R.id.staff_linkedin);
                    TextView remarks =(TextView)baseView.findViewById(R.id.staff_remarks);

                    try {
                        JSONObject cInfo =StaffObj.getJSONObject("Contact");
                        name.setText(cInfo.getString("FirstName")+" "+cInfo.getString("LastName"));
                        mobile.setText(cInfo.getString("Mobile"));
                        phone.setText(cInfo.getString("Phone"));
                        email.setText(cInfo.getString("Email"));
                        dob.setText(cInfo.getString("DateofBirth"));
                        address.setText(cInfo.getString("StreetAddress"));
                        city.setText(cInfo.getString("City"));
                        facebook.setText(cInfo.getString("Facebook"));
                        twitter.setText(cInfo.getString("Twitter"));
                        linkedin.setText(cInfo.getString("LinkedIn"));
                        remarks.setText(cInfo.getString("Remarks"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return  baseView;
                case 2:
                    final View leadView =inflater.inflate(R.layout.fragment_staff_leads, container, false);
                    final ListView hotleads =(ListView)leadView.findViewById(R.id.staff_leads);
                    CloudGust.initialize("52b02d2d75a43c192344de29", "db6c7594-7e1c-4ce2-a014-aa085c151f67");
                    CloudGust.setUser("1","admin","admin");
                    CGCollection cInfo =new CGCollection("Leads");
                    cInfo.fetch(new CGFetchCallback() {
                        @Override
                        public void done(ArrayList<CGObject> cgObjects, CGException e) {
                            com.hk.Components.ListViews adpter=new com.hk.Components.ListViews(
                                    leadView.getContext(),
                                    R.layout.fragment_summury_lead_listitem,
                                    cgObjects,
                                    "LeadSummary"
                            );
                            hotleads.setAdapter(adpter);
                        }
                    });
                    return leadView;
                case 3:
                    View noteView =inflater.inflate(R.layout.fragment_staff_notes, container, false);
                    JsonObjects nob =new JsonObjects();
                    final ListView notelist = (ListView)noteView.findViewById(R.id.staff_notes);
                    com.hk.Components.ListViews nadpter=new com.hk.Components.ListViews(
                            noteView.getContext(),
                            R.layout.fragment_note_listitem,
                            nob.GetNotes(),
                            "Notes"
                    );
                    notelist.setAdapter(nadpter);
                    return  noteView;
                default:
                    View baseView1 =inflater.inflate(R.layout.fragment_staff_basic, container, false);
                    return  baseView1;

            }

        }
    }

}
