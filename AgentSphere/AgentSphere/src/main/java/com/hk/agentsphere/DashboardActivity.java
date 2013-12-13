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
import android.widget.Toast;

import com.cloudgust.CGCollection;
import com.cloudgust.CGException;
import com.cloudgust.CGFetchCallback;
import com.cloudgust.CGObject;
import com.cloudgust.CloudGust;
import com.hk.Components.Dialogs;

import java.util.ArrayList;

;

public class DashboardActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    private Menu  mMenu;
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

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.menu_lead);
                break;
            case 2:
                mTitle = getString(R.string.menu_contacts);
                break;
            case 3:
                mTitle = getString(R.string.menu_listings);
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


        // Associate searchable configuration with the SearchView

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id)
        {
            case R.id.action_new:
                Dialogs dia= new Dialogs();
                dia.show(getFragmentManager(),"");
                return true;
            case R.id.action_settings:
                Intent settings= new Intent(this,SettingsActivity.class);
                startActivity(settings);
                return true;

            case R.id.action_list:
                 switch (context)
                 {
                     case 0:
                         Intent leads = new Intent(this,LeadsActivity.class);
                         startActivity(leads);
                         break;
                     case 1:
                         Intent contacs = new Intent(this,ContactsActivity.class);
                         startActivity(contacs);
                         return true;
                     case 2:
                         Intent listings = new Intent(this,ListingsActivity.class);
                         startActivity(listings);
                         return true;

                     default:
                         Intent dleads = new Intent(this,LeadsActivity.class);
                         startActivity(dleads);
                         return true;
                 }

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
                    CloudGust.initialize("52a9b6cb75a43c192344de1f", "9886190c-aafd-45a8-a37d-0b96651aedaf");
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
                case 2:
                    final View view2 = inflater.inflate((R.layout.fragment_dashboard_contacts),container,false);
                    CloudGust.initialize("52a9b6cb75a43c192344de1f", "9886190c-aafd-45a8-a37d-0b96651aedaf");
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
                case 3:
                    final View view3 = inflater.inflate((R.layout.fragment_dashboard_listings),container,false);
                    return view3;
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
                   Toast.makeText(getActivity().getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();
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
