package com.technotion.delhiDiamond.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.technotion.delhiDiamond.ImageNicer;
import com.technotion.delhiDiamond.R;
import com.technotion.delhiDiamond.sever_classes.Config;

import java.util.ArrayList;

public class game_sum  extends RecyclerView.Adapter<game_sum.viewHolder> {

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView date_and_time,closing_amount,card;
        ImageView card_display_;
        RelativeLayout view_full_report;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            date_and_time =itemView.findViewById(R.id.date_and_time);
            if(Config.game_report_game_type==3){
             card=itemView.findViewById(R.id.card_display);
            }else {
                card_display_ = itemView.findViewById(R.id.card_display);
            }
            closing_amount=itemView.findViewById(R.id.closing_amount);
            view_full_report=itemView.findViewById(R.id.view_full_report);


        }

    }
    ArrayList<Integer> card_num =new ArrayList<>();
    ArrayList<String> closing_coins =new ArrayList<>();
    ArrayList<String> time_=new ArrayList<>();
    ArrayList<String> num =new ArrayList<>();

    Context context;
    public game_sum(   ArrayList<Integer> card_num,ArrayList<String> closing_coins, ArrayList<String> time_, ArrayList<String> num, Context context) {
        this.card_num  = card_num;
        this.num=num;
        this.time_= time_;
        this.closing_coins = closing_coins;
        this.context = context;
    }

    @NonNull
    @Override
    public game_sum.viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View viewfull=null;
       if(Config.game_report_game_type==3){
           viewfull=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.luck7_sumry, viewGroup, false);
       }else {
          viewfull  = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sumry_report, viewGroup, false);
       }
        game_sum.viewHolder v = new game_sum.viewHolder(viewfull);
        return v;
    }

    @Override
    public void onBindViewHolder(@NonNull game_sum.viewHolder holder, final int i) {
      /*  Glide.with(context).asBitmap().load(mIcons.get(i)).into(viewHolder.imageView);
        viewHolder.textView.setText(mIconText.get(i));

        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        holder.date_and_time.setText(time_.get(i));
        holder.closing_amount.setText(closing_coins.get(i));
        if(Config.game_report_game_type==3){
            holder.card.setText(num.get(i));
        }else
        holder.card_display_.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(context.getResources(),card_num.get(i),50,50));
        /*   holder.card_display_.setImageResource(card_display.get(i));
         */

    }


    @Override
    public int getItemCount() {
        return closing_coins.size();
    }

}


