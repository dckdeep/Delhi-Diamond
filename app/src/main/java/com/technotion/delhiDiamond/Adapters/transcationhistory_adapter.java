package com.technotion.delhiDiamond.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.technotion.delhiDiamond.R;

import java.util.ArrayList;

public class transcationhistory_adapter  extends RecyclerView.Adapter<transcationhistory_adapter.viewHolder> {

    public class viewHolder extends RecyclerView.ViewHolder {

    TextView date_and_time,user_name,transaction_type,coins_transfer,closing_amount;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            date_and_time =itemView.findViewById(R.id.date_and_time);
            user_name =itemView.findViewById(R.id.user_name);
            transaction_type=itemView.findViewById(R.id.transaction_type);
            coins_transfer =itemView.findViewById(R.id.coins_transfer);
            closing_amount=itemView.findViewById(R.id.closing_amount);


        }

    }
    ArrayList<String> user_name =new ArrayList<>();
    ArrayList<String> coins_transfer =new ArrayList<>();
    ArrayList<String> closing_coins =new ArrayList<>();
    ArrayList<String> transaction_type =new ArrayList<>();
    ArrayList<String> transaction_time =new ArrayList<>();
    Context context;
    public transcationhistory_adapter(   ArrayList<String> user_name, ArrayList<String> coins_transfer,ArrayList<String> closing_coins,ArrayList<String> transaction_type,ArrayList<String> transaction_time, Context context) {
        this.user_name  = user_name;
        this.coins_transfer = coins_transfer;
        this.closing_coins = closing_coins;
        this.transaction_time = transaction_time;
        this.transaction_type = transaction_type;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View viewfull = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.transaction_history,viewGroup,false);
        viewHolder v = new viewHolder(viewfull);
        return v;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, final int i) {
      /*  Glide.with(context).asBitmap().load(mIcons.get(i)).into(viewHolder.imageView);
        viewHolder.textView.setText(mIconText.get(i));

        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
       holder.user_name.setText(user_name.get(i));
        holder.transaction_type.setText(transaction_type.get(i));
        holder.coins_transfer.setText(coins_transfer.get(i));
        holder.closing_amount.setText(closing_coins.get(i));
        holder.date_and_time.setText(transaction_time.get(i));
    }


    @Override
    public int getItemCount() {
        return user_name.size();
    }

}

