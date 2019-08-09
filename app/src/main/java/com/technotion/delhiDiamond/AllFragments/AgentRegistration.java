package com.technotion.delhiDiamond.AllFragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.technotion.delhiDiamond.MainDashBoard;
import com.technotion.delhiDiamond.R;
import com.technotion.delhiDiamond.sever_classes.Config;
import com.technotion.delhiDiamond.sever_classes.CustomRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AgentRegistration  extends Fragment {
    EditText user_name_textinput,phonetextinput,emailtextinput,player_passtextinput;
    Button submit_player_info;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.user_registration_fragment, container, false);
        submitPlayerInfo(view);
        return view;
    }

    private void submitPlayerInfo(View view) {
        user_name_textinput= view.findViewById(R.id.user_name_textinput);
        phonetextinput= view.findViewById(R.id.phonetextinput);
        emailtextinput=view.findViewById(R.id.emailtextinput);
        player_passtextinput= view.findViewById(R.id.player_passtextinput);

        submit_player_info= view.findViewById(R.id.submit_player_info);
        submit_player_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendinfo();



            }
        });



    }

    private void sendinfo() {
        if(!user_name_textinput.getText().toString().equals("")&&!phonetextinput.getText().toString().equals("")&&!player_passtextinput.getText().toString().equals("")) {
            String player_name= String.valueOf(user_name_textinput.getText());
            String player_phone= String.valueOf(phonetextinput.getText());
            String player_email= String.valueOf(emailtextinput.getText());
            String player_pass= String.valueOf(player_passtextinput.getText());


            String url = "https://delhidiamond.online/index.php/add-user";
            Map<String, String> params = new HashMap<String, String>();
            params.put("username", player_name);
            params.put("mobile", player_phone);
            params.put("email", player_email);
            params.put("password", player_pass);
            SharedPreferences sharedPreferences = getContext().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            String user_id = sharedPreferences.getString(Config.PLAYER_ID, "Not Available");

            params.put("user_id", user_id);
            CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    Log.d("Response: ", response.toString());
                    String s = String.valueOf(response);
                    Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getContext(), MainDashBoard.class);
                    startActivity(intent);
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError response) {
                    Log.d("Response: ", response.toString());
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(jsObjRequest);
        }
        else {
            invalid_submit();
        }
    }
    private void invalid_submit() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
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
}
