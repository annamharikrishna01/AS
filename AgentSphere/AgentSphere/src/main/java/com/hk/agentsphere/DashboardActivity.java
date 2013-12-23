package com.hk.agentsphere;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.cloudgust.CGCollection;
import com.cloudgust.CGException;
import com.cloudgust.CGFetchCallback;
import com.cloudgust.CGObject;
import com.cloudgust.CloudGust;
import com.hk.Components.Dialogs;
import com.hk.Components.JsonObjects;

import java.util.ArrayList;

;

public class DashboardActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    private static Menu  mMenu;
    private int context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
        context = position;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {

        return  true;
    }
    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.app_name);
                break;
            case 2:
                mTitle = getString(R.string.menu_lead);
                break;
            case 3:
                mTitle = getString(R.string.menu_contacts);
                break;
            case 4:
                mTitle = getString(R.string.menu_listings);
                break;
            case 5:
                mTitle = getString(R.string.menu_staff);
                break;
            case 6:
                mTitle = getString(R.string.menu_news);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.dashboard, menu);
            restoreActionBar();
            return true;
        }
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dashboard, menu);
        this.mMenu = menu;


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id)
        {
            case R.id.action_new:
                switch (context)
                {
                    case 0:
                        Dialogs dia= new Dialogs();
                        dia.show(getFragmentManager(),"New");
                        break;
                    case 1:
                        Intent lead = new Intent(getApplicationContext(),NewLeadActivity.class);
                        startActivity(lead);
                        break;
                    case 2:
                        Intent contact = new Intent(getApplicationContext(),NewContactActivity.class);
                        startActivity(contact);
                        break;
                    case 3:
                        Intent  listing= new Intent(getApplicationContext(),NewListingActivity.class);
                        startActivity(listing);
                        break;
                    case 4:
                        Intent employee =new Intent(getApplicationContext(),NewStaffActivity.class);
                        startActivity(employee);
                        break;
                    default:
                        Dialogs dialogue= new Dialogs();
                        dialogue.show(getFragmentManager(),"New");
                        break;
                }
                break;

            case R.id.action_settings:
                Intent settings= new Intent(this,SettingsActivity.class);
                startActivity(settings);
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    public static class PlaceholderFragment extends Fragment implements View.OnClickListener{

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
            switch(getArguments().getInt(ARG_SECTION_NUMBER))
            {
                case 1:

                    final View view = inflater.inflate((R.layout.fragment_dashboard_home), container, false);
                    JsonObjects fobjs = new JsonObjects();
                    final ListView fups =(ListView)view.findViewById(R.id.followups_list);
                    com.hk.Components.ListViews fadpter=new com.hk.Components.ListViews(
                            view.getContext(),
                            R.layout.fragment_lead_followup_listitem,
                            fobjs.getFollowups(),
                            "Followups"
                    );
                    fups.setAdapter(fadpter);
                    return view;
                case 2:
                    final View view1 = inflater.inflate((R.layout.fragment_dasboard_lead), container, false);
                    Button show = (Button)view1.findViewById(R.id.lead_showall);
                    show.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent leads = new Intent(getActivity().getApplicationContext(),LeadsActivity.class);
                            startActivity(leads);
                        }
                    });
                    final ListView hotleads =(ListView)view1.findViewById(R.id.lead_summury_list);
                    CloudGust.initialize("52b02d2d75a43c192344de29", "db6c7594-7e1c-4ce2-a014-aa085c151f67");
                    CloudGust.setUser("1","admin","admin");
                    CGCollection cInfo =new CGCollection("Leads");
                    cInfo.fetch(new CGFetchCallback() {
                        @Override
                        public void done(ArrayList<CGObject> cgObjects, CGException e) {
                            com.hk.Components.ListViews adpter=new com.hk.Components.ListViews(
                                    view1.getContext(),
                                    R.layout.fragment_summury_lead_listitem,
                                    cgObjects,
                                    "LeadSummary"
                            );
                            hotleads.setAdapter(adpter);
                        }
                    });

                    return view1;
                case 3:
                    final View view2 = inflater.inflate((R.layout.fragment_dashboard_contacts),container,false);
                    Button call = (Button)view2.findViewById(R.id.contact_showall);
                    call.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent list =new Intent(getActivity().getApplicationContext(),ContactsActivity.class);
                            startActivity(list);
                        }
                    });
                    CloudGust.initialize("52b02d2d75a43c192344de29", "db6c7594-7e1c-4ce2-a014-aa085c151f67");
                    CloudGust.setUser("1","admin","admin");
                    final ListView contactList =(ListView)view2.findViewById(R.id.contact_list);
                    CGCollection coInfo =new CGCollection("Contacts");
                    coInfo.fetch(new CGFetchCallback() {
                        @Override
                        public void done(ArrayList<CGObject> cgObjects, CGException e) {
                            com.hk.Components.ListViews adpter=new com.hk.Components.ListViews(
                                    view2.getContext(),
                                    R.layout.fragment_contact_listitem,
                                    cgObjects,
                                    "Contacts"
                            );
                            contactList.setAdapter(adpter);
                        }
                    });

                    return  view2;
                case 4:
                    final View view3 = inflater.inflate((R.layout.fragment_dashboard_listings),container,false);
                    Button all = (Button)view3.findViewById(R.id.listings_showall);
                    all.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent list =new Intent(getActivity().getApplicationContext(),ListingsActivity.class);
                            startActivity(list);
                        }
                    });
                    JsonObjects ob=new JsonObjects();
                    final ListView listingList =(ListView)view3.findViewById(R.id.listings_list);
                    com.hk.Components.ListViews adpter=new com.hk.Components.ListViews(
                            view3.getContext(),
                            R.layout.fragment_listing_listitem,
                            ob.GetListings(),
                            "Listings"
                    );
                    listingList.setAdapter(adpter);
                    return view3;
                case 5:

                    View staffView = inflater.inflate(R.layout.fragment_dashboard_staff,container,false);
                    JsonObjects sob=new JsonObjects();
                    final ListView staffList =(ListView)staffView.findViewById(R.id.staff_summary_list);
                    com.hk.Components.ListViews sadpter=new com.hk.Components.ListViews(
                            staffView.getContext(),
                            R.layout.fragment_staff_listitem,
                            sob.GetStaff(),
                            "Staff"
                    );
                    staffList.setAdapter(sadpter);

                    return staffView;
                case 6:

                    View newsView = inflater.inflate(R.layout.fragment_dashboard_news,container,false);
                    JsonObjects nob =new JsonObjects();
                    final ListView notelist = (ListView)newsView.findViewById(R.id.news_list);
                    com.hk.Components.ListViews nadpter=new com.hk.Components.ListViews(
                            newsView.getContext(),
                            R.layout.fragment_note_listitem,
                            nob.GetNotes(),
                            "Notes"
                    );
                    notelist.setAdapter(nadpter);

                      return newsView;
                default:
                    View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
                    TextView textView = (TextView) rootView.findViewById(R.id.section_label);
                    textView.setText("Default");
                    return rootView;
            }

        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((DashboardActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));

        }

        @Override
        public void onClick(View v) {
           switch (v.getId())
           {
               case R.id.leads:
               {
                  Intent leads= new Intent(getActivity().getApplicationContext(),LeadsActivity.class);
                  getActivity().startActivity(leads);
                  break;
               }
               default:
                  // Toast.makeText(getActivity().getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();
                   break;
           }

        }

    }


    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);

    }





}
