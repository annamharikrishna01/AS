package com.hk.Components;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.LocationManager;
import android.provider.CallLog;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hari on 11/27/13.
 */
public class DeviceUtils {

    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    JSONObject Location = null;
    public static void SendSms(String phoneNumber, String message, final Context context)
    {
        try {

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(context, "SMS Sent!",
                    Toast.LENGTH_LONG).show();
        } catch (Exception e)
        {
            Toast.makeText(context,
                    "SMS faild, please try again later!",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }
    public static void SendEmail(JSONObject mail,String Type,Context context)
    {
        String Email,Subject,Content;
        try{
            Email = mail.getString("Email");
            Subject = mail.getString("Subject");
            Content = mail.getString("Content");
            Intent mailIntent= new Intent(Intent.ACTION_SEND);
            mailIntent.putExtra(Intent.EXTRA_EMAIL,Email);
            mailIntent.putExtra(Intent.EXTRA_SUBJECT,Subject);
            if(Type == "plain/text")
            {
                mailIntent.setType("plain/text");
            }
            mailIntent.putExtra(android.content.Intent.EXTRA_TEXT,Content);
            context.getApplicationContext().startActivity(mailIntent);
        }
        catch (Exception ex)
        {

        }

    }

    public static ArrayList<JSONObject> GetCallLog(ContentResolver resolver,Context context)
    {
        ArrayList<JSONObject> calllog = new ArrayList<JSONObject>();
        Cursor cursor = resolver.query(CallLog.Calls.CONTENT_URI,null, null, null, null);
        cursor.moveToFirst();
        do {
            try{
                JSONObject cl = new JSONObject();
                String nameTemp = cursor.getString(cursor
                        .getColumnIndex(CallLog.Calls.CACHED_NAME));
                if (nameTemp.equals(""))
                    cl.put("Name", "Unknown");
                else
                    cl.put("Name", nameTemp);
                long dateTemp = cursor.getLong(cursor
                        .getColumnIndex(CallLog.Calls.DATE));
                cl.put("Date", dateTemp);
                long number = cursor.getLong(cursor
                        .getColumnIndex(CallLog.Calls.NUMBER));
                cl.put("Number", number);
                long durationTemp = cursor.getLong(cursor
                        .getColumnIndex(CallLog.Calls.DURATION));
                cl.put("Duration", durationTemp);
                long IS_READ = cursor.getLong(cursor
                        .getColumnIndex(CallLog.Calls.IS_READ));
                cl.put("IsRead", IS_READ);
                int typeTemp = cursor.getInt(cursor
                        .getColumnIndex(CallLog.Calls.TYPE));
                cl.put("Type", typeTemp);
                calllog.add(cl);
            }
            catch (Exception ex)
            {

            }
        } while (cursor.moveToNext());

        return  calllog;
    }

    public JSONObject GetLocation(Context context)
    {
        Location = new JSONObject();
        try
        {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        List<String> matchingProviders = locationManager.getAllProviders();
        for (String provider: matchingProviders) {
            android.location.Location location = locationManager.getLastKnownLocation(provider);
            if (location != null) {
                float accuracy = location.getAccuracy();
                Location.put("Lat",location.getLatitude());
                Location.put("Lan",location.getLongitude());
                return Location;
            }
            else
            {
                Location.put("Lat",null);
                Location.put("Lan",null);
                return Location;
            }
          }
        }
        catch (Exception e)
        {}
        return  Location;
    }

    public static  int GetAppVersion(Context context)
    {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            return "";
        }

        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = GetAppVersion(context);
        if (registeredVersion != currentVersion) {

            return "";
        }
        return registrationId;
    }

    private SharedPreferences getGCMPreferences(Context context) {


        return null;
    }

    public boolean CheckPlayService(Activity context)
    {

        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode,context,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {

               //finish();
            }
            return false;
        }
        return true;
    }


}
