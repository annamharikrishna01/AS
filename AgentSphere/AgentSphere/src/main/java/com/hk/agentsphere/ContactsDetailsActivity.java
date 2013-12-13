package com.hk.agentsphere;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class ContactsDetailsActivity extends Activity {

    private static JSONObject ContactObj;
    private String Phone;
    private String Email;
    static String Contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_details);
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

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
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
        }
    }

}
