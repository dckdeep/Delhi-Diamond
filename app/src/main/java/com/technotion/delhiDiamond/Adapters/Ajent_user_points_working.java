package com.technotion.delhiDiamond.Adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.technotion.delhiDiamond.Agent_view_user_jantri;
import com.technotion.delhiDiamond.ImageNicer;
import com.technotion.delhiDiamond.R;
import com.technotion.delhiDiamond.ajent_users_list;
import com.technotion.delhiDiamond.patti_commission;
import com.technotion.delhiDiamond.sever_classes.Config;
import com.technotion.delhiDiamond.sever_classes.CustomRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Ajent_user_points_working  extends RecyclerView.Adapter<Ajent_user_points_working.viewHolder> {

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView user_name,player_coins;
        Button send_points;



        public viewHolder(@NonNull View itemView) {
            super(itemView);
            user_name =itemView.findViewById(R.id.user_name);
            player_coins=itemView.findViewById(R.id.player_current_coins);
            send_points=itemView.findViewById(R.id.send_points);
        }

    }

    ArrayList<String> mIconText = new ArrayList<>();
    ArrayList<String> id = new ArrayList<>();
    ArrayList<String> coins= new ArrayList<>();
    Spinner shortcuts_spinner;
    ArrayAdapter shortcuts;
    int game_type=0;
    String jantri_array[] ={"--Select Game--","Delhi Diamond","Faridabad","Ghaziabad","Gali","Desawar"};
    int id_no=0;
    String jantri_array1[] ={"--Select Game--","13patti","Chidiya Kabutar","Lucky7","Delhi Diamond","Faridabad","Ghaziabad","Gali","Desawar"};
    Context context;

    public Ajent_user_points_working(   ArrayList<String> mIconText,ArrayList<String> id,ArrayList<String> coins,Context context) {

        this.mIconText = mIconText;
        this.id= id;
        this.coins= coins;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View viewfull = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_ajent_users,viewGroup,false);
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
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup);
        final ImageButton close_popup = dialog.findViewById(R.id.close_popup);
        final ImageView pop=dialog.findViewById(R.id.popup_image);
        final TextView hinditext=dialog.findViewById(R.id.hinditext);
        final LinearLayout place_bet_layout = dialog.findViewById(R.id.place_bet_layout);
        final Button add_text = dialog.findViewById(R.id.add_bet_point);
        final Button less_text = dialog.findViewById(R.id.less_bet_point);
        less_text.setVisibility(View.INVISIBLE);
        hinditext.setVisibility(View.INVISIBLE);
        place_bet_layout.setVisibility(View.INVISIBLE);
        add_text.setText("Send Coins");
        final MaterialEditText send_coins = dialog.findViewById(R.id.bet_point);
        holder.user_name.setText(mIconText.get(i));
       holder.player_coins.setText(coins.get(i));
      if(Config.check==1){
          holder.send_points.setText("Jantri");

          holder.send_points.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                Intent intent = new Intent(context, Agent_view_user_jantri.class);
                intent.putExtra("open",Config.get_game_type);
                int play_id= Integer.parseInt(id.get(i));
                intent.putExtra("id",play_id);
                Config.final_jantri_check=0;
                context.startActivity(intent);
              }
          });
      }if(Config.check==0){

            holder.send_points.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pop.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(context.getResources(),R.drawable.dice_1,300,200));
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog.setTitle("");
                    dialog.show();

                }
            });
        }


        close_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        add_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!send_coins.getText().toString().equals("")) {
                    String player_coins = String.valueOf(send_coins.getText());
                    String url = "https://delhidiamond.online/index.php/update-coins";
                    Map<String, String> params = new HashMap<String, String>();
                    SharedPreferences sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    String agent_id = sharedPreferences.getString(Config.AGENT_ID, "Not Available");
/*
                Toast.makeText(context,agent_id,Toast.LENGTH_LONG).show();
*/

                    params.put("sender_id", agent_id);
                    params.put("receiver_id", id.get(i));
                    params.put("coins_transfer", player_coins);


                    CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("Response: ", response.toString());
                       /* String s = String.valueOf(response);
                        Toast.makeText(context, s, Toast.LENGTH_LONG).show();*/
                            try {
                                String agent_current_coins = response.getString("agent_current_coins");
                                SharedPreferences sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                                //Creating editor to store values to shared preferences
                                SharedPreferences.Editor editor = sharedPreferences.edit();


                                //Adding values to editor
                                editor.putString(Config.TOKENS, agent_current_coins);
                                //Saving values to editor
                                editor.commit();

                                Intent intent = new Intent(context, ajent_users_list.class);
                                context.startActivity(intent);
                            } catch (
                                    JSONException e) {
                                Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
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
                    jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    requestQueue.add(jsObjRequest);
                    dialog.dismiss();
                }
            }
        });
       holder.user_name.setOnLongClickListener(new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(View v) {
               SharedPreferences sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
               final String user_type = sharedPreferences.getString(Config.USER_STATUS,"Not Available");
               if(user_type.equals("admin")) {
                   AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                   alertDialogBuilder.setMessage("Are you sure you want to delete person");
                   alertDialogBuilder.setPositiveButton("Yes",
                           new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface arg0, int arg1) {

                                   SharedPreferences sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                                   String URL = "https://delhidiamond.online/index.php/delete-user ";
                                   final Map<String, String> params = new HashMap<String, String>();
                                   final String agent_id = id.get(i);
                                   params.put("user_id", agent_id);


                                   CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, URL, params, new Response.Listener<JSONObject>() {

                                       @Override
                                       public void onResponse(JSONObject response) {
                                           Log.d("Response: ", response.toString());
                                           /*       Toast.makeText(Home.this,response.toString(),Toast.LENGTH_LONG).show();
                                            */

                                           SharedPreferences preferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                                           //Getting editor
                                           SharedPreferences.Editor editor = preferences.edit();

                                           //Puting the value false for loggedin
                                           editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);

                                           //Putting blank value to email
                                           editor.putString(Config.USER_SHARED_PREF, "");

                                           //Saving the sharedpreferences
                                           editor.commit();

                                           //Starting login activity
                                           Intent intent = new Intent(context, ajent_users_list.class);
                                           context.startActivity(intent);

                                       }
                                   }, new Response.ErrorListener() {

                                       @Override
                                       public void onErrorResponse(VolleyError response) {
                                           Log.d("Response: ", response.toString());
                                       }
                                   });
                                   RequestQueue requestQueue = Volley.newRequestQueue(context);
                                   jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                                   requestQueue.add(jsObjRequest);
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
               return false;
           }
       });
       holder.user_name.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               SharedPreferences sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
               final String user_type = sharedPreferences.getString(Config.USER_STATUS,"Not Available");
               if(user_type.equals("admin")) {
                   if(Config.view_list_admin==1) {
                       AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                       alertDialogBuilder.setMessage("Select One");
                       alertDialogBuilder.setPositiveButton("Patti",
                               new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface arg0, int arg1) {

                                      /* patti_fun(i);*/
                                       Intent intent = new Intent(context, patti_commission.class);
                                       intent.putExtra("open_patti",2);
                                       context.startActivity(intent);
                                   }
                               });

                       alertDialogBuilder.setNegativeButton("Commission",
                               new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface arg0, int arg1) {
                                       Intent intent = new Intent(context, patti_commission.class);
                                       intent.putExtra("open_patti",1);
                                       context.startActivity(intent);
                                   }
                               });

                       //Showing the alert dialog
                       AlertDialog alertDialog = alertDialogBuilder.create();
                       alertDialog.show();
                   }
               }
           }
       });
    }

  /*  private void patti_fun(final int i) {

        final Dialog Bahar_panel = new Dialog(context);
        Bahar_panel.setContentView(R.layout.set_commission);
        final ImageView  close_bahar_panel=Bahar_panel.findViewById(R.id.close_bahar_panel);
        shortcuts_spinner=Bahar_panel.findViewById(R.id.planets_spinner);
        final TextView text_heading =Bahar_panel.findViewById(R.id.text_heading);
        final EditText bahar_input_amount=Bahar_panel.findViewById(R.id.bahar_input_amount);
        final Button  submit_bahar_panel=Bahar_panel.findViewById(R.id.submit_bahar_panel);
        shortcuts = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,jantri_array);
        shortcuts_spinner.setAdapter(shortcuts);
        shortcuts_spinner.setVisibility(View.INVISIBLE);
        shortcuts_spinner.setEnabled(false);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Set Patti");
        alertDialogBuilder.setPositiveButton("Add Patti",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Bahar_panel.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        Bahar_panel.setTitle("");
                        Bahar_panel.show();
                        text_heading.setText("SET Patti");
                        working_panel(close_bahar_panel,bahar_input_amount,submit_bahar_panel,Bahar_panel,i);
                    }
                });

        alertDialogBuilder.setNegativeButton("Update Patti",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
*/
  /*  private void working_panel(ImageView close_bahar_panel, final EditText bahar_input_amount, Button submit_bahar_panel, final Dialog bahar_panel, final int i) {
        close_bahar_panel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bahar_panel.dismiss();
                shortcuts = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,jantri_array);
                shortcuts_spinner.setAdapter(shortcuts);
            }
        });
        submit_bahar_panel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!bahar_input_amount.getText().toString().equals("")) {
                  final ProgressDialog  progressDialog = new ProgressDialog(context);
                    progressDialog.setMessage("LOADING...");
                    progressDialog.show();
                    String get_patti = bahar_input_amount.getText().toString();
                    String get_game_type = String.valueOf(game_type);
                    String URL = "https://delhidiamond.online/index.php/add-agent-patti";
                    final Map<String, String> params = new HashMap<String, String>();
                    final String agent_id = id.get(i);
                    params.put("agent_id",agent_id);

                    params.put("patti",get_patti);


                    CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, URL, params, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("Response: ", response.toString());
                                  *//*  Toast.makeText(Home.this,response.toString(),Toast.LENGTH_LONG).show();*//*

                            bahar_panel.dismiss();
                            progressDialog.dismiss();
                            Toast.makeText(context,"Patti Has Been Set For Agent",Toast.LENGTH_LONG).show();
                            //Starting login activity
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
                }else {
                    invalid_submit();
                }

            }
        });
    }*/

    private void invalid_submit() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
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

    @Override
    public int getItemCount() {
        return mIconText.size();
    }

}
