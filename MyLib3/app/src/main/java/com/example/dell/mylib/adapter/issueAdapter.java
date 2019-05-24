package com.example.dell.mylib.adapter;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.mylib.Activities.detailActivity;

import com.example.dell.mylib.R;
import com.example.dell.mylib.book;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class issueAdapter extends RecyclerView.Adapter<issueAdapter.ViewHolder> {

    ArrayList<book> articleArrayList;
    Context ctx;

    public issueAdapter(ArrayList<book> articleArrayList, Context ctx) {
        this.articleArrayList = articleArrayList;
        this.ctx=ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) ctx.getSystemService(LAYOUT_INFLATER_SERVICE);
        View inflatedView = li.inflate(R.layout.issue, parent, false);
        ViewHolder viewHolder = new ViewHolder(inflatedView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        for (int i = 0; i < articleArrayList.size(); i++) {
            final book firstUser = articleArrayList.get(position);

            holder.date.setText(firstUser.getDate());
            holder.title.setText(firstUser.getName());
            Picasso.get().load("http://covers.openlibrary.org/b/olid/"+firstUser.getImageurl()+"-M.jpg?default=false").error(R.drawable.ic_error).into(holder.image);

            final String title=firstUser.getName();
            final ArrayList<String> autho= firstUser.getAuthor();
            final  ArrayList<String> publ=firstUser.getPublisher();
            final int k=firstUser.getB();

            final int c=firstUser.getC();

            final String author=autho.get(0);
            final String publisher=publ.get(0);
            final String image="http://covers.openlibrary.org/b/olid/"+firstUser.getImageurl()
                    +"-M.jpg?default=false";


            if(ctx.getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT)
            {holder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent i=new Intent(ctx,detailActivity.class);
                    i.putExtra("url",image);
                    i.putExtra("title",title);
                    i.putExtra("key",firstUser.getImageurl());
                                      i.putExtra("author",author);
                                      i.putExtra("publisher",publisher);
                                      i.putExtra("touch",k);
                    i.putExtra("object", firstUser);
                    i.putExtra("book",c);


                    ctx.startActivity(i);
                }

            });}
        }
    }
    @Override
    public int getItemCount() {
        return articleArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title,date;
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.text);
            date=itemView.findViewById(R.id.date);
        }
    }
}