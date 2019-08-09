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
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.technotion.delhiDiamond.Adapters.Crossing_row1;
import com.technotion.delhiDiamond.Adapters.Crossing_row2;
import com.technotion.delhiDiamond.Adapters.display_number_with_amount_delhi_diamond;
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

public class DelhiDiamond extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ProgressDialog progressDialog;
    TextView icon_card,total_coins,delete_all,view_jantri,bhar_panel_text;
    CheckBox checkBox_joda;
    Runnable runnable ;
    Timer timer1;
    int get;
    Handler handler12;
    TimerTask doTask;
    ImageView close,close_joda_panel,close_crossing,close_andar_panel,close_bahar_panel,game_logo_inside;
    EditText from_input,to_input,from_to_amount,joda_input_amount,crossing_panel_amount,andar_input_number,andar_input_amount,bahar_input_number,bahar_input_amount;
    Button add_button_delhi_diamond,Submit_from_to_nd_amount,submit_amount_joda_panel,submit_crosspanel,save_all_values,submit_andar_panel,submit_bahar_panel,cancel_action;
    EditText bid_number,Amount_entered;

    int crossing_number_count=0,joda_number_count=0,count_how_many_numbers=0,total_numbers=0;
    ArrayList<String> Number_list = new ArrayList<>();
    ArrayList<String> Amount_list = new ArrayList<>();
    RecyclerView recycler_display_number_with_amount,crossing_row_two_recycler,crossing_row_one_recycler;
    ArrayAdapter shortcuts;
    Spinner shortcuts_spinner;
    String jantri_array[] ={"--ShortCuts--","From-To","Cross","Joda","Andar","Bahar"};
    int id_1 =R.drawable.delhi_diamond_logo;
    int id_2=R.drawable.faridabad;
    int id_3=R.drawable.ghaziabad;
    int id_4=R.drawable.gali;
    int id_5=R.drawable.desawar;

    Dialog From_to_panel,Crossing_panel,joda_panel,Andar_panel,Bahar_panel;
    ArrayList<String> Crossing_row2_button_numbers = new ArrayList<>();
    ArrayList<String> Crossing_row1_button_numbers = new ArrayList<>();
    ArrayList<String> Andar_coins = new ArrayList<>();
    ArrayList<String> Bahar_coins = new ArrayList<>();
    ArrayList<String> total_coins_of_numbers= new ArrayList<>();
    ArrayList<String> Andar_Num = new ArrayList<>();
    ArrayList<String> Bahar_Num = new ArrayList<>();
    ArrayList<String> total_of_Num= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delhi_diamond);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        get  = bundle.getInt("open");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Config.get_numbers_row1.clear();
        Config.get_numbers_row2.clear();
        shortcuts_spinner = findViewById(R.id.planets_spinner);
        icon_card= findViewById(R.id.jantri);
        view_jantri=findViewById(R.id.view_jantri);
        total_coins=findViewById(R.id.total_coins);
        add_button_delhi_diamond=findViewById(R.id.add_button_delhi_diamond);
        bid_number=findViewById(R.id.bid_number);
        delete_all=findViewById(R.id.delete_all);
        save_all_values=findViewById(R.id.save_all_values);
        Amount_entered=findViewById(R.id.Amount_entered);
        recycler_display_number_with_amount= findViewById(R.id.recycler_display_number_with_amount);
        RecyclerView.LayoutManager manager = new GridLayoutManager(DelhiDiamond.this,1);
        recycler_display_number_with_amount.setHasFixedSize(true);
        recycler_display_number_with_amount.setLayoutManager(manager);

        game_logo_inside=findViewById(R.id.game_logo_inside);
        Amount_entered.setInputType(InputType.TYPE_CLASS_NUMBER);
        Amount_entered.setImeOptions(EditorInfo.IME_ACTION_DONE);
        bid_number.setImeOptions(EditorInfo.IME_ACTION_NEXT);
         From_to_panel = new Dialog(DelhiDiamond.this);
        Crossing_panel  = new Dialog(DelhiDiamond.this);
        joda_panel  = new Dialog(DelhiDiamond.this);
        Andar_panel= new Dialog(DelhiDiamond.this);
        Bahar_panel= new Dialog(DelhiDiamond.this);

        From_to_panel.setContentView(R.layout.delhidiamond_from_to_panel);
        Crossing_panel.setContentView(R.layout.delhidiamond_crossing_panel);
        joda_panel.setContentView(R.layout.delhidiamon_joda_panel);
        Andar_panel.setContentView(R.layout.delhidiamond_andar);
        Bahar_panel.setContentView(R.layout.delhidiamond_bahar);

        close_andar_panel=Andar_panel.findViewById(R.id.close_andar_panel);
        andar_input_number=Andar_panel.findViewById(R.id.andar_input_number);
        andar_input_amount=Andar_panel.findViewById(R.id.andar_input_amount);
        submit_andar_panel=Andar_panel.findViewById(R.id.submit_andar_panel);

        close_bahar_panel=Bahar_panel.findViewById(R.id.close_bahar_panel);
        bahar_input_number=Bahar_panel.findViewById(R.id.bahar_input_number);
        bahar_input_amount=Bahar_panel.findViewById(R.id.bahar_input_amount);
        submit_bahar_panel=Bahar_panel.findViewById(R.id.submit_bahar_panel);
        bahar_input_number.setHint("Number");
        bahar_input_amount.setHint("Amount");
        bhar_panel_text=Bahar_panel.findViewById(R.id.bhar_panel_text);
        bhar_panel_text.setText("Bahar Panel");

        bahar_input_amount.setInputType(InputType.TYPE_CLASS_NUMBER);
        bahar_input_number.setInputType(InputType.TYPE_CLASS_NUMBER);

        close = From_to_panel.findViewById(R.id.close);
         from_input= From_to_panel.findViewById(R.id.from_input);
         to_input =From_to_panel.findViewById(R.id.to_input);
         from_to_amount=From_to_panel.findViewById(R.id.from_to_amount);
        Submit_from_to_nd_amount=From_to_panel.findViewById(R.id.Submit_from_to_nd_amount);

        joda_input_amount=joda_panel.findViewById(R.id.joda_input_amount);
        submit_amount_joda_panel=joda_panel.findViewById(R.id.submit_amount_joda_panel);
        close_joda_panel=joda_panel.findViewById(R.id.close_joda_panel);

        crossing_row_one_recycler=Crossing_panel.findViewById(R.id.crossing_row_one_recycler);
        checkBox_joda=Crossing_panel.findViewById(R.id.checkBox_joda);
        crossing_panel_amount=Crossing_panel.findViewById(R.id.crossing_panel_amount);
        submit_crosspanel=Crossing_panel.findViewById(R.id.submit_crosspanel);
        close_crossing=Crossing_panel.findViewById(R.id.close_crossing);

        if(get==4){
            game_logo_inside .setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(DelhiDiamond.this.getResources(),id_1,300,200));
        }
        if(get==5){
            game_logo_inside .setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(DelhiDiamond.this.getResources(),id_2,300,200));

        }
        if(get==6){
            game_logo_inside .setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(DelhiDiamond.this.getResources(),id_3,300,200));

        }
        if(get==7){
            game_logo_inside .setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(DelhiDiamond.this.getResources(),id_4,300,200));

        }
        if(get==8){
            game_logo_inside .setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(DelhiDiamond.this.getResources(),id_5,300,200));

        }


        crossing_row_two_recycler=Crossing_panel.findViewById(R.id.crossing_row_two_recycler);
        Crossing_row1_button_numbers.clear();
        Crossing_row2_button_numbers.clear();
        for(int i=1; i<11;i++){
            if(i==10){
                Crossing_row1_button_numbers.add("0");
                Crossing_row2_button_numbers.add("0");
            }else{
                String s = String.valueOf(i);
                Crossing_row1_button_numbers.add(s);
                Crossing_row2_button_numbers.add(s);

            }
        }
        RecyclerView.LayoutManager manager2= new GridLayoutManager(DelhiDiamond.this,10);
        crossing_row_two_recycler.setHasFixedSize(true);
        crossing_row_two_recycler.setLayoutManager(manager2);
        Crossing_row2 attributes_crossing_row2 = new Crossing_row2(Crossing_row2_button_numbers, DelhiDiamond.this);
        crossing_row_two_recycler.setAdapter(attributes_crossing_row2);


        RecyclerView.LayoutManager manager1= new GridLayoutManager(DelhiDiamond.this,10);
        crossing_row_one_recycler.setHasFixedSize(true);
        crossing_row_one_recycler.setLayoutManager(manager1);
        Crossing_row1 attributes_crossing_row1 = new Crossing_row1(Crossing_row1_button_numbers, DelhiDiamond.this);
        crossing_row_one_recycler.setAdapter(attributes_crossing_row1);




        shortcuts = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,jantri_array);

        shortcuts_spinner.setAdapter(shortcuts);



        request_game_type(get);
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String G4_ID= sharedPreferences.getString(Config.G4_ID, "-1");
        final String G4_check_id =sharedPreferences.getString(Config.G4__Check_Game_ID, "-1");
      /* if(!G4_ID.equals(G4_check_id)){
           SharedPreferences.Editor editor = sharedPreferences.edit();
           editor.putString(Config.G4__Check_Game_ID,G4_ID);
           editor.commit();*/
           Number_list.clear();
           Amount_list.clear();
           display_all_numbers_amount();
      /* }else{
           Number_list.clear();
           Amount_list.clear();
           final String Numbers =sharedPreferences.getString(Config.G4_cards, "-1");
           final String Amounts =sharedPreferences.getString(Config.G4_coins, "-1");
           String[] parts = Numbers.split(",");
           for(int ss=0; ss<parts.length; ss++){
               Number_list.add(parts[ss]);
               *//*   Toast.makeText(context,mIconText.get(ss),Toast.LENGTH_LONG).show();*//*
           }
           String[] parts1 = Amounts.split(",");
           for(int ss=0; ss<parts1.length; ss++){
               Amount_list.add(parts1[ss]);
               *//*   Toast.makeText(context,mIconText.get(ss),Toast.LENGTH_LONG).show();*//*
           }
           display_all_numbers_amount();
       }*/


        shortcuts_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                    break;
                    case 1:
                        open_from_to_panel_working();
                        break;
                    case 2:

                        open_crossing_panel_working();
                        break;
                    case 3:
                        open_joda_panel_working();
                        break;
                    case 4:
                        open_andar_panel_working();
                        break;
                    case 5:
                        open_bahar_panel_working();
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        icon_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DelhiDiamond.this,Add_Diamond_numbers.class);
                intent.putExtra("open",get);
                startActivity(intent);
            }
        });

        add_button_delhi_diamond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(!bid_number.getText().toString().equals("")&&!Amount_entered.getText().toString().equals("")) {
                   String get_number = String.valueOf(bid_number.getText());
                   String get_amount = String.valueOf(Amount_entered.getText());
                   int direct_num = Integer.parseInt(get_number);
                   if(direct_num<101&&direct_num>-1) {
                       /*if (!Number_list.isEmpty()) {
                           if (!Number_list.contains(get_number)) {
                               Number_list.add(get_number);
                               Amount_list.add(get_amount);
                               display_all_numbers_amount();
                               bid_number.getText().clear();
                               Amount_entered.getText().clear();
                           } else {
                               int i  = Number_list.indexOf(get_number);
                               int check_value_of_amount = Integer.parseInt(get_amount);
                               int x = Integer.parseInt(Amount_list.get(i));
                               x= check_value_of_amount+x;
                               String change_amount = String.valueOf(x);
                               Amount_list.remove(i);
                               Amount_list.add(i,change_amount);
                               display_all_numbers_amount();
                               bid_number.getText().clear();
                               Amount_entered.getText().clear();
                           }
                       } else {
                          */
                           Number_list.add(get_number);
                           Amount_list.add(get_amount);
                           display_all_numbers_amount();
                           bid_number.getText().clear();
                           Amount_entered.getText().clear();
                           bid_number.requestFocus();
                       /*}*/
                   }else {
                   /*    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DelhiDiamond.this);
                       alertDialogBuilder.setMessage("Your bid is Less Than 10");
                       alertDialogBuilder.setPositiveButton("Ok",
                               new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface arg0, int arg1) {

                                   }
                               });
                       AlertDialog alertDialog = alertDialogBuilder.create();
                       alertDialog.show();
                   */
                   invalid_submit();
                   }
               }else{
              /*     Toast.makeText(DelhiDiamond.this, "No number to place bid", Toast.LENGTH_LONG).show();
              */invalid_submit();
               }

            }
        });

        delete_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Amount_list.clear();
                Number_list.clear();
                SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Config.G4_SET_TOTAL_AMOUNT, "0");
                editor.commit();
                final String total_coins_g4= sharedPreferences.getString(Config.G4_SET_TOTAL_AMOUNT, "-1");
                total_coins.setText(total_coins_g4);
                display_all_numbers_amount();
            }
        });

        view_jantri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DelhiDiamond.this,viewJantri.class);
                intent.putExtra("open",get);
                startActivity(intent);
                finish();
            }
        });

        save_all_values.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Number_list.isEmpty()) {
                    Andar_coins.clear();
                    Bahar_coins.clear();
                    Andar_Num.clear();
                    Bahar_Num.clear();
                    total_coins_of_numbers.clear();
                    total_of_Num.clear();
                    for (int i = 0; i < Number_list.size(); i++) {
                        if (Number_list.get(i).startsWith("A")) {
                            Andar_coins.add(Amount_list.get(i));
                            String value = Number_list.get(i);
                            String intValue = value.replaceAll("[^0-9]", "");
                            if(intValue.equals("0")){
                                intValue="10";
                            }
                            Andar_Num.add(intValue);
                        } else if (Number_list.get(i).startsWith("B")) {
                            Bahar_coins.add(Amount_list.get(i));
                            String value = Number_list.get(i);
                            String intValue = value.replaceAll("[^0-9]", "");
                            if(intValue.equals("0")){
                                intValue="10";
                            }
                            Bahar_Num.add(intValue);
                        } else {
                            total_coins_of_numbers.add(Amount_list.get(i));
                            total_of_Num.add(Number_list.get(i));
                        }
                    }
                    save_all_transcation();

                }else{
                    invalid_submit();
                }
            }
        });
       /* bid_number.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v , int keyCode , KeyEvent event) {

               *//* EditText editText2 = (EditText) findViewById(R.id.Amount_entered);*//*

                // TODO Auto-generated method stub
                if (keyCode == event.KEYCODE_A) {

                    Selection.setSelection((Editable) Amount_entered.getText(),bid_number.getSelectionStart());
                    Amount_entered.requestFocus();
                }

                return true;
            }
        });*/
        bid_number.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_NAVIGATE_NEXT)) || (actionId == EditorInfo.IME_ACTION_NEXT)) {
                    Amount_entered.requestFocus();
                }
                return false;
            }
        });
        Amount_entered.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    if(!bid_number.getText().toString().equals("")&&!Amount_entered.getText().toString().equals("")) {
                        String get_number = String.valueOf(bid_number.getText());
                        String get_amount = String.valueOf(Amount_entered.getText());
                        int direct_num = Integer.parseInt(get_number);
                        if(direct_num<101&&direct_num>-1) {
                       /*if (!Number_list.isEmpty()) {
                           if (!Number_list.contains(get_number)) {
                               Number_list.add(get_number);
                               Amount_list.add(get_amount);
                               display_all_numbers_amount();
                               bid_number.getText().clear();
                               Amount_entered.getText().clear();
                           } else {
                               int i  = Number_list.indexOf(get_number);
                               int check_value_of_amount = Integer.parseInt(get_amount);
                               int x = Integer.parseInt(Amount_list.get(i));
                               x= check_value_of_amount+x;
                               String change_amount = String.valueOf(x);
                               Amount_list.remove(i);
                               Amount_list.add(i,change_amount);
                               display_all_numbers_amount();
                               bid_number.getText().clear();
                               Amount_entered.getText().clear();
                           }
                       } else {
                          */ Number_list.add(get_number);
                            Amount_list.add(get_amount);
                            display_all_numbers_amount();
                            bid_number.getText().clear();
                            Amount_entered.getText().clear();
                            bid_number.requestFocus();
                            /*}*/
                        }else {
                   /*    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DelhiDiamond.this);
                       alertDialogBuilder.setMessage("Your bid is Less Than 10");
                       alertDialogBuilder.setPositiveButton("Ok",
                               new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface arg0, int arg1) {

                                   }
                               });
                       AlertDialog alertDialog = alertDialogBuilder.create();
                       alertDialog.show();
                   */
                            invalid_submit();
                        }
                    }else{
                        /*     Toast.makeText(DelhiDiamond.this, "No number to place bid", Toast.LENGTH_LONG).show();
                         */invalid_submit();
                    }
                }
                return false;
            }
        });
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
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
    private void request_game_type(int i) {
        String game_type = String.valueOf(i);

        String url = "https://delhidiamond.online/index.php/select-game";
        Map<String, String> params = new HashMap<String, String>();

/*
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String agent_id = sharedPreferences.getString(Config.AGENT_ID,"-1");
*/      SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String player_id = sharedPreferences.getString(Config.PLAYER_ID,"-1");
        params.put("user_id",player_id);
        params.put("game_type", game_type);
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", response.toString());
                /* Toast.makeText(Lucky7.this,response.toString(),Toast.LENGTH_LONG).show();*/
                try {
                    JSONArray jsonArray = response.getJSONArray("selected_game");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject employee = jsonArray.getJSONObject(i);


                        String game_id = employee.getString("id");
                         Config.starting_time = employee.getString("start_time");

                        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        //Creating editor to store values to shared preferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();


                        //Adding values to editor

                        editor.putString(Config.G4_ID,game_id);
                        /*editor.putString(Config.GAME_START_TIME,starting_time);*/
                        //Saving values to editor
                        editor.commit();

                    }
                    String[] parts =  Config.starting_time.split(" ");
                    Config.starting_time =parts[1];
                    String jsonObject = response.getString("transaction_id");
                    SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    //Creating editor to store values to shared preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();


                    //Adding values to editor
                    editor.putString(Config.TRANSACTION_ID_g4,jsonObject);

                    editor.commit();

                    /*  convert_min_into_mini();*/

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
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy( 5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsObjRequest);

    }



    private void save_all_transcation() {
        if(!Number_list.isEmpty()) {
            int resultado = 0;
            for (int i = 0; i < Amount_list.size(); i++) {
                String s = Amount_list.get(i);
                resultado += Integer.parseInt(s);
            }
            SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            final String total_tokens = sharedPreferences.getString(Config.TOKENS, "-1");
            int total = Integer.parseInt(total_tokens);
            if (resultado <= total) {
                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("LOADING...");
                progressDialog.show();
                String url = "https://delhidiamond.online/index.php/place-bid";
                Map<String, String> params = new HashMap<String, String>();

/*
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String agent_id = sharedPreferences.getString(Config.AGENT_ID,"-1");
*/
                String A_N = "", A_C = "";
                if (!Andar_Num.isEmpty()) {
                    A_N = TextUtils.join(",", Andar_Num);
                    /*  Toast.makeText(DelhiDiamond.this,A_N,Toast.LENGTH_SHORT).show();
                     */
                    A_C = TextUtils.join(",", Andar_coins);


                }
                String B_N = "", B_C = "";
                if (!Bahar_Num.isEmpty()) {
                    B_N = TextUtils.join(",", Bahar_Num);


                    B_C = TextUtils.join(",", Bahar_coins);
                }
                String T_N = "", T_C = "";
                if (!total_of_Num.isEmpty()) {
                    T_N = TextUtils.join(",", total_of_Num);
                    T_C = TextUtils.join(",", total_coins_of_numbers);
                }
                final String agent_id = sharedPreferences.getString(Config.DEALER_ID, "-1");
                final String player_id = sharedPreferences.getString(Config.PLAYER_ID, "-1");
                final String game_id = sharedPreferences.getString(Config.G4_ID, "-1");
                String game_type = String.valueOf(get);
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
                final String transaction_id_g2 = sharedPreferences.getString(Config.TRANSACTION_ID_g4, "-1");
                params.put("transaction_id", transaction_id_g2);

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
                         */
                        try {
                            String jsonObject = response.getString("transaction_id");
                            SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                            //Creating editor to store values to shared preferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();


                            //Adding values to editor
                            editor.putString(Config.TRANSACTION_ID_g4, jsonObject);

                            editor.commit();

                            progressDialog.dismiss();
                            Amount_list.clear();
                            Number_list.clear();
                            editor.putString(Config.G4_SET_TOTAL_AMOUNT, "0");
                            editor.commit();
                            final String total_coins_g4 = sharedPreferences.getString(Config.G4_SET_TOTAL_AMOUNT, "-1");
                            total_coins.setText(total_coins_g4);
                            display_all_numbers_amount();
                            Toast.makeText(DelhiDiamond.this, "Successfully Saved", Toast.LENGTH_LONG).show();
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
                jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                requestQueue.add(jsObjRequest);

            } else {
                invalid_submit();
            }
        }
        else {
            invalid_submit();
        }
    }

    private void open_bahar_panel_working() {
        Bahar_panel.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Bahar_panel.setTitle("");
        Bahar_panel.show();
        close_bahar_panel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bahar_panel.dismiss();
                shortcuts = new ArrayAdapter<String>(DelhiDiamond.this,android.R.layout.simple_list_item_1,jantri_array);
                shortcuts_spinner.setAdapter(shortcuts);
            }
        });

        submit_bahar_panel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!bahar_input_number.getText().toString().equals("")&&!bahar_input_amount.getText().toString().equals("")){
                    String get_number= String.valueOf(bahar_input_number.getText());
                    String get_amount = String.valueOf(bahar_input_amount.getText());
                    int check_number= Integer.parseInt(get_number);
                    int check_amount= Integer.parseInt(get_amount);
                    if(check_number<10&&check_number>-1){
                        /*if (!Number_list.isEmpty()) {
                            if (!Number_list.contains("BBB:"+get_number)) {
                                Number_list.add("BBB:"+get_number);
                                Amount_list.add(get_amount);
                            }else{
                                int e  = Number_list.indexOf("BBB:"+get_number);
                                int check_value_of_amount = Integer.parseInt(get_amount);
                                int x = Integer.parseInt(Amount_list.get(e));
                                x= check_value_of_amount+x;
                                String change_amount = String.valueOf(x);
                                Amount_list.remove(e);
                                Amount_list.add(e,change_amount);
                                display_all_numbers_amount();
                            }
                        } else {
                        */    Number_list.add("BBB:"+get_number);
                            Amount_list.add(get_amount);
                            bahar_input_number.getText().clear();
                            bahar_input_amount.getText().clear();
                            bahar_input_number.requestFocus();
                        /*}*/  Bahar_panel.dismiss();

                        display_all_numbers_amount();
                        shortcuts = new ArrayAdapter<String>(DelhiDiamond.this,android.R.layout.simple_list_item_1,jantri_array);
                        shortcuts_spinner.setAdapter(shortcuts);
                    }else {
                        invalid_submit();
                    }
                }else {
                    invalid_submit();
                }

            }
        });
    }

    private void open_andar_panel_working() {
        Andar_panel.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Andar_panel.setTitle("");
        Andar_panel.show();
        close_andar_panel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Andar_panel.dismiss();
                shortcuts = new ArrayAdapter<String>(DelhiDiamond.this,android.R.layout.simple_list_item_1,jantri_array);
                shortcuts_spinner.setAdapter(shortcuts);
            }
        });
        submit_andar_panel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!andar_input_number.getText().toString().equals("")&&!andar_input_amount.getText().toString().equals("")){
                    String get_number= String.valueOf(andar_input_number.getText());
                    String get_amount = String.valueOf(andar_input_amount.getText());
                    int check_number= Integer.parseInt(get_number);
                    int check_amount= Integer.parseInt(get_amount);
                    if(check_number<10&&check_number>-1){
/*
                        if (!Number_list.isEmpty()) {
                            if (!Number_list.contains("AAA:"+get_number)) {
                                Number_list.add("AAA:"+get_number);
                                Amount_list.add(get_amount);
                            }else{
                                int e  = Number_list.indexOf("AAA:"+get_number);
                                int check_value_of_amount = Integer.parseInt(get_amount);
                                int x = Integer.parseInt(Amount_list.get(e));
                                x= check_value_of_amount+x;
                                String change_amount = String.valueOf(x);
                                Amount_list.remove(e);
                                Amount_list.add(e,change_amount);
                                display_all_numbers_amount();
                            }
                        } else {
                          */  Number_list.add("AAA:"+get_number);
                            Amount_list.add(get_amount);
                        /*}*/
                        Andar_panel.dismiss();
                        andar_input_number.getText().clear();
                        andar_input_amount.getText().clear();
                        andar_input_number.requestFocus();
                        display_all_numbers_amount();
                        shortcuts = new ArrayAdapter<String>(DelhiDiamond.this,android.R.layout.simple_list_item_1,jantri_array);
                        shortcuts_spinner.setAdapter(shortcuts);
                    }else {
                        invalid_submit();
                    }
                }else {
                    invalid_submit();
                }

            }
        });
    }

    private void open_crossing_panel_working() {
        crossing_number_count=0;
        Crossing_panel.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Crossing_panel.setTitle("");
        Crossing_panel.show();
        close_crossing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Crossing_panel.dismiss();
                shortcuts = new ArrayAdapter<String>(DelhiDiamond.this,android.R.layout.simple_list_item_1,jantri_array);
                shortcuts_spinner.setAdapter(shortcuts);
                refresh_crossing();
            }
        });
        submit_crosspanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!Config.get_numbers_row1.isEmpty()&&!Config.get_numbers_row2.isEmpty()&&!crossing_panel_amount.getText().toString().equals("")){
                   String get_crossing_amount = String.valueOf(crossing_panel_amount.getText());
                   int crossing_total_numbers=(Config.get_numbers_row1.size()*Config.get_numbers_row2.size());
                   int check_amount= Integer.parseInt(get_crossing_amount)/crossing_total_numbers;
                  /* if(check_amount>=10) {*/
                       if (Config.get_numbers_row1.size() >= Config.get_numbers_row2.size()) {
                           for (int i = 0; i < Config.get_numbers_row1.size(); i++) {
                               String s1 = Config.get_numbers_row1.get(i);
                               for (int j = 0; j < Config.get_numbers_row2.size(); j++) {
                                   String s2 = Config.get_numbers_row2.get(j);
                                   String make_number = s1 + s2;
                                   if (checkBox_joda.isChecked()) {
                                       /*if (!Number_list.isEmpty()) {
                                           if (!Number_list.contains(make_number)) {
                                               Number_list.add(make_number);
                                               crossing_number_count++;
                                           }else{
                                               int e  = Number_list.indexOf(make_number);
                                               int check_value_of_amount = Integer.parseInt(get_crossing_amount);
                                               int x = Integer.parseInt(Amount_list.get(e));
                                               x= check_value_of_amount+x;
                                               String change_amount = String.valueOf(x);
                                               Amount_list.remove(e);
                                               Amount_list.add(e,change_amount);
                                               display_all_numbers_amount();
                                               Amount_entered.getText().clear();
                                           }
                                       } else {*/
                                           Number_list.add(make_number);
                                           crossing_number_count++;
                                       /*}*/
                                   } else {
                                       int check_make_numberis_joda = Integer.parseInt(make_number);
                                       if ((check_make_numberis_joda % 11) != 0) {
                                           /*if (!Number_list.isEmpty()) {
                                               if (!Number_list.contains(make_number)) {
                                                   Number_list.add(make_number);
                                                   crossing_number_count++;
                                               }else{
                                                   int e  = Number_list.indexOf(make_number);
                                                   int check_value_of_amount = Integer.parseInt(get_crossing_amount);
                                                   int x = Integer.parseInt(Amount_list.get(e));
                                                   x= check_value_of_amount+x;
                                                   String change_amount = String.valueOf(x);
                                                   Amount_list.remove(e);
                                                   Amount_list.add(e,change_amount);
                                                   display_all_numbers_amount();
                                                   Amount_entered.getText().clear();
                                               }
                                           } else {*/
                                               Number_list.add(make_number);
                                               crossing_number_count++;
                                           /*}*/
                                       }
                                   }
                               }
                           }
                           for(int i=0; i<crossing_number_count;i++){
                               Amount_list.add(get_crossing_amount);
                           }
                           Crossing_panel.dismiss();
                           display_all_numbers_amount();
                           shortcuts = new ArrayAdapter<String>(DelhiDiamond.this,android.R.layout.simple_list_item_1,jantri_array);
                           shortcuts_spinner.setAdapter(shortcuts);
                           refresh_crossing();
                       }
                       if (Config.get_numbers_row2.size() > Config.get_numbers_row1.size()) {
                           for (int i = 0; i < Config.get_numbers_row2.size(); i++) {
                               String s1 = Config.get_numbers_row2.get(i);
                               for (int j = 0; j < Config.get_numbers_row1.size(); j++) {
                                   String s2 = Config.get_numbers_row1.get(j);
                                   String make_number = s1 + s2;
                                   if (checkBox_joda.isChecked()) {
                                   /*    if (!Number_list.isEmpty()) {
                                           if (!Number_list.contains(make_number)) {
                                               Number_list.add(make_number);
                                               crossing_number_count++;
                                           }else{
                                               int e  = Number_list.indexOf(make_number);
                                               int check_value_of_amount = Integer.parseInt(get_crossing_amount);
                                               int x = Integer.parseInt(Amount_list.get(e));
                                               x= check_value_of_amount+x;
                                               String change_amount = String.valueOf(x);
                                               Amount_list.remove(e);
                                               Amount_list.add(e,change_amount);
                                               display_all_numbers_amount();
                                               Amount_entered.getText().clear();
                                           }
                                       } else {*/
                                           Number_list.add(make_number);
                                           crossing_number_count++;
                                       /*}*/
                                   } else {
                                       int check_make_numberis_joda = Integer.parseInt(make_number);
                                       if ((check_make_numberis_joda % 11) != 0) {
                                         /*  if (!Number_list.isEmpty()) {
                                               if (!Number_list.contains(make_number)) {
                                                   Number_list.add(make_number);
                                                   crossing_number_count++;
                                               }else{
                                                   int e  = Number_list.indexOf(make_number);
                                                   int check_value_of_amount = Integer.parseInt(get_crossing_amount);
                                                   int x = Integer.parseInt(Amount_list.get(e));
                                                   x= check_value_of_amount+x;
                                                   String change_amount = String.valueOf(x);
                                                   Amount_list.remove(e);
                                                   Amount_list.add(e,change_amount);
                                                   display_all_numbers_amount();
                                                   Amount_entered.getText().clear();
                                               }
                                           } else {*/
                                               Number_list.add(make_number);
                                               crossing_number_count++;
                                           /*}*/
                                       }
                                   }
                               }
                           }
                           for(int i=0; i<crossing_number_count;i++){
                             /*  int get_cross_panel_amount = Integer.parseInt(get_crossing_amount)/crossing_number_count;
                               String s = String.valueOf(get_cross_panel_amount);*/
                               Amount_list.add(get_crossing_amount);
                           }
                           Crossing_panel.dismiss();
                           display_all_numbers_amount();
                           shortcuts = new ArrayAdapter<String>(DelhiDiamond.this,android.R.layout.simple_list_item_1,jantri_array);
                           shortcuts_spinner.setAdapter(shortcuts);
                           refresh_crossing();
                       }
                  /* }else{
                       invalid_submit();
                   }*/
                }else{
                    invalid_submit();
                }
            }
        });

    }

    private void refresh_crossing() {
        Crossing_row1_button_numbers.clear();
        Crossing_row2_button_numbers.clear();
        crossing_panel_amount.getText().clear();
        for(int i=1; i<11;i++){
            if(i==10){
                Crossing_row1_button_numbers.add("0");
                Crossing_row2_button_numbers.add("0");
            }else{
                String s = String.valueOf(i);
                Crossing_row1_button_numbers.add(s);
                Crossing_row2_button_numbers.add(s);

            }
        }
        RecyclerView.LayoutManager manager2= new GridLayoutManager(DelhiDiamond.this,10);
        crossing_row_two_recycler.setHasFixedSize(true);
        crossing_row_two_recycler.setLayoutManager(manager2);
        Crossing_row2 attributes_crossing_row2 = new Crossing_row2(Crossing_row2_button_numbers, DelhiDiamond.this);
        crossing_row_two_recycler.setAdapter(attributes_crossing_row2);


        RecyclerView.LayoutManager manager1= new GridLayoutManager(DelhiDiamond.this,10);
        crossing_row_one_recycler.setHasFixedSize(true);
        crossing_row_one_recycler.setLayoutManager(manager1);
        Crossing_row1 attributes_crossing_row1 = new Crossing_row1(Crossing_row1_button_numbers, DelhiDiamond.this);
        crossing_row_one_recycler.setAdapter(attributes_crossing_row1);
    }

    private void open_joda_panel_working() {
        joda_number_count=0;
        cancel_action=joda_panel.findViewById(R.id.cancel_action);
        cancel_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joda_panel.dismiss();
                shortcuts = new ArrayAdapter<String>(DelhiDiamond.this,android.R.layout.simple_list_item_1,jantri_array);
                shortcuts_spinner.setAdapter(shortcuts);
            }
        });
        joda_panel.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        joda_panel.setTitle("");
        TextView joda_bid= joda_panel.findViewById(R.id.joda_bid);
        joda_bid.setText("Joda Bid");
        joda_panel.show();
        close_joda_panel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joda_panel.dismiss();
                shortcuts = new ArrayAdapter<String>(DelhiDiamond.this,android.R.layout.simple_list_item_1,jantri_array);
                shortcuts_spinner.setAdapter(shortcuts);
            }
        });
        submit_amount_joda_panel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!joda_input_amount.getText().toString().equals("")){
                    String get_joda = String.valueOf(joda_input_amount.getText());
                    int check_joda_amount = Integer.parseInt(get_joda)/10;
                  /*  if(check_joda_amount>=10){*/
                       for(int i=1; i<10+1;i++){
                           int joda_numbers= i*11;
                          String s= String.valueOf(joda_numbers);
                          if(i!=10) {
                              /*if (!Number_list.isEmpty()) {
                                  if (!Number_list.contains(s)) {
                                      Number_list.add(s);
                                      joda_number_count++;
                                  }else{
                                      int j  = Number_list.indexOf(s);
                                      int check_value_of_amount = Integer.parseInt(get_joda);
                                      int x = Integer.parseInt(Amount_list.get(j));
                                      x= check_value_of_amount+x;
                                      String change_amount = String.valueOf(x);
                                      Amount_list.remove(j);
                                      Amount_list.add(j,change_amount);
                                      display_all_numbers_amount();
                                      bid_number.getText().clear();
                                      Amount_entered.getText().clear();
                                  }
                              } else {*/
                                  Number_list.add(s);
                                  joda_number_count++;
                              /*}*/
                          }else {
                              /*if (!Number_list.isEmpty()) {
                                  if (!Number_list.contains(s)) {
                                      Number_list.add("00");
                                      joda_number_count++;
                                 }else {
                                      int j  = Number_list.indexOf(s);
                                      int check_value_of_amount = Integer.parseInt(get_joda);
                                      int x = Integer.parseInt(Amount_list.get(j));
                                      x= check_value_of_amount+x;
                                      String change_amount = String.valueOf(x);
                                      Amount_list.remove(j);
                                      Amount_list.add(j,change_amount);
                                      display_all_numbers_amount();
                                      bid_number.getText().clear();
                                      Amount_entered.getText().clear();
                                  }
                              } else {*/
                                  Number_list.add("00");
                                  joda_number_count++;
                              /*}*/
                          }

                       }for(int i=0; i<joda_number_count; i++){
                           /* int joda_amount = Integer.parseInt(get_joda) /   joda_number_count;
                           String dis_joda_amount = String.valueOf(joda_amount);*/
                           Amount_list.add(get_joda);
                        }
                        joda_input_amount.getText().clear();
                       joda_panel.dismiss();
                        display_all_numbers_amount();
                        shortcuts = new ArrayAdapter<String>(DelhiDiamond.this,android.R.layout.simple_list_item_1,jantri_array);
                        shortcuts_spinner.setAdapter(shortcuts);
                   /* }else{
                        invalid_submit();
                    }*/
                }else {
                    invalid_submit();
                }
            }
        });
    }

    private void open_from_to_panel_working() {
        From_to_panel.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        From_to_panel.setTitle("");
        From_to_panel.show();
        count_how_many_numbers=0;
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                From_to_panel.dismiss();
                shortcuts = new ArrayAdapter<String>(DelhiDiamond.this,android.R.layout.simple_list_item_1,jantri_array);
                shortcuts_spinner.setAdapter(shortcuts);
            }
        });

        Submit_from_to_nd_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*  SharedPreferences sharedPreferences = DelhiDiamond.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    final String current_coins = sharedPreferences.getString(Config.TOKENS, "-1");
                    int check_current_coins = Integer.parseInt(current_coins);
                  */

                if(!from_input.getText().toString().equals("")&&!to_input.getText().toString().equals("")&&!from_to_amount.getText().toString().equals("")) {
                    String get_from = String.valueOf(from_input.getText());
                    String get_to = String.valueOf(to_input.getText());
                    String get_amount = String.valueOf(from_to_amount.getText());
                    int last_goes_to = Integer.parseInt(get_to);
                    if(get_to.equals("00")){
                        get_to="100";
                    }
                    int last_goes_from = Integer.parseInt(get_from);

                    if (last_goes_to <= 100 && last_goes_from <= 100) {
                        if(last_goes_from<=last_goes_to){
                        int total_number = (Integer.parseInt(get_to) - Integer.parseInt(get_from)) + 1;
                        int dis_amount = Integer.parseInt(get_amount) / total_number;
                        /* if (dis_amount >= 10 && (last_goes_from <= last_goes_to)) {*/
                        for (int i = Integer.parseInt(get_from); i < Integer.parseInt(get_to) + 1; i++) {
                            String s = String.valueOf(i);
                               /* if (!Number_list.isEmpty()) {
                                    if (!Number_list.contains(s)) {
                                        Number_list.add(s);
                                        count_how_many_numbers++;
                                    }else{
                                        int j  = Number_list.indexOf(s);
                                        int check_value_of_amount = Integer.parseInt(get_amount);
                                        int x = Integer.parseInt(Amount_list.get(j));
                                        x= check_value_of_amount+x;
                                        String change_amount = String.valueOf(x);
                                        Amount_list.remove(j);
                                        Amount_list.add(j,change_amount);
                                        display_all_numbers_amount();
                                        bid_number.getText().clear();
                                        Amount_entered.getText().clear();
                                    }
                                } else {*/
                            Number_list.add(s);
                            count_how_many_numbers++;
                            /*  }*/
                        }
                        for (int i = 0; i < count_how_many_numbers; i++) {

                            Amount_list.add(get_amount);
                        }
                        from_input.getText().clear();
                        to_input.getText().clear();
                        from_to_amount.getText().clear();
                        from_input.requestFocus();

                        display_all_numbers_amount();
                        shortcuts = new ArrayAdapter<String>(DelhiDiamond.this, android.R.layout.simple_list_item_1, jantri_array);
                        shortcuts_spinner.setAdapter(shortcuts);
                        From_to_panel.dismiss();

                        /*} else {
                            invalid_submit();
                        }*/
                    }else {
                        invalid_submit();
                    }
                    }else {
                        invalid_submit();
                    }
                }else{
                    invalid_submit();
                }

            }
        });
    }

    private void invalid_submit() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DelhiDiamond.this);
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

    private void display_all_numbers_amount() {
        Collections.reverse(Number_list);
        Collections.reverse(Amount_list);
        display_number_with_amount_delhi_diamond attributes = new display_number_with_amount_delhi_diamond(Number_list,Amount_list, total_coins,DelhiDiamond.this);
        recycler_display_number_with_amount.setAdapter(attributes);


    }

    /* private void closecard_image(){

         for(int i=1; i<=55; i++){
             String s= String.valueOf(i);
             mIconText.add(s);
         }



     }

     private void closecard_display(){
         delhidiamond_first_recycler attributes= new delhidiamond_first_recycler(myImageList,mIconText,DelhiDiamond.this);
         vieww.setAdapter(attributes);

     }*/
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
            Intent intent= new Intent(DelhiDiamond.this,Home.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.points_report) {
            Intent intent = new Intent(DelhiDiamond.this, PointsReport.class);
            startActivity(intent);
            finish();
        }
        else if(id==R.id.coins_report){
                Intent intent= new Intent(DelhiDiamond.this,TransactionHistory.class);
                startActivity(intent);
                finish();


 /*       } else if (id == R.id.daily_summ_report) {

        } else if (id == R.id.all_summary_report) {

        } else if (id == R.id.declared_transactions) {

        } else if (id == R.id.declare_report) {

        } else if (id == R.id.share) {*/

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
                                Intent intent = new Intent(DelhiDiamond.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError response) {
                                Log.d("Response: ", response.toString());
                            }
                        });
                        RequestQueue requestQueue = Volley.newRequestQueue(DelhiDiamond.this);
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
            Intent intent = new Intent(DelhiDiamond.this, Home.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

}
