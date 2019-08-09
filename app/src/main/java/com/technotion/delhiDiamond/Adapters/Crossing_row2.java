package com.technotion.delhiDiamond.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.technotion.delhiDiamond.R;
import com.technotion.delhiDiamond.sever_classes.Config;

import java.util.ArrayList;

public class Crossing_row2 extends RecyclerView.Adapter<Crossing_row2.viewHolder> {

public class viewHolder extends RecyclerView.ViewHolder {

   TextView row2_tenth;

    public viewHolder(@NonNull View itemView) {
        super(itemView);
        row2_tenth =itemView.findViewById(R.id.row2_tenth);
    }

}

    int count=0;
    ArrayList<String> button_count = new ArrayList<>();

    Context context;
    public Crossing_row2(   ArrayList<String> button_count,Context context) {
        this.button_count = button_count;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View viewfull = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.crossing_panel_button,viewGroup,false);
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
        holder.row2_tenth.setText(button_count.get(i));
        holder.row2_tenth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count==0) {
                    holder.row2_tenth.setBackgroundResource(R.drawable.blue_border);
                    if(i==9) {
                        Config.get_numbers_row2.add("0");

                    }else{
                        int x = i + 1;
                        String s = String.valueOf(x);
                        Config.get_numbers_row2.add(s);

                    }
                    count=1;
                }
                else{
                    count=0;
                    holder.row2_tenth.setBackgroundResource(R.drawable.black_border);
                    for(int a=0; a<Config.get_numbers_row2.size(); a++){
                        String s1 = String.valueOf(i+1);
                        String dlt = Config.get_numbers_row2.get(a);
                        if(s1.equals(dlt)){
                            Config.get_numbers_row2.remove(a);

                        }
                    }
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return button_count.size();
    }

}
