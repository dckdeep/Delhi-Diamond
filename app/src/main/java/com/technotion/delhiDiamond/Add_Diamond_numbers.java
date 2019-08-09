package com.technotion.delhiDiamond;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.technotion.delhiDiamond.Adapters.delhidiamond_first_recycler;
import com.technotion.delhiDiamond.sever_classes.Config;
import com.technotion.delhiDiamond.sever_classes.CustomRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Add_Diamond_numbers extends AppCompatActivity {
    RecyclerView vieww,view;
    ProgressDialog progressDialog;
    ArrayList<String> mIconText = new ArrayList<>();
    ArrayList<String> mtext = new ArrayList<>();
    ArrayList<String> Andar_coins = new ArrayList<>();
    ArrayList<String> Bahar_coins = new ArrayList<>();
    ArrayList<String> total_coins_of_numbers= new ArrayList<>();
    ArrayList<String> Andar_Num = new ArrayList<>();
    ArrayList<String> Bahar_Num = new ArrayList<>();
    ArrayList<String> total_of_Num= new ArrayList<>();
    Button save_all_coins;
    int get;
    TextView total;
    delhidiamond_first_recycler attributes;
    boolean bandera = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Config.get_jantri_coins.clear();
        Config.get_jantri_numbers.clear();
        setContentView(R.layout.activity_add__diamond_numbers);
        RecyclerView.LayoutManager manager1 = new GridLayoutManager(Add_Diamond_numbers.this,5);
        view=findViewById(R.id.delhi_diamond_second_recyclerView);
        total=findViewById(R.id.total);
        Bundle bundle = getIntent().getExtras();
        get  = bundle.getInt("open");

        view.setHasFixedSize(true);
        view.setLayoutManager(manager1);
        save_all_coins=findViewById(R.id.save_all_coins);
        numberafter55();
        display_numberafter55();
        save_all_coins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!Config.get_jantri_coins.isEmpty()) {
                    int resultado=0;
                    for(int i = 0;i< Config.get_jantri_coins.size();i++){
                        String s= Config.get_jantri_coins.get(i);
                        if(!s.equals("")) {
                            resultado += Integer.parseInt(s);
                        }else{
                            Config.get_jantri_numbers.remove(i);
                            Config.get_jantri_coins.remove(i);
                        }

                    }
                    if(!Config.get_jantri_coins.isEmpty()){
                    SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    final String total_tokens = sharedPreferences.getString(Config.TOKENS,"-1");
                    int total = Integer.parseInt(total_tokens);
                    if(resultado<=total) {
                        Andar_coins.clear();
                        Bahar_coins.clear();
                        Andar_Num.clear();
                        Bahar_Num.clear();
                        total_coins_of_numbers.clear();
                        total_of_Num.clear();
                        for (int i = 0; i < Config.get_jantri_numbers.size(); i++) {
                            if (Config.get_jantri_numbers.get(i).startsWith("A")) {
                                if(Config.get_jantri_numbers.get(i).endsWith("0")){
                                    Andar_coins.add(Config.get_jantri_coins.get(i));
                                    Andar_Num.add("10");
                                }else {
                                    Andar_coins.add(Config.get_jantri_coins.get(i));
                                    String value = Config.get_jantri_numbers.get(i);
                                    String intValue = String.valueOf(value.charAt(value.length() - 1));
                                    Andar_Num.add(intValue);
                                }
                            } else if (Config.get_jantri_numbers.get(i).startsWith("B")) {
                                if(Config.get_jantri_numbers.get(i).endsWith("0")){
                                    Bahar_coins.add(Config.get_jantri_coins.get(i));
                                    Bahar_Num.add("10");

                                }else {
                                    Bahar_coins.add(Config.get_jantri_coins.get(i));
                                    String value = Config.get_jantri_numbers.get(i);
                                    String intValue = String.valueOf(value.charAt(value.length() - 1));
                                    Bahar_Num.add(intValue);
                                }
                            } else {
                                String s = Config.get_jantri_coins.get(i);
                                total_coins_of_numbers.add(s);
                                total_of_Num.add(Config.get_jantri_numbers.get(i));
                            }
                        }
                  /*  String result = TextUtils.join(",", Config.get_jantri_coins);
                    Toast.makeText(Add_Diamond_numbers.this,result,Toast.LENGTH_SHORT).show();*/


                        save_all_transcation();
                    }else{
                        invalid_submit();
                    }
                    }else{
                        invalid_submit();
                    }
                }else{
                    invalid_submit();
                }
            }
        });
    }

    private void invalid_submit() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Add_Diamond_numbers.this);
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
    private void numberafter55(){

        for(int i=1; i<=100; i++){
            String s= String.valueOf(i);
            mtext.add(s);
        }
        mtext.add("A#111");
        mtext.add("A#222");
        mtext.add("A#333");
        mtext.add("A#444");
        mtext.add("A#555");
        mtext.add("A#666");
        mtext.add("A#777");
        mtext.add("A#888");
        mtext.add("A#999");
        mtext.add("A#000");
        mtext.add("B#1111");
        mtext.add("B#2222");
        mtext.add("B#3333");
        mtext.add("B#4444");
        mtext.add("B#5555");
        mtext.add("B#6666");
        mtext.add("B#7777");
        mtext.add("B#8888");
        mtext.add("B#9999");
        mtext.add("B#0000");
    }
    private void display_numberafter55(){
        attributes= new delhidiamond_first_recycler(mtext,total,Add_Diamond_numbers.this);

        view.setAdapter(attributes);

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent intent = new Intent(Add_Diamond_numbers.this, DelhiDiamond.class);
            intent.putExtra("open",get);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
    private void save_all_transcation() {
        if(!Config.get_jantri_numbers.isEmpty()){
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("LOADING...");
            progressDialog.show();
            String url = "https://delhidiamond.online/index.php/place-bid";
            Map<String, String> params = new HashMap<String, String>();

/*
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String agent_id = sharedPreferences.getString(Config.AGENT_ID,"-1");
*/
            String A_N= "",A_C="";
            if(!Andar_Num.isEmpty()){
                A_N= TextUtils.join(",",Andar_Num);
               /* Toast.makeText(DelhiDiamond.this,A_N,Toast.LENGTH_SHORT).show();
               */ A_C= TextUtils.join(",",Andar_coins);


            }
            String B_N="",B_C="";
            if(!Bahar_Num.isEmpty()){
                B_N= TextUtils.join(",",Bahar_Num);


                B_C= TextUtils.join(",",Bahar_coins);
            }
            String T_N="", T_C="";
            if(!total_of_Num.isEmpty()){
                T_N= TextUtils.join(",",total_of_Num);
                T_C= TextUtils.join(",",total_coins_of_numbers);
            }
            SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            final String agent_id = sharedPreferences.getString(Config.DEALER_ID,"-1");
            final String player_id = sharedPreferences.getString(Config.PLAYER_ID,"-1");
            final String game_id = sharedPreferences.getString(Config.G4_ID,"-1");
            String game_type= String.valueOf(get);
            params.put("agent_id", agent_id);
            params.put("player_id", player_id);
            params.put("game_id", game_id);
            params.put("game_type", game_type);
            params.put("card_id", T_N);
            params.put("bid_amount", T_C);
            params.put("array_andar", A_N);
            params.put("array_andar_amount", A_C);
            params.put("array_bahar", B_N);
            params.put("array_bahar_amount", B_C);
            final String transaction_id_g2 = sharedPreferences.getString(Config.TRANSACTION_ID_g4,"-1");
            params.put("transaction_id",transaction_id_g2);

          /*  final String TRANSACTION_ID_g4 = sharedPreferences.getString(Config.TRANSACTION_ID_g4,"-1");
            if(!TRANSACTION_ID_g4.equals("-1")) {
                params.put("transaction_id", TRANSACTION_ID_g4);*/
            /*     Toast.makeText(DelhiDiamond.this,TRANSACTION_ID_g4,Toast.LENGTH_SHORT).show();
             */ /*}*/

            CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    Log.d("Response: ", response.toString());

                    /*Toast.makeText(DelhiDiamond.this,response.toString(),Toast.LENGTH_LONG).show();
                     */try {
                        String jsonObject = response.getString("transaction_id");
                        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        //Creating editor to store values to shared preferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();


                        //Adding values to editor
                        editor.putString(Config.TRANSACTION_ID_g4,jsonObject);

                        editor.commit();

                        progressDialog.dismiss();
                        Config.get_jantri_numbers.clear();
                        Config.get_jantri_coins.clear();
                        Toast.makeText(Add_Diamond_numbers.this,"Successfully Saved",Toast.LENGTH_LONG).show();
                        display_numberafter55();
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

        }else{
            invalid_submit();
        }
    }

}
