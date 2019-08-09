package com.technotion.delhiDiamond.Adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.rengwuxian.materialedittext.MaterialEditText;
import com.technotion.delhiDiamond.ImageNicer;
import com.technotion.delhiDiamond.R;
import com.technotion.delhiDiamond.sever_classes.Config;
import com.technotion.delhiDiamond.sever_classes.CustomRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class opencardrecycler extends RecyclerView.Adapter<opencardrecycler.viewHolder> {

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView,tab_bet_text;
        CardView cardView;



        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imageView =itemView.findViewById(R.id.closecard_img);
            textView =itemView.findViewById(R.id.points_count);
            cardView=itemView.findViewById(R.id.card);
        }

    }
    ArrayList<Integer> myImageList = new ArrayList<>();
    ArrayList<String> mIconText = new ArrayList<>();
    Context context;
    TextView total_amount;
    int game_type;
    int count_check=0;
    ProgressDialog progressDialog;

    public opencardrecycler(   ArrayList<Integer> myImageList, ArrayList<String> mIconText,TextView total_amount, int game_type,Context context) {
        this.myImageList = myImageList;
        this.mIconText = mIconText;
        this.total_amount=total_amount;
        this.context = context;
        this.game_type=game_type;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View viewfull = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.closecarddisplay,viewGroup,false);
        viewHolder v = new viewHolder(viewfull);
        return v;
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, final int i) {
      /*  Glide.with(context).asBitmap().load(mIcons.get(i)).into(viewHolder.imageView);
        viewHolder.textView.setText(mIconText.get(i));

        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        if(game_type==1) {
            SharedPreferences sharedPreferencess = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            final String total___Amount = sharedPreferencess.getString(Config.G1_SET_TOTAL_AMOUNT, "0");
            total_amount.setText(total___Amount);
        }if(game_type==2){
            SharedPreferences sharedPreferencess = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            final String total___Amount = sharedPreferencess.getString(Config.G2_SET_TOTAL_AMOUNT, "0");
            total_amount.setText(total___Amount);
        }
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup);
        holder.cardView.setBackground(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        holder.imageView.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(context.getResources(),myImageList.get(i),50,50));
        holder.textView.setText(mIconText.get(i));

        final ImageView popup__image   =(ImageView)dialog.findViewById(R.id.popup_image);
        final ImageButton close_popup = dialog.findViewById(R.id.close_popup);
        final Button add_text = dialog.findViewById(R.id.add_bet_point);
        final Button less_text = dialog.findViewById(R.id.less_bet_point);
        final TextView hinditext =dialog.findViewById(R.id.hinditext);
        if(game_type==1){
            hinditext.setText("जीतने पर 100 के 1200 पॉइंट्स मिलेंगे");
        }if(game_type==2){
            hinditext.setText("जीतने पर 100 के 1100 पॉइंट्स मिलेंगे");
        }
       /* less_text.setEnabled(false);
        less_text.setVisibility(View.INVISIBLE);*/
        final MaterialEditText bet_point = dialog.findViewById(R.id.bet_point);


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup__image.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(context.getResources(),myImageList.get(i),300,200));
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setTitle("");
                get_bid_id(i);
                dialog.show();


            }
        });

        close_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                /*          Toast.makeText(context,"You Can Change Your Bet Till 14min",Toast.LENGTH_LONG).show();
                 */      }
        });

        add_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bet_amount = String.valueOf(bet_point.getText());
                SharedPreferences sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                final String current_coins = sharedPreferences.getString(Config.TOKENS, "-1");
                final String get_bid_id_of_postion =sharedPreferences.getString(Config.BID_ID, "-1");

                int check_current_coins = Integer.parseInt(current_coins);
                int check_bet_amount = Integer.parseInt(bet_amount);

                if (check_bet_amount >= 10) {
                    if (check_current_coins >= check_bet_amount) {
                        if (!bet_amount.equals("")) {
                            if (get_bid_id_of_postion.equals("0")) {

                                int bid = Integer.parseInt(bet_amount);

                                send_bid_amount(i, bid, holder);
                            } else {
                                int convert = Integer.parseInt(bet_amount);
                                count_check=1;
                                send_update_bid_amount(i, convert, holder);
                            }
                            /*  Toast.makeText(context, get_bid_id_of_postion, Toast.LENGTH_LONG).show();*/
                            dialog.dismiss();
                        } else {
                            dialog.dismiss();
                            Toast.makeText(context, "Please Enter Your Coins", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                        alertDialogBuilder.setMessage("Your Current Coins Are Less Than Your Bid Coins");
                        alertDialogBuilder.setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {

                                    }
                                });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();

                    }
                }else{
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setMessage("Your bid is Less Than 10");
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
        less_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mIconText.get(i).equals("0")) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setMessage("Your Want Remove bid From The Card");
                    alertDialogBuilder.setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    count_check=0;
                                    send_update_bid_amount(i, 0, holder);
                                    dialog.dismiss();
                                    /*  Toast.makeText(context, "You Can Change Your Bid Till 14min", Toast.LENGTH_LONG).show();*/
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
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
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
    }

    private void get_bid_id(int i) {
        String url = "https://delhidiamond.online/index.php/get-bid-id";
        Map<String, String> params = new HashMap<String, String>();
        SharedPreferences sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String player_id = sharedPreferences.getString(Config.PLAYER_ID,"-1");
        final String card_id = String.valueOf(i+1);
        if(game_type==1){
            final String game1_id = sharedPreferences.getString(Config.GAME1_ID,"-1");
            params.put("game_id",game1_id);
            params.put("game_type",String.valueOf(game_type));
        }if(game_type==2){
            final String game2_id = sharedPreferences.getString(Config.GAME2_ID,"-1");
            params.put("game_id",game2_id);
            params.put("game_type",String.valueOf(game_type));
        }
        params.put("player_id",player_id);

        params.put("card_id",card_id);
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", response.toString());
                /*           Toast.makeText(context,response.toString(),Toast.LENGTH_LONG).show();
                 */       try {

                    String bid_id = response.getString("bid_id");
                    SharedPreferences sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

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
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy( 5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsObjRequest);
    }


    private void send_update_bid_amount(int i, int convertint, final viewHolder holder) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String url = "https://delhidiamond.online/index.php/update-bid";
        Map<String, String> params = new HashMap<String, String>();

        SharedPreferences sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String bid_id = sharedPreferences.getString(Config.BID_ID,"-1");
        final String player_id = sharedPreferences.getString(Config.PLAYER_ID,"-1");
        /* final String transaction_id = sharedPreferences.getString(Config.TRANSACTION_ID,"-1");*/
        final String card_id = String.valueOf(i+1);
        final String bid__amount = String.valueOf(convertint);
    /*    if(game_type==1){
            final String bid_id_g1 = sharedPreferences.getString(Config.G1_BID_ID,"-1");
            String[] parts = bid_id_g1.split(",");
            String bid_id_g1_card =parts[i];
            params.put("bid_id", bid_id_g1_card);
        }*/

        if(game_type==1){
            final String game1_id = sharedPreferences.getString(Config.GAME1_ID,"-1");
            mIconText.set(i, String.valueOf(convertint));
            convertintoint();
            final String total_amount = sharedPreferences.getString(Config.G1_SET_TOTAL_AMOUNT,"0");
            params.put("total_amount",total_amount);
            params.put("game_id",game1_id);
            final String transaction_id_g1 = sharedPreferences.getString(Config.TRANSACTION_ID_g1,"-1");
            params.put("transaction_id",transaction_id_g1);
        }if(game_type==2){
            final String game2_id = sharedPreferences.getString(Config.GAME2_ID,"-1");
            final String total_amount = sharedPreferences.getString(Config.G2_SET_TOTAL_AMOUNT,"0");
            int total=0;
            if(count_check==1) {
                total= Integer.parseInt(total_amount);
                total = total+convertint;
            }if(count_check==0){
                total= Integer.parseInt(total_amount);
                int x= Integer.parseInt(mIconText.get(i));
                total = total-x;
            }

            String total_amt=String.valueOf(total);
            params.put("total_amount",total_amt);
            params.put("game_id",game2_id);
            final String transaction_id_g2 = sharedPreferences.getString(Config.TRANSACTION_ID_g2,"-1");
            params.put("transaction_id",transaction_id_g2);
        }
        params.put("bid_id", bid_id);
        params.put("player_id",player_id);

        params.put("card_id",card_id);
        params.put("bid_amount",bid__amount);



    /*    Toast.makeText(context,bid_id+"\nplayer_id"+player_id+"\ntransaction_id"+
                transaction_id+"\ncard_id"+card_id+"\nbid__amount"+bid__amount,Toast.LENGTH_LONG).show();
*/


        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", response.toString());
                progressDialog.dismiss();
                /*   Toast.makeText(context,response.toString(),Toast.LENGTH_LONG).show();*/
                try {

                    String bid_id = response.getString("bid_id");
                    String card_id = response.getString("card_id");
                    String bid_amount = response.getString("bid_amount");
                    String transaction_id = response.getString("transaction_id");
                    SharedPreferences sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                    //Creating editor to store values to shared preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();


                    //Adding values to editor
                    editor.putString(Config.BID_ID,bid_id);
                    editor.putString(Config.CARD_ID,card_id);
                    editor.putString(Config.BID_Amount,bid_amount);

                    if(game_type==1) {
                        editor.putString(Config.G1_coins, "");
                        editor.putString(Config.TRANSACTION_ID_g1,transaction_id);
                    }if(game_type==2){
                        editor.putString(Config.G2_coins, "");
                        editor.putString(Config.TRANSACTION_ID_g2,transaction_id);
                    }


                    /*Gson gson = new Gson();
                    String json = gson.toJson(Config.G1_coins);*/

                    //Saving values to editor
                    editor.commit();

                    SharedPreferences sharedPreferencess = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    final String card___id = sharedPreferencess.getString(Config.CARD_ID,"-1");
                    final String bid_Amount = sharedPreferencess.getString(Config.BID_Amount,"-1");
                    int card_no= Integer.parseInt(card___id)-1;

                    /*int convert = Integer.parseInt(bid_Amount);
                    int convertint=Integer.parseInt(mIconText.get(card_no));
                    convertint = convertint+ convert;*/




                    mIconText.set(card_no,bid_Amount);

                    String result = TextUtils.join(",", mIconText);
                    if(game_type==1){
                        editor.putString(Config.G1_coins,result);
                        editor.commit();
                        mIconText.clear();
                        final String game_start_time = sharedPreferencess.getString(Config.G1_coins,"-1");
                        String[] parts = game_start_time.split(",");
                        for(int ss=0; ss<parts.length; ss++){
                            mIconText.add(parts[ss]);
                            /*   Toast.makeText(context,mIconText.get(ss),Toast.LENGTH_LONG).show();*/
                        }
                    }
                    if(game_type==2){
                        editor.putString(Config.G2_coins,result);
                        editor.commit();
                        mIconText.clear();
                        final String game_start_time = sharedPreferencess.getString(Config.G2_coins,"-1");
                        String[] parts = game_start_time.split(",");
                        for(int ss=0; ss<parts.length; ss++){
                            mIconText.add(parts[ss]);
                            /*   Toast.makeText(context,mIconText.get(ss),Toast.LENGTH_LONG).show();*/
                        }
                    }

                    /*Config.G1_coins.set(card_no,bid_Amount);*/

                    // Toast.makeText(context,mIconText.get(card_no),Toast.LENGTH_LONG).show();
                    holder.textView.setText(mIconText.get(card_no));

                    convertintoint();
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
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy( 5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsObjRequest);

    }
    private void send_bid_amount(final int i, int bid_amount, final viewHolder holder) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String url = "https://delhidiamond.online/index.php/place-bid";
        Map<String, String> params = new HashMap<String, String>();


        SharedPreferences sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String agent_id = sharedPreferences.getString(Config.DEALER_ID,"-1");
        final String player_id = sharedPreferences.getString(Config.PLAYER_ID,"-1");
        if(game_type==1){
            final String game1_id = sharedPreferences.getString(Config.GAME1_ID,"-1");
            final String total_amount = sharedPreferences.getString(Config.G1_SET_TOTAL_AMOUNT,"0");
            int total = Integer.parseInt(total_amount);
            int total_bid_placed =total+bid_amount;
            String total_amt=String.valueOf(total_bid_placed);
            params.put("total_amount",total_amt);
            params.put("game_id",game1_id);
        }if(game_type==2){
            final String game2_id = sharedPreferences.getString(Config.GAME2_ID,"-1");
            final String total_amount = sharedPreferences.getString(Config.G2_SET_TOTAL_AMOUNT,"0");
            int total = Integer.parseInt(total_amount);
            int total_bid_placed =total+bid_amount;
            String total_amt=String.valueOf(total_bid_placed);
            params.put("total_amount",total_amt);
            params.put("game_id",game2_id);
        }
        final String card_id = String.valueOf(i+1);
        final String bid__amount = String.valueOf(bid_amount);
        final String game__type = String.valueOf(game_type);
        params.put("agent_id", agent_id);
        params.put("player_id",player_id);

        params.put("card_id",card_id);
        params.put("bid_amount",bid__amount);
        params.put("game_type",game__type);
        if(game_type==1) {
            final String s = sharedPreferences.getString(Config.count_G1_trns, "0");
            if (s.equals("0")) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Config.count_G1_trns,"1");
                editor.commit();
            }else{
                final String transaction_id_g1 = sharedPreferences.getString(Config.TRANSACTION_ID_g1,"-1");
                params.put("transaction_id",transaction_id_g1);
                /*  Toast.makeText(context,transaction_id_g1.toString(),Toast.LENGTH_LONG).show();*/
            }
        } if(game_type==2) {
            final String s = sharedPreferences.getString(Config.count_G2_trns, "0");
            if (!s.equals("0")) {
                final String transaction_id_g2 = sharedPreferences.getString(Config.TRANSACTION_ID_g2,"-1");
                params.put("transaction_id",transaction_id_g2);
            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Config.count_G2_trns,"1");
                editor.commit();
            }
        }

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", response.toString());
                progressDialog.dismiss();
                /* Toast.makeText(context,response.toString(),Toast.LENGTH_LONG).show();*/

                try {
                    String bid_id = response.getString("bid_id");
                    String card_id = response.getString("card_id");
                    String bid_amount = response.getString("bid_amount");
                    String transaction_id = response.getString("transaction_id");
                    SharedPreferences sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                    //Creating editor to store values to shared preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    /* Toast.makeText(context,card_id,Toast.LENGTH_LONG).show();*/

                    //Adding values to editor
                  /* if(game_type==1){
                       SharedPreferences sharedPreferencess = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                       final String bid_id_g1 = sharedPreferencess.getString(Config.G1_BID_ID,"no values");
                       if(!bid_id_g1.equals("no values")) {
                           String s = bid_id_g1 + "," + bid_id;
                           editor.putString(Config.G1_BID_ID,s);
                       }else{
                           editor.putString(Config.G1_BID_ID,","+bid_id);
                       }
                   }*/
                    editor.putString(Config.BID_ID,bid_id);
                    editor.putString(Config.CARD_ID,card_id);
                    editor.putString(Config.BID_Amount,bid_amount);
                    if(game_type==1){
                        editor.putString(Config.TRANSACTION_ID_g1,transaction_id);
                    }
                    if(game_type==2){
                        editor.putString(Config.TRANSACTION_ID_g2,transaction_id);
                    }


                    //Saving values to editor
                    editor.commit();

                    SharedPreferences sharedPreferencess = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    final String card___id = sharedPreferencess.getString(Config.CARD_ID,"-1");
                    final String bid_Amount = sharedPreferencess.getString(Config.BID_Amount,"-1");
                    int card_no= Integer.parseInt(card___id)-1;

                    int convert = Integer.parseInt(bid_Amount);
                    int convertint=Integer.parseInt(mIconText.get(card_no));
                    convertint = convertint+ convert;

                    String bid= String.valueOf(convertint);
                    mIconText.set(card_no,bid);
                    String result = TextUtils.join(",", mIconText);
                    if(game_type==1){
                        editor.putString(Config.G1_coins,result);
                        editor.commit();
                        mIconText.clear();
                        final String game_start_time = sharedPreferencess.getString(Config.G1_coins,"-1");
                        String[] parts = game_start_time.split(",");
                        for(int ss=0; ss<parts.length; ss++){
                            mIconText.add(parts[ss]);
                            /*   Toast.makeText(context,mIconText.get(ss),Toast.LENGTH_LONG).show();*/
                        }
                    }
                    if(game_type==2){
                        editor.putString(Config.G2_coins,result);
                        editor.commit();
                        mIconText.clear();
                        final String game_start_time = sharedPreferencess.getString(Config.G2_coins,"-1");
                        String[] parts = game_start_time.split(",");
                        for(int ss=0; ss<parts.length; ss++){
                            mIconText.add(parts[ss]);
                            /*   Toast.makeText(context,mIconText.get(ss),Toast.LENGTH_LONG).show();*/
                        }
                    }

                    /*  Toast.makeText(context,mIconText.get(card_no),Toast.LENGTH_LONG).show();*/
                    holder.textView.setText(mIconText.get(card_no));
                    convertintoint();
                    //mIconText.set(m,bet_amount);*/
                    /* Toast.makeText(context,"You Can Change Your Bet Till 14min",Toast.LENGTH_LONG).show();
                     */ } catch (JSONException e) {
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

    private void convertintoint() {

        int total=0;
        for(int x =0 ;x<mIconText.size();x++){
            int total_points = Integer.parseInt(mIconText.get(x));
            total=total+total_points;
        }
        String total_bet_amount= String.valueOf(total);
        if(game_type==1) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

            //Creating editor to store values to shared preferences
            SharedPreferences.Editor editor = sharedPreferences.edit();


            //Adding values to editor
            editor.putString(Config.G1_SET_TOTAL_AMOUNT, total_bet_amount);
            editor.commit();
            SharedPreferences sharedPreferencess = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            final String total___Amount = sharedPreferencess.getString(Config.G1_SET_TOTAL_AMOUNT, "-1");
            total_amount.setText(total___Amount);
        }if(game_type==2){
            SharedPreferences sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

            //Creating editor to store values to shared preferences
            SharedPreferences.Editor editor = sharedPreferences.edit();


            //Adding values to editor
            editor.putString(Config.G2_SET_TOTAL_AMOUNT, total_bet_amount);
            editor.commit();
            SharedPreferences sharedPreferencess = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            final String total___Amount = sharedPreferencess.getString(Config.G2_SET_TOTAL_AMOUNT, "-1");
            total_amount.setText(total___Amount);
        }
    }


    @Override
    public int getItemCount() {
        return mIconText.size();
    }

}