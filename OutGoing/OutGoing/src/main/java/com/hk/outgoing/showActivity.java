package com.hk.outgoing;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.util.List;

public class showActivity extends Activity {

    private ActivityManager mActivityManager;
    private boolean mDismissed = false;

    private static final int MSG_ID_CHECK_TOP_ACTIVITY = 1;
    private static final long DELAY_INTERVAL = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {


            // TODO Auto-generated method stub

            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

            mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            mHandler.sendEmptyMessageDelayed(MSG_ID_CHECK_TOP_ACTIVITY,
                    DELAY_INTERVAL);

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
            setContentView(R.layout.screen);
            super.onCreate(savedInstanceState);
        }
        catch (Exception e) {

            e.printStackTrace();
        }

    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == MSG_ID_CHECK_TOP_ACTIVITY && !mDismissed) {
                List<ActivityManager.RunningTaskInfo> tasks = mActivityManager
                        .getRunningTasks(1);
                String topActivityName = tasks.get(0).topActivity
                        .getClassName();
                if (!topActivityName.equals(showActivity.this
                        .getComponentName().getClassName())) {
                    // Try to show on top until user dismiss this activity
                    Intent i = new Intent();
                    i.setClass(getApplicationContext(), showActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
                    startActivity(i);
                }
                sendEmptyMessageDelayed(MSG_ID_CHECK_TOP_ACTIVITY,
                        DELAY_INTERVAL);
            }
        };
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.show, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
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
            View rootView = inflater.inflate(R.layout.fragment_show, container, false);
            return rootView;
        }
    }

}
