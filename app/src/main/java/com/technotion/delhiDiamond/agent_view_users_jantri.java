package com.technotion.delhiDiamond;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.technotion.delhiDiamond.Adapters.Ajent_user_points_working;
import com.technotion.delhiDiamond.sever_classes.Config;
import com.technotion.delhiDiamond.sever_classes.CustomRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class agent_view_users_jantri extends AppCompatActivity {
  ArrayList<String> myImageList = new ArrayList<>();
  ArrayList<String> users_id = new ArrayList<>();
  ArrayList<String> users_coins = new ArrayList<>();
  int get=0;
  Button full_jantri;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_agent_view_users_jantri);
    Bundle bundle = getIntent().getExtras();
    get  = bundle.getInt("open");
    Config.get_game_type=get;
    full_jantri=findViewById(R.id.full_jantri);
    full_jantri.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(agent_view_users_jantri.this,Agent_view_user_jantri.class);
        intent.putExtra("open",get);
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String user = sharedPreferences.getString(Config.PLAYER_ID,"Not Available");
        int play_id = Integer.parseInt(user);
        intent.putExtra("id",play_id);
        Config.final_jantri_check=1;
        startActivity(intent);
      }
    });
    add_no_of_users();
  }
  private void add_no_of_users() {
    final ProgressDialog progressDialog = new ProgressDialog(this);
    progressDialog.setMessage("LOADING...");
    progressDialog.show();
    SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
    final String user_type = sharedPreferences.getString(Config.USER_STATUS,"Not Available");
    String url ="";
    Map<String, String> params = new HashMap<String, String>();
    String agent_id="";

    if(user_type.equals("agent")){
      url = "https://delhidiamond.online/index.php/active-users-game_type ";
      agent_id = sharedPreferences.getString(Config.PLAYER_ID,"-1");

    }
    String game = String.valueOf(get);
    params.put("user_id", agent_id);
    params.put("game_type",game);

    CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

      @Override
      public void onResponse(JSONObject response) {
        Log.d("Response: ", response.toString());
        try {
          JSONArray jsonArray = response.getJSONArray("response");

          for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject employee = jsonArray.getJSONObject(i);
            /* if(user_type.equals("admin")) {
             *//*    if (i != 0) {
                               String id = employee.getString("id");
                               *//**//*                        String username = employee.getString("username");*//**//*
                               String user_coins = employee.getString("user_coins");
                               myImageList.add(employee.getString("username"));
                               users_id.add(id);
                               users_coins.add(user_coins);
                           }
                       }else{*//*
                           String id = employee.getString("id");
                           *//*                        String username = employee.getString("username");*//*
                           String user_coins = employee.getString("user_coins");
                           myImageList.add(employee.getString("username"));
                           users_id.add(id);
                           users_coins.add(user_coins);
                       }else {*/
            String id = employee.getString("id");
            /*                        String username = employee.getString("username");*/
            String user_coins = employee.getString("total_amount");
            myImageList.add(employee.getString("username"));
            users_id.add(id);
            users_coins.add(user_coins);

          }
          progressDialog.dismiss();
          usersendpoints();

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
    jsObjRequest.setRetryPolicy(new DefaultRetryPolicy( 5000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    requestQueue.add(jsObjRequest);
  }

  private void usersendpoints(){
    RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(),1);
    RecyclerView vieww= findViewById(R.id.user_points_working);
    vieww.setHasFixedSize(true);
    vieww.setLayoutManager(manager);
    Context context =agent_view_users_jantri.this;
    Collections.reverse(myImageList);
    Collections.reverse(users_id);
    Collections.reverse(users_coins);

    Ajent_user_points_working attributes= new Ajent_user_points_working(myImageList,users_id,users_coins,context);
    Config.check=1;
    vieww.setAdapter(attributes);
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK ) {

      Intent intent = new Intent(agent_view_users_jantri.this, AjentsDashBoard.class);
      startActivity(intent);
      finish();}

    return super.onKeyDown(keyCode, event);
  }
}
