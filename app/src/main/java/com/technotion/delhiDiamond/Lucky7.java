package com.technotion.delhiDiamond;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.technotion.delhiDiamond.sever_classes.Config;
import com.technotion.delhiDiamond.sever_classes.CustomRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class Lucky7 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    int check =0;
    int game_type =3;
    public static final Random RANDOM = new Random();
    private Button lucky7_1_6,lucky7_7,lucky7_8_12;
    private ImageView imageView1, imageView2,icon_card,lock_bid__icon;
    TextView timer,lock_bid_text;
    int res1=0;
    int res2=0;
    int i;
    Context context;
    MediaPlayer play_music;
    long end_game_time=0;
    long current_game_time=0;
    long timeLeft =120000;
    long get_result_time=0;
    int total_=0,  count_check=0;
    Dialog Result_dialog;
    LinearLayout lock_bid;
    CountDownTimer countDownTimer;
    TextView total_bet_amount,bet_lucky7_1_6,bet_lucky7_7,bet_lucky7_8_12;
    ArrayList<String> mIconText = new ArrayList<>();
    ArrayList<Integer> Lucky7_Card_images = new ArrayList<>();
    ProgressDialog progressDialog;
    Handler handler12;
    TimerTask doTask;
    Runnable runnable ;
    int winner_card_no=0;
    ArrayList<String>bid_of_card= new ArrayList<>();
    ArrayList<Integer> Win_image = new ArrayList<>();
    Animation zoomin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lucky7);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Result_dialog= new Dialog(this);
        zoomin = AnimationUtils.loadAnimation(Lucky7.this, R.anim.zoomin);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        context =Lucky7.this;
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Win_image.clear();
        Win_image.add(R.drawable.lose);
        Win_image.add(R.drawable.win);
        Lucky7_Card_images.clear();
        Lucky7_Card_images.add(R.drawable.one_to_six);
        Lucky7_Card_images.add(R.drawable.seven);
        Lucky7_Card_images.add(R.drawable.eight_to_twelve);

        handler12 = new Handler();

        timer = findViewById(R.id.timer_text);
        lock_bid=findViewById(R.id.lock_bid);
        lock_bid.setVisibility(View.INVISIBLE);
        imageView1 = findViewById(R.id.rolling_1);
        imageView2 = findViewById(R.id.rolling_2);
        total_bet_amount=findViewById(R.id.total_bet_amount);
        bet_lucky7_1_6=findViewById(R.id.bet_lucky7_1_6);
        bet_lucky7_7=findViewById(R.id.bet_lucky7_7);
        bet_lucky7_8_12=findViewById(R.id.bet_lucky7_8_12);
        lucky7_1_6=findViewById(R.id.lucky7_1_6);
        lucky7_7=findViewById(R.id.lucky7_7);
        icon_card=findViewById(R.id.icon_card);
        lucky7_8_12=findViewById(R.id.lucky7_8_12);
        lock_bid__icon= findViewById(R.id.lock_bid__icon);
        lock_bid_text =findViewById(R.id.lock_bid_text);



        request_game_type(game_type);
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup);
        final ImageView popup__image   =(ImageView)dialog.findViewById(R.id.popup_image);
        final ImageButton close_popup = dialog.findViewById(R.id.close_popup);
        final Button add_text = dialog.findViewById(R.id.add_bet_point);
        final Button less_text = dialog.findViewById(R.id.less_bet_point);
        final MaterialEditText bet_point = dialog.findViewById(R.id.bet_point);
        final TextView hinditext =dialog.findViewById(R.id.hinditext);
       /* less_text.setEnabled(false);
        less_text.setVisibility(View.INVISIBLE);
       */ popup__image.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(Lucky7.this.getResources(),R.drawable.dice_1,300,200));
        icon_card.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(this.getResources(),R.drawable.dice_1,300,200));



       /* rollDices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int x =0 ;x<mIconText.size();x++){
                    int total_points = Integer.parseInt(mIconText.get(x));
                    total_=total_+total_points;
                }
                if(total_!=0) { displaydice();

                }else{
                    Toast.makeText(Lucky7.this, "Please Set The Bet First", Toast.LENGTH_LONG).show();

                }
            }
        });
*/
        lucky7_1_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(mIconText.get(1).equals("0")&&mIconText.get(2).equals("0")){
                i=0;
                hinditext.setText("");
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setTitle("");
                  get_bid_id(i);

                  dialog.show();}
                else{
                  Toast.makeText(Lucky7.this, "You have fixed your bet", Toast.LENGTH_LONG).show();

              }
            }
        });

        lucky7_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mIconText.get(0).equals("0")&&mIconText.get(2).equals("0")){
                i=1;
                    hinditext.setText("");
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setTitle("");
                    get_bid_id(i);
                    dialog.show();
                }
                else{
                    Toast.makeText(Lucky7.this, "You have fixed your bet", Toast.LENGTH_LONG).show();

                }
            }
        });

        lucky7_8_12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mIconText.get(0).equals("0")&&mIconText.get(1).equals("0")){
                    i=2;
                    hinditext.setText("");

                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setTitle("");
                get_bid_id(i);

                dialog.show();
                }
                else{
                    Toast.makeText(Lucky7.this, "You have fixed your bet", Toast.LENGTH_LONG).show();

                }
            }
        });



        close_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        add_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bet_point.getText().equals("")) {
                    String bet_amount = String.valueOf(bet_point.getText());
                    SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                    final String current_coins = sharedPreferences.getString(Config.TOKENS, "-1");
                    String trans =sharedPreferences.getString(Config.TRANSACTION_ID_g3, "-1");;
                    final String get_bid_id_of_postion =bid_of_card.get(i);
                    int check_bet_amount = Integer.parseInt(bet_amount);
                    int check_current_coins = Integer.parseInt(current_coins);
                    final String bid_id = sharedPreferences.getString(Config.BID_ID,"-1");

                    if(check_current_coins>=check_bet_amount){
                        int convert = Integer.parseInt(bet_amount);
                        int convertint = Integer.parseInt(mIconText.get(i));

                        if (bid_id.equals("0")) {
                           /* Toast.makeText(Lucky7.this,bet_amount,Toast.LENGTH_SHORT).show();
                           */

                           send_bid_amount(i, convert);


                        } else {

                            send_update_bid_amount(i, convert);
                        }
                        dialog.dismiss();
                    }else{

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Lucky7.this);
                        alertDialogBuilder.setMessage("Your Coins Are Less Then You Bet");
                        alertDialogBuilder.setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {

                                    }
                                });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();

                    }
                }else{
                    Toast.makeText(Lucky7.this, "Please Enter Your Coins", Toast.LENGTH_LONG).show();


                }
            }
        });

        less_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mIconText.get(i).equals("0")) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Lucky7.this);
                    alertDialogBuilder.setMessage("Your Want Remove bid From The Card");
                    alertDialogBuilder.setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    send_update_bid_amount(i, 0);
                                    bet_point.getText().clear();
                                    dialog.dismiss();
                                /*    Toast.makeText(Lucky7.this, "You Can Change Your Bid Till 14min", Toast.LENGTH_LONG).show();*/
                                }
                            });
                    alertDialogBuilder.setNegativeButton("No",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {

                                    dialog.dismiss();

                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }else{
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Lucky7.this);
                    alertDialogBuilder.setMessage("Your Card BID Is Already 0(Zero)");
                    alertDialogBuilder.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
        });
        refresh();
    /*    icon_card.setImageResource(R.drawable.dice_1);
    */}
    private void refresh() {

        Timer timer1 = new Timer();
        doTask = new TimerTask() {
            @Override
            public void run() {
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        check_internetconnection();
                    }
                };
                handler12.post(runnable);
            }
        };
        timer1.schedule(doTask, 0,2000);
    }

    private void check_internetconnection() {
        if(isOnline()){
        }else{
            doTask.cancel();
            handler12.removeCallbacks(runnable);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Please Check Your Net Connection");
            alertDialogBuilder.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {

                           refresh();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }
    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    private void add_coins() {

        mIconText.clear();
        for(int i=0; i<bid_of_card.size(); i++) {
            String get_bid_of_card = bid_of_card.get(i);
            mIconText.add(get_bid_of_card);
            setBetText(mIconText.get(i),i);

        }

        for(int i=0; i<mIconText.size(); i++) {
            setBetText(mIconText.get(i),i);

        }
        /*SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        final String g3_count_open = sharedPreferences.getString(Config.G3_COUNT_OPEN, "_one_");
        if (g3_count_open.equals("_two_")) {
            SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            //Getting editor
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(Config.G3_COUNT_OPEN, "_three_");
            editor.commit();
            mIconText.clear();
            for(int ss=0; ss<3; ss++){
                mIconText.add("0");
            }

            String result = TextUtils.join(",", mIconText);


            editor.putString(Config.G3_coins, result);
            editor.commit();
        }

        *//*String result = TextUtils.join(",", mIconText);*//*
      *//*  Toast.makeText(Lucky7.this,g3_count_open,Toast.LENGTH_LONG).show();*//*
        if (g3_count_open.equals("_three_")) {
            mIconText.clear();
            SharedPreferences sharedPreferencess = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            final String game_start_time = sharedPreferencess.getString(Config.G3_coins, "-1");
            String[] parts = game_start_time.split(",");
            for (int ss = 0; ss < parts.length; ss++) {
                mIconText.add(parts[ss]);

            }
            for(int zz= 0; zz<mIconText.size(); zz++){
                String coins = mIconText.get(zz);

                setBetText(coins,zz);
            }


        }*/
    }



    private void request_game_type(int i) {
        progressDialog = new ProgressDialog(Lucky7.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        String game_type = String.valueOf(i);
        String url = "https://delhidiamond.online/index.php/select-game";
        Map<String, String> params = new HashMap<String, String>();

/*
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String agent_id = sharedPreferences.getString(Config.AGENT_ID,"-1");
*/
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String player_id = sharedPreferences.getString(Config.PLAYER_ID,"-1");
        params.put("user_id",player_id);
        params.put("game_type", game_type);
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", response.toString());
               /* Toast.makeText(Lucky7.this,response.toString(),Toast.LENGTH_LONG).show();*/
                progressDialog.dismiss();
                try {
                    JSONArray jsonArray = response.getJSONArray("selected_game");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject employee = jsonArray.getJSONObject(i);


                        String game_id = employee.getString("id");
                        String starting_time = employee.getString("start_time");

                        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        //Creating editor to store values to shared preferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();


                        //Adding values to editor
                        editor.putString(Config.G3_ID,game_id);
                        editor.putString(Config.GAME_START_TIME_g3,starting_time);
                        //Saving values to editor
                        String jsonObject = response.getString("transaction_id");
                        editor.putString(Config.TRANSACTION_ID_g3,jsonObject);


                        //Adding values to editor
                        editor.commit();
                        JSONObject jsonobj2 = response.getJSONObject("bid_amount");
                        bid_of_card.clear();
                        for (int a = 1; a < 4; a++) {
                            String xyz = String.valueOf(a);
                            String get_bid_amount_on_card = jsonobj2.getString(xyz);
                            /*   Toast.makeText(Dashboard.this, get_bid_amount_on_card, Toast.LENGTH_LONG).show();
                             */   bid_of_card.add(get_bid_amount_on_card);
                        }
                      /*  String scx = TextUtils.join(",", bid_of_card);
                        Toast.makeText(Lucky7.this, scx, Toast.LENGTH_LONG).show();
                      */  /*opencard_image();
                        opencard_display();*/
                        add_coins();
                        int total=0;
                        for(int x =0 ;x<bid_of_card.size();x++){
                            int total_points = Integer.parseInt(bid_of_card.get(x));
                            total=total+total_points;
                            total_=total;
                        }
                        String total_bet__amount= String.valueOf(total);
                        /*SharedPreferences sharedPreferences =getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                        //Creating editor to store values to shared preferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();

*/
                        //Adding values to editor
                        editor.putString(Config.G3_SET_TOTAL_AMOUNT, total_bet__amount);
                        editor.commit();
                        SharedPreferences sharedPreferencess = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        final String total___Amount = sharedPreferencess.getString(Config.G3_SET_TOTAL_AMOUNT, "-1");

                        total_bet_amount.setText(total___Amount);
                    }
                   /* SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    final String check_game_id = sharedPreferences.getString(Config.G3_CHECK_ID,"new");
                    final String game_id = sharedPreferences.getString(Config.G3_ID,"new");

                    if(check_game_id!=game_id){
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(Config.G3_CHECK_ID,game_id);
                        editor.putString(Config.G3_COUNT_OPEN, "_one_");
                        editor.putString(Config.G3_coins,"0");
                        editor.putString(Config.G3_SET_TOTAL_AMOUNT,"0");

                        editor.commit();

                    }*/
                    convert_min_into_mini();

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

    private void get_bid_id(int i) {
        String url = "https://delhidiamond.online/index.php/get-bid-id";
        Map<String, String> params = new HashMap<String, String>();
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String player_id = sharedPreferences.getString(Config.PLAYER_ID,"-1");
        final String card_id = String.valueOf(i+1);
            final String game2_id = sharedPreferences.getString(Config.G3_ID,"-1");
            params.put("game_id",game2_id);
            params.put("game_type",String.valueOf(game_type));

        params.put("player_id",player_id);

        params.put("card_id",card_id);
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", response.toString());
                /*    Toast.makeText(context,response.toString(),Toast.LENGTH_LONG).show();*/
                try {

                    String bid_id = response.getString("bid_id");
                    SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                    //Creating editor to store values to shared preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Config.BID_ID,bid_id);
                    editor.commit();

                    /*    Toast.makeText(context,"You Can Change Your Bet Till 14min",Toast.LENGTH_LONG).show();
                     */} catch (JSONException e) {
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

    private void send_bid_amount(int i,int bid_amount) {
        String url = "https://delhidiamond.online/index.php/place-bid";
        Map<String, String> params = new HashMap<String, String>();

        SharedPreferences sharedPreferences =getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String agent_id = sharedPreferences.getString(Config.DEALER_ID,"-1");
        final String player_id = sharedPreferences.getString(Config.PLAYER_ID,"-1");
        final String game_id = sharedPreferences.getString(Config.G3_ID,"-1");
        final String card_id = String.valueOf(i+1);
        final String bid__amount = String.valueOf(bid_amount);
        final String game__type = String.valueOf("3");
        params.put("agent_id", agent_id);
        params.put("player_id",player_id);
        params.put("game_id",game_id);
        params.put("card_id",card_id);
        params.put("bid_amount",bid__amount);
      /*  Toast.makeText(Lucky7.this,bid__amount,Toast.LENGTH_SHORT).show();
      */  params.put("game_type",game__type);
        final String total_amount = sharedPreferences.getString(Config.G3_SET_TOTAL_AMOUNT,"0");
        int total = Integer.parseInt(total_amount+bid_amount);
        String total_amt=String.valueOf(total);
        params.put("total_amount",total_amt);
        final String s = sharedPreferences.getString(Config.count_G3_trns, "0");
        if (!s.equals("0")) {
         final String transaction_id_g3 = sharedPreferences.getString(Config.TRANSACTION_ID_g3,"-1");
        params.put("transaction_id",transaction_id_g3);
        }else{
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Config.count_G3_trns,"1");
            editor.commit();
        }



/*

        Toast.makeText(this,agent_id,Toast.LENGTH_LONG).show();

*/


        final CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", response.toString());
             /*   Toast.makeText(Lucky7.this,response.toString(),Toast.LENGTH_LONG).show();
             */   try {



                    String bid_id = response.getString("bid_id");
                    String card_id = response.getString("card_id");
                    String bid_amount = response.getString("bid_amount");
                    String transaction_id = response.getString("transaction_id");
                    SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                    //Creating editor to store values to shared preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    /* Toast.makeText(context,card_id,Toast.LENGTH_LONG).show();*/

                    //Adding values to editor
                    editor.putString(Config.BID_ID,bid_id);
                    editor.putString(Config.CARD_ID,card_id);
                    editor.putString(Config.BID_Amount,bid_amount);
                    editor.putString(Config.TRANSACTION_ID_g3,transaction_id);
                    editor.putString(Config.first,"1");

                        //Saving values to editor
                        editor.commit();
                        SharedPreferences sharedPreferencess = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        final String card___id = sharedPreferencess.getString(Config.CARD_ID,"-1");
                        final String bid_Amount = sharedPreferencess.getString(Config.BID_Amount,"-1");
                        int card_no= Integer.parseInt(card___id)-1;





                        mIconText.set(card_no,bid_Amount);

                      String result = TextUtils.join(",", mIconText);

                            editor.putString(Config.G3_coins,result);
                            editor.commit();
                            mIconText.clear();
                            final String game_start_time = sharedPreferencess.getString(Config.G3_coins,"-1");
                            String[] parts = game_start_time.split(",");
                            for(int ss=0; ss<parts.length; ss++){
                                mIconText.add(parts[ss]);

                            }

                     /*  Toast.makeText(Lucky7.this,result,Toast.LENGTH_LONG).show();*/
                        setBetText(mIconText.get(card_no),card_no);



                        convertintoint();


               /*     Toast.makeText(Lucky7.this,"You Can Change Your Bet Till 14min",Toast.LENGTH_LONG).show();*/
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
    private void setBetText(String s,int card_card_no)
    {
        if(card_card_no==0){
            bet_lucky7_1_6.setText(s);
        }if(card_card_no==1){
            bet_lucky7_7.setText(s);
        }if(card_card_no==2){
            bet_lucky7_8_12.setText(s);
        }
    }
    private void send_update_bid_amount(int i, int convertint) {
        String url = "https://delhidiamond.online/index.php/update-bid";
        Map<String, String> params = new HashMap<String, String>();

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String bid_id = sharedPreferences.getString(Config.BID_ID,"-1");
        final String player_id = sharedPreferences.getString(Config.PLAYER_ID,"-1");
        mIconText.set(i, String.valueOf(convertint));
        convertintoint();
        final String total_amount = sharedPreferences.getString(Config.G3_SET_TOTAL_AMOUNT,"0");

        params.put("total_amount",total_amount);
        final String transaction_id = sharedPreferences.getString(Config.TRANSACTION_ID_g3,"-1");
        final String card_id = String.valueOf(i+1);
        final String bid__amount = String.valueOf(convertint);
/*Toast.makeText(Lucky7.this,bid__amount,Toast.LENGTH_LONG).show();
 */       params.put("bid_id", bid_id);
        params.put("player_id",player_id);
        params.put("transaction_id",transaction_id);
        params.put("card_id",card_id);
        params.put("bid_amount",bid__amount);



    /*    Toast.makeText(context,bid_id+"\nplayer_id"+player_id+"\ntransaction_id"+
                transaction_id+"\ncard_id"+card_id+"\nbid__amount"+bid__amount,Toast.LENGTH_LONG).show();
*/


        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", response.toString());
             /*       Toast.makeText(Lucky7.this,response.toString(),Toast.LENGTH_LONG).show();
             */   try {

                    String bid_id = response.getString("bid_id");
                    String card_id = response.getString("card_id");
                    String bid_amount = response.getString("bid_amount");
                    String transaction_id = response.getString("transaction_id");
                    SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                    //Creating editor to store values to shared preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();


                    //Adding values to editor
                    editor.putString(Config.BID_ID,bid_id);
                    editor.putString(Config.CARD_ID,card_id);
                    editor.putString(Config.BID_Amount,bid_amount);
                    editor.putString(Config.TRANSACTION_ID_g3,transaction_id);
                    editor.putString(Config.G3_coins,"");


                    //Saving values to editor
                    editor.commit();

                    SharedPreferences sharedPreferencess = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    final String card___id = sharedPreferencess.getString(Config.CARD_ID,"-1");
                    final String bid_Amount = sharedPreferencess.getString(Config.BID_Amount,"-1");
                    int card_no= Integer.parseInt(card___id)-1;





                    mIconText.set(card_no,bid_Amount);

                    String result = TextUtils.join(",", mIconText);

                    editor.putString(Config.G3_coins,result);
                    editor.commit();
                    mIconText.clear();
                    final String game_start_time = sharedPreferencess.getString(Config.G3_coins,"-1");
                    String[] parts = game_start_time.split(",");
                    for(int ss=0; ss<parts.length; ss++){
                        mIconText.add(parts[ss]);

                    }

                    /* Toast.makeText(this,player_id,Toast.LENGTH_LONG).show();*/
                    setBetText(mIconText.get(card_no),card_no);



                    convertintoint();
               /*     Toast.makeText(Lucky7.this,"You Can Change Your Bet Till 14min",Toast.LENGTH_LONG).show();*/
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
    private void convert_min_into_mini() {
        int curr_min =new Time(System.currentTimeMillis()).getMinutes();
        current_game_time = TimeUnit.MINUTES.toMillis(curr_min);
        int curr_sec = new Time(System.currentTimeMillis()).getSeconds();
        current_game_time = current_game_time+TimeUnit.SECONDS.toMillis(curr_sec);

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String game_start_time = sharedPreferences.getString(Config.GAME_START_TIME_g3,"-1");
     /*  String game_start_time ="10:55:02";*/
       String[] parts = game_start_time.split(":");

        for(int i=0; i<parts.length;i++) {
            String s = parts[i];
            if (i != 0) {
                String make_game_end_time = convertstring_into_int(s, i);
               /*Toast.makeText(Lucky7.this,make_game_end_time,Toast.LENGTH_LONG).show();
               */ get_end_time(make_game_end_time, i);
            }
        }


        // UPDATE timeLeft for playing game on current time
      /*  String xyz = String.valueOf(end_game_time);
        String assx = String.valueOf(current_game_time);
        String assec = String.valueOf(end_game_time-current_game_time);
        Toast.makeText(Lucky7.this,"end_time==>"+xyz+"current_time==>"+assx+"=="+assec,Toast.LENGTH_LONG).show();
      */  timeLeft =end_game_time-current_game_time;
        get_result_time = timeLeft-30000;
        timerstart();
    }

    private void get_end_time(String make_game_end_time,int a) {

        int time = Integer.parseInt(make_game_end_time);
        if(a==1) {
            end_game_time = TimeUnit.MINUTES.toMillis(time);
        }else{
            end_game_time=end_game_time+TimeUnit.SECONDS.toMillis(time);
        }

    }

    private String convertstring_into_int(String s,int a) {
        int i = Integer.parseInt(s);
        int x=0;
        if(a==1) {
            x = i + 3;
            return s= String.valueOf(x);
        }else{
            return s= String.valueOf(i);
        }

    }
    private void displaydice() {

        int value1 = randomDiceValue();
        int value2 = randomDiceValue();
      /*  int sum = value1+value2;
        String sumString = String.valueOf(sum);
        Toast.makeText(Lucky7.this,"The Number is "+sumString,Toast.LENGTH_LONG).show();
      */  res1 = getResources().getIdentifier("rollingdice_" + value1, "drawable", "com.technotion.delhidiamond");
        res2  = getResources().getIdentifier("rollingdice_" + value2, "drawable", "com.technotion.delhidiamond");


       /* final ObjectAnimator oa1 = ObjectAnimator.ofFloat(imageView1, "scaleX", 1f, 0f);
        final ObjectAnimator oa2 = ObjectAnimator.ofFloat(imageView1, "scaleX", 0f, 1f);
        oa1.setInterpolator(new DecelerateInterpolator());
        oa2.setInterpolator(new AccelerateDecelerateInterpolator());
        oa1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                imageView1.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(Lucky7.this.getResources(),res1,120,120));
                oa2.start();
            }
        });
        oa1.setDuration(1000);
        oa2.setDuration(1000);
        oa1.start();

        final ObjectAnimator oa11 = ObjectAnimator.ofFloat(imageView2, "scaleX", 1f, 0f);
        final ObjectAnimator oa22 = ObjectAnimator.ofFloat(imageView2, "scaleX", 0f, 1f);
        oa11.setInterpolator(new DecelerateInterpolator());
        oa22.setInterpolator(new AccelerateDecelerateInterpolator());
        oa11.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                imageView2.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(Lucky7.this.getResources(),res2,120,120));
                oa22.start();
            }
        });
        oa11.setDuration(800);
        oa22.setDuration(800);
        oa11.start();*/
               imageView1.animate().rotation(imageView1.getRotation()-360).start();
                imageView2.animate().rotation(imageView2.getRotation()-360).start();
                imageView1.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(Lucky7.this.getResources(),res1,120,120));
                imageView2.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(Lucky7.this.getResources(),res2,120,120));

    }
    private void convertintoint() {

        int total=0;
        for(int x =0 ;x<mIconText.size();x++){
            int total_points = Integer.parseInt(mIconText.get(x));
            total=total+total_points;
            total_=total;
        }
        String total_bet__amount= String.valueOf(total);
        SharedPreferences sharedPreferences =getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //Creating editor to store values to shared preferences
        SharedPreferences.Editor editor = sharedPreferences.edit();


        //Adding values to editor
        editor.putString(Config.G3_SET_TOTAL_AMOUNT, total_bet__amount);
        editor.commit();
        SharedPreferences sharedPreferencess = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String total___Amount = sharedPreferencess.getString(Config.G3_SET_TOTAL_AMOUNT, "-1");
        total_bet_amount.setText(total___Amount);
    }

    public static int randomDiceValue() {
        return RANDOM.nextInt(6) + 1;
    }



    @Override
    protected void onStop() {
        play_music.stop();
        super.onStop();
    }
    private void timerstart() {

        countDownTimer = new CountDownTimer(timeLeft,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft =millisUntilFinished;
                if(!Lucky7.this.isFinishing()) {

                    updatetime();

                }
            }

            @Override
            public void onFinish() {
                timer.setText("0:00");
                if(!Lucky7.this.isFinishing()){
                    Result_dialog.dismiss();
                    SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    //Creating editor to store values to shared preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Config.G3_COUNT_OPEN, "_one_");
                    editor.putString(Config.G3_coins, "0");
                    editor.putString(Config.G3_SET_TOTAL_AMOUNT, "0");
                    editor.putString(Config.count_G3_trns, "0");
                    editor.commit();
                    Intent intent = new Intent(Lucky7.this,Home.class);
                    startActivity(intent);
                    finish();
                }

            }
        }.start();

    }

    private void updatetime() {
        int time_in_min = (int)timeLeft/60000;
        int time_in_sec = (int)timeLeft%60000/1000;
        update_text_time();
/*
        if(time_in_min<=1){
            lock_bid.setVisibility(View.VISIBLE);
            lock_bid__icon.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(Lucky7.this.getResources(),R.drawable.locked__icon,300,200));
            lock_bid_text.setText("Bid Is Locked");
            lucky7_1_6.setEnabled(false);
            lucky7_7.setEnabled(false);
            lucky7_8_12.setEnabled(false);

        }
*/
        if(time_in_min==0) {
            if(time_in_sec<=30&&time_in_sec>15){
                lock_bid.setVisibility(View.VISIBLE);
                lock_bid_text.setText("Bid Is Locked");
                lucky7_1_6.setEnabled(false);
                lucky7_7.setEnabled(false);
                lucky7_8_12.setEnabled(false);
                play_music.stop();
                play_music= MediaPlayer.create(context,R.raw.rolling_dice);
                play_music.setLooping(true);
                play_music.start();
                displaydice();

            }
            if(time_in_sec<=15){
                if(count_check==0) {
                    play_music.stop();
                    get_result();

                }
            }
        }


    }
    private void update_text_time() {
        int text_time_left = (int) (timeLeft-30000);
        if(text_time_left>-1) {
            int time_in_min = (int) text_time_left / 60000;
            int time_in_sec = (int) text_time_left % 60000 / 1000;
            String time = "";
            if (time_in_min < 10) {
                time = "0";
            }
            time += time_in_min;
            time += ":";


            if (time_in_sec < 10) time += "0";
            time += time_in_sec;
            timer.setText(time);
        }
    }
    private void get_result() {
        doTask.cancel();
        handler12.removeCallbacks(runnable);
        String game_type = String.valueOf(3);
        final String url = "https://delhidiamond.online/index.php/fetch-results";
        Map<String, String> params = new HashMap<String, String>();


        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String game_id = sharedPreferences.getString(Config.G3_ID,"-1");

        params.put("game_type", game_type);
        params.put("game_id", game_id);

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", response.toString());
                /*   Toast.makeText(Lucky7.this,response.toString(),Toast.LENGTH_LONG).show();*/
                 try {
                     SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                     SharedPreferences.Editor editor = sharedPreferences.edit();



                     String first = response.getString("first_number");
                     String second = response.getString("second_number");
                    /* Toast.makeText(Lucky7.this,first+"===="+second,Toast.LENGTH_LONG).show();
                    */ int value1= Integer.parseInt(first);
                     int value2 = Integer.parseInt(second);
                     int x=value1+value2;
                     String card_no= String.valueOf(x);
                     editor.putString(Config.CARD_ID, card_no);

                     res1 = getResources().getIdentifier("rollingdice_" + value1, "drawable", "com.technotion.delhidiamond");
                     res2  = getResources().getIdentifier("rollingdice_" + value2, "drawable", "com.technotion.delhidiamond");
                     imageView1.animate().rotation(imageView1.getRotation()-360).start();
                     imageView2.animate().rotation(imageView2.getRotation()-360).start();
                     imageView1.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(Lucky7.this.getResources(),res1,120,120));
                     imageView2.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(Lucky7.this.getResources(),res2,120,120));
                     imageView1.setAnimation(zoomin);
                     imageView2.setAnimation(zoomin);


                     editor.commit();
                         JSONArray jsonArray = response.getJSONArray("winning_users");

                         for (int i = 0; i < jsonArray.length(); i++) {
                             JSONObject employee = jsonArray.getJSONObject(i);


                             String player_id = employee.getString("user_id");
                             String tokens = employee.getString("win_coins");


                             //Adding values to editor
                             editor.putString(Config.Win_player_id, player_id);

                             //Saving values to editor
                             editor.commit();

                             final String player_idd = sharedPreferences.getString(Config.PLAYER_ID, "-1");
                             final String Win_player_id = sharedPreferences.getString(Config.Win_player_id, "-1");

                           /*  Toast.makeText(Lucky7.this,player_idd+"=="+Win_player_id,Toast.LENGTH_LONG).show();*/
                             if (player_idd.equals(Win_player_id)) {
                                 editor.putString(Config.GET_TOKENS, tokens);
                                 //Saving values to editor
                                 editor.commit();
                                 check = 1;
                             }
                         }

                         final String win_card_no = sharedPreferences.getString(Config.CARD_ID, "-1");
                         winner_card_no = Integer.parseInt(win_card_no) ;

                         if (check == 1) {
                             /*final String player_idd = sharedPreferences.getString(Config.G3_SET_TOTAL_AMOUNT, "-1");
                             final String win_card_amount = sharedPreferences.getString(Config.GET_TOKENS, "-1");
                             int check_card_coins = Integer.parseInt(win_card_amount);
                             int total_bid = Integer.parseInt(player_idd);
                             if (check_card_coins >= total_bid) {
                               */
                             final Handler handler = new Handler();
                             handler.postDelayed(new Runnable() {
                                 @Override
                                 public void run() {
                                     //Do something after 100ms
                                     show_pop(1, winner_card_no);
                                 }
                             }, 3000);


                             /*}*/
                         }
                         if (check == 0) {
                         /*    Toast.makeText(Lucky7.this, win_card_no, Toast.LENGTH_LONG).show();
                         */
                             final Handler handler = new Handler();
                             handler.postDelayed(new Runnable() {
                                 @Override
                                 public void run() {
                                     //Do something after 100ms
                                     show_pop(0, winner_card_no);
                                 }
                             }, 3000);
                         }






/*
                    convert_min_into_mini();
*/

                } catch (JSONException e) {
                     SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);


                     final String win_card_no = sharedPreferences.getString(Config.CARD_ID, "-1");
                   winner_card_no = Integer.parseInt(win_card_no) ;
/*
                     if (check == 1) {
                         show_pop(1, winner_card_no);
                     }*/
                     if (check == 0) {
                    /*     Toast.makeText(Lucky7.this, win_card_no, Toast.LENGTH_LONG).show();
                    */     final Handler handler = new Handler();
                         handler.postDelayed(new Runnable() {
                             @Override
                             public void run() {
                                 //Do something after 100ms
                                 show_pop(0, winner_card_no);
                             }
                         }, 3000);
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
        count_check++;
    }

    private void show_pop(int i, int winner_card_no) {

        Result_dialog.setContentView(R.layout.lucky7_result_display);
        Result_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Result_dialog.setTitle("");
        String ssssss = String.valueOf(winner_card_no);
       /* Toast.makeText(Lucky7.this,ssssss,Toast.LENGTH_LONG).show();*/
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
         String coins = sharedPreferences.getString(Config.GET_TOKENS,"-1");
      TextView dice_number   = Result_dialog.findViewById(R.id.WIN_CARD);
        final TextView Total_Coins_recived   =Result_dialog.findViewById(R.id.Coins_display);
        final ImageView win   =(ImageView)Result_dialog.findViewById(R.id.win);
        if(i==1){

            final String player_idd = sharedPreferences.getString(Config.G3_SET_TOTAL_AMOUNT, "-1");
            String get_tokens = sharedPreferences.getString(Config.GET_TOKENS, "-1");

            int p = Integer.parseInt(player_idd);
            int coins_check = Integer.parseInt(get_tokens);
            int result = coins_check-p;
            get_tokens = String.valueOf(result);
            Total_Coins_recived.setText(get_tokens);
          /*  Total_Coins_recived.setText(coins);*/
/*
            popup__image.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(Lucky7.this.getResources(), Lucky7_Card_images.get(winner_card_no), 300, 200));
*/
            String s = String.valueOf(winner_card_no);
            dice_number.setText(ssssss);

            win.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(Lucky7.this.getResources(),Win_image.get(1),300,200));
        }/*if(i==1&&x==0){ final String player_idd = sharedPreferences.getString(Config.G3_SET_TOTAL_AMOUNT, "-1");
            int p = Integer.parseInt(player_idd);
            int coins_check = Integer.parseInt(coins);
            int result = p-coins_check;
            coins = String.valueOf(result);
            Total_Coins_recived.setText(coins);
            popup__image.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(Lucky7.this.getResources(),Lucky7_Card_images.get(winner_card_no),300,200));

            win.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(Lucky7.this.getResources(),Win_image.get(0),300,200));}
       */ if(i==0) {
            final String player_idd = sharedPreferences.getString(Config.G3_SET_TOTAL_AMOUNT, "-1");
            Total_Coins_recived.setText(player_idd);
/*
            popup__image.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(Lucky7.this.getResources(), Lucky7_Card_images.get(winner_card_no), 300, 200));
*/
            dice_number.setText(ssssss);
            win.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(Lucky7.this.getResources(),Win_image.get(0),300,200));
        }

        if(!Lucky7.this.isFinishing()){
        Result_dialog.show();}
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        play_music.stop();

        super.onPause();
    }

    @Override
    protected void onStart() {
        play_music= MediaPlayer.create(context,R.raw.happy_dream);
        play_music.setLooping(true);
        play_music.start();

        super.onStart();
    }

    @Override
    protected void onResume() {
        play_music.stop();
        play_music= MediaPlayer.create(context,R.raw.happy_dream);
        play_music.setLooping(true);
        play_music.start();
        super.onResume();
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            // Handle the camera action
            play_music.stop();
            Intent intent= new Intent(Lucky7.this,Home.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.points_report) {
            play_music.stop();
            Intent intent= new Intent(Lucky7.this,PointsReport.class);
            startActivity(intent);
            finish();

/*
        } else if (id == R.id.daily_summ_report) {

        } else if (id == R.id.all_summary_report) {

        } else if (id == R.id.declared_transactions) {

        } else if (id == R.id.declare_report) {

        } else if (id == R.id.share) {*/

        }else if(id==R.id.coins_report){
            play_music.stop();
            Intent intent= new Intent(Lucky7.this,TransactionHistory.class);
            startActivity(intent);
            finish();
        }
        else if (id == R.id.logout) {
            logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to logout?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        String URL = "https://delhidiamond.online/index.php/logout";
                        final Map<String, String> params = new HashMap<String, String>();
                        final String agent_id = sharedPreferences.getString(Config.PLAYER_ID, "-1");
                        params.put("user_id",agent_id);


                        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, URL, params, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("Response: ", response.toString());
                                /*        Toast.makeText(Home.this,response.toString(),Toast.LENGTH_LONG).show();
                                 */

                                SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);
                                //Getting editor
                                SharedPreferences.Editor editor = preferences.edit();

                                //Puting the value false for loggedin
                                editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);

                                //Putting blank value to email
                                editor.putString(Config.USER_SHARED_PREF, "");

                                //Saving the sharedpreferences
                                editor.commit();

                                //Starting login activity
                                play_music.stop();
                                Intent intent = new Intent(Lucky7.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError response) {
                                Log.d("Response: ", response.toString());
                            }
                        });
                        RequestQueue requestQueue = Volley.newRequestQueue(Lucky7.this);
                        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy( 5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                        requestQueue.add(jsObjRequest);}
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


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            countDownTimer.cancel();
            Intent intent = new Intent(Lucky7.this, Home.class);
            play_music.stop();
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
