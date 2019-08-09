package com.technotion.delhiDiamond.Adapters;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//import com.bumptech.glide.Glide;

import com.technotion.delhiDiamond.ImageNicer;
import com.technotion.delhiDiamond.R;

import java.util.ArrayList;


public class closecardrecycler extends RecyclerView.Adapter<closecardrecycler.viewHolder> {

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
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
    public closecardrecycler(   ArrayList<Integer> myImageList, ArrayList<String> mIconText, Context context) {
        this.myImageList = myImageList;
        this.mIconText = mIconText;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View viewfull = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.closecarddisplay,viewGroup,false);
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
        holder.cardView.setBackground(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        holder.imageView.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(context.getResources(),myImageList.get(i),50,50));
        holder.textView.setText(mIconText.get(i));
    }


    @Override
    public int getItemCount() {
        return mIconText.size();
    }

}
