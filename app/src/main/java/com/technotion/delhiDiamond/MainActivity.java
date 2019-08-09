package com.technotion.delhiDiamond;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.crashlytics.android.Crashlytics;
import com.technotion.delhiDiamond.sever_classes.Config;
import com.technotion.delhiDiamond.sever_classes.CustomRequest;

import io.fabric.sdk.android.Fabric;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    Button login;
    EditText userid_textinput,passtextinput;
    private boolean loggedIn = false;
    RelativeLayout email_layout,pass_layout;
    TextView if_userid_is_wrong,if_pass_is_wrong;
    ProgressDialog  progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        if_userid_is_wrong =findViewById(R.id.if_userid_is_wrong);
        if_pass_is_wrong=findViewById(R.id.if_pass_is_wrong);
        email_layout= findViewById(R.id.email_layout);
        pass_layout = findViewById(R.id.pass_layout);
        userid_textinput=findViewById(R.id.userid_textinput);
        passtextinput=findViewById(R.id.passtextinput);
        login =findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userid= String.valueOf(userid_textinput.getText());
                String pass= String.valueOf(passtextinput.getText());

                login(userid,pass);

/*
                if(userid.equals("12345")&&pass.equals("12345")){
                Intent intent= new Intent(MainActivity.this,AjentsDashBoard.class);
                startActivity(intent);}
                if(userid.equals("000")&&pass.equals("000")){
                    Intent intent= new Intent(MainActivity.this,Home.class);
                    startActivity(intent);
                }*/
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);

        //If we will get true
        if(loggedIn){
            //We will start the Profile Activity
            String user_type = sharedPreferences.getString(Config.USER_STATUS,"Not Available");
            if(user_type.equals("admin")){
                Intent intent = new Intent(MainActivity.this, MainDashBoard.class);
                startActivity(intent);
                finish();}
            if(user_type.equals("agent")){
            Intent intent = new Intent(MainActivity.this, AjentsDashBoard.class);
            startActivity(intent);
            finish();}
            if(user_type.equals("player")){
                Intent intent = new Intent(MainActivity.this,Home.class);
                startActivity(intent);
                finish();
            }
        }
    }


    public void login(final String user, final String pass){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("CHECKING ALL CREDENTIALS...");
        progressDialog.show();
        String url = "https://delhidiamond.online/index.php/user-login";
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", user);
        params.put("password", pass);

        String error="";
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", response.toString());
                progressDialog.dismiss();
                /*String x = "";
                String s = String.valueOf(response);
                Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();
                String[] parts = s.split(x);*/
                try {

                    String json_status = response.getString("user_type");
                    String user_coins = response.getString("user_coins");

                   // Toast.makeText(MainActivity.this,json_status,Toast.LENGTH_LONG).show();
                    if (json_status.equalsIgnoreCase("admin")) {
                        String agent_id = response.getString("ID");

                        SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                        //Creating editor to store values to shared preferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();


                        //Adding values to editor
                        editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                        editor.putString(Config.USER_SHARED_PREF, user);
                        editor.putString(Config.AGENT_ID,agent_id);
                        editor.putString(Config.USER_STATUS,json_status);
                        //Saving values to editor
                        editor.commit();

                        Intent intent = new Intent(MainActivity.this,MainDashBoard.class);
                        startActivity(intent);
                        finish();
                    }
                    if (json_status.equalsIgnoreCase("agent")) {
                        String agent_id = response.getString("ID");
                        String dealer_id = response.getString("agent_id");

                        SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                        //Creating editor to store values to shared preferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();


                        //Adding values to editor
                        editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                        editor.putString(Config.USER_SHARED_PREF, user);
                        editor.putString(Config.AGENT_ID,dealer_id);
                        editor.putString(Config.PLAYER_ID,agent_id);
                        editor.putString(Config.USER_STATUS,json_status);
                        editor.putString(Config.TOKENS,user_coins);
                        //Saving values to editor
                        editor.commit();

                        Intent intent = new Intent(MainActivity.this,AjentsDashBoard.class);
                        startActivity(intent);
                        finish();
                    }

                    if(json_status.equalsIgnoreCase("player")){
                        String player_id = response.getString("ID");
                        String dealer_id = response.getString("agent_id");



                        SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                        //Creating editor to store values to shared preferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();


                        //Adding values to editor

                        editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                        editor.putString(Config.USER_SHARED_PREF, user);
                        editor.putString(Config.AGENT_ID,player_id);
                        editor.putString(Config.DEALER_ID,dealer_id);
                        editor.putString(Config.TOKENS,user_coins);
                        editor.putString(Config.USER_STATUS,json_status);
                        editor.putString(Config.PLAYER_ID,player_id);
                       //Saving values to editor
                        editor.commit();




                        Intent intent = new Intent(MainActivity.this,Home.class);
                        startActivity(intent);
                        finish();
                    }


                } catch (JSONException e) {
/*
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();

                 */  String check_userid="Error:"+e.getMessage();
                    progressDialog.dismiss();
                    if(check_userid.equals("Error:No value for user_type")){
                        email_layout.setBackgroundResource(R.drawable.red_border);
                        if_userid_is_wrong.setText("Invalid Account Holder's Name");
                        pass_layout.setBackgroundResource(R.drawable.red_border);
                        if_pass_is_wrong.setText("Invalid Password");

                    }

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


}
