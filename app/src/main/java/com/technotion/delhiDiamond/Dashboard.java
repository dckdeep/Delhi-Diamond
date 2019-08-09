package com.technotion.delhiDiamond;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.technotion.delhiDiamond.Adapters.closecardrecycler;
import com.technotion.delhiDiamond.Adapters.opencardrecycler;
import com.technotion.delhiDiamond.sever_classes.Config;
import com.technotion.delhiDiamond.sever_classes.CustomRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    int timer_check=1;
    int screenWidth;
    int screenHeight;
    Handler handler12;
    TimerTask doTask;
    Runnable runnable ;
    Dialog Result_dialog;
    MediaPlayer play_music;
    Context context;
    final Handler handler = new Handler();
    ArrayList<Integer> myImageList = new ArrayList<>();
    ArrayList<Integer> myImageList1 = new ArrayList<>();
    ArrayList<String> mIconText = new ArrayList<>();
    ArrayList<String> mIconText1 = new ArrayList<>();
    RecyclerView vieww;
    TextView total_amount,timer,lock_bid_text;
    Button start;
    int get,count_check=0,out_check=0;
    ImageView roation_card,roation_card2,roation_card3,roation_card4,roation_card5,roation_card6,roation_card7,roation_card8,lock_bid__icon,icon_card;
    CountDownTimer countDownTimer;
    long timeLeft =120000;
    long timeLeft_for_card_postion=10000;
    float arrowUpX;
    float arrowUpY;
    float arrowDownX;
    float arrowDownY;
    float arrowLeftX;
    float arrowLeftY;
    float arrowRightX;
    float arrowRightY;
    float arrowUpX1;
    float arrowUpY2;
    float arrowDownX1;
    float arrowDownY2;
    float arrowLeftX1;
    float arrowLeftY2;
    float arrowRightX1;
    float arrowRightY2;
    int count=0;
    int check=0;
    int tempvalue=0;
    long end_game_time=0;
    int id_bar_card= R.drawable.card_1;
    int id_bar_bird= R.drawable.birds;

    long current_game_time=0;
    int idd=R.drawable.crush_ball;
    RelativeLayout rotaion_view,second_drop_shuffle;
    LinearLayout lock_bid;
    int get_count,count_request_game=0;
    Timer timer1;
    Handler handler1 = new Handler();
    Timer timer11= new Timer();

    TextView text_rules,hinditext_rules;
    ArrayList<String>card_nos= new ArrayList<>();
    ArrayList<String>bid_of_card = new ArrayList<>();

    ArrayList<Integer> Win_image = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context =getApplication();
        Bundle bundle = getIntent().getExtras();
        get  = bundle.getInt("open");
        tempvalue=get;
        get_count = bundle.getInt("count");
/*

        request_game_type(get);

*/

        text_rules=findViewById(R.id.text_rules);
        hinditext_rules=findViewById(R.id.hinditext_rules);
        if(get==1){
            text_rules.setText("जीतने पर 100 के 1200 पॉइंट्स मिलेंगे, 10 से कम की  बेट नहीं लगेगी ।");
            hinditext_rules.setText("कृपया ऊपर दिए गए कार्ड्स में से चुनें ");
        }if(get==2){
            text_rules.setText("जीतने पर 100 के 1100 पॉइंट्स मिलेंगे, 10 से कम की  बेट नहीं लगेगी ।");
            hinditext_rules.setText("कृपया ऊपर दी गई चीज़ो में से चुनें ");
        }
        Win_image.clear();
        Win_image.add(R.drawable.lose);
        Win_image.add(R.drawable.win);

        lock_bid=findViewById(R.id.lock_bid);
        lock_bid.setVisibility(View.INVISIBLE);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        RecyclerView.LayoutManager manager = new GridLayoutManager(Dashboard.this,4);
        total_amount= findViewById(R.id.total_bet_amount);
        vieww =findViewById(R.id.closecard_recycler);
        timer = findViewById(R.id.timer_text);

        rotaion_view=findViewById(R.id.rotaion_view);
        vieww.setHasFixedSize(true);
        vieww.setLayoutManager(manager);
        closecard_image();
        start=findViewById(R.id.startgame);
        start.setVisibility(View.INVISIBLE);
        start.setEnabled(false);
        second_drop_shuffle= findViewById(R.id.second_drop_shuffle);

        handler12 = new Handler();
        Result_dialog= new Dialog(Dashboard.this);
        Result_dialog.setContentView(R.layout.display_result);
        timer1 = new Timer();
        roation_card=findViewById(R.id.rotation_card1);
        roation_card2=findViewById(R.id.rotation_card2);
        roation_card3=findViewById(R.id.rotation_card3);
        roation_card4=findViewById(R.id.rotation_card4);
        roation_card5=findViewById(R.id.rotation_card5);
        roation_card6=findViewById(R.id.rotation_card6);
        roation_card7=findViewById(R.id.rotation_card7);
        roation_card8=findViewById(R.id.rotation_card8);
        lock_bid__icon= findViewById(R.id.lock_bid__icon);
        lock_bid_text =findViewById(R.id.lock_bid_text);
        icon_card=findViewById(R.id.icon_card);

        if(get==1){
            rotaion_view.setVisibility(View.INVISIBLE);
            second_drop_shuffle.setVisibility(View.INVISIBLE);
            icon_card.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(Dashboard.this.getResources(),id_bar_card,300,200));
            request_game_type(tempvalue);

        }
        if(get==2){
            rotaion_view.setVisibility(View.INVISIBLE);
            second_drop_shuffle.setVisibility(View.INVISIBLE);
            icon_card.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(Dashboard.this.getResources(),id_bar_bird,300,200));
            icon_card.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(Dashboard.this.getResources(),id_bar_bird,300,200));
            request_game_type(tempvalue);

        }

        /*
        String s = String.valueOf(get);
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();*/
        /*start.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           if(count==0) {
               count++;


               if(get==1) {

                   request_game_type(1);
                   opencard_image();
                   opencard_display();
                   total_amount.setText("0");

               }

               if(get==2){
                   myImageList.clear();
                   request_game_type(2);
                   second_game_card_image();

                   opencard_display();
                   total_amount.setText("0");
               }
               start.setVisibility(View.INVISIBLE);

           }else{
               count=0;
              Intent intent = new Intent(Dashboard.this,Dashboard.class);
               intent.putExtra("open",tempvalue);
              startActivity(intent);
              finish();
           }
        }
    });*/
/*
        if(get==1){
            SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            final String game_id = sharedPreferences.getString(Config.GAME1_ID,"-1");
            final String check_game_id = sharedPreferences.getString(Config.Game1_Check_ID,"-1");

            if(game_id.equals(check_game_id)){
                myImageList.clear();
                start.setVisibility(View.INVISIBLE);
                start.setEnabled(false);
                second_drop_shuffle.setVisibility(View.INVISIBLE);
                rotaion_view.setVisibility(View.INVISIBLE);
                opencard_image();
                opencard_display();
                count_request_game++;
                request_game_type(tempvalue);
            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Config.Game1_Check_ID,game_id);
                editor.putString(Config.G1_COUNT_OPEN,"two");
                editor.commit();
                count_request_game++;
                delay_activity();
            }
        }if(get==2){
            SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            final String game_id = sharedPreferences.getString(Config.GAME2_ID,"-1");
            final String check_game_id = sharedPreferences.getString(Config.Game2_Check_ID,"-1");

            if(game_id.equals(check_game_id)){
                mIconText1.clear();
                start.setVisibility(View.INVISIBLE);
                start.setEnabled(false);
                rotaion_view.setVisibility(View.INVISIBLE);
                second_drop_shuffle.setVisibility(View.INVISIBLE);
                second_game_card_image();
                opencard_display();
                count_request_game++;
                request_game_type(tempvalue);
            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Config.Game2_Check_ID,game_id);
                editor.putString(Config.G2_COUNT_OPEN,"two_");
                editor.commit();
                count_request_game++;
                delay_activity();
            }

        }*/
        /*if(get_count==0){
        delay_activity();}
        else{

            if(get==1) {
                myImageList.clear();
                start.setVisibility(View.INVISIBLE);
                start.setEnabled(false);
                second_drop_shuffle.setVisibility(View.INVISIBLE);
                rotaion_view.setVisibility(View.INVISIBLE);
                opencard_image();
                opencard_display();
                request_game_type(tempvalue);

            }if(get==2){
                mIconText1.clear();
                start.setVisibility(View.INVISIBLE);
                start.setEnabled(false);
                rotaion_view.setVisibility(View.INVISIBLE);
                second_drop_shuffle.setVisibility(View.INVISIBLE);
                second_game_card_image();
                opencard_display();
                request_game_type(tempvalue);
                           }
        }*/
        refresh();
/*
      show_pop(0,0);
*/

    }
    private void refresh() {


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
    private void delay_activity() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                //
                if(tempvalue==1){
                    second_drop_shuffle.setVisibility(View.INVISIBLE);


                    moving();
                }if(tempvalue==2) {

                    countDownTimer = new CountDownTimer(timeLeft_for_card_postion,5000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            fun();

                        }

                        @Override
                        public void onFinish() {
                            rotaion_view.setVisibility(View.INVISIBLE);
                            second_drop_shuffle.setVisibility(View.INVISIBLE);
/*
                            closecard_display();
*/

                            myImageList.clear();
                            request_game_type(2);
                            second_game_card_image();

                            opencard_display();
                            total_amount.setText("0");
                        }
                    }.start();

                }//
            }
        }, 100);
    }

    private void convert_min_into_mini() {
        int curr_min =new Time(System.currentTimeMillis()).getMinutes();
        current_game_time =TimeUnit.MINUTES.toMillis(curr_min);
        int curr_sec = new Time(System.currentTimeMillis()).getSeconds();
        current_game_time = current_game_time+TimeUnit.SECONDS.toMillis(curr_sec);

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if(get==1) {
            final String game_start_time = sharedPreferences.getString(Config.GAME_START_TIME_g1, "-1");
            /* String game_start_time ="10:10:02";*/
            String[] parts = game_start_time.split(":");
            for(int i=0; i<parts.length;i++) {
                String s = parts[i];
                if (i != 0) {
                    String make_game_end_time = convertstring_into_int(s, i);
                    get_end_time(make_game_end_time, i);
                }
            }
        }if(get==2){
            final String game_start_time = sharedPreferences.getString(Config.GAME_START_TIME_g2, "-1");
            String[] parts = game_start_time.split(":");
            for(int i=0; i<parts.length;i++) {
                String s = parts[i];
                if (i != 0) {
                    String make_game_end_time = convertstring_into_int(s, i);
                    get_end_time(make_game_end_time, i);
                }
            }
        }





 /*   // UPDATE timeLeft for playing game on current time
      String xyz = String.valueOf(end_game_time-current_game_time);
        Toast.makeText(Dashboard.this,xyz,Toast.LENGTH_LONG).show();*/
        timeLeft =end_game_time-current_game_time;
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
            if(get==1){
                x = i + 5;
            }if(get==2){
                x = i + 4;

            }
            return s= String.valueOf(x);
        }else{
            return s= String.valueOf(i);
        }

    }

    private void request_game_type(int i) {
        String game_type = String.valueOf(i);
        String url = "https://delhidiamond.online/index.php/select-game";
        Map<String, String> params = new HashMap<String, String>();
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String player_id = sharedPreferences.getString(Config.PLAYER_ID,"-1");
        params.put("user_id",player_id);
        params.put("game_type", game_type);
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", response.toString());
                /*  Toast.makeText(Dashboard.this,response.toString(),Toast.LENGTH_LONG).show();*/
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
                        if(get==1) {
                            editor.putString(Config.GAME1_ID, game_id);
                            editor.putString(Config.GAME_START_TIME_g1,starting_time);
                        }if(get==2){
                            editor.putString(Config.GAME2_ID, game_id);
                            editor.putString(Config.GAME_START_TIME_g2,starting_time);
                        }

                        //Saving values to editor
                        editor.commit();

                    }


                    JSONObject jsonArray1 = response.getJSONObject("cards");



                    JSONObject jsonobj2 = response.getJSONObject("bid_amount");
                    /*Iterator x = jsonobj2.keys();
                    JSONArray jsonArray2 = new JSONArray();

                    while (x.hasNext()){
                        String key = (String) x.next();
                        jsonArray2.put(jsonobj2.get(key));
                    }*/
                    if(get==1) {
                        bid_of_card.clear();
                        for (int i = 1; i < 14; i++) {
                            String xyz = String.valueOf(i);
                            String get_bid_amount_on_card = jsonobj2.getString(xyz);
                            /*   Toast.makeText(Dashboard.this, get_bid_amount_on_card, Toast.LENGTH_LONG).show();
                             */   bid_of_card.add(get_bid_amount_on_card);
                        }
                       /* String scx = TextUtils.join(",", bid_of_card);
                        Toast.makeText(Dashboard.this, scx, Toast.LENGTH_LONG).show();
                       */ opencard_image();
                        opencard_display();
                        int total=0;
                        for(int x =0 ;x<mIconText.size();x++){
                            int total_points = Integer.parseInt(mIconText.get(x));
                            total=total+total_points;
                        }
                        String total_bet_amount= String.valueOf(total);

                        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                        //Creating editor to store values to shared preferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        String jsonObject = response.getString("transaction_id");
                        editor.putString(Config.TRANSACTION_ID_g1,jsonObject);

                        //Adding values to editor
                        editor.putString(Config.G1_SET_TOTAL_AMOUNT, total_bet_amount);
                        editor.commit();
                        final String total___Amount = sharedPreferences.getString(Config.G1_SET_TOTAL_AMOUNT, "-1");
                        total_amount.setText(total___Amount);

                    }if(get==2){
                        bid_of_card.clear();
                        for (int i = 1; i < 13; i++) {
                            String xyz = String.valueOf(i);
                            String get_bid_amount_on_card = jsonobj2.getString(xyz);
                            /*    Toast.makeText(Dashboard.this, get_bid_amount_on_card, Toast.LENGTH_LONG).show();
                             */    bid_of_card.add(get_bid_amount_on_card);
                        }
                        /*String scx = TextUtils.join(",", bid_of_card);
                         *//* Toast.makeText(Dashboard.this, scx, Toast.LENGTH_LONG).show();
                         */ second_game_card_image();
                        opencard_display();
                        int total=0;
                        for(int x =0 ;x<mIconText1.size();x++){
                            int total_points = Integer.parseInt(mIconText1.get(x));
                            total=total+total_points;
                        }
                        String total_bet_amount= String.valueOf(total);

                        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                        //Creating editor to store values to shared preferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        String jsonObject = response.getString("transaction_id");
                        editor.putString(Config.TRANSACTION_ID_g2,jsonObject);

                        //Adding values to editor
                        editor.putString(Config.G2_SET_TOTAL_AMOUNT, total_bet_amount);
                        editor.commit();
                        final String total___Amount = sharedPreferences.getString(Config.G2_SET_TOTAL_AMOUNT, "-1");
                        total_amount.setText(total___Amount);

                    }

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
        requestQueue.add(jsObjRequest);
    }
    private void fun() {
        ImageView imageView = (ImageView) findViewById(R.id.rotation_card1221);
        ImageView imageView1 = (ImageView) findViewById(R.id.rotation_card122);
        ImageView imageView2 = (ImageView) findViewById(R.id.rotation_card12_2_);
        ImageView imageView3 = (ImageView) findViewById(R.id.rotation_card122_);
        ImageView imageView4 = (ImageView) findViewById(R.id.rotation_card1_2_2_);
        ImageView imageView5 = (ImageView) findViewById(R.id.rotation_card1_2__2_);

        imageView.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(Dashboard.this.getResources(),idd,100,120));
        imageView1.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(Dashboard.this.getResources(),idd,100,120));
        imageView2.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(Dashboard.this.getResources(),idd,100,120));
        imageView3.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(Dashboard.this.getResources(),idd,100,120));
        imageView4.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(Dashboard.this.getResources(),idd,100,120));
        imageView5.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(Dashboard.this.getResources(),idd,100,120));

        float bottomOfScreen = getResources().getDisplayMetrics()
                .heightPixels - (imageView.getHeight() * 4);
        //bottomOfScreen is where you want to animate to


        imageView.animate()
                .translationY(bottomOfScreen)
                .setInterpolator(new AccelerateInterpolator())
                .setInterpolator(new BounceInterpolator())
                .setDuration(5000);
        imageView1.animate()
                .translationY(bottomOfScreen)
                .setInterpolator(new AccelerateInterpolator())
                .setInterpolator(new BounceInterpolator())
                .setDuration(5000);
        imageView2.animate()
                .translationY(bottomOfScreen)
                .setInterpolator(new AccelerateInterpolator())
                .setInterpolator(new BounceInterpolator())
                .setDuration(5000);
        imageView3.animate()
                .translationY(bottomOfScreen)
                .setInterpolator(new AccelerateInterpolator())
                .setInterpolator(new BounceInterpolator())
                .setDuration(5000);
        imageView4.animate()
                .translationY(bottomOfScreen)
                .setInterpolator(new AccelerateInterpolator())
                .setInterpolator(new BounceInterpolator())
                .setDuration(5000);
        imageView5.animate()
                .translationY(bottomOfScreen)
                .setInterpolator(new AccelerateInterpolator())
                .setInterpolator(new BounceInterpolator())
                .setDuration(5000);

    }

    private void second_game_card_image() {
        myImageList1.clear();
        myImageList1.add(R.drawable.chidiyacard___1);


        myImageList1.add(R.drawable.chidiyacard___2);


        myImageList1.add(R.drawable.chidiyacard___3);

        myImageList1.add(R.drawable.chidiyacard___4);


        myImageList1.add(R.drawable.chidiyacard___5);


        myImageList1.add(R.drawable.chidiyacard___6);


        myImageList1.add(R.drawable.chidiyacard___7);


        myImageList1.add(R.drawable.chidiyacard___8);


        myImageList1.add(R.drawable.chidiyacard___9);


        myImageList1.add(R.drawable.chidiyacard___10);


        myImageList1.add(R.drawable.chidiyacard___11);


        myImageList1.add(R.drawable.chidiyacard___12);


        add_coins(2);


    }


    public void moving(){


        WindowManager wm = getWindowManager();

        Display display =wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth=size.x;
        screenHeight=size.y;

        roation_card.setX(-80.0f);
        roation_card.setY(-80.0f);
        roation_card2.setX(-80.0f);
        roation_card2.setY(screenHeight+80.0f);
        roation_card3.setX(screenWidth+80.0f);
        roation_card3.setY(-80.0f);
        roation_card4.setX(-80.0f);
        roation_card4.setY(-80.0f);
        roation_card5.setX(-120.0f);
        roation_card5.setY(-120.0f);

        roation_card6.setX(-120.0f);
        roation_card6.setY(screenHeight+120.0f);
        roation_card7.setX(screenWidth+120.0f);
        roation_card7.setY(-120.0f);
        roation_card8.setX(-120.0f);
        roation_card8.setY(-120.0f);


        countDownTimer = new CountDownTimer(timeLeft_for_card_postion,2500) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer_move();

            }

            @Override
            public void onFinish() {
                rotaion_view.setVisibility(View.VISIBLE);
            /*  myImageList.clear();
              request_game_type(1);
              opencard_image();
              opencard_display();
              total_amount.setText("0");*/


                /*closecard_display();
                 */
                /*opencard_display();*/
            }
        }.start();


        //  timer_move();
    }

    private void timer_move() {
        timer11.schedule(new TimerTask() {
            @Override
            public void run() {
                handler1.post(new Runnable() {
                    @Override
                    public void run() {

                        changePos();
                    }
                });
            }
        }, 0, 70);
    }

    public void changePos() {
        arrowUpY-=10;
        if(roation_card.getY()+roation_card.getHeight()<0){
            arrowUpX=(float)Math.floor(Math.random()*(screenWidth-roation_card.getWidth()));
            arrowUpY = screenHeight+100.0f;
        }
        roation_card.setX(arrowUpX);
        roation_card.setY(arrowUpY);
        roation_card.animate().rotation(roation_card.getRotation()-360).start();
        //Animation aniRotate = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_clockwise);
        //roation_card.startAnimation(aniRotate);

        arrowDownY+=10;
        if(roation_card2.getY()>screenHeight){
            arrowDownX=(float)Math.floor(Math.random()*(screenWidth-roation_card2.getWidth()));
            arrowDownY =-100.0f;
        }
        roation_card2.setX(arrowDownX);
        roation_card2.setY(arrowDownY);
        roation_card2.animate().rotation(roation_card2.getRotation()-360).start();

        arrowRightX+=10;
        if(roation_card3.getX()>screenWidth){
            arrowRightX=-100.0f;
            arrowRightY=(float)Math.floor(Math.random()*(screenHeight-roation_card3.getHeight()));
        }
        roation_card3.setX(arrowRightX);
        roation_card3.setY(arrowRightY);
        roation_card3.animate().rotation(roation_card3.getRotation()-360).start();

        arrowLeftX-=10;
        if(roation_card4.getX()+roation_card4.getWidth()<0){
            arrowLeftX=screenWidth+100.0f;
            arrowLeftY=(float)Math.floor(Math.random()*(screenHeight-roation_card4.getHeight()));
        }
        roation_card4.setX(arrowLeftX);
        roation_card4.setY(arrowLeftY);
        roation_card4.animate().rotation(roation_card4.getRotation()-360).start();


        arrowUpY2-=5;
        if(roation_card5.getY()+roation_card5.getHeight()<0){
            arrowUpX1=(float)Math.floor(Math.random()*(screenWidth-roation_card5.getWidth()));
            if(arrowUpX1==arrowUpX){
                arrowUpX1=(float)Math.floor(Math.random()*(screenWidth-roation_card5.getWidth()));
            }
            arrowUpY2 = screenHeight+100.0f;
        }
        roation_card5.setX(arrowUpX1);
        roation_card5.setY(arrowUpY2);
        roation_card5.animate().rotation(roation_card5.getRotation()-360).start();



        arrowDownY2+=5;
        if(roation_card6.getY()>screenHeight){
            arrowDownX1=(float)Math.floor(Math.random()*(screenWidth-roation_card6.getWidth()));
            if(arrowDownX1==arrowDownX){
                arrowDownX1=(float)Math.floor(Math.random()*(screenWidth-roation_card6.getWidth()));

            }
            arrowDownY2 =-100.0f;
        }
        roation_card6.setX(arrowDownX1);
        roation_card6.setY(arrowDownY2);
        roation_card6.animate().rotation(roation_card6.getRotation()-360).start();

        arrowRightX1+=5;
        if(roation_card7.getX()>screenWidth){
            arrowRightX1=-100.0f;
            arrowRightY2=(float)Math.floor(Math.random()*(screenHeight-roation_card7.getHeight()));
            if(arrowRightY2==arrowRightY){
                arrowRightY2=(float)Math.floor(Math.random()*(screenHeight-roation_card7.getHeight()));
            }
        }
        roation_card7.setX(arrowRightX1);
        roation_card7.setY(arrowRightY2);
        roation_card7.animate().rotation(roation_card7.getRotation()-360).start();

        arrowLeftX1-=5;
        if(roation_card8.getX()+roation_card4.getWidth()<0){
            arrowLeftX1=screenWidth+100.0f;
            arrowLeftY2=(float)Math.floor(Math.random()*(screenHeight-roation_card8.getHeight()));
            if(arrowLeftY2==arrowLeftY){
                arrowLeftY2=(float)Math.floor(Math.random()*(screenHeight-roation_card8.getHeight()));
            }
        }
        roation_card8.setX(arrowLeftX1);
        roation_card8.setY(arrowLeftY2);
        roation_card8.animate().rotation(roation_card8.getRotation()-360).start();


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
                if(!Dashboard.this.isFinishing()) {
                    updatetime();
                }

            }

            @Override
            public void onFinish() {
                start.setVisibility(View.VISIBLE);

                timer.setText("0:00");
                if(!Dashboard.this.isFinishing()) {
                    /*   Result_dialog.show();
                     */   Result_dialog.dismiss();
                    if(get==1) {
                        if (timer_check == 1) {
                            SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                            //Creating editor to store values to shared preferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(Config.G1_COUNT_OPEN, "one");
                            editor.putString(Config.G1_SET_TOTAL_AMOUNT, "0");
                            editor.putString(Config.G1_coins, "0");
                            editor.putString(Config.count_G1_trns, "0");

                            editor.commit();
                            play_music.stop();
                            Intent intent = new Intent(Dashboard.this, Home.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                    if(get==2){
                        if(timer_check==1) {
                            SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                            //Creating editor to store values to shared preferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(Config.G2_COUNT_OPEN, "one_");
                            editor.putString(Config.G2_SET_TOTAL_AMOUNT, "0");
                            editor.putString(Config.G2_coins, "0");
                            editor.putString(Config.count_G2_trns, "0");
                            editor.commit();
                            play_music.stop();
                            Intent intent = new Intent(Dashboard.this, Home.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }

            }
        }.start();

    }

    private void updatetime() {
        int time_in_min = (int)timeLeft/60000;
        int time_in_sec = (int)timeLeft%60000/1000;

        update_text_time();
        if(time_in_min==0) {
            if(time_in_sec<=30){
                if(get==1) {

                    lock_bid.setVisibility(View.VISIBLE);
                    lock_bid_text.setText("Bid Is Locked");
                    closecardrecycler attributes = new closecardrecycler(myImageList, mIconText, Dashboard.this);
                    vieww.setAdapter(attributes);
                    /*  get_locked_bid(get);*/
                }
            }
            if(time_in_sec<=30){

                if (get==2){

                    lock_bid.setVisibility(View.VISIBLE);
                    lock_bid_text.setText("Bid Is Locked");
                    closecardrecycler attributes = new closecardrecycler(myImageList1, mIconText1, Dashboard.this);
                    vieww.setAdapter(attributes);
                    /* get*/
                }
            }

            if (time_in_sec <= 20) {
                if(get==1) {
                    if(count_request_game==0){
                        count_request_game++;
                        play_music.stop();
                        play_music= MediaPlayer.create(context,R.raw.card_shuffle);
                        play_music.setLooping(true);
                        play_music.start();
                        rotaion_view.setVisibility(View.VISIBLE);
                        moving();
                    }
                    if (time_in_sec <= 10) {
                        if (count_check == 0) {
                            play_music.stop();
                            /*rotaion_view.setVisibility(View.VISIBLE);*/
                            rotaion_view.setVisibility(View.VISIBLE);
                            moving();
                            get_result();



                        }
                    }
                }
            }
            if (time_in_sec <= 20) {
                if(get==2){
                    if(count_request_game==0){
                        count_request_game++;
                        rotaion_view.setVisibility(View.VISIBLE);
                        play_music.stop();
                        play_music= MediaPlayer.create(context,R.raw.card_shuffle);
                        play_music.setLooping(true);
                        play_music.start();
                        moving();
                    }
                    if (time_in_sec <= 10) {
                        if (count_check == 0) {
                            play_music.stop();
                            /* rotaion_view.setVisibility(View.VISIBLE);*/
                            rotaion_view.setVisibility(View.VISIBLE);
                            moving();
                            get_result();


                        }
                    }
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
        timer1.cancel();
        doTask.cancel();
        handler12.removeCallbacks(runnable);
        if(get==1) {
            String game_type = String.valueOf(1);
            final String url = "https://delhidiamond.online/index.php/fetch-results";
            Map<String, String> params = new HashMap<String, String>();


            SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            final String game_id = sharedPreferences.getString(Config.GAME1_ID,"-1");

            params.put("game_type", game_type);
            params.put("game_id", game_id);

            CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    Log.d("Response: ", response.toString());
                    /*   Toast.makeText(Dashboard.this, response.toString(), Toast.LENGTH_LONG).show();
                     */   try {
                        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();


                        String card_no = response.getString("card_opened");
                        editor.putString(Config.CARD_ID, card_no);

                        editor.commit();
                        JSONArray jsonArray = response.getJSONArray("winning_users");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject employee = jsonArray.getJSONObject(i);


                            String player_id = employee.getString("user_id");
                            String tokens = employee.getString("win_coins");


                            //Adding values to editor
                            editor.putString(Config.Win_player_id, player_id);
                            /* editor.putString(Config.GET_TOKENS, tokens);*/
                            //Saving values to editor
                            editor.commit();

                            final String player_idd = sharedPreferences.getString(Config.PLAYER_ID, "-1");
                            final String Win_player_id = sharedPreferences.getString(Config.Win_player_id, "-1");

                            /*    Toast.makeText(Lucky7.this,player_idd+"=="+Win_player_id,Toast.LENGTH_LONG).show();
                             */
                            if (player_idd.equals(Win_player_id)) {
                                editor.putString(Config.GET_TOKENS, tokens);
                                //Saving values to editor
                                editor.commit();
                                check = 1;
                            }
                        }

                        final String win_card_no = sharedPreferences.getString(Config.CARD_ID, "-1");
                        int winner_card_no = Integer.parseInt(win_card_no) - 1;


                        if (check == 1) {

                            final String player_idd = sharedPreferences.getString(Config.G1_SET_TOTAL_AMOUNT, "-1");
                            final String win_card_amount = sharedPreferences.getString(Config.GET_TOKENS, "-1");
                            int check_card_coins = Integer.parseInt(win_card_amount);
                            int total_bid = Integer.parseInt(player_idd);
                            if (check_card_coins >= total_bid) {
                                show_pop(1, winner_card_no,1);

                            } if(check_card_coins<total_bid) {
                                show_pop(1, winner_card_no,0);
                            }

                               /* if(get==2){
                                    final String player_idd = sharedPreferences.getString(Config.G2_SET_TOTAL_AMOUNT, "-1");
                                    String Win_player_id = mIconText.get(winner_card_no);
                                    int check_card_coins = Integer.parseInt(Win_player_id);
                                    int total_bid = Integer.parseInt(player_idd);
                                    if (check_card_coins > total_bid) {
                                        show_pop(1, winner_card_no);
                                    } else {
                                        show_pop(0, winner_card_no);
                                    }*/
                            // }
                        }
                        if (check == 0) {
                            /*    Toast.makeText(Dashboard.this, "fgfghfhghfh", Toast.LENGTH_LONG).show();
                             */   show_pop(0, winner_card_no,0);
                        }






/*
                    convert_min_into_mini();
*/

                    } catch (JSONException e) {
                        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);


                        final String win_card_no = sharedPreferences.getString(Config.CARD_ID, "-1");
                        int winner_card_no = Integer.parseInt(win_card_no) - 1;

                        if (check == 0) {
                            /*   Toast.makeText(Lucky7.this, win_card_no, Toast.LENGTH_LONG).show();
                             */
                            show_pop(0, winner_card_no,0);
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
        if(get==2){
            String game_type = String.valueOf(2);
            final String url = "https://delhidiamond.online/index.php/fetch-results";
            Map<String, String> params = new HashMap<String, String>();


            SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            final String game_id = sharedPreferences.getString(Config.GAME2_ID,"-1");

            params.put("game_type", game_type);
            params.put("game_id", game_id);

            CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    Log.d("Response: ", response.toString());
                    /*  Toast.makeText(Dashboard.this, response.toString(), Toast.LENGTH_LONG).show();*/
                    try {
                        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();


                        String card_no = response.getString("card_opened");
                        editor.putString(Config.CARD_ID, card_no);

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

                            /*    Toast.makeText(Lucky7.this,player_idd+"=="+Win_player_id,Toast.LENGTH_LONG).show();
                             */
                            if (player_idd.equals(Win_player_id)) {
                                check = 1;
                                editor.putString(Config.GET_TOKENS, tokens);
                                //Saving values to editor
                                editor.commit();
                            }
                        }

                        final String win_card_no = sharedPreferences.getString(Config.CARD_ID, "-1");
                        int winner_card_no = Integer.parseInt(win_card_no) - 1;

                        if (check == 1) {
                            final String player_idd = sharedPreferences.getString(Config.G2_SET_TOTAL_AMOUNT, "-1");
                            final String win_card_amount = sharedPreferences.getString(Config.GET_TOKENS, "-1");
                            int check_card_coins = Integer.parseInt(win_card_amount);
                            int total_bid = Integer.parseInt(player_idd);
                            if (check_card_coins >= total_bid) {
                                show_pop(1, winner_card_no,1);
                            }else{
                                show_pop(1, winner_card_no,0);
                            }
                        }
                        if (check == 0) {
                            /* Toast.makeText(Lucky7.this, win_card_no, Toast.LENGTH_LONG).show();
                             */
                            show_pop(0, winner_card_no,0);
                        }






/*
                    convert_min_into_mini();
*/

                    } catch (JSONException e) {
                        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);


                        final String win_card_no = sharedPreferences.getString(Config.CARD_ID, "-1");
                        int winner_card_no = Integer.parseInt(win_card_no) - 1;

                       /* if (check == 1) {
                            show_pop(1, winner_card_no);
                        }*/
                        if (check == 0) {
                            /*   Toast.makeText(Lucky7.this, win_card_no, Toast.LENGTH_LONG).show();
                             */
                            show_pop(0, winner_card_no,0);
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
        count_check++;
    }

    private void show_pop(int i, int winner_card_no,int x) {
        Result_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Result_dialog.setTitle("");
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String coins = sharedPreferences.getString(Config.GET_TOKENS, "-1");
        final ImageView popup__image = (ImageView) Result_dialog.findViewById(R.id.WIN_CARD);
        final TextView Total_Coins_recived = Result_dialog.findViewById(R.id.Coins_display);
        final ImageView win = (ImageView) Result_dialog.findViewById(R.id.win);
        if (i == 1 && x == 1) {

            if (get == 1) {

                final String player_idd = sharedPreferences.getString(Config.G1_SET_TOTAL_AMOUNT, "-1");
                String get_tokens = sharedPreferences.getString(Config.GET_TOKENS, "-1");

                int p = Integer.parseInt(player_idd);

                int coins_check = Integer.parseInt(get_tokens);
                int result = coins_check-p;
                get_tokens = String.valueOf(result);
                Total_Coins_recived.setText(get_tokens);
                win.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(Dashboard.this.getResources(), Win_image.get(1), 300, 200));
                popup__image.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(Dashboard.this.getResources(), myImageList.get(winner_card_no), 300, 200));


            }
            if (get == 2) {
            /*    String winning_coins=mIconText1.get(winner_card_no);
                int coins__win = Integer.parseInt(winning_coins)*10;
                winning_coins = String.valueOf(coins__win);
                Total_Coins_recived.setText(winning_coins);
            */
                final String player_idd = sharedPreferences.getString(Config.G2_SET_TOTAL_AMOUNT, "-1");
                String get_tokens = sharedPreferences.getString(Config.GET_TOKENS, "-1");

                int p = Integer.parseInt(player_idd);
                int coins_check = Integer.parseInt(get_tokens);
                int result = coins_check-p;
                get_tokens = String.valueOf(result);
                Total_Coins_recived.setText(get_tokens);
                win.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(Dashboard.this.getResources(), Win_image.get(1), 300, 200));
                popup__image.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(Dashboard.this.getResources(), myImageList1.get(winner_card_no), 300, 200));

            }
        }
        if (i == 1 && x == 0) {


            if (get == 1) {
                final String player_idd = sharedPreferences.getString(Config.G1_SET_TOTAL_AMOUNT, "-1");
                String get_tokens = sharedPreferences.getString(Config.GET_TOKENS, "-1");

                int p = Integer.parseInt(player_idd);

                int coins_check = Integer.parseInt(get_tokens);
                int result = p-coins_check;
                get_tokens = String.valueOf(result);
                Total_Coins_recived.setText(get_tokens);
                win.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(Dashboard.this.getResources(), Win_image.get(0), 300, 200));
                popup__image.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(Dashboard.this.getResources(), myImageList.get(winner_card_no), 300, 200));


            }
            if (get == 2) {
            /*    String winning_coins=mIconText1.get(winner_card_no);
                int coins__win = Integer.parseInt(winning_coins)*10;
                winning_coins = String.valueOf(coins__win);
                Total_Coins_recived.setText(winning_coins);
            */ final String player_idd = sharedPreferences.getString(Config.G2_SET_TOTAL_AMOUNT, "-1");
                String get_tokens = sharedPreferences.getString(Config.GET_TOKENS, "-1");

                int p = Integer.parseInt(player_idd);
                int coins_check = Integer.parseInt(get_tokens);
                int result = p-coins_check;
                get_tokens = String.valueOf(result);
                Total_Coins_recived.setText(get_tokens);
                win.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(Dashboard.this.getResources(), Win_image.get(0), 300, 200));
                popup__image.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(Dashboard.this.getResources(), myImageList1.get(winner_card_no), 300, 200));

            }

        }
        if (i == 0 && x == 0) {
            if (get == 1) {
                String winning_coins = sharedPreferences.getString(Config.G1_SET_TOTAL_AMOUNT, "0");
                Total_Coins_recived.setText(winning_coins);
                win.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(Dashboard.this.getResources(), Win_image.get(0), 300, 200));
                popup__image.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(Dashboard.this.getResources(), myImageList.get(winner_card_no), 300, 200));
            }
            if (get == 2) {
                String winning_coins = sharedPreferences.getString(Config.G2_SET_TOTAL_AMOUNT, "0");
                Total_Coins_recived.setText(winning_coins);
                win.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(Dashboard.this.getResources(), Win_image.get(0), 300, 200));
                popup__image.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(Dashboard.this.getResources(), myImageList1.get(winner_card_no), 300, 200));

            }
        }

        if(!Dashboard.this.isFinishing()) {
            Result_dialog.show();
        }
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
    private void closecard_image(){
        mIconText.clear();
        myImageList.clear();
        myImageList.add(R.drawable.backcard);
        mIconText.add("0");

        myImageList.add(R.drawable.backcard);
        mIconText.add("0");

        myImageList.add(R.drawable.backcard);
        mIconText.add("0");

        myImageList.add(R.drawable.backcard);
        mIconText.add("0");

        myImageList.add(R.drawable.backcard);
        mIconText.add("0");

        myImageList.add(R.drawable.backcard);
        mIconText.add("0");

        myImageList.add(R.drawable.backcard);
        mIconText.add("0");

        myImageList.add(R.drawable.backcard);
        mIconText.add("0");

        myImageList.add(R.drawable.backcard);
        mIconText.add("0");

        myImageList.add(R.drawable.backcard);
        mIconText.add("0");

        myImageList.add(R.drawable.backcard);
        mIconText.add("0");

        myImageList.add(R.drawable.backcard);
        mIconText.add("0");

        myImageList.add(R.drawable.backcard);
        mIconText.add("0");






    }
    private void closecard_display(){
        Context context = vieww.getContext();
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(context,R.anim.card_suffual_animation);
        closecardrecycler attributes= new closecardrecycler(myImageList,mIconText,Dashboard.this);
        vieww.setAdapter(attributes);
        vieww.setLayoutAnimation(controller);
        vieww.getAdapter().notifyDataSetChanged();
        vieww.scheduleLayoutAnimation();

    }
    private void opencard_image(){
        myImageList.clear();
        myImageList.add(R.drawable.card1);

        myImageList.add(R.drawable.card2);

        myImageList.add(R.drawable.card3);

        myImageList.add(R.drawable.card4);

        myImageList.add(R.drawable.card5);

        myImageList.add(R.drawable.card6);

        myImageList.add(R.drawable.card7);

        myImageList.add(R.drawable.card8);

        myImageList.add(R.drawable.card9);

        myImageList.add(R.drawable.card10);

        myImageList.add(R.drawable.card11);

        myImageList.add(R.drawable.card12);

        myImageList.add(R.drawable.card13);



        add_coins(1);


    }

    private void add_coins(int add_coins_for_game) {
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        if(add_coins_for_game==1) {
            mIconText.clear();
            for(int i=0; i<bid_of_card.size(); i++){
                String get_bid_of_card = bid_of_card.get(i);
                mIconText.add(get_bid_of_card);
            }

/*

         final String g1_count_open = sharedPreferences.getString(Config.G1_COUNT_OPEN, "one");
            if (g1_count_open.equals("two")) {
                SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                //Getting editor
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(Config.G1_COUNT_OPEN, "three");
                editor.commit();
                mIconText.clear();
                for(int ss=0; ss<13; ss++){
                    mIconText.add("0");
                }

                String result = TextUtils.join(",", mIconText);


                editor.putString(Config.G1_coins, result);
                editor.commit();
            }


            if (g1_count_open.equals("three")) {
                mIconText.clear();
                SharedPreferences sharedPreferencess = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                final String game_start_time = sharedPreferencess.getString(Config.G1_coins, "-1");
                String[] parts = game_start_time.split(",");
                for (int ss = 0; ss < parts.length; ss++) {
                    mIconText.add(parts[ss]);

                }

            }
*/
        }
        if(add_coins_for_game==2) {
            mIconText1.clear();
            for(int i=0; i<bid_of_card.size(); i++){
                String get_bid_of_card = bid_of_card.get(i);
                mIconText1.add(get_bid_of_card);
            }

            /* final String g2_count_open = sharedPreferences.getString(Config.G2_COUNT_OPEN, "one_");

            if (g2_count_open.equals("two_")) {

                SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                //Getting editor
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(Config.G2_COUNT_OPEN, "three_");
                editor.commit();
                mIconText1.clear();
              for(int ss=1; ss<13; ss++){
                  mIconText1.add("0");
              }



                String result = TextUtils.join(",", mIconText1);


                editor.putString(Config.G2_coins, result);
                editor.commit();
            }


            if (g2_count_open.equals("three_")) {
                mIconText1.clear();

                SharedPreferences sharedPreferencess = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                final String game_start_time = sharedPreferencess.getString(Config.G2_coins, "-1");
                String[] parts = game_start_time.split(",");

                for (int ss = 0; ss < parts.length; ss++) {
                    mIconText1.add(parts[ss]);

                }
               *//* String s = String.valueOf(myImageList1.size());
                String x = String.valueOf(mIconText1.size());
              *//**//*  Toast.makeText(this,s+"===>"+x,Toast.LENGTH_LONG).show();*//*
             *//*  String result = TextUtils.join(",", mIconText1);
                Toast.makeText(this, result, Toast.LENGTH_LONG).show();*//*

            }*/
        }
    }


    private void opencard_display(){
        Context context = vieww.getContext();
        //ObjectAnimation controller = AnimationUtils.ObjectAnimation(context,R.anim.card_flip_anim);
        if(tempvalue==1) {
            LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(context,R.anim.card_suffual_animation);
            opencardrecycler attributes = new opencardrecycler(myImageList,mIconText, total_amount, get, Dashboard.this);
            vieww.setAdapter(attributes);
            vieww.setLayoutAnimation(controller);
            vieww.getAdapter().notifyDataSetChanged();
            vieww.scheduleLayoutAnimation();
        }if(tempvalue==2){
            LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(context,R.anim.card_suffual_animation);
            opencardrecycler attributes = new opencardrecycler(myImageList1, mIconText1, total_amount, get, Dashboard.this);
            vieww.setAdapter(attributes);
            vieww.setLayoutAnimation(controller);
            vieww.getAdapter().notifyDataSetChanged();
            vieww.scheduleLayoutAnimation();

        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        play_music.stop();
        timer_check=0;
        super.onPause();
    }

/*

    @Override
    protected void onStart() {
        play_music= MediaPlayer.create(context,R.raw.happy_dream);
        play_music.setLooping(true);
        play_music.start();

        super.onStart();
    }
*/

    @Override
    protected void onResume() {
        timer_check=1;
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
            Intent intent= new Intent(Dashboard.this,Home.class);
            startActivity(intent);
            finish();
        }else if(id==R.id.coins_report){
            play_music.stop();
            Intent intent= new Intent(Dashboard.this,TransactionHistory.class);
            startActivity(intent);
            finish();
        }
        else if (id == R.id.points_report) {
            play_music.stop();
            Intent intent= new Intent(Dashboard.this,PointsReport.class);
            startActivity(intent);
            finish();



        } else if (id == R.id.logout) {
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
                                Intent intent = new Intent(Dashboard.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError response) {
                                Log.d("Response: ", response.toString());
                            }
                        });
                        RequestQueue requestQueue = Volley.newRequestQueue(Dashboard.this);
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
            play_music.stop();
            Intent intent = new Intent(Dashboard.this, Home.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

}
