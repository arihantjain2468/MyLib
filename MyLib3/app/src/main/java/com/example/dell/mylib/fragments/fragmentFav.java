package com.example.dell.mylib.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dell.mylib.R;
import com.example.dell.mylib.adapter.bookAdapter;
import com.example.dell.mylib.response.docs;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.dell.mylib.Activities.MainActivity.firebaseUser;
import static com.example.dell.mylib.Activities.MainActivity.databaseReference;

public class fragmentFav extends Fragment implements ChildEventListener{

    RecyclerView r;
    ArrayList<docs> q=new ArrayList<>();
    bookAdapter b;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("TAG", "onCreateView: fav " );
        View view=inflater.inflate(R.layout.list_books,container,false);

        r=view.findViewById(R.id.list);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),3);
        r.setLayoutManager(gridLayoutManager);

        b = new bookAdapter(q, getContext(),null);
        r.setAdapter(b);
   if(databaseReference!=null) {
/*
       databaseReference.child(firebaseUser.getUid()).child("bookmark").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               for (DataSnapshot child : dataSnapshot.getChildren()) {
       //            databaseReference.child(firebaseUser.getUid()).child("bookmark").
                           child.addChildEventListener(this);
               }

           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
*/
       databaseReference.child(firebaseUser.getUid()).child("bookmark").addChildEventListener(this);
   }
         return view;
    }
docs task;
    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        task = dataSnapshot.getValue(docs.class);
        q.add(task);
        b.notifyDataSetChanged();
    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }

    @Override
    public void onPause() {
        super.onPause();
        q.clear();
        b.notifyDataSetChanged();
        Log.e("TAG123", "onPause: " );
    }
}
