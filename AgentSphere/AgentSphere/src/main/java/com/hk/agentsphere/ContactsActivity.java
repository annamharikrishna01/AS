package com.hk.agentsphere;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
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

public class ContactsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.contacts, menu);
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


    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_contacts, container, false);
            CloudGust.initialize("52a9b6cb75a43c192344de1f", "9886190c-aafd-45a8-a37d-0b96651aedaf");
            CloudGust.setUser("1","admin","admin");
            final ListView contactList =(ListView)rootView.findViewById(R.id.contact_list);
            CGCollection cInfo =new CGCollection("Contacts");
            cInfo.fetch(new CGFetchCallback() {
                @Override
                public void done(ArrayList<CGObject> cgObjects, CGException e) {
                    com.hk.Components.ListViews adpter=new com.hk.Components.ListViews(
                            rootView.getContext(),
                            R.layout.fragment_contact_listitem,
                            cgObjects,
                            "Contacts"
                    );
                    contactList.setAdapter(adpter);
                }
            });
            return rootView;
        }
    }

}
