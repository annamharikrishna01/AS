package com.hk.agentsphere;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.cloudgust.CGException;
import com.cloudgust.CGObject;
import com.cloudgust.CGSaveCallback;
import com.cloudgust.CloudGust;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewContactActivity extends Activity {

    private  EditText cFirstName,cLastName,cEmail,cMobile,cPhone,cAddress,cCity,cZipCode,cDateBirth,cFacebook,cTwitter,cLinkedIn,cRemarks;
    private AutoCompleteTextView cCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_new_contact);
        CloudGust.initialize("52b02d2d75a43c192344de29", "db6c7594-7e1c-4ce2-a014-aa085c151f67");
        CloudGust.setUser("1","admin","admin");
        ActionBar ab= getActionBar();
        ab.setHomeButtonEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id)
        {
            case R.id.contact_save:
                CGObject cInfo =new CGObject("Contacts");
                try {
                    cInfo.copy(GetContactInfo());
                    cInfo.save(new CGSaveCallback() {
                        @Override
                        public void done(CGObject cgObject, CGException e) {
                            Toast.makeText(getApplicationContext(), "Contact Saved", Toast.LENGTH_SHORT).show();
                            Intent leadDetail = new Intent(getApplicationContext(), ContactsActivity.class);
                            startActivity(leadDetail);
                            finish();

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    public void ValidateContact()
    {}

    public JSONObject GetContactInfo()
    {

        JSONObject cInfo = new JSONObject();
        EditText FirstName = (EditText)findViewById(R.id.FirstName);
        EditText LastName = (EditText)findViewById(R.id.LastName);
        EditText Email = (EditText)findViewById(R.id.Email);
        EditText Phone=(EditText)findViewById(R.id.Phone);
        EditText Mobile =(EditText)findViewById(R.id.Mobile);
        EditText AddressLine=(EditText)findViewById(R.id.AddressLine);
        EditText City=(EditText)findViewById(R.id.City);
        EditText ZipCode=(EditText)findViewById(R.id.ZipCode);
        Spinner Country=(Spinner)findViewById(R.id.Country);
        EditText Facebook=(EditText)findViewById(R.id.Facebook);
        EditText Dob= (EditText)findViewById(R.id.DateOfBirth);
        EditText Twitter=(EditText)findViewById(R.id.Twitter);
        EditText LinkedIn=(EditText)findViewById(R.id.LinkedIn);
        EditText Remarks =(EditText)findViewById(R.id.Remarks);

        try {
            cInfo.put("FirstName",FirstName.getText());
            cInfo.put("LastName",LastName.getText());
            cInfo.put("Email",Email.getText());
            cInfo.put("Phone",Phone.getText());
            cInfo.put("Mobile",Mobile.getText());
            cInfo.put("StreetAddress",AddressLine.getText());
            cInfo.put("City",City.getText());
            cInfo.put("ZipCode",ZipCode.getText());
            cInfo.put("Facebook",Facebook.getText());
            cInfo.put("Twitter",Twitter.getText());
            cInfo.put("DateofBirth",Dob.getText());
            cInfo.put("LinkedIn",LinkedIn.getText());
            cInfo.put("Country",Country.getSelectedItem().toString());
            cInfo.put("Type","New");
            cInfo.put("Remarks",Remarks.getText());
            cInfo.put("Leads",new ArrayList<String>());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cInfo;
    }



}

