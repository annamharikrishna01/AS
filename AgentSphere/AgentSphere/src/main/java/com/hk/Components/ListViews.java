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
import com.hk.agentsphere.R;

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
        else {

           View view= li.inflate(R.layout.fragment_navigation_item,group,false);
           View nView = navigationView(li, position, group);

           return  nView;
       }
    }

     
    public View leadsummuryList(LayoutInflater li,int position,ViewGroup group)
    {
        View view= li.inflate(R.layout.fragment_summury_lead_listitem,group,false);
        TextView lm = (TextView)view.findViewById(R.id.lead_name);
        TextView ls =(TextView)view.findViewById(R.id.Lead_status);
        //TextView lp =(TextView)view.findViewById(R.id.lead_recent_activity);
        ImageButton le=(ImageButton)view.findViewById(R.id.lead_email);
        ImageButton lc = (ImageButton) view.findViewById(R.id.lead_call);

        final CGObject ob = objects.get(position);
        try {
            ob.put("id",ob.getId());
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


}
