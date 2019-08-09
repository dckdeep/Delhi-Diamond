package com.technotion.delhiDiamond;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.technotion.delhiDiamond.Adapters.transcationhistory_adapter;
import com.technotion.delhiDiamond.sever_classes.Config;
import com.technotion.delhiDiamond.sever_classes.CustomRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TransactionHistory extends AppCompatActivity {
    ArrayList<String> user_name =new ArrayList<>();
    ArrayList<String> coins_transfer =new ArrayList<>();
    ArrayList<String> closing_coins =new ArrayList<>();
    ArrayList<String> transaction_type =new ArrayList<>();
    ArrayList<String> transaction_time =new ArrayList<>();
    ArrayList<String> proper_time = new ArrayList<>();
    String jantri_array[] ={"--Select Game--","13Patti","Chidiya kabutar","Lucky7","Delhi Diamond","Faridabad","Ghaziabad","Gali","Desawar"};
    String jantri_array1[] ={"--Select History--","Coins","Game"};
    CalendarView calendarView;
    Dialog dialog;
    String date="";
    Button select_date;
    TextView no_game_yet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);
        select_date=findViewById(R.id.select_date);
        dialog = new Dialog(TransactionHistory.this);
        dialog.setContentView(R.layout.calendar_pop);
        calendarView = dialog.findViewById(R.id.calender);
        select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_history();

            }
        });
        no_game_yet=findViewById(R.id.no_game_yet);

       /* select_date=findViewById(R.id.select_date);
        dialog = new Dialog(TransactionHistory.this);
        dialog.setContentView(R.layout.calendar_pop);
        calendarView=dialog.findViewById(R.id.calender);
        calendarView= new CalendarView( TransactionHistory.this );
        final String[] s = {""};
        select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setTitle("");
                dialog.show();
                calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

                    @Override
                    public void onSelectedDayChange(CalendarView view, int year, int month,
                                                    int dayOfMonth) {
                        int d = dayOfMonth;
                       String curDate =String.valueOf(d);
                       Toast.makeText(TransactionHistory.this,curDate,Toast.LENGTH_LONG).show();
                    }
                });




            }
        });*/
   /*     addtransactions();
   */ }

    private void get_history() {
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setTitle("");
        dialog.show();
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                user_name.clear();
                coins_transfer.clear();
                closing_coins.clear();
                transaction_type.clear();
                transaction_time.clear();
                date = dayOfMonth+"-"+(month+1)+"-"+year;
                Log.d("Response: ", date.toString());
                /* Toast.makeText(PointsReport.this,date,Toast.LENGTH_LONG).show();*/
                dialog.dismiss();
                select_date.setText(date);
                addtransactions();

            }
        });
    }


    private void addtransactions() {
       final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("LOADING...");
        progressDialog.show();


        String url = "https://delhidiamond.online/index.php/get-transactions";
        Map<String, String> params = new HashMap<String, String>();

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String agent_id = sharedPreferences.getString(Config.PLAYER_ID,"-1");

        params.put("user_id", agent_id);
        params.put("current_date", date);
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", response.toString());
                progressDialog.dismiss();
                String res = "";
                try {
                    res = response.getString("response");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                /* Toast.makeText(TransactionHistory.this,response.toString(),Toast.LENGTH_LONG).show();
                 */
                if(res.equals("0")){
                    no_game_yet.setText("NO GAME FOR THE DATE("+date+")");
                }else{
                    no_game_yet.setText("");
                try {
                    JSONArray jsonArray = response.getJSONArray("transactions");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject employee = jsonArray.getJSONObject(i);

                       /* String coinstransfer = employee.getString("coins_transfer");
                        String closing_amount = employee.getString("closing_amount");
                        String transactiontype = employee.getString("transaction_type");
                        String transactiontime = employee.getString("transaction_time");
                        user_name.add( employee.getString("dealer_name"));
                        coins_transfer.add(coinstransfer);
                        closing_coins.add(closing_amount);
                        transaction_type.add(transactiontype);
                        transaction_time.add(transactiontime);*/


                        proper_time.clear();
                        String dealer_name = employee.getString("dealer_name");
                        String coinstransfer = employee.getString("coins_transfer");
                        String closing_amount = employee.getString("closing_amount");
                        String transactiontype = employee.getString("transaction_type");
                        String transactiontime = employee.getString("transaction_time");
                        if (dealer_name.equals("Admin")) {
                            user_name.add("Transferred By:-\nGAME BID");
                        } else {
                            user_name.add("Transferred By:-\n" + dealer_name);
                        }
                        String[] parts = transactiontime.split(" ");
                        proper_time.add(parts[1]);
                        proper_time.add(parts[0]);
                        transactiontime = TextUtils.join(" ", proper_time);
                        coins_transfer.add(coinstransfer);
                        closing_coins.add(closing_amount);
                        transaction_type.add(transactiontype);
                        transaction_time.add(transactiontime);
                        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                        //Creating editor to store values to shared preferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();


                        //Adding values to editor
                        editor.putString(Config.TOKENS, closing_amount);

                    }
                    usersendpoints();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
        RecyclerView vieww= findViewById(R.id.recycler_transaction_history);
        vieww.setHasFixedSize(true);
        vieww.setLayoutManager(manager);
        Context context =TransactionHistory.this;
        Collections.reverse(user_name);
        Collections.reverse(coins_transfer);
        Collections.reverse(closing_coins);
        Collections.reverse(transaction_type);
        Collections.reverse(transaction_time);
        transcationhistory_adapter attributes= new transcationhistory_adapter(user_name,coins_transfer,closing_coins,transaction_type,transaction_time,context);
        vieww.setAdapter(attributes);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            final String user_type = sharedPreferences.getString(Config.USER_STATUS,"-1");
            if(user_type.equals("agent")) {
                Intent intent = new Intent(TransactionHistory.this, AjentsDashBoard.class);
                startActivity(intent);
                finish();
            }if(user_type.equals("player")){
                Intent intent = new Intent(TransactionHistory.this, Home.class);
                startActivity(intent);
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
