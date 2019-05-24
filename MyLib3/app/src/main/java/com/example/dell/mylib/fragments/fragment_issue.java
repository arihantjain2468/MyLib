package com.example.dell.mylib.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dell.mylib.R;
import com.example.dell.mylib.adapter.bookAdapter;
import com.example.dell.mylib.adapter.issueAdapter;
import com.example.dell.mylib.book;
import com.example.dell.mylib.response.docs;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

import static com.example.dell.mylib.Activities.MainActivity.firebaseUser;
import static com.example.dell.mylib.Activities.MainActivity.databaseReference;

public class fragment_issue extends Fragment implements ChildEventListener{

    RecyclerView r;
    ArrayList<book> as=new ArrayList<>();
    issueAdapter b;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.list_books,container,false);
        Log.e("TAG", "onCreateView: issue" );
        r=view.findViewById(R.id.list);
        LinearLayoutManager l=new LinearLayoutManager(getContext());
        r.setLayoutManager(l);

        b = new issueAdapter(as, getContext());
        r.setAdapter(b);
        as.clear();
        if(databaseReference!=null) {
            databaseReference.child(firebaseUser.getUid()).child("issue").addChildEventListener(this);
        }
        return view;
    }
    book task;
    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        task = dataSnapshot.getValue(book.class);
        as.add(task);
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
}
