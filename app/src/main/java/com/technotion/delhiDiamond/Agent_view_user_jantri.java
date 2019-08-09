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
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.technotion.delhiDiamond.Adapters.viewJantri_Adapter;
import com.technotion.delhiDiamond.sever_classes.Config;
import com.technotion.delhiDiamond.sever_classes.CustomRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Agent_view_user_jantri extends AppCompatActivity {
  RecyclerView view_jantri_andar_recycler,view_jantri_bhar_recycler,view_jantri_number_recycler;
  ArrayList<String> mIconText = new ArrayList<>();
  ArrayList<String> mIconText1 = new ArrayList<>();
  ArrayList<String> mIconText2 = new ArrayList<>();
  ArrayList<String> mIconText_amount = new ArrayList<>();
  ArrayList<String> mIconText1_amount = new ArrayList<>();
  ArrayList<String> mIconText2_amount = new ArrayList<>();
  ProgressDialog progressDialog;
  TextView total_jantri,logos_name;
  int total=0;
  int get,player_id;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_agent_view_user_jantri);
    RecyclerView.LayoutManager manager = new GridLayoutManager(Agent_view_user_jantri.this,5);
    view_jantri_number_recycler=findViewById(R.id.view_jantri_numbers_recycler);
    view_jantri_number_recycler.setHasFixedSize(true);
    view_jantri_number_recycler.setLayoutManager(manager);
    Bundle bundle = getIntent().getExtras();
    get  = bundle.getInt("open");
    player_id=bundle.getInt("id");
    total_jantri=findViewById(R.id.total_jantri);
    logos_name=findViewById(R.id.logos_name);
    if(get==4){
      logos_name.setText("Delhi Diamond");
    }
    if(get==5){

      logos_name.setText("Faridabad");
    }
    if(get==6){

      logos_name.setText("Ghaziabad");
    }
    if(get==7){

      logos_name.setText("GALI");
    }
    if(get==8){

      logos_name.setText("Desawar");
    }

    RecyclerView.LayoutManager manager1 = new GridLayoutManager(Agent_view_user_jantri.this,5);
    view_jantri_andar_recycler=findViewById(R.id.view_jantri_andar_recycler);
    view_jantri_andar_recycler.setHasFixedSize(true);
    view_jantri_andar_recycler.setLayoutManager(manager1);
    RecyclerView.LayoutManager manager2 = new GridLayoutManager(Agent_view_user_jantri.this,5);
    view_jantri_bhar_recycler=findViewById(R.id.view_jantri_bhar_recycler);
    view_jantri_bhar_recycler.setHasFixedSize(true);
    view_jantri_bhar_recycler.setLayoutManager(manager2);
    add_number();
  }
  private void add_number() {
    progressDialog = new ProgressDialog(this);
    progressDialog.setMessage("LOADING...");
    progressDialog.show();
    Map<String, String> params = new HashMap<String, String>();
    String url="";

    if(Config.final_jantri_check==0) {
      url = "https://delhidiamond.online/index.php/get-delhi-diamond-user-bids ";
      total = 0;
/*
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String agent_id = sharedPreferences.getString(Config.AGENT_ID,"-1");
*/
      SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
      String id = String.valueOf(player_id);
      /*  final String game1_id = sharedPreferences.getString(Config.G4_ID,"-1");*/
      String game_type = String.valueOf(get);
      /*  params.put("game_id",game1_id);*/
      params.put("game_type", game_type);
      params.put("player_id", id);
    }
    if(Config.final_jantri_check==1){

      url  = "https://delhidiamond.online/index.php/get-coins-report  ";
      total = 0;
/*
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String agent_id = sharedPreferences.getString(Config.AGENT_ID,"-1");
*/
      SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
      String id = String.valueOf(player_id);
      /*  final String game1_id = sharedPreferences.getString(Config.G4_ID,"-1");*/
      String game_type = String.valueOf(get);
      /*  params.put("game_id",game1_id);*/
      params.put("game_type", game_type);
      params.put("user_id", id);

    }

    CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

      @Override
      public void onResponse(JSONObject response) {
        Log.d("Response: ", response.toString());
        progressDialog.dismiss();
        /* Toast.makeText(Lucky7.this,response.toString(),Toast.LENGTH_LONG).show();*/
        try {
          JSONObject get_numbers_amount = response.getJSONObject("bid_amount");
          mIconText_amount.clear();
          mIconText.clear();
          for(int i=1; i<101;i++){
            String xyz = String.valueOf(i);
            mIconText.add(xyz);
            String get_bid_amount_on_card = get_numbers_amount.getString(xyz);
            int x= Integer.parseInt(get_bid_amount_on_card);
            total=total+x;
            if(!get_bid_amount_on_card.equals("0")) {
              mIconText_amount.add(get_bid_amount_on_card);

            }else{
              mIconText_amount.add("-");
            }
          }

          viewJantri_Adapter attributes = new viewJantri_Adapter(mIconText,mIconText_amount, Agent_view_user_jantri.this);
          view_jantri_number_recycler.setAdapter(attributes);


          JSONObject get_andar_amount = response.getJSONObject("andar_haraf_amount");
          mIconText1.clear();
          mIconText1_amount.clear();
          for(int i=1; i<11; i++){
            String xyz = String.valueOf(i);
            if(i==10){
              mIconText1.add("0");
            }else{
              mIconText1.add(xyz);
            }

            String get_bid_amount_on_card = get_andar_amount.getString(xyz);
            int x= Integer.parseInt(get_bid_amount_on_card);
            total=total+x;
            if(!get_bid_amount_on_card.equals("0")) {
              mIconText1_amount.add(get_bid_amount_on_card);
            }else{
              mIconText1_amount.add("-");
            }
          }
          viewJantri_Adapter attributes1 = new viewJantri_Adapter(mIconText1,mIconText1_amount, Agent_view_user_jantri.this);
          view_jantri_andar_recycler.setAdapter(attributes1);

          JSONObject get_bahar_amount = response.getJSONObject("bahar_haraf_amount");
          mIconText2.clear();
          mIconText2_amount.clear();
          for(int i=1; i<11; i++){
            String xyz = String.valueOf(i);

            if(i==10){
              mIconText2.add("0");
            }else{
              mIconText2.add(xyz);
            }
            String get_bid_amount_on_card = get_bahar_amount.getString(xyz);
            int x= Integer.parseInt(get_bid_amount_on_card);
            total=total+x;
            if(!get_bid_amount_on_card.equals("0")) {
              mIconText2_amount.add(get_bid_amount_on_card);
            }else{
              mIconText2_amount.add("-");
            }   }
          viewJantri_Adapter attributes2 = new viewJantri_Adapter(mIconText2,mIconText2_amount, Agent_view_user_jantri.this);
          view_jantri_bhar_recycler.setAdapter(attributes2);
          String total_bets = String.valueOf(total);
          total_jantri.setText(total_bets);

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
    jsObjRequest.setRetryPolicy(new DefaultRetryPolicy( 5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    RequestQueue requestQueue = Volley.newRequestQueue(this);
    requestQueue.add(jsObjRequest);
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK ) {
      Intent intent = new Intent(Agent_view_user_jantri.this, agent_view_users_jantri.class);
      intent.putExtra("open",get);
      startActivity(intent);
      finish();
    }
    return super.onKeyDown(keyCode, event);
  }
}
