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
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.technotion.delhiDiamond.R;
import com.technotion.delhiDiamond.Adapters.result_report_adapter;
import com.technotion.delhiDiamond.sever_classes.Config;
import com.technotion.delhiDiamond.sever_classes.CustomRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ResultReport_delhi_diamond_game extends Fragment {
    RecyclerView coins_report_recycler_view,no_recycler_view,vertical_layout_number;
    ArrayList<String> coins_report = new ArrayList<>();
    ArrayList<String> coins_numbers = new ArrayList<>();
    ArrayList<String> coins_numbers2 = new ArrayList<>();
    ScrollView coins_report_scrolling;
    Context context;
    Button refresh_coins;
    TextView heading_text,max_open;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        context=getContext();

        View view = inflater.inflate(R.layout.result_money_report, container, false);
        /*submitPlayerInfo(view);*/
        coinsreportInfo(view);
        return view;
    }

    private void coinsreportInfo(View view) {
        coins_report_recycler_view=view.findViewById(R.id.coins_report_recycler_view);
        no_recycler_view=view.findViewById(R.id.nos_recycler_view);
        vertical_layout_number=view.findViewById(R.id.vertical_layout_number);
        coins_report_scrolling= view.findViewById(R.id.coins_report_scrolling);
        coins_report_scrolling.fullScroll(ScrollView.FOCUS_DOWN);
        refresh_coins=view.findViewById(R.id.refresh_coins);
        heading_text=view.findViewById(R.id.heading_text);
        max_open=view.findViewById(R.id.max_open);
        RecyclerView.LayoutManager manager1= new GridLayoutManager(context,10);
        coins_report_recycler_view.setHasFixedSize(true);
        coins_report_recycler_view.setLayoutManager(manager1);
        RecyclerView.LayoutManager manager= new GridLayoutManager(context,1);
        no_recycler_view.setHasFixedSize(true);
        no_recycler_view.setLayoutManager(manager);
        RecyclerView.LayoutManager manager2= new GridLayoutManager(context,10);
        vertical_layout_number.setHasFixedSize(true);
        vertical_layout_number.setLayoutManager(manager2);
        heading_text.setText("Money Report");
        add_coins_report();
        refresh_coins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_current_coins();
            }
        });
    }

    private void add_coins_report() {
        coins_numbers.clear();
        coins_numbers2.clear();

        for(int i =0; i<10; i++){
            coins_numbers.add(String.valueOf(i));

        }
        for(int i =1; i<11; i++){
            if(i==10){
                coins_numbers2.add("0");
            }else {
                coins_numbers2.add(String.valueOf(i));
            }
        }
        get_current_coins();

    }

    private void get_coins_report_adapter() {
        result_report_adapter attributes_crossing_row1 = new result_report_adapter(coins_report, context);
        coins_report_recycler_view.setAdapter(attributes_crossing_row1);
        result_report_adapter attributes_crossing_row = new result_report_adapter(coins_numbers, context);
        no_recycler_view.setAdapter(attributes_crossing_row);
        result_report_adapter attributes_crossing_row2 = new result_report_adapter(coins_numbers2, context);
        vertical_layout_number.setAdapter(attributes_crossing_row2);
    }

    private void get_current_coins() {
        coins_report.clear();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Refreshing...");
        progressDialog.show();
        String url = "https://delhidiamond.online/index.php/get-money-report";
        Map<String, String> params = new HashMap<String, String>();

        /*final String agent_id = sharedPreferences.getString(Config.AGENT_ID,"-1");*/

        SharedPreferences sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String agent_id = sharedPreferences.getString(Config.AGENT_ID,"-1");

        params.put("user_id",agent_id);
        params.put("game_type", String.valueOf(Config.get_game));
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", response.toString());

                try {
                    JSONObject get_numbers_amount = response.getJSONObject("money_report");
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

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
                ArrayList<Float> in = new ArrayList<>();
                for(int i=0; i<coins_report.size(); i++){
                    if(!coins_report.get(i).equals("-")) {
                        float s = Float.parseFloat(coins_report.get(i));
                    in.add(s);
                    }
                }
                float s = Collections.max(in);
                String ss= String.valueOf(s);
                int x = in.indexOf(s)+1;
                Toast.makeText(context,x+"=="+ss,Toast.LENGTH_LONG).show();
                max_open.setText(":"+x+" with amount ="+ss);
                get_coins_report_adapter();
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
