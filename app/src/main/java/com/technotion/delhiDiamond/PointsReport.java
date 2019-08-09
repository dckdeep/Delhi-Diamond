package com.technotion.delhiDiamond;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.technotion.delhiDiamond.Adapters.Game_Report_Adapter;
import com.technotion.delhiDiamond.Adapters.Game_Rest_Report_Adapter;
import com.technotion.delhiDiamond.sever_classes.Config;
import com.technotion.delhiDiamond.sever_classes.CustomRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class PointsReport extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ArrayList<Integer> card_open =new ArrayList<>();

    ArrayList<Integer> display_card =new ArrayList<>();

    ArrayList<String> amount =new ArrayList<>();

    ArrayList<String> win_or_lose =new ArrayList<>();

    ArrayList<String> transaction_time =new ArrayList<>();
    ProgressDialog progressDialog;

    ArrayList<String> display_num = new ArrayList<>();

    ArrayList<String> game_id_ = new ArrayList<>();


    Handler handler;
    TimerTask doTask1;
    Runnable runnable1 ;
    Timer  timer1;

    String jantri_array[] ={"--Select Game--","13patti","Chidiya Kabutar","Lucky7","Delhi Diamond","Faridabad","Ghaziabad","Gali","Desawar"};
    String jantri1_array[] ={"--Select Game--","Delhi Diamond","Faridabad","Ghaziabad","Gali","Desawar"};

    ArrayAdapter shortcuts;
    Spinner select_game;
    int view_list=0;
    Button select_date;
    Dialog dialog;
    CalendarView calendarView;
    String date="";
    TextView no_game_yet,total_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        select_game= findViewById(R.id.select_game);
        select_date=findViewById(R.id.select_date);
        no_game_yet=findViewById(R.id.no_game_yet);
        total_amount=findViewById(R.id.total_amount);
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String user_type = sharedPreferences.getString(Config.USER_STATUS,"-1");
        if(user_type.equals("agent")) {
            if (Config.com_patti == 0) {
                shortcuts = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, jantri1_array);
                select_game.setAdapter(shortcuts);
                select_game.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0:
                                view_list = 0;
                                break;
                            case 1:
                                view_list = 4;

                                break;
                            case 2:
                                view_list = 5;

                                break;
                            case 3:
                                view_list = 6;

                                break;
                            case 4:
                                view_list = 7;
                                break;
                            case 5:
                                view_list = 8;
                                break;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            } else {
                shortcuts = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, jantri_array);
                select_game.setAdapter(shortcuts);
                select_game.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0:
                                view_list = 0;
                                break;
                            case 1:
                                view_list = 1;
                                opencard_image();
                                break;
                            case 2:
                                view_list = 2;
                                second_game_card_image();
                                break;
                            case 3:
                                view_list = 3;

                                break;
                            case 4:
                                view_list = 4;

                                break;
                            case 5:
                                view_list = 5;

                                break;
                            case 6:
                                view_list = 6;

                                break;
                            case 7:
                                view_list = 7;

                                break;
                            case 8:
                                view_list = 8;

                                break;

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        }if(user_type.equals("player")){
            shortcuts = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, jantri_array);
            select_game.setAdapter(shortcuts);
            select_game.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0:
                            view_list = 0;
                            break;
                        case 1:
                            view_list = 1;
                            opencard_image();
                            break;
                        case 2:
                            view_list = 2;
                            second_game_card_image();
                            break;
                        case 3:
                            view_list = 3;

                            break;
                        case 4:
                            view_list = 4;

                            break;
                        case 5:
                            view_list = 5;

                            break;
                        case 6:
                            view_list = 6;

                            break;
                        case 7:
                            view_list = 7;

                            break;
                        case 8:
                            view_list = 8;

                            break;

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        dialog = new Dialog(PointsReport.this);
        dialog.setContentView(R.layout.calendar_pop);
        calendarView = dialog.findViewById(R.id.calender);

        select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(view_list!=0){
                get_history();


            }else{
                select_date.setText("Select Date");
                alert_fun();
            }


            }
        });


     /*   addtransactions();*/
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

    private void get_history() {
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setTitle("");
        dialog.show();
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                date = dayOfMonth+"-"+(month+1)+"-"+year;
                Log.d("Response: ", date.toString());
               /* Toast.makeText(PointsReport.this,date,Toast.LENGTH_LONG).show();*/
                dialog.dismiss();
                select_date.setText(date);
                addtransactions();

            }
        });
    }

    private void alert_fun() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PointsReport.this);
        alertDialogBuilder.setMessage("Please Select the Game");
        alertDialogBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void addtransactions() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("LOADING...");
        progressDialog.show();

        amount.clear();
        win_or_lose.clear();
        transaction_time.clear();
        display_num.clear();
        game_id_.clear();
        display_card.clear();
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String user_type = sharedPreferences.getString(Config.USER_STATUS,"-1");

        String url ="";
        if(user_type.equals("agent")){
            if(Config.com_patti==0){
                url = "https://delhidiamond.online/index.php/get-patti-transactions ";
            }
            if(Config.com_patti==1){
                url = "https://delhidiamond.online/index.php/get-commission-transactions ";
            }
        }
        if(user_type.equals("player")) {
            url = "https://delhidiamond.online/index.php/card-opened-by-date ";
        }
        Map<String, String> params = new HashMap<String, String>();

        final String agent_id = sharedPreferences.getString(Config.PLAYER_ID,"-1");

        params.put("user_id", agent_id);

        String game_type= String.valueOf(view_list);
       /* if(view_list==1){

        }
        if(view_list==2){
            second_game_card_image();
        }*/
        params.put("current_date", date);

        params.put("game_type", game_type);
        Config.game_report_game_type=view_list;
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", response.toString());
                progressDialog.dismiss();
                String res="";
                try {
                     res= response.getString("response");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(res.equals("0")){
                    no_game_yet.setText("NO GAME FOR THE DATE("+date+")");
                }else{
                    no_game_yet.setText("");
                 /*   Toast.makeText(PointsReport.this,response.toString(),Toast.LENGTH_LONG).show();
                 */   try {
                        JSONArray jsonArray = response.getJSONArray("response");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject employee = jsonArray.getJSONObject(i);
                            String card =employee.getString("card_opened");
                            String coins =employee.getString("winner");
                            String game_id=employee.getString("games_ids");
                            String time =employee.getString("start_time");
                            String time2 =employee.getString("end_time");

                            String[] parts = time.split(" ");
                            time=parts[1];

                            String[] parts2 = time2.split(" ");
                            time2=parts2[1];
                            if(view_list==1||view_list==2) {
                               int card_num = Integer.parseInt(card)-1;
                               int display=card_open.get(card_num);
                               display_card.add(display);

                           }else{
                               display_num.add(card);
                           }
                           amount.add(coins);
                            game_id_.add(game_id);
                            time=time+"--"+time2;
                            transaction_time.add(time);
                           int check_coins= Integer.parseInt(coins);
                           if(check_coins<0){
                               win_or_lose.add("Lose");
                           }if(check_coins>0){
                               win_or_lose.add("WIN");
                            }
                            if(check_coins==0){
                               if( user_type.equals("player")) {
                                   win_or_lose.add("You Have Not Played");
                               }if(user_type.equals("agent")){
                                    win_or_lose.add("Players Have Not Played");
                                }
                            }
                        }
                        if(view_list==1||view_list==2){
                            report_first_two_games();
                        }else{
                            report_rest_games();

                        }
                        progressDialog.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
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

    private void report_rest_games() {
        RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(),1);
        RecyclerView vieww= findViewById(R.id.user_transaction_details);
        vieww.setHasFixedSize(true);
        vieww.setLayoutManager(manager);
        Context context =PointsReport.this;
       /* Collections.reverse(display_card);
        Collections.reverse(amount);
        Collections.reverse(win_or_lose);
        Collections.reverse(transaction_time);*/
      /* int totl=0;
       for(int i=0; i<amount.size();i++){
           String s=
       }*/
        Game_Rest_Report_Adapter attributes= new Game_Rest_Report_Adapter(display_num,amount,win_or_lose,transaction_time,game_id_,context);
        vieww.setAdapter(attributes);

    }

    private void report_first_two_games() {
        RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(),1);
        RecyclerView vieww= findViewById(R.id.user_transaction_details);
        vieww.setHasFixedSize(true);
        vieww.setLayoutManager(manager);
        Context context =PointsReport.this;
       /* Collections.reverse(display_card);
        Collections.reverse(amount);
        Collections.reverse(win_or_lose);
        Collections.reverse(transaction_time);*/
        Game_Report_Adapter attributes= new Game_Report_Adapter(display_card,amount,win_or_lose,transaction_time,game_id_,context);

        vieww.setAdapter(attributes);
    }


    private void usersendpoints(){
        RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(),1);
        RecyclerView vieww= findViewById(R.id.user_transaction_details);
        vieww.setHasFixedSize(true);
        vieww.setLayoutManager(manager);
        Context context =PointsReport.this;

     /*   Collections.reverse(coins_transfer);
        Collections.reverse(closing_coins);
        Collections.reverse(transaction_type);*/
        Collections.reverse(transaction_time);
/*
     *//*   transcationhistory_adapter attributes= new transcationhistory_adapter(user_name,coins_transfer,closing_coins,transaction_type,transaction_time,context);*//*
        vieww.setAdapter(attributes);*/
    }

    private void refresh() {
        handler = new Handler();
        timer1 = new Timer();






        if (!PointsReport.this.isFinishing()) {
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
            SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            final String user_type = sharedPreferences.getString(Config.USER_STATUS,"-1");
            if(user_type.equals("agent")) {
                Intent intent = new Intent(PointsReport.this, AjentsDashBoard.class);
                startActivity(intent);
                finish();
            }if(user_type.equals("player")){
                Intent intent = new Intent(PointsReport.this, Home.class);
                startActivity(intent);
                finish();
            }
        } else if (id == R.id.points_report) {
            SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            final String user_type = sharedPreferences.getString(Config.USER_STATUS,"-1");
            if(user_type.equals("agent")) {
                if (Config.com_patti == 0) {
                    Config.com_patti = 0;
                    Intent intent = new Intent(PointsReport.this, PointsReport.class);
                    startActivity(intent);
                    finish();
                }else{
                    Config.com_patti = 1;
                    Intent intent = new Intent(PointsReport.this, PointsReport.class);
                    startActivity(intent);
                    finish();
                }

            }if(user_type.equals("player")){
                Intent intent = new Intent(PointsReport.this, PointsReport.class);
                startActivity(intent);
                finish();
            }

      /*  } else if (id == R.id.daily_summ_report) {

        } else if (id == R.id.all_summary_report) {

        } else if (id == R.id.declared_transactions) {

        } else if (id == R.id.declare_report) {

        } else if (id == R.id.share) {
*/
        }else if(id==R.id.coins_report){
            Intent intent= new Intent(PointsReport.this,TransactionHistory.class);
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

                        //Getting out sharedpreferences
                        SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        //Getting editor
                        SharedPreferences.Editor editor = preferences.edit();

                        //Puting the value false for loggedin
                        editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);

                        //Putting blank value to email
                        editor.putString(Config.USER_SHARED_PREF, "");

                        //Saving the sharedpreferences
                        editor.commit();

                        //Starting login activity
                        Intent intent = new Intent(PointsReport.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
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
            SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            final String user_type = sharedPreferences.getString(Config.USER_STATUS,"-1");
            if(user_type.equals("agent")) {
                Intent intent = new Intent(PointsReport.this, AjentsDashBoard.class);
                startActivity(intent);
                finish();
            }if(user_type.equals("player")){
                Intent intent = new Intent(PointsReport.this, Home.class);
                startActivity(intent);
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
