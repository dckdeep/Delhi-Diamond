package com.technotion.delhiDiamond.Adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.technotion.delhiDiamond.R;
import com.technotion.delhiDiamond.sever_classes.Config;
import com.technotion.delhiDiamond.sever_classes.CustomRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class result_report_adapter extends RecyclerView.Adapter<result_report_adapter.viewHolder> {

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView textView;



        public viewHolder(@NonNull View itemView) {
            super(itemView);

            textView =itemView.findViewById(R.id.points_count);

        }

    }
    ArrayList<Integer> myImageList = new ArrayList<>();
    ArrayList<String> mIconText = new ArrayList<>();
    Context context;
    ProgressDialog progressDialog;
    String s;
    public result_report_adapter(    ArrayList<String> mIconText, Context context) {
        this.mIconText = mIconText;
        this.context = context;
    }

    @NonNull
    @Override
    public result_report_adapter.viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View viewfull = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.draw_coins_for_adapter,viewGroup,false);
        result_report_adapter.viewHolder v = new result_report_adapter.viewHolder(viewfull);
        return v;
    }

    @Override
    public void onBindViewHolder(@NonNull result_report_adapter.viewHolder holder, final int i) {
      /*  Glide.with(context).asBitmap().load(mIcons.get(i)).into(viewHolder.imageView);
        viewHolder.textView.setText(mIconText.get(i));

        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        holder.textView.setText(mIconText.get(i));
        final Dialog dialog1 = new Dialog(context);
        dialog1.setContentView(R.layout.show_number_for_result);
        holder.textView.setText(mIconText.get(i));

        final Button cancel_action= dialog1.findViewById(R.id.cancel_action);
        final Button show_action=dialog1.findViewById(R.id.show_action);
        final TextView Number_display_amount=dialog1.findViewById(R.id.Number_display_amount);
        final TextView Number_display=dialog1.findViewById(R.id.Number_display);
        s = String.valueOf(i+1);
        Number_display.setText(s);
        Number_display_amount.setText(mIconText.get(i));

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog1.setTitle("");
                dialog1.show();
            }
        });
        cancel_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        show_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Are you sure you want to show this number?");
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        upload_result(i);
                        dialog1.dismiss();
                    }
                });
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    dialog1.dismiss();
                    }
                });
                //Showing the alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

    }

    private void upload_result(int i) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String url = "https://delhidiamond.online/index.php/declare-result";
        Map<String, String> params = new HashMap<String, String>();
        s= String.valueOf(i+1);
        int profit = Integer.parseInt(mIconText.get(i));
        SharedPreferences sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String agent_id = sharedPreferences.getString(Config.AGENT_ID,"-1");
/*
        final String agent_id = sharedPreferences.getString(Config.AGENT_ID,"-1");*/


        params.put("user_id",agent_id);
        params.put("game_type", String.valueOf(Config.get_game));
        params.put("card_id",s);
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", response.toString());

              /*  try {
                    JSONObject get_numbers_amount = response.getJSONObject("money_report");

                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
                progressDialog.dismiss();
                Toast.makeText(context,"Your Number "+s+"is Been Opened",Toast.LENGTH_LONG).show();
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

       Toast.makeText(context,s,Toast.LENGTH_LONG).show();
    }

    @Override
    public int getItemCount() {
        return mIconText.size();
    }

}

