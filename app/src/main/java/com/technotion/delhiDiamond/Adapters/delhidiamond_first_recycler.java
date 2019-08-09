package com.technotion.delhiDiamond.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.technotion.delhiDiamond.R;
import com.technotion.delhiDiamond.sever_classes.Config;

import java.util.ArrayList;

public class delhidiamond_first_recycler extends RecyclerView.Adapter<delhidiamond_first_recycler.viewHolder> {
    String[] escrito;
    boolean check= false;
    public class viewHolder extends RecyclerView.ViewHolder {
        EditText editText;
        TextView textView;
      //  CardView cardView;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            editText =itemView.findViewById(R.id.delhi_diamond_recycler_first_edittext);
            textView =itemView.findViewById(R.id.delhi_diamond_recycler_first_text);
           // cardView=itemView.findViewById(R.id.card);
            editText.setImeOptions(EditorInfo.IME_ACTION_NEXT);


        }

    }

    ArrayList<String> mIconText = new ArrayList<>();
    ArrayList<String> temp =new ArrayList<>();
    Context context;
    int postion =0  ;
    TextView total;
    int count_check_number=0,count=0;
    public delhidiamond_first_recycler(   ArrayList<String> mIconText, TextView total,Context context) {
        this.mIconText = mIconText;
        this.total=total;
        this.context = context;
        escrito = new String[mIconText.size()];
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View viewfull = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.delhi_diamond_first_recycler,viewGroup,false);
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

 holder.cardView.setBackground(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        holder.imageView.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(context.getResources(),myImageList.get(i),50,50));

            }
        });*/
        holder.textView.setText(mIconText.get(i));
        if(i==119){
            holder.editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        }
        holder.editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_NAVIGATE_NEXT)) || (actionId == EditorInfo.IME_ACTION_NEXT)) {

                    holder.editText.requestFocus();

                }
                return false;
            }
        });




        holder.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                String check = holder.editText.getText().toString();
           /*     holder.editText.setSelection(0,3);*/

                if(holder.editText.getText().toString().trim().length()==7){
                   holder.editText.getText().clear();
                    /* */
                    /*if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_NAVIGATE_NEXT)) || (actionId == EditorInfo.IME_ACTION_NEXT)) {

                        holder.editText.requestFocus();

                    }*/
                }
                fun(check,i);

            }
            @Override
            public void afterTextChanged(Editable s) {



            }

        });


    }

    private void fun(String check,int i) {
        int postion_check = 0;
        String tttl="";
        if(Config.get_jantri_coins.isEmpty()){
          Config.get_jantri_coins.add(check);
         /* Config.get_tt=check;
            int x= Integer.parseInt(Config.get_tt);
            tt= tt+x;
*/
          postion=i;
       }else{
           if(postion==i) {
               if(!Config.get_jantri_numbers.contains(mIconText.get(i))) {
                   count_check_number++;
                   int x = Config.get_jantri_coins.size() - 1;
                   Config.get_jantri_coins.remove(x);
                   Config.get_jantri_coins.add(x, check);
                 /*  Config.get_tt=Config.get_jantri_coins.get(x);
                   tt=0;
                   int xx= Integer.parseInt(Config.get_tt);
                   tt= tt+xx;*/
               }
               if(Config.get_jantri_numbers.contains(mIconText.get(i))){
                   if (Config.get_jantri_numbers.contains(mIconText.get(i).toString())) {
                       postion_check = Config.get_jantri_numbers.indexOf(mIconText.get(i).toString());
                   }
                   Config.get_jantri_coins.remove(postion_check);
                   Config.get_jantri_coins.add(postion_check,check);
                   Config.get_tt=Config.get_jantri_coins.get(postion_check);
                  /* tt=0;
                   if(!Config.get_tt.equals("")) {
                       int x = Integer.parseInt(Config.get_tt);
                       tt = tt + x;
                   }*/

               }
           }if(postion!=i){

               if(!Config.get_jantri_numbers.contains(mIconText.get(i))) {
                   Config.get_jantri_coins.add(check);
                  /* int x= Integer.parseInt(check);
                   Config._tt=tt+x;
                   String d = String.valueOf(Config._tt);*/
                  /* tttl=check;
                   int xyz = Integer.parseInt(Config.get_tt);
                   tt=tt+xyz;
                   String ff = String.valueOf(tt);
                   total.setText(ff);*/
                 /*  Toast.makeText(context,d,Toast.LENGTH_LONG).show();*/
                   postion = i;

                   count_check_number = 0;

               }if(Config.get_jantri_numbers.contains(mIconText.get(i))){


                   if (Config.get_jantri_numbers.contains(mIconText.get(i).toString())) {
                       postion_check = Config.get_jantri_numbers.indexOf(mIconText.get(i).toString());
                   }

                   Config.get_jantri_coins.remove(postion_check);
                   Config.get_jantri_coins.add(postion_check,check);
                   /* tt=0;
                    int x= Integer.parseInt(check);
                    Config._tt=tt+x;*/
                   postion = i;
                   count_check_number = 0;
                   count=0;
               }

           }
       }

        if(!Config.get_jantri_numbers.contains(mIconText.get(i))){
            if(count_check_number==0) {
                String ssss = String.valueOf(mIconText.get(i));
                Config.get_jantri_numbers.add(ssss);
         /*       for(int x=0;Config.)
                    ;
         */
            }
        }
        settext();
    }

    private void settext() {
        int totl=0;
        for(int i=0;i<Config.get_jantri_coins.size();i++){
            int temp=0;
                String s =Config.get_jantri_coins.get(i);
               if(!s.equals("")){
                temp= Integer.parseInt(s);
            }else{
                   temp=0;
               }
            totl=totl+temp;

        }
        String s= String.valueOf(totl);
        total.setText(s);
    }


    @Override
    public int getItemCount() {
        return mIconText.size();
    }

    public String[] getEscrito() {
        return escrito;
    }

}

