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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.technotion.delhiDiamond.Adapters.patti_Adapter;
import com.technotion.delhiDiamond.sever_classes.Config;
import com.technotion.delhiDiamond.sever_classes.CustomRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class patti_commission extends AppCompatActivity {

    int get=0;
    Spinner  shortcuts_spinner;
    String jantri_array[] ={"--Select Game--","13patti","Chidiya Kabutar","Lucky7","Delhi Diamond","Faridabad","Ghaziabad","Gali","Desawar"};
    ArrayAdapter shortcuts;
    int view_list=0;
    ArrayList<String> myImageList = new ArrayList<>();
    ArrayList<String> users_id = new ArrayList<>();
    ArrayList<String> users_coins = new ArrayList<>();
    ArrayList<String> patti_id = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patti_commission);
        Bundle bundle = getIntent().getExtras();
        get=bundle.getInt("open_patti");
        shortcuts_spinner=findViewById(R.id.planets_spinner);
        shortcuts_spinner.setEnabled(true);
        shortcuts_spinner.setVisibility(View.VISIBLE);
        Config.view_list_admin=get;
        if(get==2){
            shortcuts_spinner.setEnabled(false);
            shortcuts_spinner.setVisibility(View.INVISIBLE);
            userlist();
        }
        if(get==1){
            shortcuts_spinner.setEnabled(true);
            shortcuts_spinner.setVisibility(View.VISIBLE);
            shortcuts = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,jantri_array);
            shortcuts_spinner.setAdapter(shortcuts);
            shortcuts_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    switch (position){
                        case 0:
                            break;
                        case 1:
                            view_list=1;
                            userlist();
                            break;
                        case 2:
                            view_list=2;
                            userlist();
                            break;
                        case 3:
                            view_list=3;
                            userlist();
                            break;
                        case 4:
                            view_list=4;
                            userlist();
                            break;
                        case 5:
                            view_list=5;
                            userlist();
                            break;
                        case 6:
                            view_list=6;
                            userlist();
                            break;
                        case 7:
                            view_list=7;
                            userlist();
                            break;
                        case 8:
                            view_list=8;
                            userlist();
                            break;

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

    }

    private void userlist() {
        myImageList.clear();
        users_id.clear();
        users_coins.clear();
        patti_id.clear();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("LOADING...");
        progressDialog.show();
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String user_type = sharedPreferences.getString(Config.USER_STATUS,"Not Available");
        String url ="";
        Map<String, String> params = new HashMap<String, String>();
        String agent_id="";
           if(get==2) {
                url = "https://delhidiamond.online/index.php/get-agent-patti-list";
               agent_id = "1";

            }if(get==1) {
            url = "https://delhidiamond.online/index.php/get-agent-commision-list";
            agent_id = "1";
            Config.game_type_admin=view_list;
            String game_type= String.valueOf(view_list);
            params.put("game_type", game_type);
        }
        params.put("user_id", agent_id);
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", response.toString());
                try {
                  if(get==2) {
                      JSONArray jsonArray = response.getJSONArray("patti_id");

                      for (int i = 0; i < jsonArray.length(); i++) {
                          JSONObject employee = jsonArray.getJSONObject(i);

                          String id = employee.getString("id");
                          /*                        String username = employee.getString("username");*/
                          String user_coins = employee.getString("partnership");
                          String dealer_id = employee.getString("agent_id");
                          myImageList.add(employee.getString("username"));
                          patti_id.add(id);
                          users_id.add(dealer_id);
                          users_coins.add(user_coins);

                      }
                  }
                    if(get==1) {
                        JSONArray jsonArray = response.getJSONArray("commision_list");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject employee = jsonArray.getJSONObject(i);

                            String id = employee.getString("id");
                            /*                        String username = employee.getString("username");*/
                            String user_coins = employee.getString("commision");
                            String dealer_id = employee.getString("agent_id");
                            myImageList.add(employee.getString("username"));
                            patti_id.add(id);
                            users_id.add(dealer_id);
                            users_coins.add(user_coins);

                        }
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
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy( 5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsObjRequest);
    }

    private void usersendpoints(){
        RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(),1);
        RecyclerView vieww= findViewById(R.id.user_points_working);
        vieww.setHasFixedSize(true);
        vieww.setLayoutManager(manager);
        Context context =patti_commission.this;
        Collections.reverse(myImageList);
        Collections.reverse(users_id);
        Collections.reverse(users_coins);
        Collections.reverse(patti_id);
        patti_Adapter attributes= new patti_Adapter(myImageList,patti_id,users_coins,users_id,context);
        vieww.setAdapter(attributes);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            String user_type = sharedPreferences.getString(Config.USER_STATUS,"Not Available");

                Intent intent = new Intent(patti_commission.this, ajent_users_list.class);
                startActivity(intent);
                finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
