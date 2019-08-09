package com.technotion.delhiDiamond.AllFragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.technotion.delhiDiamond.Adapters.coins_report_adpater;
import com.technotion.delhiDiamond.R;
import com.technotion.delhiDiamond.sever_classes.Config;
import com.technotion.delhiDiamond.sever_classes.CustomRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CoinsReport_delhi_diamond_game extends Fragment {
  RecyclerView coins_report_recycler_view,no_recycler_view,vertical_layout_number,andar_layout_amount,bahar_layout_amount,nos_andar_bahar_recycler_view,count_recycler;
  ArrayList<String> coins_report = new ArrayList<>();
    ArrayList<String> coins_report_andar = new ArrayList<>();
    ArrayList<String> coins_report_bahar = new ArrayList<>();
    ArrayList<String> coins_numbers = new ArrayList<>();
    ArrayList<String> coins_numbers2 = new ArrayList<>();
    ArrayList<String> coins_numbers3 = new ArrayList<>();
    ScrollView coins_report_scrolling;
  Context context;
  Button refresh_coins;
  TextView heading_text,jantri_amount_text,andar_amount_text,bahar_amount_text,total_amount_text_of_jantri;
  ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context=getContext();

/*
        int get_game=getArguments().getInt("open");
*/
/*
        get=get_game;
*/
        View view = inflater.inflate(R.layout.coins_report_delhi_diamond, container, false);

        coinsreportInfo(view);
        return view;
    }

    private void coinsreportInfo(View view) {
        coins_report_recycler_view=view.findViewById(R.id.coins_report_recycler_view);
        no_recycler_view=view.findViewById(R.id.nos_recycler_view);
        vertical_layout_number=view.findViewById(R.id.vertical_layout_number);
        nos_andar_bahar_recycler_view=view.findViewById(R.id.nos_andar_bahar_recycler_view);
        andar_layout_amount=view.findViewById(R.id.andar_layout_amount);
        bahar_layout_amount=view.findViewById(R.id.bahar_layout_amount);
        coins_report_scrolling= view.findViewById(R.id.coins_report_scrolling);
        jantri_amount_text=view.findViewById(R.id.jantri_amount_text);
        andar_amount_text=view.findViewById(R.id.andar_amount_text);
        bahar_amount_text = view.findViewById(R.id.bahar_amount_text);
        count_recycler=view.findViewById(R.id.count_recycler);
        total_amount_text_of_jantri=view.findViewById(R.id.total_amount_text_of_jantri);
        coins_report_scrolling.fullScroll(ScrollView.FOCUS_DOWN);
        refresh_coins=view.findViewById(R.id.refresh_coins);
        heading_text=view.findViewById(R.id.heading_text);
        RecyclerView.LayoutManager manager1= new GridLayoutManager(context,10);
        coins_report_recycler_view.setHasFixedSize(true);
        coins_report_recycler_view.setLayoutManager(manager1);
        RecyclerView.LayoutManager manager= new GridLayoutManager(context,1);
        no_recycler_view.setHasFixedSize(true);
        no_recycler_view.setLayoutManager(manager);

        RecyclerView.LayoutManager manager7= new GridLayoutManager(context,1);
        count_recycler.setHasFixedSize(true);
        count_recycler.setLayoutManager(manager7);
        RecyclerView.LayoutManager manager3= new GridLayoutManager(context,10);
        vertical_layout_number.setHasFixedSize(true);
        vertical_layout_number.setLayoutManager(manager3);
        RecyclerView.LayoutManager manager6= new GridLayoutManager(context,10);
        nos_andar_bahar_recycler_view.setHasFixedSize(true);
        nos_andar_bahar_recycler_view.setLayoutManager(manager6);
        RecyclerView.LayoutManager manager4= new GridLayoutManager(context,10);
        andar_layout_amount.setHasFixedSize(true);
        andar_layout_amount.setLayoutManager(manager4);
        RecyclerView.LayoutManager manager5= new GridLayoutManager(context,10);
        bahar_layout_amount.setHasFixedSize(true);
        bahar_layout_amount.setLayoutManager(manager5);
        heading_text.setText("Coins Report");
        add_coins_report();
        refresh_coins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_current_coins();
            }
        });
    }

    private void add_coins_report() {
        coins_report.clear();
        coins_numbers2.clear();
        coins_numbers3.clear();


        for(int i =0; i<10; i++){
                coins_numbers.add(String.valueOf(i));

        }

        for(int i =1; i<11; i++){
            if(i==10){
                coins_numbers2.add("0");
            }else {
                coins_numbers2.add(String.valueOf(i));
            }
            int x=i*10;
            coins_numbers3.add(String.valueOf(x));
        }
        get_current_coins();

    }

    private void get_coins_report_adapter() {
        coins_report_adpater attributes_crossing_row1 = new coins_report_adpater(coins_report, context);
        coins_report_recycler_view.setAdapter(attributes_crossing_row1);
        coins_report_adpater attributes_crossing_row = new coins_report_adpater(coins_numbers, context);
        no_recycler_view.setAdapter(attributes_crossing_row);
        coins_report_adpater attributes_crossing_row7 = new coins_report_adpater(coins_numbers3, context);
        count_recycler.setAdapter(attributes_crossing_row7);
        coins_report_adpater attributes_crossing_row3 = new coins_report_adpater(coins_numbers2, context);
        vertical_layout_number.setAdapter(attributes_crossing_row3);
        coins_report_adpater attributes_crossing_row4 = new coins_report_adpater(coins_report_andar, context);
        andar_layout_amount.setAdapter(attributes_crossing_row4);
        coins_report_adpater attributes_crossing_row5 = new coins_report_adpater(coins_report_bahar, context);
        bahar_layout_amount.setAdapter(attributes_crossing_row5);
        coins_report_adpater attributes_crossing_row6 = new coins_report_adpater(coins_numbers2, context);
        nos_andar_bahar_recycler_view.setAdapter(attributes_crossing_row6);

    }

    private void get_current_coins() {
        coins_report.clear();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Refreshing...");
        progressDialog.show();
        String url = "https://delhidiamond.online/index.php/get-coins-report";
        Map<String, String> params = new HashMap<String, String>();
        SharedPreferences sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String agent_id = sharedPreferences.getString(Config.AGENT_ID,"-1");

        params.put("user_id",agent_id);
        params.put("game_type", String.valueOf(Config.get_game));

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", response.toString());

                try {
                    JSONObject get_numbers_amount = response.getJSONObject("bid_amount");
                    coins_report.clear();
                    for(int i=1; i<101;i++){
                        String xyz = String.valueOf(i);
                        String get_bid_amount_on_card = get_numbers_amount.getString(xyz);
                        if(!get_bid_amount_on_card.equals("0")) {
                            coins_report.add(get_bid_amount_on_card);

                        }else{
                            coins_report.add("-");
                        }
                    }
                    JSONObject get_anadar_amount = response.getJSONObject("andar_haraf_amount");
                    coins_report_andar.clear();
                    for(int i=1; i<11;i++){
                        String xyz = String.valueOf(i);
                        String get_bid_amount_on_card = get_anadar_amount.getString(xyz);
                        if(!get_bid_amount_on_card.equals("0")) {
                            coins_report_andar.add(get_bid_amount_on_card);

                        }else{
                            coins_report_andar.add("-");
                        }
                    }
                    JSONObject get_bahar_amount = response.getJSONObject("bahar_haraf_amount");
                    coins_report_bahar.clear();
                    for(int i=1; i<11;i++){
                        String xyz = String.valueOf(i);
                        String get_bid_amount_on_card = get_bahar_amount.getString(xyz);
                        if(!get_bid_amount_on_card.equals("0")) {
                            coins_report_bahar.add(get_bid_amount_on_card);

                        }else{
                            coins_report_bahar.add("-");
                        }
                    }
                    String total_number_amount = response.getString("total_bid_amount");
                     jantri_amount_text.setText(total_number_amount);
                    String total_andar_amount = response.getString("total_andar_amount");
                    andar_amount_text.setText(total_andar_amount);
                    String total_bahar_amount = response.getString("total_bahar_amount");
                    bahar_amount_text.setText(total_bahar_amount);
                    float t_n= Float.parseFloat(total_number_amount);
                    float t_a = Float.parseFloat(total_andar_amount);
                    float t_b = Float.parseFloat(total_bahar_amount);
                    float t_t =t_n+t_a+t_b;
                    String total_jantri = String.valueOf(t_t);
                    total_amount_text_of_jantri.setText(total_jantri);
                    progressDialog.dismiss();
                    get_coins_report_adapter();
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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy( 5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsObjRequest);
    }
}
