package com.hk.agentsphere;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
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
import java.util.Locale;

public class LeadDetailsActivity extends Activity implements ActionBar.TabListener {


    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    String Lead;
    static JSONObject LeadObj;
    String Phone;
    String Email;
    static int MAIL_REQUEST=125;
    static int SET_FOLLOWUP=126;
    private AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead_details);
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        CloudGust.initialize("52a9b6cb75a43c192344de1f", "9886190c-aafd-45a8-a37d-0b96651aedaf");
        CloudGust.setUser("1","admin","admin");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        Bundle extras= getIntent().getExtras();
        if(extras != null)
        {
            String lead = extras.getString("Lead");
            try {
                LeadObj = new JSONObject(lead);
                JSONObject cInfo =LeadObj.getJSONObject("Contact");
                Phone = cInfo.getString("Phone");
                Email =cInfo.getString("Email");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Lead=lead;

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

        getMenuInflater().inflate(R.menu.lead_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.lead_call:
                Intent call = new Intent(Intent.ACTION_CALL);
                call.setData(Uri.parse("tel:" + Phone));
                startActivity(call);
                break;
            case R.id.lead_new_email:
                Intent mail = new Intent(Intent.ACTION_SEND);
                mail.setType("text/plain");
                mail.putExtra(Intent.EXTRA_EMAIL, new String[]{Email});
                mail.putExtra(Intent.EXTRA_SUBJECT,"");
                startActivity(mail);
                break;
            case R.id.lead_convert:
                LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
                final View v = inflater.inflate(R.layout.fragment_convert_leadoptions,(ViewGroup)getWindow().getDecorView().findViewById(R.layout.activity_lead_details));
                AlertDialog.Builder builder = new AlertDialog.Builder(LeadDetailsActivity.this);
                builder.setTitle("Convert Lead");
                builder.setView(v);
                builder.setPositiveButton("Convert Lead", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {
                            final String id= LeadObj.getString("id");
                            JSONObject converRemarks = new JSONObject();
                            EditText remarks= (EditText)v.findViewById(R.id.Notes);
                            converRemarks.put("Closing Remrks",remarks.getText());
                            LeadObj.put("Closing",converRemarks);
                            LeadObj.put("Status","Converted Lead");
                            CGObject leadob = new CGObject("Leads");
                            leadob.setId(id);
                            leadob.copy(LeadObj);
                            leadob.save(new CGSaveCallback() {
                                @Override
                                public void done(CGObject cgObject, CGException e) {

                                    try {
                                        JSONObject contact =cgObject.getJSONObject("Contact");
                                        AddContact(contact,id);

                                    } catch (JSONException e1) {
                                        e1.printStackTrace();
                                    }
                                    return;
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }).setNegativeButton("Close Lead", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {
                            String id= LeadObj.getString("id");
                            JSONObject converRemarks = new JSONObject();
                            EditText remarks= (EditText)v.findViewById(R.id.Notes);
                            converRemarks.put("Closing Remrks",remarks.getText());
                            LeadObj.put("Closing",converRemarks);
                            LeadObj.put("Status","Closed Lead");
                            CGObject leadob = new CGObject("Leads");
                            leadob.setId(id);
                            leadob.copy(LeadObj);
                            leadob.save(new CGSaveCallback() {
                                @Override
                                public void done(CGObject cgObject, CGException e) {
                                    Toast.makeText(getApplicationContext(),"Lead Closed",Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
                builder.setCancelable(true);
                dialog = builder.create();
                dialog.show();
                break;
            case R.id.lead_mark:
                break;
            case R.id.lead_remainder:
                    Intent remainder = new Intent(getApplicationContext(),FollowupActivity.class);
                    remainder.putExtra("Lead",Lead);
                    startActivityForResult(remainder,SET_FOLLOWUP);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void AddContact( JSONObject contact,String id){

        JSONObject cInfo =new JSONObject();
        ArrayList<String> cId = new ArrayList<String>();
        cId.add(id);


        try {
            cInfo.put("FirstName",contact.getString("FirstName"));
            cInfo.put("LastName",contact.getString("LastName"));
            cInfo.put("Email",contact.getString("Email"));
            cInfo.put("Phone",contact.getString("Phone"));
            cInfo.put("Mobile",contact.getString("Mobile"));
            cInfo.put("StreetAddress",contact.getString("StreetAddress"));
            cInfo.put("City",contact.getString("City"));
            cInfo.put("ZipCode",contact.getString("ZipCode"));
            cInfo.put("Facebook",contact.getString("Facebook"));
            cInfo.put("Twitter",contact.getString("Twitter"));
            cInfo.put("DateofBirth",contact.getString("DateofBirth"));
            cInfo.put("LinkedIn","");
            cInfo.put("Country",contact.getString("Country"));
            cInfo.put("Type","New");
            cInfo.put("Remarks","");
            cInfo.put("Leads",cId);
            CGObject con =new CGObject("Contacts");
            con.copy(cInfo);
            con.save(new CGSaveCallback() {
                @Override
                public void done(CGObject cgObject, CGException e) {
                    Toast.makeText(getApplicationContext(),"Contact Added",Toast.LENGTH_SHORT).show();
                }
            });
        } catch (JSONException e) {


        }

    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==resultCode)
        {
            String message=data.getStringExtra("Lead");
            if(!message.isEmpty())
            {
                Lead = message;
            }
        }
        else
        {}
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

            return PlaceholderFragment.newInstance(position + 1,Lead);
        }

        @Override
        public int getCount() {

            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return "Basic Info";
                case 1:
                    return "Followups";
                case 2:
                    return "TimeLine";
                case 3:
                    return "ShortLists";
            }
            return null;
        }
    }


    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";


        public static PlaceholderFragment newInstance(int sectionNumber,String lead) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putString("Lead",lead);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                Bundle savedInstanceState) {
            int section =getArguments().getInt(ARG_SECTION_NUMBER);
            switch (section)
            {
                case 1:
                    View View1 = inflater.inflate(R.layout.fragment_lead_basic, container, false);
                    final JSONObject val = GetJsonObject(getArguments().getString("Lead"));
                    TextView fDate =(TextView)View1.findViewById(R.id.followup_date);
                    TextView fTitle =(TextView)View1.findViewById(R.id.followup_title);
                    TextView fRemark = (TextView)View1.findViewById(R.id.followup_remark);
                    TextView cNmae = (TextView)View1.findViewById(R.id.lead_contact_name);
                    TextView cPhone = (TextView)View1.findViewById(R.id.lead_contact_phone);
                    TextView cEmail = (TextView)View1.findViewById(R.id.lead_contact_email);
                    TextView cAddressline =(TextView)View1.findViewById(R.id.lead_contact_addressline);
                    TextView cCity = (TextView)View1.findViewById(R.id.lead_contact_city);
                    TextView cDob=(TextView)View1.findViewById(R.id.lead_contact_dob);
                    TextView type=(TextView)View1.findViewById(R.id.lead_req_type);
                    TextView loc =(TextView)View1.findViewById(R.id.lead_req_location);
                    TextView minPrice =(TextView)View1.findViewById(R.id.lead_req_price);
                    TextView beds =(TextView)View1.findViewById(R.id.lead_req_beds);
                    TextView baths =(TextView)View1.findViewById(R.id.lead_req_baths);
                    TextView area = (TextView)View1.findViewById(R.id.lead_req_area);
                    TextView age =(TextView)View1.findViewById(R.id.lead_req_age);
                    TextView proptype =(TextView)View1.findViewById(R.id.lead_req_proptype);
                    TextView lotsize =(TextView)View1.findViewById(R.id.lead_req_lotsize);
                    TextView features = (TextView)View1.findViewById(R.id.lead_req_features);
                    TextView leadname =(TextView)View1.findViewById(R.id.Lead_head_name);
                    TextView status =(TextView)View1.findViewById(R.id.Lead_status);
                    final Spinner changeStatus = (Spinner)View1.findViewById(R.id.lead_change_status);
                    SeekBar confidence =(SeekBar)View1.findViewById(R.id.lead_score);
                    final TextView score =(TextView)View1.findViewById(R.id.lead_score_text);
                    final String[] Val =new String[1];
                    confidence.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            Val[0]=String.valueOf(progress);
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                            score.setText(Val[0]);

                        }
                    });


                    Button update =(Button)View1.findViewById(R.id.lead_update);
                    update.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            try {
                                LeadObj.put("Status",changeStatus.getSelectedItem().toString());
                                LeadObj.put("Score",score.getText());
                                String id= LeadObj.getString("id");
                                CGObject update =new CGObject("Leads");
                                update.setId(id);
                                update.copy(LeadObj);
                                update.save(new CGSaveCallback() {
                                    @Override
                                    public void done(CGObject cgObject, CGException e) {
                                        Toast.makeText(getActivity().getApplicationContext(),"Lead Updated",Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                });

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                    if(val != null)
                    {
                        try {
                            status.setText(val.getString("Status"));
                            if(!val.getString("Score").isEmpty())
                            {
                                score.setText(val.getString("Score"));
                               confidence.setProgress(Integer.parseInt(val.getString("Score")));
                            }

                            String fObj = val.get("Followups").toString();
                            ArrayList<JSONObject> followups = GetFoloowups(fObj);
                            JSONObject ob = followups.get(followups.size()-1);
                            fDate.setText(ob.getString("Date"));
                            fTitle.setText(ob.getString("Title"));
                            fRemark.setText(ob.getString("Notes"));
                            JSONObject cInfo = val.getJSONObject("Contact");
                            cNmae.setText(cInfo.getString("FirstName")+" "+cInfo.getString("LastName"));
                            leadname.setText(cInfo.getString("FirstName")+" "+cInfo.getString("LastName"));
                            cPhone.setText(cInfo.getString("Mobile")+","+cInfo.getString("Phone"));
                            cEmail.setText(cInfo.getString("Email"));
                            cAddressline.setText(cInfo.getString("StreetAddress"));
                            cCity.setText(cInfo.getString("City")+"-"+cInfo.getString("ZipCode"));
                            cDob.setText(cInfo.getString("DateofBirth"));
                            JSONObject req =val.getJSONObject("Requirements");
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

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                    return View1;
                case 2:
                    View View2 = inflater.inflate(R.layout.fragment_lead_followups, container, false);
                    JSONObject val1 = GetJsonObject((getArguments().getString("Lead")));
                    if(val1 != null)
                    {
                        try {
                            String fObj = val1.get("Followups").toString();
                            final ArrayList<JSONObject> followup = GetFoloowups(fObj);
                            ListView followups =(ListView)View2.findViewById(R.id.lead_followup_list);
                            LeadLists listadapter= new  LeadLists(getActivity().getApplicationContext(),R.layout.fragment_lead_followup_listitem,followup,"Followups");
                            followups.setAdapter(listadapter);
                            followups.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    JSONObject f= followup.get(position);
                                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                    View contentView = inflater.inflate(R.layout.fragment_followupdetail_dialog,container,false);
                                    TextView date =(TextView)contentView.findViewById(R.id.followup_date);
                                    TextView remarks =(TextView)contentView.findViewById(R.id.followup_remark);
                                    TextView type =(TextView)contentView.findViewById(R.id.followup_type);
                                    try {
                                        alert.setTitle(f.getString("Title"));
                                        date.setText(f.getString("Date"));
                                        remarks.setText(f.getString("Notes"));
                                       // type.setText(f.getString("Type"));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    alert.setView(contentView);
                                    alert.show();

                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    return View2;
                case 3:
                    View View3 = inflater.inflate(R.layout.fragment_lead_timeline, container, false);
                    return View3;
                case 4:
                    View view4=inflater.inflate(R.layout.fragment_lead_shortlists, container, false);
                    return view4;
               default:
                   View rootView = inflater.inflate(R.layout.fragment_lead_basic, container, false);
                   return rootView;

            }

        }
        public JSONObject GetJsonObject(String jsonString)
        {
            try {
                JSONObject tLead =new JSONObject(jsonString);
                return  tLead;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        public ArrayList<JSONObject> GetFoloowups(String followupString)
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

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
         return  followups;

        }


         public class LeadLists extends  ArrayAdapter<JSONArray>{

             private final Context context;
             private final ArrayList<JSONObject> objects;
             private final String type;
             private final int Resource;

             public LeadLists(Context context, int resource,ArrayList objects,String Type) {
                 super(context, resource,objects);
                 this.context = context;
                 this.objects = objects;
                 this.type = Type;
                 this.Resource = resource;
             }
             @Override
             public View getView(int position,View v,ViewGroup group)
             {
                 final ViewGroup vgroup = group;

                 final LayoutInflater li =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                 if(this.type == "Followups")
                 {
                     View view= li.inflate(Resource, group, false);
                     TextView date = (TextView)view.findViewById(R.id.followup_date);
                     TextView remark =(TextView)view.findViewById(R.id.followup_remark);
                     TextView title =(TextView)view.findViewById(R.id.followup_title);
                     JSONObject item = objects.get(position);

                     try {
                         date.setText(item.getString("Date"));
                         title.setText(item.getString("Title"));
                         remark.setText(item.getString("Notes"));
                     } catch (JSONException e) {

                     }
                     return view;
                 }
                 else
                 {
                     View view= li.inflate(Resource, group, true);
                     return view;
                 }
             }
         }




    }

}
