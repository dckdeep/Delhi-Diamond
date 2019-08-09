package com.technotion.delhiDiamond.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.technotion.delhiDiamond.Game_Summary;
import com.technotion.delhiDiamond.ImageNicer;
import com.technotion.delhiDiamond.R;
import com.technotion.delhiDiamond.sever_classes.Config;

import java.util.ArrayList;

public class Game_Report_Adapter  extends RecyclerView.Adapter<Game_Report_Adapter.viewHolder> {

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView date_and_time,user_name,closing_amount,change_coins_text;
        ImageView card_display_;
        RelativeLayout view_full_report;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            change_coins_text=itemView.findViewById(R.id.change_coins_text);
            date_and_time =itemView.findViewById(R.id.date_and_time);
            user_name =itemView.findViewById(R.id.user_name);
            card_display_=itemView.findViewById(R.id.card_display);
            closing_amount=itemView.findViewById(R.id.closing_amount);
            view_full_report=itemView.findViewById(R.id.view_full_report);


        }

    }
    ArrayList<String> win_or_lose =new ArrayList<>();
    ArrayList<Integer> card_num =new ArrayList<>();
    ArrayList<String> closing_coins =new ArrayList<>();
    ArrayList<String> transaction_time =new ArrayList<>();
    ArrayList<String> game_id =new ArrayList<>();
    Context context;
    public Game_Report_Adapter(   ArrayList<Integer> card_num,ArrayList<String> closing_coins, ArrayList<String> win_or_lose,ArrayList<String> transaction_time,ArrayList<String> game_id, Context context) {
        this.card_num  = card_num;
        this.closing_coins = closing_coins;
        this.win_or_lose = win_or_lose;
        this.game_id=game_id;
        this.transaction_time = transaction_time;
        this.context = context;
    }

    @NonNull
    @Override
    public Game_Report_Adapter.viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View viewfull = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.game_report,viewGroup,false);
        Game_Report_Adapter.viewHolder v = new Game_Report_Adapter.viewHolder(viewfull);
        return v;
    }

    @Override
    public void onBindViewHolder(@NonNull Game_Report_Adapter.viewHolder holder, final int i) {
      /*  Glide.with(context).asBitmap().load(mIcons.get(i)).into(viewHolder.imageView);
        viewHolder.textView.setText(mIconText.get(i));

        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

        holder.user_name.setText(win_or_lose.get(i));
        holder.closing_amount.setText(closing_coins.get(i));
        holder.date_and_time.setText(transaction_time.get(i));
        holder.card_display_.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(context.getResources(), card_num.get(i), 50, 50));

        /*   holder.card_display_.setImageResource(card_display.get(i));
         */

        SharedPreferences sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String user_type = sharedPreferences.getString(Config.USER_STATUS, "-1");
        if (!user_type.equals("agent")) {
            if (!win_or_lose.get(i).equals("You Have Not Played")) {
                holder.view_full_report.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String game = game_id.get(i);
                        int g = Integer.parseInt(game);
                        String time = transaction_time.get(i);
                        Intent intent = new Intent(context, Game_Summary.class);
                        intent.putExtra("game_ids", g);
                        intent.putExtra("time", time);
                        context.startActivity(intent);
                    }
                });
            }
        }
        if (user_type.equals("agent")) {
            if (Config.com_patti == 0) {
                holder.change_coins_text.setText("Patti:-");
            }
            if (Config.com_patti == 1) {
                holder.change_coins_text.setText("Commission:-");
            }

        }
    }


    @Override
    public int getItemCount() {
        return closing_coins.size();
    }

}

