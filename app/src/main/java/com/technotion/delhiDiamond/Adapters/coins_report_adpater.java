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

public class coins_report_adpater extends RecyclerView.Adapter<coins_report_adpater.viewHolder> {

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
    public coins_report_adpater(    ArrayList<String> mIconText, Context context) {
        this.mIconText = mIconText;
        this.context = context;
    }

    @NonNull
    @Override
    public coins_report_adpater.viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View viewfull = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.draw_coins_for_adapter,viewGroup,false);
        coins_report_adpater.viewHolder v = new coins_report_adpater.viewHolder(viewfull);
        return v;
    }

    @Override
    public void onBindViewHolder(@NonNull coins_report_adpater.viewHolder holder, final int i) {
      /*  Glide.with(context).asBitmap().load(mIcons.get(i)).into(viewHolder.imageView);
        viewHolder.textView.setText(mIconText.get(i));

        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        holder.textView.setText(mIconText.get(i));
    }


    @Override
    public int getItemCount() {
        return mIconText.size();
    }

}

