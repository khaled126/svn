package com.example.mohsher.drsdemo;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class NavActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);

        SharedPreferences settings = getSharedPreferences("settings", 0);
        boolean firstStart = settings.getBoolean("firstStart", true);

        if(firstStart) {
            //do something 1st time only.
            demoAsyncTask task = new demoAsyncTask();
            task.execute();

            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("firstStart", false);
            editor.commit();
        }

        //Set the initial fragment
        HomeFragment homeFragment = new HomeFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();

        //Replace XML fragment container with created Home Fragment
        fragmentTransaction.replace(R.id.fragment_container, homeFragment, "HOME");
        fragmentTransaction.commit();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //App bar text
        getSupportActionBar().setTitle("Home");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            //App bar text
            getSupportActionBar().setTitle("HOME");

            //Set the Home fragment
            HomeFragment homeFragment = new HomeFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();

            //Replace XML fragment container with created Home Fragment
            fragmentTransaction.replace(R.id.fragment_container, homeFragment, "HOME");
            fragmentTransaction.commit();

        } else if (id == R.id.nav_appointments) {

            //App bar text
            getSupportActionBar().setTitle("APPOINTMENTS");

            //Set the Appointment fragment
            AppointmentsFragment appointmentsFragment = new AppointmentsFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();

            //Replace XML fragment container with created Appointments Fragment
            fragmentTransaction.replace(R.id.fragment_container, appointmentsFragment, "APPOINTMENTS");
            fragmentTransaction.commit();

        } else if (id == R.id.nav_contact) {

            //App bar text
            getSupportActionBar().setTitle("CONTACT");

            //Set the Appointment fragment
            ContactFragment contactFragment = new ContactFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();

            //Replace XML fragment container with created Contact Fragment
            fragmentTransaction.replace(R.id.fragment_container, contactFragment, "CONTACT");
            fragmentTransaction.commit();

        } else if (id == R.id.nav_feedback) {

            //App bar text
            getSupportActionBar().setTitle("SEND FEEDBACK");

            //Set the Appointment fragment
            FeedbackFragment feedbackFragment = new FeedbackFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();

            //Replace XML fragment container with created Contact Fragment
            fragmentTransaction.replace(R.id.fragment_container, feedbackFragment, "FEEDBACK");
            fragmentTransaction.commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /********************************** Async Task **************************************/
    private class demoAsyncTask extends AsyncTask {
        public static final String PREFS_NAME = "MyPrefsFile";
        /***
         *
         * @param inputStream Server request in the form of bits stream
         * @return JSON as string to be used in application
         * @throws IOException
         */
        private String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }

        @Override
        protected Void doInBackground(Object[] params) {

            SharedPreferences userData = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = userData.edit();
            //create Url
            URL url = null;
            try {
                //url = new URL("http://192.168.1.10:8080/send");
                url = new URL("https://protected-sierra-68087.herokuapp.com/send");

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            String jsonResponce = "";

            //makeing HTTP request
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;

            //establish connection
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type","application/json");
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.connect();

                JSONObject jsonParam = new JSONObject();
                jsonParam.put("message", "Registration successful.");
                jsonParam.put("registrationId", userData.getString("registrationId", ""));
                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                out.write(jsonParam.toString());
                out.close();

                //response as bit stream
                inputStream = urlConnection.getInputStream();
                jsonResponce = readFromStream(inputStream);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    // function must handle java.io.IOException here
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            //Parse JSON
            try {
                JSONArray rootJson = new JSONArray(jsonResponce);
                if(rootJson.length() > 0)
                {
                    for(int i = 0 ; i < rootJson.length() ; i++)
                    {
                        JSONObject object = rootJson.getJSONObject(i);

                        String result = object.getString("result");
                        String message = object.getString("message");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
