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

import com.example.dell.mylib.Activities.MainActivity;
import com.example.dell.mylib.R;

public class fragmentBooks extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("TAG", "onCreateView: "+ "Books" );
        RecyclerView rv;
        View view=inflater.inflate(R.layout.list_books,container,false);

        rv=view.findViewById(R.id.list);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),3);
        rv.setLayoutManager(gridLayoutManager);
        String url="engineerings";
        MainActivity mainActivity= (MainActivity) getActivity();

        mainActivity.makeNetworkCall(url,rv,getContext());
        return view;
              //  super.onCreateView(inflater, container, savedInstanceState);


    }
}
