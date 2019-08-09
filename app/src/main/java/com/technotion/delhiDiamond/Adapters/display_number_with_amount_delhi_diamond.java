package com.technotion.delhiDiamond.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.technotion.delhiDiamond.ImageNicer;
import com.technotion.delhiDiamond.R;
import com.technotion.delhiDiamond.sever_classes.Config;

import java.util.ArrayList;

public class display_number_with_amount_delhi_diamond extends RecyclerView.Adapter<display_number_with_amount_delhi_diamond.viewHolder> {

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView textView,amount_text;
         Button dlt;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
             textView=itemView.findViewById(R.id.jantri_number);
            amount_text =itemView.findViewById(R.id.jantri_coins);
            dlt=itemView.findViewById(R.id.delte);

        }

    }
    int total_delhi_diamond_game_amount=0;
    ArrayList<String> myImageList = new ArrayList<>();
    ArrayList<String> mIconText = new ArrayList<>();
    Context context;
    TextView total_amount;
    public display_number_with_amount_delhi_diamond(   ArrayList<String> myImageList, ArrayList<String> mIconText,TextView total_amount, Context context) {
        this.myImageList = myImageList;
        this.mIconText = mIconText;
        this.context = context;
        this.total_amount=total_amount;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View viewfull = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.delhi_diamond_display_number_with_amount,viewGroup,false);
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
      holder.amount_text.setText(mIconText.get(i));
      holder.textView.setText(myImageList.get(i));
        set_total_amount();
        holder.dlt.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              mIconText.remove(i);
              myImageList.remove(i);
              notifyItemRemoved(i);
              notifyItemRangeChanged(i,mIconText.size());
              set_total_amount();
          }
      });
    }

    private void set_total_amount() {

            total_delhi_diamond_game_amount = 0;
            for (int i = 0; i < mIconText.size(); i++) {
                int total = Integer.parseInt(mIconText.get(i));
                total_delhi_diamond_game_amount = total_delhi_diamond_game_amount + total;
            }
            String s = String.valueOf(total_delhi_diamond_game_amount);
        SharedPreferences sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Config.G4_SET_TOTAL_AMOUNT, s);
        editor.commit();
        final String total_coins_g4= sharedPreferences.getString(Config.G4_SET_TOTAL_AMOUNT, "-1");
            total_amount.setText(total_coins_g4);

    }


    @Override
    public int getItemCount() {
        return mIconText.size();
    }

}

