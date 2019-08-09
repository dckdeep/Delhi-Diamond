package com.technotion.delhiDiamond.AllFragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.technotion.delhiDiamond.Admin_Delhi_Diamond_Game_view;
import com.technotion.delhiDiamond.ImageNicer;
import com.technotion.delhiDiamond.R;
import com.technotion.delhiDiamond.TransactionHistory;
import com.technotion.delhiDiamond.ajent_users_list;
import com.technotion.delhiDiamond.sever_classes.Config;
import com.technotion.delhiDiamond.sever_classes.CustomRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Admindashbord extends Fragment {
    TextView Current_Tokens;
    ImageView onethree_patti,birds,lucky7,delhi_diamond,faridabad,ghaziabad,gali,desawar,check_coins,list_of_users;
    int id_1 =R.drawable.onethree_patti;
    int id_2 =R.drawable.birds;
    int id_3 =R.drawable.lucky7;
    int id_4 =R.drawable.delhi_diamond_logo;
    int id_5=R.drawable.faridabad;
    int id_6 =R.drawable.ghaziabad;
    int id_7 =R.drawable.gali;
    int id_8 =R.drawable.desawar;
    int id_9 =R.drawable.check_coins;
    int id_10 =R.drawable.list_of_user;
    TextView onethree_patti_users,chidiya_button_users,lucky7_button_users,delhi_diamond_button_users,faridabad_button_users,ghaziabad_button_users,gali_button_users,desawar_button_users;
    Button ref;
    Context context;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

      view = inflater.inflate(R.layout.admin_working_panel, container, false);
        // userlist();
        // usersendpoints(view);
        onethree_patti_users=view.findViewById(R.id.onethree_patti_users);
        chidiya_button_users=view.findViewById(R.id.chidiya_button_users);
        lucky7_button_users=view.findViewById(R.id.lucky7_button_users);
         delhi_diamond_button_users=view.findViewById(R.id.delhi_diamond_button_users);
        faridabad_button_users=view.findViewById(R.id.faridabad_button_users);
        ghaziabad_button_users=view.findViewById(R.id.ghaziabad_button_users);
        gali_button_users=view.findViewById(R.id.gali_button_users);
        desawar_button_users=view.findViewById(R.id.desawar_button_users);

        context =getContext();
        ref=view.findViewById(R.id.ref);
        ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                active_user();
                opendashbord(view);

            }
        });
        active_user();
        opendashbord(view);
        return view;
    }

    private void active_user() {
       final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("LOADING...");
        progressDialog.show();
        String URL = "https://delhidiamond.online/index.php/get-active-users";
        final Map<String, String> params = new HashMap<String, String>();
     /* *//*  final String agent_id = context.sharedPreferences.getString(Config.PLAYER_ID, "-1");
      *//* ;*/

        params.put("user_id","1");
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, URL, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", response.toString());
                /*        Toast.makeText(Home.this,response.toString(),Toast.LENGTH_LONG).show();
                 */

                progressDialog.dismiss();
                JSONArray jsonArray = null;
                try {
                    jsonArray = response.getJSONArray("response");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject employee = jsonArray.getJSONObject(i);

                        String game_type = employee.getString("game_type");
                        /*                        String username = employee.getString("username");*/
                        String users_count = employee.getString("users_count");
                       if(game_type.equals("1")){
                           onethree_patti_users.setText(users_count);
                       }
                        if(game_type.equals("2")){
                            chidiya_button_users.setText(users_count);
                        }
                        if(game_type.equals("3")){
                            lucky7_button_users.setText(users_count);
                        }
                        if(game_type.equals("4")){
                            delhi_diamond_button_users.setText(users_count);
                        }
                        if(game_type.equals("5")){
                            faridabad_button_users.setText(users_count);
                        }
                        if(game_type.equals("6")){
                            ghaziabad_button_users.setText(users_count);
                        }

                        if(game_type.equals("7")){
                            gali_button_users.setText(users_count);
                        }

                        if(game_type.equals("8")){
                            desawar_button_users.setText(users_count);
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
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy( 5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsObjRequest);
    }

    private void opendashbord(final View view) {
        Current_Tokens = view.findViewById(R.id.current_tokens);
        LinearLayout patti_button = view.findViewById(R.id.patti_button);
        LinearLayout chidiya_button = view.findViewById(R.id.chidiya_button);
        LinearLayout lucky7_button = view.findViewById(R.id.lucky7_button);
        LinearLayout delhi_diamond_button = view.findViewById(R.id.delhi_diamond_button);
        LinearLayout faridabad_button = view.findViewById(R.id.faridabad_button);
        LinearLayout ghaziabad_button = view.findViewById(R.id.ghaziabad_button);
        LinearLayout gali_button = view.findViewById(R.id.gali_button);
        LinearLayout desawar_button = view.findViewById(R.id.desawar_button);

        LinearLayout ajent_dashboard_points_display = view.findViewById(R.id.ajent_dashboard_points_display);
        LinearLayout List_of_users = view.findViewById(R.id.List_of_users);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        onethree_patti=view.findViewById(R.id.onethree_patti);
        onethree_patti.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(context.getResources(),id_1,300,200));
        birds=view.findViewById(R.id.birds);
        birds.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(context.getResources(),id_2,300,200));
        lucky7=view.findViewById(R.id.lucky7);
        lucky7.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(context.getResources(),id_3,300,200));
        delhi_diamond=view.findViewById(R.id.delhi_diamond);
        delhi_diamond.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(context.getResources(),id_4,300,200));
        faridabad=view.findViewById(R.id.faridabad);
        faridabad.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(context.getResources(),id_5,300,200));

        ghaziabad= view.findViewById(R.id.ghaziabad);
        ghaziabad.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(context.getResources(),id_6,300,200));

        gali=view.findViewById(R.id.gali);
        gali.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(context.getResources(),id_7,300,200));

        desawar=view.findViewById(R.id.desawar);
        desawar.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(context.getResources(),id_8,300,200));

        check_coins=view.findViewById(R.id.check_coins);
        check_coins.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(context.getResources(),id_9,300,200));

        list_of_users=view.findViewById(R.id.list_of_users);
        list_of_users.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(context.getResources(),id_10,300,200));



        String user_coin = sharedPreferences.getString(Config.TOKENS,"Not Available");
        Current_Tokens.setText(user_coin);

        patti_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        chidiya_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        lucky7_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        delhi_diamond_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Admin_Delhi_Diamond_Game_view.class);
                intent.putExtra("open",4);
                startActivity(intent);
            }
        });

        faridabad_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Admin_Delhi_Diamond_Game_view.class);
                intent.putExtra("open",5);
                startActivity(intent);
            }
        });

        ghaziabad_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Admin_Delhi_Diamond_Game_view.class);
                intent.putExtra("open",6);
                startActivity(intent);
            }
        });

        gali_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Admin_Delhi_Diamond_Game_view.class);
                intent.putExtra("open",7);
                startActivity(intent);
            }
        });

        desawar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Admin_Delhi_Diamond_Game_view.class);
                intent.putExtra("open",8);
                startActivity(intent);
            }
        });
        ajent_dashboard_points_display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TransactionHistory.class);
                startActivity(intent);

            }
        });
        List_of_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), ajent_users_list.class);
                startActivity(intent);
            }
        });
    }



}
