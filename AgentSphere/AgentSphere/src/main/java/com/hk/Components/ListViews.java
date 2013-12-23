package com.hk.Components;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloudgust.CGObject;
import com.hk.agentsphere.ContactsDetailsActivity;
import com.hk.agentsphere.LeadDetailsActivity;
import com.hk.agentsphere.ListingDetailsActivity;
import com.hk.agentsphere.R;
import com.hk.agentsphere.StaffDetailsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Hari on 11/22/13.
 */
public class ListViews extends ArrayAdapter<JSONObject>{

    private final Context context;
    private final ArrayList<CGObject> objects;
    private final String type;

    public ListViews(Context context, int resource,ArrayList objects,String type) {

        super(context, resource,objects);
        this.context = context;
        this.objects = objects;
        this.type = type;


    }
    @Override
    public View getView(int position,View v,ViewGroup group)
    {

       LayoutInflater li =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       if(this.type == "Nav")
       {
        View view= li.inflate(R.layout.fragment_navigation_item,group,false);
        View nView = navigationView(li, position, group);

        return  nView;
       }
       else if(this.type == "LeadSummary")
       {
           View view= li.inflate(R.layout.fragment_summury_lead_listitem,group,false);
           View nView = leadsummuryList(li, position, group);

           return  nView;
       }
        else if(this.type == "Contacts")
       {
           View view= li.inflate(R.layout.fragment_contact_listitem,group,false);
           View nView = contactsList(li, position, group);
           return  nView;
       }
       else if(this.type == "Listings")
       {
           View view= li.inflate(R.layout.fragment_listing_listitem,group,false);
           View nView =listingsList(li, position, group);
           return  nView;

       }
       else if(this.type == "Notes")
       {
           View view= li.inflate(R.layout.fragment_note_listitem,group,false);
            View nView =notelist(li, position, group);
            return  nView;

        }
       else if(this.type == "Followups")
       {
           View view= li.inflate(R.layout.fragment_lead_followup_listitem,group,false);
           View nView =followuplist(li, position, group);
           return  nView;
       }
       else if(this.type == "Staff")
       {
           View view= li.inflate(R.layout.fragment_staff_listitem,group,false);
           View nView =employeeList(li, position, group);
           return  nView;
       }
       else {

           View view= li.inflate(R.layout.fragment_navigation_item,group,false);
           View nView = navigationView(li, position, group);

           return  nView;
       }
    }

    public View followuplist(LayoutInflater li, int position, ViewGroup group) {
        View view= li.inflate(R.layout.fragment_lead_followup_listitem, group, false);
        TextView date = (TextView)view.findViewById(R.id.followup_date);
        TextView remark =(TextView)view.findViewById(R.id.followup_remark);
        TextView title =(TextView)view.findViewById(R.id.followup_title);
        JSONObject item = objects.get(position);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent det = new Intent(getContext(),LeadDetailsActivity.class);
                det.putExtra("Followup",1);
                getContext().startActivity(det);
                return;
            }
        });

        try {
            date.setText(item.getString("Date"));
            title.setText(item.getString("Title"));
            remark.setText(item.getString("Notes"));
        } catch (JSONException e) {

        }
        return view;
    }

    public View notelist(LayoutInflater li, int position, ViewGroup group) {
        View view= li.inflate(R.layout.fragment_note_listitem,group,false);
        TextView ln = (TextView)view.findViewById(R.id.note_title);
        TextView lt =(TextView)view.findViewById(R.id.note_date);
        TextView lp =(TextView)view.findViewById(R.id.note_meassage);
        final JSONObject ob = objects.get(position);
        try {
            ln.setText(ob.getString("Title"));
            lt.setText(ob.getString("Date"));
            lp.setText(ob.getString("Message"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;

    }

    public View listingsList(LayoutInflater li, int position, ViewGroup group) {

        View view= li.inflate(R.layout.fragment_listing_listitem,group,false);
        TextView ln = (TextView)view.findViewById(R.id.listing_name);
        TextView lt =(TextView)view.findViewById(R.id.listing_type);
        TextView lp =(TextView)view.findViewById(R.id.listing_price);

        TextView la=(TextView)view.findViewById(R.id.listing_address);
        TextView lb=(TextView)view.findViewById(R.id.listing_beds);
        TextView lba=(TextView)view.findViewById(R.id.listing_baths);
        TextView lar=(TextView)view.findViewById(R.id.listing_area);

        final JSONObject ob = objects.get(position);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listingDetail = new Intent(getContext(), ListingDetailsActivity.class);
                listingDetail.putExtra("Listing", ob.toString());
                getContext().startActivity(listingDetail);
                return;
            }
        });
        try {
            ln.setText(ob.getString("Name")+","+ob.getString("Community"));
            lt.setText(ob.getString("Listing Type"));
            lp.setText("Price-"+ob.getString("Price"));
            la.setText(ob.getString("Street Address"));
            lb.setText("Beds-"+ob.getString("Beds"));
            lba.setText("Baths-"+ob.getString("Baths"));
            lar.setText("Area-"+ob.getString("Total Area")+"Sqft");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }

    public View leadsummuryList(LayoutInflater li,int position,ViewGroup group)
    {
        View view= li.inflate(R.layout.fragment_summury_lead_listitem,group,false);
        TextView lm = (TextView)view.findViewById(R.id.lead_name);
        TextView ls =(TextView)view.findViewById(R.id.Lead_status);
        TextView lp =(TextView)view.findViewById(R.id.lead_prev_action);
        TextView pt =(TextView)view.findViewById(R.id.lead_prev_time);
        ImageButton le=(ImageButton)view.findViewById(R.id.lead_email);
        ImageButton lc = (ImageButton) view.findViewById(R.id.lead_call);

        final CGObject ob = objects.get(position);
        try {
            ob.put("id",ob.getId());
            String fObj = ob.get("Followups").toString();
            ArrayList<JSONObject> followups = GetFoloowups(fObj);
            JSONObject followup = followups.get(followups.size()-1);
            if(followup != null)
            {
                  pt.setText("18 dec 11:00 am :");
               // fTitle.setText(ob.getString("Title"));
                 lp.setText(followup.getString("Notes"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent leadDetail = new Intent(getContext(), LeadDetailsActivity.class);
                leadDetail.putExtra("Lead", ob.toString());
                getContext().startActivity(leadDetail);
            }
        });
        try {
           final JSONObject contact =   ob.getJSONObject("Contact");
            lc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent call = new Intent(Intent.ACTION_CALL);
                    try {
                        call.setData(Uri.parse("tel:" + contact.getString("Mobile")));
                        getContext().startActivity(call);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            le.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mail = new Intent(Intent.ACTION_SEND);
                    mail.setType("text/plain");
                    try {
                        mail.putExtra(Intent.EXTRA_EMAIL, new String[]{contact.getString("Email")});
                        mail.putExtra(Intent.EXTRA_SUBJECT,"");
                        getContext().startActivity(mail);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
            if(contact != null)
            {
                lm.setText(contact.getString("FirstName")+ " "+contact.getString("LastName"));
                ls.setText(ob.getString("Status"));
               // le.setText(contact.getString("Email"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;

    }

    private ArrayList<JSONObject> GetFoloowups(String fObj) {
        ArrayList<JSONObject> followups = new ArrayList<JSONObject>();
        if (!fObj.isEmpty())
        {
            try {

                JSONArray temp = new JSONArray(fObj);
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

    public View navigationView(LayoutInflater li,int position,ViewGroup group)
    {
        View view= li.inflate(R.layout.fragment_navigation_item,group,false);
        TextView tv = (TextView)view.findViewById(R.id.menu_text);
        ImageView iv=(ImageView)view.findViewById(R.id.imageView);
        JSONObject ob= objects.get(position);
        try {
            tv.setText(ob.getString("Name"));
            iv.setImageResource(ob.getInt("Image"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
      

    }

    public View contactsList(LayoutInflater li,int position,ViewGroup group)
    {
        View view= li.inflate(R.layout.fragment_contact_listitem,group,false);
        TextView lm = (TextView)view.findViewById(R.id.contac_name);
        TextView lp =(TextView)view.findViewById(R.id.contact_phone);
        TextView le=(TextView)view.findViewById(R.id.contact_email);
        ImageButton lc = (ImageButton) view.findViewById(R.id.contact_call);

        final JSONObject ob = objects.get(position);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent conatctDetail = new Intent(getContext(), ContactsDetailsActivity.class);
                conatctDetail.putExtra("Contact", ob.toString());
                getContext().startActivity(conatctDetail);
            }
        });
        try {
            lc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent call = new Intent(Intent.ACTION_CALL);
                    try {
                        call.setData(Uri.parse("tel:" + ob.getString("Phone")));
                        getContext().startActivity(call);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            if(ob != null)
            {
                lm.setText(ob.getString("FirstName")+ " "+ob.getString("LastName"));
                lp.setText(ob.getString("Mobile"));
                le.setText(ob.getString("Email"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }

    public View employeeList(LayoutInflater li,int position,ViewGroup group)
    {

        View view= li.inflate(R.layout.fragment_staff_listitem,group,false);
         TextView sn = (TextView)view.findViewById(R.id.staff_name);
         TextView sl=(TextView)view.findViewById(R.id.staff_leads);
         TextView sp =(TextView)view.findViewById(R.id.staff_pending);
         TextView spo=(TextView)view.findViewById(R.id.staff_postponed);
        TextView sw=(TextView)view.findViewById(R.id.staff_working);
         ImageButton lc = (ImageButton) view.findViewById(R.id.staff_call);


        final JSONObject ob = objects.get(position);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stDetail =new Intent(getContext(), StaffDetailsActivity.class);
                stDetail.putExtra("Staff", ob.toString());
                getContext().startActivity(stDetail);
                return;
            }
        });
        if(ob != null)
        {
            try {
                final JSONObject contact =   ob.getJSONObject("Contact");
                lc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent call = new Intent(Intent.ACTION_CALL);
                        try {
                            call.setData(Uri.parse("tel:" + contact.getString("Mobile")));
                            getContext().startActivity(call);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                sn.setText(contact.getString("FirstName") + " " + contact.getString("LastName"));
                sl.setText("Leads-"+ob.getString("Leads"));
                sp.setText("Pending-"+ob.getString("Pending"));
                sw.setText("Working"+ob.getString("Working"));
                spo.setText("Postponed-"+ob.getString("Postponed"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return  view;
    }


}
