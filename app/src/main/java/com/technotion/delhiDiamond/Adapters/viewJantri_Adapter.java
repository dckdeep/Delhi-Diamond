package com.technotion.delhiDiamond.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.technotion.delhiDiamond.R;

import java.util.ArrayList;

public class viewJantri_Adapter extends RecyclerView.Adapter<viewJantri_Adapter.viewHolder> {

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView text_num;
        TextView text_amount;
        LinearLayout first_layout;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            text_num = itemView.findViewById(R.id.text_num);
            text_amount = itemView.findViewById(R.id.text_amount);
            first_layout= itemView.findViewById(R.id.first_layout);

        }

    }

    ArrayList<String> mIconText_amount = new ArrayList<>();
    ArrayList<String> mIconText = new ArrayList<>();
    Context context;

    public viewJantri_Adapter(ArrayList<String> mIconText, ArrayList<String>mIconText_amount, Context context) {
        this.mIconText = mIconText;
        this.mIconText_amount=mIconText_amount;
        this.context = context;
    }

    @NonNull
    @Override
    public viewJantri_Adapter.viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View viewfull = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_jantri_recyclerview, viewGroup, false);
        viewJantri_Adapter.viewHolder v = new viewJantri_Adapter.viewHolder(viewfull);
        return v;
    }

    @Override
    public void onBindViewHolder(@NonNull viewJantri_Adapter.viewHolder holder, final int i) {
      /*  Glide.with(context).asBitmap().load(mIcons.get(i)).into(viewHolder.imageView);
        viewHolder.textView.setText(mIconText.get(i));

        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        holder.text_num.setText(mIconText.get(i));
        holder.text_amount.setText(mIconText_amount.get(i));

    }
    @Override
    public int getItemCount() {
/*
        String s = String.valueOf(mIconText.size());
        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();

*/
        return mIconText.size();
    }
}

