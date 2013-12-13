package com.hk.agentsphere;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class StartActivity extends FragmentActivity {



    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "q@q.com:qwerty"
    };
    public static final String EXTRA_EMAIL = "demo@example.com";

    private String Email;
    private String Password;

    private EditText uEmail;
    private EditText uPassword;

    private View uLoginForm;
    private View uLoginStatus;

    private TextView uLoginStatusMessage;


    private PagerAdapter mPagerAdapter;
    ViewPager mViewPager;
    int Position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {

               // invalidateOptionsMenu();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id)
        {
            case R.id.Register:
                mViewPager.setCurrentItem(1);
               return true;
            case R.id.login :
                mViewPager.setCurrentItem(0);
                return true;
            case R.id.ForgotPassword:
                mViewPager.setCurrentItem(2);
                return true;
        }
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        @Override
        public Fragment getItem(int position) {

            return StartHolderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 3;
        }
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
    }




}
