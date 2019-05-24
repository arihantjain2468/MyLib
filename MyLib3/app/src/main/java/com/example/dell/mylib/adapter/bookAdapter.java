package com.example.dell.mylib.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.mylib.Activities.detailActivity;
import com.example.dell.mylib.R;
import com.example.dell.mylib.response.docs;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class bookAdapter extends RecyclerView.Adapter<bookAdapter.ViewHolder> {

    ArrayList<docs> articleArrayList;
    Context ctx;
String ur;
    public bookAdapter(ArrayList<docs> articleArrayList, Context ctx,String ur) {
        this.articleArrayList = articleArrayList;
        this.ctx=ctx;
        this.ur=ur;
        Log.e("TAG1", "bookAdapter: "+ctx );
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) ctx.getSystemService(LAYOUT_INFLATER_SERVICE);
        View inflatedView = li.inflate(R.layout.item, parent, false);
        ViewHolder viewHolder = new ViewHolder(inflatedView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        for (int i = 0; i < articleArrayList.size(); i++) {
            final docs firstUser = articleArrayList.get(position);
            String publisher=null;
            String author=null;
            final int ab=firstUser.getA();
            final int k=firstUser.getB();
            ArrayList<String> a= firstUser.getPublisher();
            ArrayList<String> b=firstUser.getAuthor_name();
try {
     publisher = a.get(0);
     author = b.get(0);
}catch (Exception e){};
              final int c=firstUser.getC();
            holder.title.setText(firstUser.gettitle());
            Picasso.get().load("http://covers.openlibrary.org/b/olid/"+firstUser.getCover_edition_key()+"-M.jpg?default=false").error(R.drawable.ic_error).into(holder.image);

            final String title=firstUser.gettitle();
            final String image="http://covers.openlibrary.org/b/olid/"+firstUser.getCover_edition_key()
                                                   +"-M.jpg?default=false";

                final String finalPublisher = publisher;
                final String finalAuthor = author;
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(ctx, detailActivity.class);
                        i.putExtra("url", image);
                        i.putExtra("title", title);
                        i.putExtra("key", firstUser.getCover_edition_key());
                        i.putExtra("author", finalAuthor);
                        i.putExtra("publisher", finalPublisher);
                        i.putExtra("no", ab);
                        i.putExtra("b", k);
                        i.putExtra("as", firstUser);
                        i.putExtra("ur", ur);
                        i.putExtra("book", c);
                        ctx.startActivity(i);
                    }
                }
            );
/*
            if(ctx.getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE) {
                holder.title.setOnClickListener(new View.OnClickListener() {

                    @Override

                    public void onClick(View view) {
                        communicator.startFragmentDetail(detailFragment.newInstance(currentplanetitem));
                    }

                });
            }
*/
        }
    }


    @Override
    public int getItemCount() {
        return articleArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.text);
        }
    }
}