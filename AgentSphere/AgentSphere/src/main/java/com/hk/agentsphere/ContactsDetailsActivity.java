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

public class ContactsDetailsActivity extends Activity implements ActionBar.TabListener {

    private static JSONObject ContactObj;
    private String Phone;
    private String Email;
    static String Contact;
    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_details);
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        Bundle extras= getIntent().getExtras();
        if(extras != null)
        {
            String contact = extras.getString("Contact");
            try {
                ContactObj = new JSONObject(contact);
                Phone = ContactObj.getString("Mobile");
                Email =ContactObj.getString("Email");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Contact=contact;
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
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.contacts_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){

            case R.id.contact_call:
                Intent call = new Intent(Intent.ACTION_CALL);
                call.setData(Uri.parse("tel:" + Phone));
                startActivity(call);
                return true;
            case R.id.contact_new_email:
                Intent mail = new Intent(Intent.ACTION_SEND);
                mail.setType("text/plain");
                mail.putExtra(Intent.EXTRA_EMAIL, new String[]{Email});
                mail.putExtra(Intent.EXTRA_SUBJECT,"");
                startActivity(mail);
                return true;
            case R.id.contact_edit:
                break;
            case R.id.contact_attachment:
                break;
            case R.id.contact_mark:
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return PlaceholderFragment.newInstance(position + 1,Contact);
        }

        @Override
        public int getCount() {

            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return "Basic Info";
                case 1:
                    return "Notes";
                case 2:
                    return "Leads";

            }
            return null;
        }
    }

    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";
        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber,String lead) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putString("Contact",lead);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            int section =getArguments().getInt(ARG_SECTION_NUMBER);
            switch (section)
            {
                case 1:

            View rootView = inflater.inflate(R.layout.fragment_contacts_details, container, false);
            TextView name =(TextView)rootView.findViewById(R.id.contact_name);
            TextView mobile =(TextView)rootView.findViewById(R.id.contact_mobile);
            TextView phone =(TextView)rootView.findViewById(R.id.contact_phone);
            TextView email =(TextView)rootView.findViewById(R.id.contact_email);
            TextView dob =(TextView)rootView.findViewById(R.id.contact_dob);
            TextView address =(TextView)rootView.findViewById(R.id.contact_addressline);
            TextView city =(TextView)rootView.findViewById(R.id.contact_city);
            TextView facebook =(TextView)rootView.findViewById(R.id.contact_facebook);
            TextView twitter =(TextView)rootView.findViewById(R.id.contact_twitter);
            TextView linkedin =(TextView)rootView.findViewById(R.id.contact_linkedin);
            TextView remarks =(TextView)rootView.findViewById(R.id.contact_remarks);

            try {
                name.setText(ContactObj.getString("FirstName")+" "+ContactObj.getString("LastName"));
                mobile.setText(ContactObj.getString("Mobile"));
                phone.setText(ContactObj.getString("Phone"));
                email.setText(ContactObj.getString("Email"));
                dob.setText(ContactObj.getString("DateofBirth"));
                address.setText(ContactObj.getString("StreetAddress"));
                city.setText(ContactObj.getString("City"));
                facebook.setText(ContactObj.getString("Facebook"));
                twitter.setText(ContactObj.getString("Twitter"));
                linkedin.setText(ContactObj.getString("LinkedIn"));
                remarks.setText(ContactObj.getString("Remarks"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return rootView;

                case 2:
                    View view1 = inflater.inflate(R.layout.fragment_contact_notes, container, false);
                    JsonObjects nob =new JsonObjects();
                    final ListView notelist = (ListView)view1.findViewById(R.id.conatcts_notes);
                    com.hk.Components.ListViews nadpter=new com.hk.Components.ListViews(
                            view1.getContext(),
                            R.layout.fragment_note_listitem,
                            nob.GetNotes(),
                            "Notes"
                    );
                    notelist.setAdapter(nadpter);
                    return view1;
                case 3:
                    final View view2 = inflater.inflate(R.layout.fragment_contact_leads, container, false);
                    JsonObjects lob =new JsonObjects();
                    CloudGust.initialize("52b02d2d75a43c192344de29", "db6c7594-7e1c-4ce2-a014-aa085c151f67");
                    CloudGust.setUser("1","admin","admin");
                    final ListView hotleads =(ListView)view2.findViewById(R.id.contact_leads);
                    CGCollection cInfo =new CGCollection("Leads");
                    cInfo.fetch(new CGFetchCallback() {
                        @Override
                        public void done(ArrayList<CGObject> cgObjects, CGException e) {
                            com.hk.Components.ListViews adpter = new com.hk.Components.ListViews(
                                    view2.getContext(),
                                    R.layout.fragment_summury_lead_listitem,
                                    cgObjects,
                                    "LeadSummary"
                            );
                            hotleads.setAdapter(adpter);
                        }
                    });
                    return view2;
                default:
                    return null;
            }
        }
    }

}
