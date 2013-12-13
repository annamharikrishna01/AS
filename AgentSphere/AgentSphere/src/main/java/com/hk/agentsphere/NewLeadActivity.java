package com.hk.agentsphere;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudgust.CGException;
import com.cloudgust.CGObject;
import com.cloudgust.CGSaveCallback;
import com.cloudgust.CloudGust;
import com.hk.Components.JsonObjects;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewLeadActivity extends Activity {

    private CGObject cObj;
    public static JSONObject Lead;
    static final int GET_REQUIREMENTS = 1;
    static JSONObject requirements ;
    static  String Message;
    static String score;
    Bundle InstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_lead);
        CloudGust.initialize("52a9b6cb75a43c192344de1f", "9886190c-aafd-45a8-a37d-0b96651aedaf");
        CloudGust.setUser("1","admin","admin");

        ActionBar ab= getActionBar();
        ab.setHomeButtonEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        InstanceState = savedInstanceState;
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.new_lead, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        final Context context = getApplicationContext();
        switch(id)
        {
            case R.id.lead_save:
                try {
                    final JsonObjects ob=new JsonObjects();
                    CGObject cInfo =new CGObject("Leads");
                    cInfo.put("Contact",GetContactInfo());
                    cInfo.put("Followups",GetFollowup());
                    cInfo.put("Requirements",requirements);
                    cInfo.put("Status","New");
                    cInfo.put("Score",score);
                    cInfo.put("Closing",new JSONObject());
                    cInfo.save(new CGSaveCallback() {
                        @Override
                        public void done(CGObject cgObject, CGException e) {

                            Toast.makeText(getApplicationContext(), "Lead Added", Toast.LENGTH_SHORT).show();
                            Intent leadDetail = new Intent(context, LeadDetailsActivity.class);
                            leadDetail.putExtra("Lead", cgObject.toString());
                            startActivity(leadDetail);
                            finish();

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
              break;
            case  R.id.lead_cancel:
                finish();
                break;

        }

        return super.onOptionsItemSelected(item);
    }


    public static class PlaceholderFragment extends Fragment implements View.OnClickListener {

        LayoutInflater linflater;
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            linflater=inflater;
            final String[] value = new String[1];
            View rootView = inflater.inflate(R.layout.fragment_new_lead, container, false);
            SeekBar bar = (SeekBar)rootView.findViewById(R.id.lead_score);
            final TextView progresstext =(TextView)rootView.findViewById(R.id.lead_score_text);

            bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                   value[0] =String.valueOf(progress);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                    progresstext.setText(value[0]);
                    score = value[0];

                }
            });
            Button btn_req = (Button)rootView.findViewById(R.id.btn_getRequirements);
            btn_req.setOnClickListener(this);

            return rootView;
        }

        @Override
        public void onClick(View v) {

            switch (v.getId())
            {
                case R.id.btn_getRequirements:
                    Intent reqIntent= new Intent(getActivity().getApplicationContext(),RequirementActivity.class);
                    startActivityForResult(reqIntent,GET_REQUIREMENTS);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onActivityResult(int requestCode,int resultCode,Intent data)
        {
            super.onActivityResult(requestCode,resultCode,data);
            if(requestCode==GET_REQUIREMENTS)
            {
                String message=data.getStringExtra("Requirements");
                try {
                    JSONObject req = new JSONObject(message);
                    if(req!=null)
                    {
                        requirements =req;
                        View v =getView();
                        Button b =(Button)v.findViewById(R.id.btn_getRequirements);
                        b.setVisibility(View.GONE);
                        ViewGroup g=(ViewGroup)v.getParent();
                        FrameLayout C = (FrameLayout)v.findViewById(R.id.requirement_info);
                        View r =linflater.inflate(R.layout.fragment_newlead_requirements,g,false);
                        TextView type=(TextView)r.findViewById(R.id.lead_req_type);
                        TextView loc =(TextView)r.findViewById(R.id.lead_req_location);
                        TextView minPrice =(TextView)r.findViewById(R.id.lead_req_price);
                        TextView beds =(TextView)r.findViewById(R.id.lead_req_beds);
                        TextView baths =(TextView)r.findViewById(R.id.lead_req_baths);
                        TextView area = (TextView)r.findViewById(R.id.lead_req_area);
                        TextView age =(TextView)r.findViewById(R.id.lead_req_age);
                        TextView proptype =(TextView)r.findViewById(R.id.lead_req_proptype);
                        TextView lotsize =(TextView)r.findViewById(R.id.lead_req_lotsize);
                        TextView features = (TextView)r.findViewById(R.id.lead_req_features);

                        type.setText(req.getString("Type"));
                        loc.setText(req.getString("Location"));
                        minPrice.setText(req.getString("MinPrice")+"-"+req.getString("MaxPrice"));
                        beds.setText(req.getString("Beds"));
                        baths.setText(req.getString("Baths"));
                        area.setText(req.getString("Area"));
                        age.setText(req.getString("Age"));
                        proptype.setText(req.getString("PropertyType"));
                        lotsize.setText(req.getString("LotSize"));
                        features.setText(req.getString("Features"));
                        C.addView(r);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            else
            {

            }

        }
    }


    public  void ValidateLead()
    {}

    public ArrayList<JSONObject> GetFollowup()
    {
        ArrayList<JSONObject> followups = new ArrayList<JSONObject>();
        JSONObject fInfo = new JSONObject();
        EditText Title = (EditText)findViewById(R.id.followup_title);
        Spinner Type = (Spinner)findViewById(R.id.followup_type);
        EditText NextFDate = (EditText)findViewById(R.id.FollowupDate);
        EditText Time=(EditText)findViewById(R.id.FollowupTime);
        EditText Notes =(EditText)findViewById(R.id.Remarks);

        try {
            fInfo.put("Title",Title.getText());
            fInfo.put("Type",Type.getSelectedItem().toString());
            fInfo.put("Date",NextFDate.getText());
            fInfo.put("Time",Time.getText());
            fInfo.put("Notes",Notes.getText());
            followups.add(fInfo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
       return followups;

    }
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
       EditText Country=(EditText)findViewById(R.id.Country);
       EditText Facebook=(EditText)findViewById(R.id.Facebook);
       EditText Twitter=(EditText)findViewById(R.id.Twitter);
       EditText LinkedIn=(EditText)findViewById(R.id.LinkedIn);

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
            cInfo.put("DateofBirth","");
 //           cInfo.put("LinkedIn",LinkedIn.getText());
            cInfo.put("Country",Country.getText());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cInfo;
    }


}
