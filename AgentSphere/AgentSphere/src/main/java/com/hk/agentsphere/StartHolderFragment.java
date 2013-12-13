package com.hk.agentsphere;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Hari on 12/4/13.
 */
public class StartHolderFragment extends Fragment {

    public static final String ARG_SECTION_NUMBER = "section_number";
    private int mPageNumber;
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "q@q.com:qwerty"
    };

    private String Email;
    private String Password;

    private EditText uEmail;
    private EditText uPassword;

    private View uLoginForm;
    private View uLoginStatus;
    private UserLogin authTask = null;
    private UserRegistration regTask = null;
    private UserForgotPassword forTask = null;
    private TextView uLoginStatusMessage;

    public static  StartHolderFragment newInstance(int sectionNumber) {

        StartHolderFragment fragment = new StartHolderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public StartHolderFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_SECTION_NUMBER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        switch(getArguments().getInt(ARG_SECTION_NUMBER))
        {
            case 0:
                View rootView = inflater.inflate(R.layout.fragment_start, container, false);
                SetLogIn(rootView);
                Button lt = (Button)rootView.findViewById(R.id.SignIn);
                lt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ValidateLogin();

                    }
                });

                return rootView;
            case 1:
                View view1 = inflater.inflate(R.layout.fragment_register,container, false);
                return view1;
            case 2:
                View view2 = inflater.inflate(R.layout.fragment_forgot,container, false);
                return view2;
            default:
                View dview = inflater.inflate(R.layout.fragment_start, container, false);
                return dview;

        }

    }

    public int getPageNumber() {
        return mPageNumber;
    }

    public void ValidateLogin()
    {
        if (authTask != null) {
            return;
        }
        uEmail.setError(null);
        uPassword.setError(null);

        Email = uEmail.getText().toString();
        Password = uPassword.getText().toString();
        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(Password)) {
            uPassword.setError(getString(R.string.error_field_required));
            focusView = uPassword;
            cancel = true;
        } else if (Password.length() < 4) {
            uPassword.setError(getString(R.string.error_invalid_password));
            focusView = uPassword;
            cancel = true;
        }

        if (TextUtils.isEmpty(Email)) {
            uEmail.setError(getString(R.string.error_field_required));
            focusView = uEmail;
            cancel = true;
        } else if (!Email.contains("@")) {
            uEmail.setError(getString(R.string.error_invalid_email));
            focusView = uEmail;
            cancel = true;
        }

        if (cancel) {

            focusView.requestFocus();

        } else {

            //showProgress(true);

            String url = "http://192.168.40.165/api/login";

            authTask = new UserLogin();
            authTask.execute((url));
        }

    }

    public void ValidateRegister()
    {

    }
    public void ValidateForgot()
    {}

    public class UserLogin extends AsyncTask<String,Integer,Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {

            String pass = Email+":"+Password;
            //HttpClient httpclient = new DefaultHttpClient();

            // Encrypt crypt = new Encrypt();
            // Prepare a request object
            // HttpGet httpget = new HttpGet(params[0]);
            //httpget.setHeader("Accept", "application/json");
            // httpget.setHeader("Authorization", crypt.BasicBase64(pass));
            // Execute the request
            //HttpResponse response;
            // try {
            //    response = httpclient.execute(httpget);
            // Examine the response status
            //    Log.i("Praeda", response.getStatusLine().toString());

            // Get hold of the response entity
            //    HttpEntity entity = response.getEntity();
            // If the response does not enclose an entity, there is no need
            // to worry about connection release

            //  if (entity != null) {

            // A Simple JSON Response Read
            //    InputStream instream = entity.getContent();
            //     String result= convertStreamToString(instream);
            // now you have the string representation of the HTML request
            //     instream.close();
            // }


            // } catch (Exception e) {}
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            authTask = null;
            //showProgress(false);

            if (result) {
                Intent dash = new Intent(getActivity().getApplicationContext(),DashboardActivity.class);
                startActivity(dash);
                getActivity().finish();
            } else {
                uPassword.setError(getString(R.string.error_incorrect_password));
                uPassword.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            authTask = null;
            //showProgress(false);
        }


    }


    public class UserRegistration extends  AsyncTask<String ,Integer,Boolean>{
        @Override
        protected Boolean doInBackground(String... params) {
            return null;
        }
    }


    public class UserForgotPassword extends AsyncTask<String,Intent,Boolean>{
        @Override
        protected Boolean doInBackground(String... params) {
            return null;
        }
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            uLoginStatus.setVisibility(View.VISIBLE);
            uLoginStatus.animate()
                    .setDuration(shortAnimTime)
                    .alpha(show ? 1 : 0)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            uLoginStatus.setVisibility(show ? View.VISIBLE : View.GONE);
                        }
                    });
        } else {

            uLoginStatus.setVisibility(show ? View.VISIBLE : View.GONE);

        }
    }

    private String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public void SetLogIn(View v)
    {
        uEmail =(EditText)v.findViewById(R.id.LoginEmail);
        uEmail.setText(Email);
        uPassword = (EditText)v.findViewById(R.id.LoginPassword);
        uPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.SignIn || id == EditorInfo.IME_NULL) {
                    ValidateLogin();
                    return true;
                }
                return false;
            }
        });
        //  uLoginStatus= findViewById(R.id.login_progress);
        //  uLoginStatusMessage=(TextView)findViewById(R.id.progressMesage);
    }

    public void SetRegistration(View v)
    {}

    public void SetForgot(View v)
    {}

}
