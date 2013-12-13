package com.hk.agentsphere;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudgust.CGException;
import com.cloudgust.CGObject;
import com.cloudgust.CGSaveCallback;
import com.cloudgust.CloudGust;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FollowupActivity extends Activity {
     public String Lead;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras= getIntent().getExtras();
        if(extras != null)
        {
            String lead = extras.getString("Lead");
            Lead=lead;

        }
        setContentView(R.layout.activity_followup);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.followup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.followup_save:
                View v=getWindow().getDecorView();
                JSONObject followup = GetFollowup(v);
                CloudGust.initialize("52a9b6cb75a43c192344de1f", "9886190c-aafd-45a8-a37d-0b96651aedaf");
                CloudGust.setUser("1","admin","admin");
                try {
                    if(followup != null)
                    {
                    JSONObject leadObj = new JSONObject(Lead);
                    leadObj.put("Followups",GetFoloowups(leadObj.get("Followups").toString(),followup));
                    CGObject newfollowup =new CGObject("Leads");
                    newfollowup.setId(leadObj.getString("id"));
                    newfollowup.copy(leadObj);
                        newfollowup.save(new CGSaveCallback() {
                            @Override
                            public void done(CGObject cgObject, CGException e) {
                                Toast.makeText(getApplicationContext(), "Followup Added", Toast.LENGTH_SHORT).show();
                                Intent returnIntent = new Intent();
                                returnIntent.putExtra("Lead",cgObject.toString());
                                setResult(LeadDetailsActivity.SET_FOLLOWUP, returnIntent);
                                finish();

                            }
                        });

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.followup_cancel:
                Intent returnIntent = new Intent();
                setResult(55, returnIntent);
                finish();
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
            View rootView = inflater.inflate(R.layout.fragment_followup, container, false);
            return rootView;
        }
    }

    public JSONObject GetFollowup(View view)
    {
        TextView title= (TextView)view.findViewById(R.id.followup_title);
        Spinner type= (Spinner)view.findViewById(R.id.followup_type);
        TextView date =(TextView)view.findViewById(R.id.followup_date);
        TextView time= (TextView)view.findViewById(R.id.followup_time);
        TextView note = (TextView)view.findViewById(R.id.Notes);

        JSONObject followup =new JSONObject();
        try {
        followup.put("Title",title.getText());
        followup.put("Type",type.getSelectedItem().toString());
        followup.put("Date", date.getText());
        followup.put("Time", time.getText());
        followup.put("Notes",note.getText());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  followup;
    }

    public ArrayList<JSONObject> GetFoloowups(String followupString,JSONObject newFollowup)
    {
        ArrayList<JSONObject> followups = new ArrayList<JSONObject>();
        if (!followupString.isEmpty())
        {
            try {

                JSONArray temp = new JSONArray(followupString);
                for (int i =0;i< temp.length();i++)
                {
                    followups.add(new JSONObject(temp.get(i).toString()));
                }
                followups.add(newFollowup);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{

            followups.add(newFollowup);
        }
        return  followups;

    }

}
