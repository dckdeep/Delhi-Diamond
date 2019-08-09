package com.technotion.delhiDiamond;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.technotion.delhiDiamond.AllFragments.Admindashbord;
import com.technotion.delhiDiamond.AllFragments.AgentRegistration;
import com.technotion.delhiDiamond.sever_classes.Config;
import com.technotion.delhiDiamond.sever_classes.CustomRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainDashBoard extends AppCompatActivity {


    ViewPager viewPager;
    TabLayout tabLayout;
    Dialog joda_panel;
    TextView bhar_panel_text,current_tokens;
    EditText joda_input_amount;
    Button submit_amount_joda_panel,cancel_action;
    ImageButton close_joda_panel;
    Dialog Bahar_panel;
    ImageView close_bahar_panel;
    EditText bahar_input_number;
    EditText bahar_input_amount;
    Button submit_bahar_panel;
    String currentVersion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dash_board);
        tabLayout=findViewById(R.id.tabLayout);
        viewPager= findViewById(R.id.view_pager);
        Bahar_panel= new Dialog(MainDashBoard.this);
        Bahar_panel.setContentView(R.layout.delhidiamond_bahar);
        close_bahar_panel=Bahar_panel.findViewById(R.id.close_bahar_panel);
        bahar_input_number=Bahar_panel.findViewById(R.id.bahar_input_number);
        bahar_input_amount=Bahar_panel.findViewById(R.id.bahar_input_amount);
        submit_bahar_panel=Bahar_panel.findViewById(R.id.submit_bahar_panel);
        bhar_panel_text=Bahar_panel.findViewById(R.id.bhar_panel_text);
        bhar_panel_text.setText("Change Password");

        addtransactions();
        //Fetching email from shared preferences

        //Showing the current logged in email to textview
        MainDashBoard.ViewPagerAdapter viewPagerAdapter = new MainDashBoard.ViewPagerAdapter(getSupportFragmentManager());
     /*   viewPagerAdapter.add(new Ajent_DashBoard(),"DashBoard");

     */


        viewPagerAdapter.add(new Admindashbord(),"DashBoard");
        viewPagerAdapter.add(new AgentRegistration(),"Agents Registration");
        //viewPagerAdapter.add(new OurStory(),"");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


        try {
            currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        new MainDashBoard.GetVersionCode().execute();
    }

    private void addtransactions() {
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
       /* SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Config.TOKENS,"");

        //Saving values to editor
        editor.commit();*/

        String url = "https://delhidiamond.online/index.php/get-current-amount";
        Map<String, String> params = new HashMap<String, String>();

        final String agent_id = sharedPreferences.getString(Config.AGENT_ID,"-1");

        params.put("user_id", agent_id);
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", response.toString());
                try {
                    String closing_amount = response.getString("current_coins");


                    SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                    //Creating editor to store values to shared preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();


                    //Adding values to editor
                    editor.putString(Config.TOKENS,closing_amount);

                    //Saving values to editor
                    editor.commit();
                    String coins = sharedPreferences.getString(Config.TOKENS,"Not Available");
                    current_tokens=findViewById(R.id.current_tokens);
                    current_tokens.setText(coins);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {
                Log.d("Response: ", response.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy( 5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsObjRequest);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        ViewPagerAdapter(FragmentManager fragmentManager){
            super(fragmentManager);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        @Override
        public Fragment getItem(int i) {

            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void add(Fragment fragment, String title ){
            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin_menu, menu);
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
            logout();
            return true;
        }if(id==R.id.action_change){
            change_pass();
            return true;
        }
        if(id==R.id.action_add_coins){
            add_tran();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void change_pass() {

        bahar_input_number.setHint("Old Password");
        bahar_input_amount.setHint("New Password");

        bahar_input_amount.setInputType(InputType.TYPE_CLASS_TEXT);
        bahar_input_number.setInputType(InputType.TYPE_CLASS_TEXT);
        Bahar_panel.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Bahar_panel.setTitle("");
        Bahar_panel.show();
        close_bahar_panel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bahar_panel.dismiss();
            }
        });
        submit_bahar_panel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!bahar_input_amount.getText().toString().equals("")&&!bahar_input_number.getText().toString().equals("")){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainDashBoard.this);
                    alertDialogBuilder.setMessage("Are You Sure Want To Change Password");
                    alertDialogBuilder.setPositiveButton("Sure",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    String old_pass =bahar_input_number.getText().toString();
                                    String new_pass =bahar_input_amount.getText().toString();
                                    change_pass_(old_pass,new_pass,Bahar_panel);
                                    bahar_input_amount.getText().clear();
                                    bahar_input_number.getText().clear();
                                }
                            });
                    alertDialogBuilder.setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    Bahar_panel.dismiss();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }else{
                    invalid_submit();
                }
            }
        });
    }

    private void change_pass_(String old_pass, String new_pass, final Dialog bahar_panel) {
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String URL = "https://delhidiamond.online/index.php/change-password ";
        final Map<String, String> params = new HashMap<String, String>();
        final String agent_id = sharedPreferences.getString(Config.PLAYER_ID, "-1");
        params.put("user_id",agent_id);
        params.put("old_password",old_pass);
        params.put("new_password",new_pass);


        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, URL, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", response.toString());
                String change="";

                try {
                    change=response.getString("response");
                } catch (JSONException e) {
                    e.printStackTrace();
                    try {
                        change=response.getString("error");
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }


                if(change.equals("Password changed Successfully")) {
                    Toast.makeText(MainDashBoard.this, "Password Has Changed", Toast.LENGTH_LONG).show();
                    bahar_panel.dismiss();
                }
                else{
                    bahar_input_number.setBackgroundResource(R.drawable.red_border);
                    Toast.makeText(MainDashBoard.this, "Invalid Old Password ", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {
                Log.d("Response: ", response.toString());

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(MainDashBoard.this);
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy( 5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsObjRequest);
    }
    private void add_tran() {
        joda_panel  = new Dialog(MainDashBoard.this);
        joda_panel.setContentView(R.layout.delhidiamon_joda_panel);
        joda_input_amount=joda_panel.findViewById(R.id.joda_input_amount);
        submit_amount_joda_panel=joda_panel.findViewById(R.id.submit_amount_joda_panel);
        close_joda_panel=joda_panel.findViewById(R.id.close_joda_panel);
        cancel_action=joda_panel.findViewById(R.id.cancel_action);
        joda_panel.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        joda_panel.setTitle("");
        TextView joda_bid= joda_panel.findViewById(R.id.joda_bid);
        joda_bid.setText("ADD Coins");
        joda_panel.show();
        cancel_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joda_panel.dismiss();
            }
        });
        close_joda_panel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joda_panel.dismiss();
            }
        });
        submit_amount_joda_panel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!joda_input_amount.getText().toString().equals("")){
                    String getcoins= String.valueOf(joda_input_amount.getText());
                    Add_coins(getcoins);
                }else {
                    invalid_submit();
                }
            }
        });
    }

    private void Add_coins(String getcoins) {
      final ProgressDialog  progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("LOADING...");
        progressDialog.show();
        String url = "https://delhidiamond.online/index.php/create-owner-coins";
        Map<String, String> params = new HashMap<String, String>();
        params.put("coins_transfer",getcoins );
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String user_id = sharedPreferences.getString(Config.AGENT_ID,"Not Available");

        params.put("user_id", user_id);
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", response.toString());
                try {
                    /*JSONObject jsonArray1 = response.getJSONObject("cards");
                    */String coins =response.getString("user_current_coins");
/*

                    SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                    //Creating editor to store values to shared preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();


                    //Adding values to editor
                    editor.putString(Config.TOKENS, coins);
                    editor.commit();


*/
                    joda_panel.dismiss();
                    progressDialog.dismiss();
                    Intent intent = new Intent(MainDashBoard.this,MainDashBoard.class);
                    startActivity(intent);
                Toast.makeText(MainDashBoard.this,coins,Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
/*
                Toast.makeText(MainDashBoard.this,"Coins Has been Added to your Account",Toast.LENGTH_LONG).show();
*/
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {
                Log.d("Response: ", response.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(MainDashBoard.this);
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy( 5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsObjRequest);


    }


    private void invalid_submit() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainDashBoard.this);
        alertDialogBuilder.setMessage("Invalid Entry Please Re-submit again");
        alertDialogBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void logout() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to logout?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        //Getting out sharedpreferences
                        SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        //Getting editor
                        SharedPreferences.Editor editor = preferences.edit();

                        //Puting the value false for loggedin
                        editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);

                        //Putting blank value to email
                        editor.putString(Config.USER_SHARED_PREF, "");

                        //Saving the sharedpreferences
                        editor.commit();

                        //Starting login activity
                        Intent intent = new Intent(MainDashBoard.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    class GetVersionCode extends AsyncTask<Void, String, String> {

        @Override

        protected String doInBackground(Void... voids) {

            String newVersion = null;

            try {
                Document document = Jsoup.connect("https://play.google.com/store/apps/details?id=" + MainDashBoard.this.getPackageName()  + "&hl=en")
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get();
                if (document != null) {
                    Elements element = document.getElementsContainingOwnText("Current Version");
                    for (Element ele : element) {
                        if (ele.siblingElements() != null) {
                            Elements sibElemets = ele.siblingElements();
                            for (Element sibElemet : sibElemets) {
                                newVersion = sibElemet.text();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newVersion;

        }


        @Override

        protected void onPostExecute(String onlineVersion) {

            super.onPostExecute(onlineVersion);

            if (onlineVersion != null && !onlineVersion.isEmpty()) {

                if (Float.valueOf(currentVersion) < Float.valueOf(onlineVersion)) {
                    //show anything
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainDashBoard.this);
                    alertDialogBuilder.setMessage("Update Latest Version");
                    alertDialogBuilder.setPositiveButton("Update",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {

                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" +MainDashBoard.this.getPackageName())));


                                }
                            });

                    alertDialogBuilder.setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    finishAffinity();
                                }
                            });

                    //Showing the alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                }

            }

            Log.d("update", "Current version " + currentVersion + "playstore version " + onlineVersion);

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity(); // or finish();
    }
}