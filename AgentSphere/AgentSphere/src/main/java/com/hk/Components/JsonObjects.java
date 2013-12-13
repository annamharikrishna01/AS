package com.hk.Components;

import com.hk.agentsphere.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Hari on 11/22/13.
 */
public class JsonObjects {

    public ArrayList<JSONObject> getMenuObject()
    {

        ArrayList<JSONObject> array=new ArrayList<JSONObject>();

        try {
            JSONObject obj = new JSONObject();
            obj.put("Name","Leads");
            obj.put("Image", R.drawable.ic_lead);
            array.add(obj);
            obj = new JSONObject();
            obj.put("Name","Contacts");
            obj.put("Image", R.drawable.ic_contacts);
            array.add(obj);
            obj = new JSONObject();
            obj.put("Name","Listings");
            obj.put("Image", R.drawable.ic_properties);
            array.add(obj);
            obj = new JSONObject();
            obj.put("Name","Staff");
            obj.put("Image", R.drawable.ic_employee);
            array.add(obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  array;
    }
public static JSONObject getLead()
{
    JSONObject obj = new JSONObject();
    try {

        JSONObject cInfo = new JSONObject();
        cInfo.put("FirstName","Hari");
        cInfo.put("LastName","Krishna");
        cInfo.put("Name","Harikrishna");
        cInfo.put("Mobile","9908436507");
        cInfo.put("Phone","9908436507");
        cInfo.put("Email","Hari000@gmail.com");
        cInfo.put("StreetAddress","1-25/317/10,DownTown");
        cInfo.put("City","NewYork");
        cInfo.put("ZipCode","83456");
        cInfo.put("DateofBirth","12/8/1990");
        obj.put("Contact",cInfo);
        obj.put("Status","Working");
        obj.put("Score","5.5");


    } catch (JSONException e) {
        e.printStackTrace();
    }
    return  obj;
}
    public ArrayList<JSONObject> getLeads()
    {
        ArrayList<JSONObject> array=new ArrayList<JSONObject>();

        for(int i=0;i<=25;i++)
        {

        try {
            JSONObject obj = new JSONObject();
            JSONObject cInfo = new JSONObject();
            cInfo.put("FirstName","Hari");
            cInfo.put("LastName","Krishna"+i);
            cInfo.put("Name","Harikrishna"+i);
            cInfo.put("Mobile","9908436507");
            cInfo.put("Phone","9908436507");
            cInfo.put("Email","Hari000"+i+"@gmail.com");
            cInfo.put("StreetAddress","1-25/317/10,DownTown");
            cInfo.put("City","NewYork");
            cInfo.put("ZipCode","83456");
            cInfo.put("DateofBirth","12/8/1990");
            obj.put("Contact",cInfo);
            obj.put("Followups",getFollowups());
            obj.put("Requirements",getRequirements());
            obj.put("Status","Working");
            obj.put("Score","5.5");

            array.add(obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        }
        return  array;
    }
    public JSONObject getLeadsSummury()
    {
        JSONObject Info = new JSONObject();
        try {
            Info.put("Total","15");
            Info.put("Converted","5");
            Info.put("New","6");
            Info.put("Working","2");
            Info.put("Lost","2");
        } catch (JSONException e) {
            e.printStackTrace();
        }
      return Info;
    }

    public ArrayList<JSONObject> getFollowups()
    {
        ArrayList<JSONObject> followups = new ArrayList<JSONObject>();
        try {
            JSONObject fInfo = new JSONObject();
            fInfo.put("Title","Call to get lead requirements");
            fInfo.put("FollowupDate","Dec 6");
            fInfo.put("Time","10:40 am");
            fInfo.put("Remarks","Get Requirements");
            followups.add(fInfo);
            fInfo = new JSONObject();
            fInfo.put("Title","Meetup with Lead to show listings");
            fInfo.put("FollowupDate","Dec 7");
            fInfo.put("Time","12:00 am");
            fInfo.put("Remarks","Show Listing");
            followups.add(fInfo);
            fInfo = new JSONObject();
            fInfo.put("Title","Call Lead to get feedback");
            fInfo.put("FollowupDate","Dec 8");
            fInfo.put("Time","11:00 am");
            fInfo.put("Remarks","Get Feedback");
            followups.add(fInfo);
            fInfo = new JSONObject();
            fInfo.put("Title","Call Lead to get more information");
            fInfo.put("FollowupDate","Dec 9");
            fInfo.put("Time","1:00 pm");
            fInfo.put("Remarks","Get More Information");
            followups.add(fInfo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return followups;
    }

    public JSONObject getRequirements()
    {

        JSONObject req =new JSONObject();
        try {
            req.put("Type","Sale");
            req.put("Location","Newyork");
            req.put("MinPrice","50000");
            req.put("MaxPrice","80000");
            req.put("Beds","3+ Beds");
            req.put("Baths","2+ Beds");
            req.put("Area","2000+ Sqft");
            req.put("Age","New");
            req.put("PropertyType","Single Family Home");
            req.put("LotSize","All Sizes");
            req.put("Features","Pool,Garage");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return req;
    }
}
