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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.technotion.delhiDiamond.Adapters.game_sum;
import com.technotion.delhiDiamond.sever_classes.Config;
import com.technotion.delhiDiamond.sever_classes.CustomRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Game_Summary extends AppCompatActivity {
    ArrayList<Integer> card_open =new ArrayList<>();
    ArrayList<String> amount =new ArrayList<>();
    ArrayList<String> time_ =new ArrayList<>();
    ArrayList<Integer> display_card =new ArrayList<>();
    ArrayList<String> display_num =new ArrayList<>();

    String time ="";
    int get;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game__summary);
        Bundle bundle = getIntent().getExtras();
        get  = bundle.getInt("game_ids");
        time = bundle.getString("time");
        if(Config.game_report_game_type==1){
            opencard_image();
        }if(Config.game_report_game_type==2){
            second_game_card_image();
        }
        sumry();
    }

    private void opencard_image(){
        card_open.clear();
        card_open.add(R.drawable.card1);

        card_open.add(R.drawable.card2);

        card_open.add(R.drawable.card3);

        card_open.add(R.drawable.card4);

        card_open.add(R.drawable.card5);

        card_open.add(R.drawable.card6);

        card_open.add(R.drawable.card7);

        card_open.add(R.drawable.card8);

        card_open.add(R.drawable.card9);

        card_open.add(R.drawable.card10);

        card_open.add(R.drawable.card11);

        card_open.add(R.drawable.card12);

        card_open.add(R.drawable.card13);






    }
    private void second_game_card_image() {
        card_open.clear();
        card_open.add(R.drawable.chidiyacard___1);


        card_open.add(R.drawable.chidiyacard___2);


        card_open.add(R.drawable.chidiyacard___3);

        card_open.add(R.drawable.chidiyacard___4);


        card_open.add(R.drawable.chidiyacard___5);


        card_open.add(R.drawable.chidiyacard___6);


        card_open.add(R.drawable.chidiyacard___7);


        card_open.add(R.drawable.chidiyacard___8);


        card_open.add(R.drawable.chidiyacard___9);


        card_open.add(R.drawable.chidiyacard___10);


        card_open.add(R.drawable.chidiyacard___11);


        card_open.add(R.drawable.chidiyacard___12);


    }


    private void sumry() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("LOADING...");
        progressDialog.show();
        String url = "https://delhidiamond.online/index.php/view-game-bids ";
        Map<String, String> params = new HashMap<String, String>();

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String agent_id = sharedPreferences.getString(Config.PLAYER_ID,"-1");

        params.put("user_id", agent_id);

        String game_type= String.valueOf(Config.game_report_game_type);
       /* if(view_list==1){

        }
        if(view_list==2){
            second_game_card_image();
        }*/
       String g_id= String.valueOf(get);
        params.put("game_id", g_id);

        params.put("game_type", game_type);
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", response.toString());
                progressDialog.dismiss();
                Toast.makeText(Game_Summary.this,response.toString(), Toast.LENGTH_LONG).show();

                JSONArray jsonArray = null;
                try {
                    jsonArray = response.getJSONArray("response");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject employee = jsonArray.getJSONObject(i);
                        String card =employee.getString("card_id");
                        String coins =employee.getString("bid_amount");
                        if(Config.game_report_game_type==3) {
                            display_num.add(card);
                        }else {
                            int card_num = Integer.parseInt(card) - 1;
                            int display = card_open.get(card_num);
                            display_card.add(display);
                        }
                        amount.add(coins);
                        time_.add(time);
                    }
                        diply();

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
        requestQueue.add(jsObjRequest);
    }

    private void display_luck7_sumry() {
    }

    private void diply() {

        RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(),1);
        RecyclerView vieww= findViewById(R.id.user_transaction_details);
        vieww.setHasFixedSize(true);
        vieww.setLayoutManager(manager);
        Context context =Game_Summary.this;
       /* Collections.reverse(display_card);
        Collections.reverse(amount);
        Collections.reverse(win_or_lose);
        Collections.reverse(transaction_time);*/
        game_sum attributes= new game_sum(display_card,amount,time_,display_num,context);

        vieww.setAdapter(attributes);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent intent = new Intent(Game_Summary.this, PointsReport.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
