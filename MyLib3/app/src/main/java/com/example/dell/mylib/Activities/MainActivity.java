package com.example.dell.mylib.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.dell.mylib.R;
import com.example.dell.mylib.adapter.bookAdapter;
import com.example.dell.mylib.fragments.fragmentBooks;
import com.example.dell.mylib.fragments.fragmentFav;
import com.example.dell.mylib.fragments.fragment_issue;
import com.example.dell.mylib.response.APIresponse;
import com.example.dell.mylib.response.docs;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener {
    ViewPager vp;
    public static FirebaseUser firebaseUser;
    public static DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseAuth.getInstance().addAuthStateListener(this);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        if (firebaseAuth.getCurrentUser() == null) {
            Intent loginIntent = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setIsSmartLockEnabled(false)
                    .setAvailableProviders(Arrays.asList(
                            new AuthUI.IdpConfig.GoogleBuilder().build(),
                            new AuthUI.IdpConfig.EmailBuilder().build()))
                    .build();
            startActivity(loginIntent);
        } else {
            vp = findViewById(R.id.viewPager);
            vp.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
            TabLayout tabLayout = findViewById(R.id.tabLayout);
            tabLayout.setupWithViewPager(vp);

            FloatingActionButton fab = findViewById(R.id.fab);

            final View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_view, null, true);
            final AlertDialog customDialog = new AlertDialog.Builder(this)
                    .setTitle("Search")
                    .setView(dialogView)
                    .setPositiveButton("Go", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            RecyclerView r = findViewById(R.id.list);
                            EditText Et = dialogView.findViewById(R.id.search);
                            String text = Et.getText().toString();
                            makeNetworkCall(text, r, MainActivity.this);
                        }
                    }).create();

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    customDialog.show();
                }
            });
        }
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        fragmentBooks fragmentBooks = new fragmentBooks();
        fragmentFav fragmentFav = new fragmentFav();
        fragment_issue fragment_issue = new fragment_issue();

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return fragmentBooks;
                case 1:
                    return fragmentFav;
                case 2:
                    return fragment_issue;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Books";
                case 1:
                    return "BookMark";
                case 2:
                    return "Issued";
            }
            return "";
        }
    }


  /*  ArrayList<docs> doc = new ArrayList<>();
    docs task = new docs();
    bookAdapter b;*/

    public void makeNetworkCall(final String ur, final RecyclerView rec, final Context context) {

        final String url = "http://openlibrary.org/search.json?title=" + ur;
        final int[] a = {0};
        final ArrayList<docs> doc = new ArrayList<>();
        final docs[] task = {new docs()};
        final bookAdapter[] b = new bookAdapter[1];

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    if (child.getKey().equals(ur)) {
                        a[0] = 1;
                        b[0] = new bookAdapter(doc, context,ur);
                        rec.setAdapter(b[0]);
                        databaseReference.child(ur).addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                task[0] = dataSnapshot.getValue(docs.class);
                                doc.add(task[0]);
                                b[0].notifyDataSetChanged();
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
                        });

                    }
                }
                if (a[0] == 0) {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    final Request request = new Request.Builder()
                            .url(url)
                            .build();

                    okHttpClient.newCall(request).enqueue(new Callback() {

                        @Override
                        public void onFailure(Call call, IOException e) {
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            final String result = response.body().string();
                            final Gson gson = new Gson();

                            final APIresponse apiResponse = gson.fromJson(result, APIresponse.class);
                            (MainActivity.this).runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    ArrayList<docs> users = apiResponse.getDocs();
                                    //     final ArrayList<docs> d = new ArrayList<>();
                                    bookAdapter adapter = new bookAdapter(users, context,ur);
                                    rec.setAdapter(adapter);

                                    for (int i = 0; i < users.size(); i++) {
                                        docs f = users.get(i);

                                        int ab = f.getA();
                                        ArrayList<String> a = f.getPublisher();
                                        ArrayList<String> b = f.getAuthor_name();
                                        String title = f.gettitle();
                                        String key = f.getCover_edition_key();
                                        int k = f.getB();
                                        docs bo = new docs(title, key, a, b, ab, k);

                                        if (databaseReference != null)
                                            databaseReference.child(ur).push().setValue(bo);
                                    }
                                }
                            });
                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }
    }// Activity ends
