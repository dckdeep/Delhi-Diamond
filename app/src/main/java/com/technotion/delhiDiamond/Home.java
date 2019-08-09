package com.technotion.delhiDiamond;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.technotion.delhiDiamond.sever_classes.Config;
import com.technotion.delhiDiamond.sever_classes.CustomRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ImageButton delhi_diamond,second_game,lucky7,delhi_diamond_game,gali,ghaziabad,desawar,faridabad;
    TextView player_name,player_coins;
    Handler handler12,handler;
    TimerTask doTask,doTask1;
    Runnable runnable,runnable1 ;
    Context context;
    MediaPlayer play_music;
    Timer timer,timer1;
    int id_1 =R.drawable.onethree_patti;
    int id_2 =R.drawable.birds;
    int id_3 =R.drawable.lucky7;
    int id_4 =R.drawable.delhi_diamond_logo;
    int id_5=R.drawable.faridabad;
    int id_6 =R.drawable.ghaziabad;
    int id_7 =R.drawable.gali;
    int id_8 =R.drawable.desawar;
    Dialog Bahar_panel;
    ImageView close_bahar_panel;
    EditText bahar_input_number;
    EditText bahar_input_amount;
    Button submit_bahar_panel,ref;
    Dialog joda_panel;
    String game_status="";
    String currentVersion;
    TextView delhi_result,fari_result,ghazi_result,gali_result,des_result,bhar_panel_text;
    EditText joda_input_amount;
    Button submit_amount_joda_panel,cancel_action;
    ImageButton close_joda_panel;
    int count=0,game____type=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context=Home.this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        refresh();
        player_name = findViewById(R.id.player_name);
        player_coins=findViewById(R.id.current_coins);
        Bahar_panel= new Dialog(Home.this);
        Bahar_panel.setContentView(R.layout.delhidiamond_bahar);
         close_bahar_panel=Bahar_panel.findViewById(R.id.close_bahar_panel);
         bahar_input_number=Bahar_panel.findViewById(R.id.bahar_input_number);
         bahar_input_amount=Bahar_panel.findViewById(R.id.bahar_input_amount);
         submit_bahar_panel=Bahar_panel.findViewById(R.id.submit_bahar_panel);
         bhar_panel_text=Bahar_panel.findViewById(R.id.bhar_panel_text);
         bhar_panel_text.setText("Change Password");


         delhi_result=findViewById(R.id.delhi_diamond_result);
         fari_result=findViewById(R.id.faridabad_result);
         ghazi_result=findViewById(R.id.ghaziabad_result);
         gali_result=findViewById(R.id.gali_result);
         des_result=findViewById(R.id.desawar_result);
         des_result.setText("");
         fari_result.setText("");
         ghazi_result.setText("");
         gali_result.setText("");
         delhi_result.setText("");
        ref=findViewById(R.id.ref);
        ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addtransactions();
                SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                String players_coins = sharedPreferences.getString(Config.TOKENS, "Not Available");
                player_coins.setText(players_coins);
            }
        });
       /*  final MediaPlayer play_music = MediaPlayer.create(context,R.raw.happy_dream);
         play_music.setLooping(true);
         play_music.start();*/
        addtransactions();
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String players_name = sharedPreferences.getString(Config.USER_SHARED_PREF,"Not Available");
        String players_coins = sharedPreferences.getString(Config.TOKENS,"Not Available");

        player_name.setText(players_name);
        player_coins.setText(players_coins);
        delhi_diamond = findViewById(R.id.home_delhi_diamond_button);
        delhi_diamond.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(Home.this.getResources(),id_1,300,200));
        second_game=findViewById(R.id.second_game);
        second_game.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(Home.this.getResources(),id_2,300,200));
        delhi_diamond_game=findViewById(R.id.delhi_diamond);
        delhi_diamond_game.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(Home.this.getResources(),id_4,300,200));
        gali=findViewById(R.id.gali);
        gali.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(Home.this.getResources(),id_7,300,200));
        ghaziabad=findViewById(R.id.ghaziabad);
        ghaziabad.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(Home.this.getResources(),id_6,300,200));
        desawar=findViewById(R.id.desawar);
        desawar.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(Home.this.getResources(),id_8,300,200));
        faridabad=findViewById(R.id.faridabad);
        faridabad.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(Home.this.getResources(),id_5,300,200));
        lucky7= findViewById(R.id.Lucky7);
        lucky7.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(Home.this.getResources(),id_3,300,200));


        try {
            currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        new GetVersionCode().execute();

        delhi_diamond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              /*  SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                final String g1_count_open = sharedPreferences.getString(Config.G1_COUNT_OPEN,"one");
                  if(g1_count_open.equals("one")){
                      //Getting out sharedpreferences
                      SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);
                      //Getting editor
                      SharedPreferences.Editor editor = preferences.edit();
                      editor.putString(Config.G1_COUNT_OPEN,"two");
                      editor.commit();

                      timer1.cancel();
                      doTask1.cancel();
                      handler.removeCallbacks(runnable1);
                      play_music.stop();
               Intent intent= new Intent(Home.this,Dashboard.class);
               intent.putExtra("open",1);
               intent.putExtra("count",0);
                startActivity(intent);
                finish();



              }
                else{

                      timer1.cancel();
                      doTask1.cancel();
                      handler.removeCallbacks(runnable1);
                      play_music.stop();
                  Intent intent= new Intent(Home.this,Dashboard.class);
                  intent.putExtra("open",1);
                  intent.putExtra("count",1);
                  startActivity(intent);
                  finish();
              }*/
                fun(1);
            }
        });


        second_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                final String g2_count_open = sharedPreferences.getString(Config.G2_COUNT_OPEN,"one_");
                if(g2_count_open.equals("one_")){
                    //Getting out sharedpreferences
                    SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);
                    //Getting editor
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(Config.G2_COUNT_OPEN,"two_");
                    editor.commit();


                }
                else{

                    timer1.cancel();
                    doTask1.cancel();
                    handler.removeCallbacks(runnable1);
                    play_music.stop();

                    Intent intent= new Intent(Home.this,Dashboard.class);
                    intent.putExtra("open",2);
                    intent.putExtra("count",1);
                    startActivity(intent);
                    finish();
                }*/
                fun(2);
            }
        });

        lucky7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                final String g3_count_open = sharedPreferences.getString(Config.G3_COUNT_OPEN,"_one_");
                if(g3_count_open.equals("_one_")){
                    //Getting out sharedpreferences
                    SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);
                    //Getting editor
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(Config.G3_COUNT_OPEN,"_two_");
                    editor.commit();


                    timer1.cancel();
                    doTask1.cancel();
                    handler.removeCallbacks(runnable1);
                    play_music.stop();

                    Intent intent= new Intent(Home.this,Lucky7.class);
                    startActivity(intent);
                    finish();
                }   else{

                    timer1.cancel();
                    doTask1.cancel();
                    handler.removeCallbacks(runnable1);
                    play_music.stop();

                    Intent intent= new Intent(Home.this,Lucky7.class);
                    startActivity(intent);
                    finish();
                }*/
                fun(3);
            }
        });
        delhi_diamond_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Date currentTime = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("HH");
                String time =dateFormat.format(currentTime);
                int t = Integer.parseInt(time);
                if(t>=12&&t<=18) {

                    timer1.cancel();
                    doTask1.cancel();
                    handler.removeCallbacks(runnable1);
                    play_music.stop();

                    Intent intent = new Intent(Home.this, DelhiDiamond.class);
                    intent.putExtra("open", 4);
                    startActivity(intent);
                    finish();
                }
                if(t>18){
                    display_reult(4);
                }*/
                fun(4);

              /* display_reult(4);*/

            }
        });
        gali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Date currentTime = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("HH");
                String time =dateFormat.format(currentTime);
                int t = Integer.parseInt(time);
                if(t>=12&&t<=22) {

                    timer1.cancel();
                    doTask1.cancel();
                    handler.removeCallbacks(runnable1);
                    play_music.stop();

                    Intent intent = new Intent(Home.this, DelhiDiamond.class);
                    intent.putExtra("open", 7);
                    startActivity(intent);
                    finish();
                }if(t>22){

                    display_reult(7);
                }*/
                fun(7);
            }
        });
        desawar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*count=0;
                Date currentTime = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("HH");
                DateFormat dateFormat1 = new SimpleDateFormat("MM");
                String time =dateFormat.format(currentTime);
                String time1 =dateFormat1.format(currentTime);
                int t1 = Integer.parseInt(time1);
                int t = Integer.parseInt(time);
                if((t>2)&&t<12){
                    display_reult(8);
                }if(t==2){
                    count=1;
                    if(t1>55){
                        display_reult(8);
                    }else{

                        timer1.cancel();
                        doTask1.cancel();
                        handler.removeCallbacks(runnable1);
                        play_music.stop();

                        Intent intent = new Intent(Home.this, DelhiDiamond.class);
                        intent.putExtra("open", 8);
                        startActivity(intent);
                        finish();
                    }
                }
                else {
            if(count==0) {

                timer1.cancel();
                doTask1.cancel();
                handler.removeCallbacks(runnable1);
                play_music.stop();

                Intent intent = new Intent(Home.this, DelhiDiamond.class);
                intent.putExtra("open", 8);
                startActivity(intent);
                finish();
            }
                }*/
                fun(8);
            }
        });
        ghaziabad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 /*   Date currentTime = Calendar.getInstance().getTime();
                    DateFormat dateFormat = new SimpleDateFormat("HH");
                DateFormat dateFormat1 = new SimpleDateFormat("MM");
                String time =dateFormat.format(currentTime);
                String time1 =dateFormat1.format(currentTime);
                int t1 = Integer.parseInt(time1);
                    int t = Integer.parseInt(time);
                    if(t>=12&&t<19) {

                        timer1.cancel();
                        doTask1.cancel();
                        handler.removeCallbacks(runnable1);
                        play_music.stop();

                        Intent intent = new Intent(Home.this, DelhiDiamond.class);
                        intent.putExtra("open", 6);
                        startActivity(intent);
                        finish();
                    }if(t==19){
                        if(t1<=45){

                            timer1.cancel();
                            doTask1.cancel();
                            handler.removeCallbacks(runnable1);
                            play_music.stop();

                            Intent intent = new Intent(Home.this, DelhiDiamond.class);
                            intent.putExtra("open", 6);
                            startActivity(intent);
                            finish();
                        }else{
                            display_reult(6);
                        }
                }
                    if(t>=20){
                    display_reult(6);
                }*/
                fun(6);
            }
        });
        faridabad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Date currentTime = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("HH");
                DateFormat dateFormat1 = new SimpleDateFormat("MM");
                String time =dateFormat.format(currentTime);
                String time1 =dateFormat1.format(currentTime);
                int t1 = Integer.parseInt(time1);

                int t = Integer.parseInt(time);
                if(t>=12&&t<17) {

                    timer1.cancel();
                    doTask1.cancel();
                    handler.removeCallbacks(runnable1);
                    play_music.stop();

                    Intent intent = new Intent(Home.this, DelhiDiamond.class);
                       intent.putExtra("open", 5);
                       startActivity(intent);
                       finish();

                }if(t==17){
                    if(t1<=30){

                        timer1.cancel();
                        doTask1.cancel();
                        handler.removeCallbacks(runnable1);
                        play_music.stop();

                        Intent intent = new Intent(Home.this, DelhiDiamond.class);
                        intent.putExtra("open", 5);
                        startActivity(intent);
                        finish();

                    }else{
                      display_reult(5);
                    }
                }
                if(t>=18){
                    display_reult(5);
                }*/
                fun(5);
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    private void fun(final int i) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("LOADING...");
        progressDialog.show();
        String url = "https://delhidiamond.online/index.php/get-bet-status";
        Map<String, String> params = new HashMap<String, String>();
        String game = String.valueOf(i);
        params.put("game_type", game);
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", response.toString());
                progressDialog.dismiss();
                try {
                    String game_status = response.getString("bet_status");

                    if(game_status.equals("opened")){
                        if(i==1){
                            timer1.cancel();
                            doTask1.cancel();
                            handler.removeCallbacks(runnable1);
                            play_music.stop();
                            Intent intent= new Intent(Home.this,Dashboard.class);
                            intent.putExtra("open",1);
                            intent.putExtra("count",0);
                            startActivity(intent);
                            finish();

                        }
                        if(i==2){
                            timer1.cancel();
                            doTask1.cancel();
                            handler.removeCallbacks(runnable1);
                            play_music.stop();
                            Intent intent= new Intent(Home.this,Dashboard.class);
                            intent.putExtra("open",2);
                            intent.putExtra("count",0);
                            startActivity(intent);
                            finish();
                        }
                        if(i==3){
                            timer1.cancel();
                            doTask1.cancel();
                            handler.removeCallbacks(runnable1);
                            play_music.stop();

                            Intent intent= new Intent(Home.this,Lucky7.class);
                            startActivity(intent);
                            finish();
                        }
                        if(i==4){
                            timer1.cancel();
                            doTask1.cancel();
                            handler.removeCallbacks(runnable1);
                            play_music.stop();

                            Intent intent = new Intent(Home.this, DelhiDiamond.class);
                            intent.putExtra("open", 4);
                            startActivity(intent);
                            finish();
                        }if(i==5){
                            timer1.cancel();
                            doTask1.cancel();
                            handler.removeCallbacks(runnable1);
                            play_music.stop();

                            Intent intent = new Intent(Home.this, DelhiDiamond.class);
                            intent.putExtra("open", 5);
                            startActivity(intent);
                            finish();
                        }if(i==6){
                            timer1.cancel();
                            doTask1.cancel();
                            handler.removeCallbacks(runnable1);
                            play_music.stop();

                            Intent intent = new Intent(Home.this, DelhiDiamond.class);
                            intent.putExtra("open", 6);
                            startActivity(intent);
                            finish();
                        }
                        if(i==7){
                            timer1.cancel();
                            doTask1.cancel();
                            handler.removeCallbacks(runnable1);
                            play_music.stop();

                            Intent intent = new Intent(Home.this, DelhiDiamond.class);
                            intent.putExtra("open", 7);
                            startActivity(intent);
                            finish();
                        }
                        if(i==8){
                            timer1.cancel();
                            doTask1.cancel();
                            handler.removeCallbacks(runnable1);
                            play_music.stop();

                            Intent intent = new Intent(Home.this, DelhiDiamond.class);
                            intent.putExtra("open", 8);
                            startActivity(intent);
                            finish();
                        }
                    }if(game_status.equals("closed")){
                        if(i>3){
                            display_reult(i);
                        }
                    }



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

   /* @Override
    protected void onStart() {
        final MediaPlayer play_music = MediaPlayer.create(context,R.raw.happy_dream);
        play_music.setLooping(true);
        play_music.start();
        super.onStart();
    }*/

    @Override
    protected void onResume() {
        play_music= MediaPlayer.create(context,R.raw.beautiful_memories);
        play_music.setLooping(true);
        play_music.start();
        super.onResume();
    }

    class GetVersionCode extends AsyncTask<Void, String, String> {

        @Override

        protected String doInBackground(Void... voids) {

            String newVersion = null;

            try {
                Document document = Jsoup.connect("https://play.google.com/store/apps/details?id=" + Home.this.getPackageName()  + "&hl=en")
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get();
                if (document != null) {
                    Elements element = document.getElementsContainingOwnText("Current Version");
                    for (Element ele : element) {
                        if (ele.siblingElements() != null) {
                            Elements sibElemets = ele.siblingElements();
                            for (Element sibElemet : sibElemets) {
                                newVersion = sibElemet.text();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newVersion;

        }


        @Override

        protected void onPostExecute(String onlineVersion) {

            super.onPostExecute(onlineVersion);

            if (onlineVersion != null && !onlineVersion.isEmpty()) {

                if (Float.valueOf(currentVersion) < Float.valueOf(onlineVersion)) {
                    //show anything
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Home.this);
                    alertDialogBuilder.setMessage("Update Latest Version");
                    alertDialogBuilder.setPositiveButton("Update",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" +Home.this.getPackageName())));
                                }
                            });

                    alertDialogBuilder.setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    finishAffinity();
                                }
                            });

                    //Showing the alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }

            }

            Log.d("update", "Current version " + currentVersion + "playstore version " + onlineVersion);

        }
    }

    private void display_reult(final int a) {

        String url = "https://delhidiamond.online/index.php/get-winner-card";
        Map<String, String> params = new HashMap<String, String>();

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String user_id = String.valueOf(a);


        params.put("game_type", user_id);


        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", response.toString());
                try {
                    /*JSONObject jsonArray1 = response.getJSONObject("cards");
                     */String coins =response.getString("winner_card");
/*

                    SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                    //Creating editor to store values to shared preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();


                    //Adding values to editor
                    editor.putString(Config.TOKENS, coins);
                    editor.commit();


*/
                if(a==4){
                    delhi_result.setText(coins);
                }
                    if(a==5){
                        fari_result.setText(coins);
                    }
                    if(a==6){
                        ghazi_result.setText(coins);
                    }
                    if(a==7){
                        gali_result.setText(coins);
                    }
                    if(a==8){
                        des_result.setText(coins);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
/*
                Toast.makeText(MainDashBoard.this,"Coins Has been Added to your Account",Toast.LENGTH_LONG).show();
*/
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {
                Log.d("Response: ", response.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(Home.this);
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy( 5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsObjRequest);
    }


    private void refresh() {
        handler = new Handler();
        timer1 = new Timer();






        if (!Home.this.isFinishing()) {
        doTask1 = new TimerTask() {
            @Override
            public void run() {
                runnable1 = new Runnable() {
                    @Override
                    public void run() {
                        check_internetconnection();

                    }
                }

                ;
                handler.post(runnable1);
            }
        }

        ;
        timer1.schedule(doTask1, 0, 2000);

}
    }

    private void check_internetconnection() {
        if(isOnline()){
        }else{
            doTask1.cancel();
            handler.removeCallbacks(runnable1);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Please Check Your Net Connection");
            alertDialogBuilder.setPositiveButton("OK",
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
    private void addtransactions() {
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
       /* SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Config.TOKENS,"");

        //Saving values to editor
        editor.commit();*/

        String url = "https://delhidiamond.online/index.php/get-current-amount";
        Map<String, String> params = new HashMap<String, String>();

        final String agent_id = sharedPreferences.getString(Config.AGENT_ID,"-1");

        params.put("user_id", agent_id);
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", response.toString());
                try {
                    String closing_amount = response.getString("current_coins");


                    SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                    //Creating editor to store values to shared preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();


                    //Adding values to editor
                    editor.putString(Config.TOKENS,closing_amount);

                    //Saving values to editor
                    editor.commit();

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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.home) {
            // Handle the camera action

            Intent intent= new Intent(Home.this,Home.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.points_report) {
            play_music.stop();
            Intent intent= new Intent(Home.this,PointsReport.class);
            startActivity(intent);
            finish();

       /* } else if (id == R.id.daily_summ_report) {

        } else if (id == R.id.all_summary_report) {

        } else if (id == R.id.declared_transactions) {

        } else if (id == R.id.declare_report) {

        } else if (id == R.id.share) {
*/
        } else if(id==R.id.coins_report){
            play_music.stop();
            Intent intent= new Intent(Home.this,TransactionHistory.class);
            startActivity(intent);
            finish();
        }else if (id == R.id.logout) {
            logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.player_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            logout();
            return true;
        }if(id==R.id.action_change){
            change_pass();
            return true;
        }
        if(id==R.id.send_coins){
            tranfer_coins();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void tranfer_coins() {
        joda_panel  = new Dialog(Home.this);
        joda_panel.setContentView(R.layout.delhidiamon_joda_panel);
        joda_input_amount=joda_panel.findViewById(R.id.joda_input_amount);
        submit_amount_joda_panel=joda_panel.findViewById(R.id.submit_amount_joda_panel);
        close_joda_panel=joda_panel.findViewById(R.id.close_joda_panel);
        cancel_action=joda_panel.findViewById(R.id.cancel_action);
        joda_panel.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        joda_panel.setTitle("");
        TextView joda_bid= joda_panel.findViewById(R.id.joda_bid);
        joda_bid.setText("Transfer Coins");
        joda_panel.show();
        cancel_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joda_panel.dismiss();
            }
        });
        close_joda_panel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joda_panel.dismiss();
            }
        });
        submit_amount_joda_panel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!joda_input_amount.getText().toString().equals("")){
                    String getcoins= String.valueOf(joda_input_amount.getText());
                    Add_coins(getcoins);
                }else {
                    invalid_submit();
                }
            }
        });
    }

    private void Add_coins(String getcoins) {
        final ProgressDialog  progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("LOADING...");
        progressDialog.show();
        String url = "https://delhidiamond.online/index.php/return-coin-request";
        Map<String, String> params = new HashMap<String, String>();
        params.put("coins_transfer",getcoins );
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String user_id = sharedPreferences.getString(Config.PLAYER_ID,"Not Available");
        String agent_id = sharedPreferences.getString(Config.DEALER_ID,"Not Available");

        params.put("user_id", user_id);

        params.put("agent_id", agent_id);

        params.put("coins_transfer", getcoins);
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", response.toString());
                try {
                    /*JSONObject jsonArray1 = response.getJSONObject("cards");
                     */String coins =response.getString("user_current_coins");
/*

                    SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                    //Creating editor to store values to shared preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();


                    //Adding values to editor
                    editor.putString(Config.TOKENS, coins);
                    editor.commit();


*/
                    joda_panel.dismiss();
                    progressDialog.dismiss();
                    Intent intent = new Intent(Home.this,Home.class);
                    startActivity(intent);
                    Toast.makeText(Home.this,coins,Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
/*
                Toast.makeText(MainDashBoard.this,"Coins Has been Added to your Account",Toast.LENGTH_LONG).show();
*/
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {
                Log.d("Response: ", response.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(Home.this);
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy( 5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsObjRequest);


    }

    @Override
    protected void onPause() {
          play_music.stop();

        super.onPause();
    }

    public static boolean isApplicationSentToBackground(final Context context) {

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }

        return false;
    }

    private void change_pass() {

        bahar_input_number.setHint("Old Password");
        bahar_input_amount.setHint("New Password");
        bahar_input_amount.setInputType(InputType.TYPE_CLASS_TEXT);
        bahar_input_number.setInputType(InputType.TYPE_CLASS_TEXT);
        Bahar_panel.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Bahar_panel.setTitle("");
        Bahar_panel.show();
        close_bahar_panel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bahar_panel.dismiss();
            }
        });
        submit_bahar_panel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!bahar_input_amount.getText().toString().equals("")&&!bahar_input_number.getText().toString().equals("")){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Home.this);
                    alertDialogBuilder.setMessage("Are You Sure Want To Change Password");
                    alertDialogBuilder.setPositiveButton("Sure",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    String old_pass =bahar_input_number.getText().toString();
                                    String new_pass =bahar_input_amount.getText().toString();
                                    change_pass_(old_pass,new_pass,Bahar_panel);
                                    bahar_input_amount.getText().clear();
                                    bahar_input_number.getText().clear();
                                }
                            });
                    alertDialogBuilder.setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    Bahar_panel.dismiss();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }else{
                    invalid_submit();
                }
            }
        });
    }

    private void change_pass_(String old_pass, String new_pass, final Dialog bahar_panel) {
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String URL = "https://delhidiamond.online/index.php/change-password ";
        final Map<String, String> params = new HashMap<String, String>();
        final String agent_id = sharedPreferences.getString(Config.PLAYER_ID, "-1");
        params.put("user_id",agent_id);
        params.put("old_password",old_pass);
        params.put("new_password",new_pass);


        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, URL, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", response.toString());
                String change="";

                try {
                    change=response.getString("response");
                } catch (JSONException e) {
                    e.printStackTrace();
                    try {
                        change=response.getString("error");
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }


                if(change.equals("Password changed Successfully")) {
                    Toast.makeText(Home.this, "Password Has Changed", Toast.LENGTH_LONG).show();
                    bahar_panel.dismiss();
                }
                else{
                    bahar_input_number.setBackgroundResource(R.drawable.red_border);
                    Toast.makeText(Home.this, "Invalid Old Password ", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {
                Log.d("Response: ", response.toString());

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(Home.this);
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy( 5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsObjRequest);
    }
    private void invalid_submit() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Home.this);
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

                                timer1.cancel();
                                doTask1.cancel();
                                handler.removeCallbacks(runnable1);
                                Intent intent = new Intent(Home.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError response) {
                                Log.d("Response: ", response.toString());
                            }
                        });
                        RequestQueue requestQueue = Volley.newRequestQueue(Home.this);
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
            finishAffinity();
        }
        return super.onKeyDown(keyCode, event);
    }

}
