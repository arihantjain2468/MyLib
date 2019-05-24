package com.example.dell.mylib.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.mylib.AlarmReceiver;
import com.example.dell.mylib.R;
import com.example.dell.mylib.book;

import com.example.dell.mylib.response.docs;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import static com.example.dell.mylib.Activities.MainActivity.databaseReference;
import static com.example.dell.mylib.Activities.MainActivity.firebaseUser;

public class detailActivity extends AppCompatActivity {

    ImageButton boo,notify,mark;
    TextView text;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_fragmentactivity);
        Intent i = getIntent();
        final String url = i.getStringExtra("url");
        final String title=i.getStringExtra("title");
        final String author=i.getStringExtra("author");
        final String publisher=i.getStringExtra("publisher");
        final String key=i.getStringExtra("key");
        final int[] a = {i.getIntExtra("no", 5)};
        final docs[] docs = {i.getParcelableExtra("as")};
        final int[] b = {i.getIntExtra("b", 5)};
        final int[] c={i.getIntExtra("book",0)};
        final String ur=i.getStringExtra("ur");

             final book book[]={i.getParcelableExtra("object")};
             final int[] bb={i.getIntExtra("touch",0)};

        Log.e("TAGa", "onCreate: "+book[0] );
        ImageView iv =findViewById(R.id.detailImage);
        TextView titl=findViewById(R.id.DetailName);
        final TextView aut=findViewById(R.id.DetailWriter);
        TextView pub=findViewById(R.id.Detailpublish);

        boo=findViewById(R.id.detailNo);
        notify=findViewById(R.id.detailnotify);
        mark=findViewById(R.id.detailfav);

        text=findViewById(R.id.no);
        titl.setText(title);
        aut.setText(author);
        Picasso.get().load(url).into(iv);
        pub.setText(publisher);

            text.setText("" + c[0]);
            if (c[0] == 0) {
                notify.setVisibility(View.VISIBLE);
            }

        final ArrayList<String> asdf =new ArrayList<>();
        asdf.add(author);

        final ArrayList<String> asd =new ArrayList<>();
        asd.add(publisher);
        Log.e("qwer", "onCreate: "+a[0] );
        final String[] q = new String[1];


        databaseReference.child(firebaseUser.getUid()).child("bookmark").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    docs d=child.getValue(docs.class);
                    String t=d.gettitle();
                    if (t.equals(title))
                    {
                        Log.e("TAG123", "onDataChange: "+t );
                       docs[0] =d;
                       q[0] =child.getKey();
                    }
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(docs[0].getA()==0)
                {   Log.e("geta", "onClick: "+docs[0].getA() );
                    a[0] = 1;
                    docs[0].setA(a[0]);
                    docs bo = new docs(title,key,asdf,asd,a[0],b[0]);
                    databaseReference.child(firebaseUser.getUid()).child("bookmark").push().setValue(bo);
                    Toast.makeText(detailActivity.this,title+" added on bookmark",Toast.LENGTH_SHORT).show();

                }else {
                    Log.e("geta", "onClick: "+docs[0].getA() );
                    Toast.makeText(detailActivity.this,title+" removed from bookmark",Toast.LENGTH_SHORT).show();
                    a[0]=0;
                    docs[0].setA(a[0]);
                    databaseReference.child(firebaseUser.getUid()).child("bookmark").child(q[0]).removeValue();

                }
            }
        });

            String d = new String();
        final SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
        final Calendar calendar=Calendar.getInstance();
        Date date=new Date();
        d =format.format(date);
        try {
            calendar.setTime(format.parse(d));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.add(Calendar.DAY_OF_MONTH,7);
        final String ne=format.format(calendar.getTime());

        final AlertDialog alertDialog =
                new AlertDialog.Builder(this)
                        .setTitle("Return Date")
                        .setMessage(title+"         \n " + ne)
                        .create();

        final String[] m = new String[1];
        if(book[0]==null)
            book[0]=new book(title,url,asdf,asd,ne,bb[0]);


        databaseReference.child(firebaseUser.getUid()).child("issue").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Log.e("TAG", "onDataChange: "+child.getKey());
                    book a=child.getValue(book.class);
                    String p=a.getName();
                    if (p.equals(title))
                    {
                        Log.e("TAG123", "onDataChange: "+p );
                        book[0]=a;
                        m[0] =child.getKey();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        final AlarmManager alarmManager= (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent notificationIntent=new Intent(this, AlarmReceiver.class);
        notificationIntent.putExtra("title",title);
        notificationIntent.putExtra("date",ne);
        final PendingIntent broadcast=PendingIntent.getBroadcast(this,100,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);


        boo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {if(c[0]>0)
                {
                    if (book[0].getB() == 0) {
                        Log.e("get", "onClick: "+book[0].getB() );
                        alertDialog.show();
                        Log.e("TAG", "onClick: "+book[0].getC() );
                        c[0]-=1;
                        bb[0] = 1;


                        calendar.add(Calendar.DAY_OF_MONTH,-2);
                        final String n=format.format(calendar.getTime());

                        databaseReference.child(ur).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot Child : dataSnapshot.getChildren()) {
                                    docs a=Child.getValue(docs.class);
                                    String p=a.gettitle();
                                    if (p.equals(title))
                                    {
                                        databaseReference.child(ur).child(Child.getKey()).child("c").setValue(c[0]);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        Log.e("time", "onClick: "+calendar.getTimeInMillis() );
                       // alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                         //       SystemClock.elapsedRealtime()+10000,
                           //     86400000,broadcast);
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+432000000,
                                86400000,broadcast);
                        book[0].setB(bb[0]);
                        book[0].setC(c[0]);
                        book book = new book(title, key, asdf, asd, ne, bb[0]);
                        Toast.makeText(detailActivity.this,title+" added on issued",Toast.LENGTH_SHORT).show();

                        databaseReference.child(firebaseUser.getUid()).child("issue").push().setValue(book);
                    } else {
                        bb[0] = 0;
                        c[0]+=1;
                        book[0].setB(bb[0]);
                        book[0].setC(c[0]);
                        Toast.makeText(detailActivity.this,title+" removed from issued",Toast.LENGTH_SHORT).show();

                        databaseReference.child(ur).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot Child : dataSnapshot.getChildren()) {
                                    docs a=Child.getValue(docs.class);
                                    String p=a.gettitle();
                                    if (p.equals(title))
                                    {
                                        databaseReference.child(ur).child(Child.getKey()).child("c").setValue(c[0]);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                          alarmManager.cancel(broadcast);
                        databaseReference.child(firebaseUser.getUid()).child("issue").child(m[0]).removeValue();
                    }}else{                        Toast.makeText(detailActivity.this,title+" can't be issue",Toast.LENGTH_SHORT).show();


                }
                    text.setText("" + c[0]);
                }catch (Exception e){}
            }
        });
        /*AlarmManager alarmManager= (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent notificationIntent=new Intent(this, AlarmReceiver.class);
        notificationIntent.putExtra("title",title);
        notificationIntent.putExtra("date",ne);
        PendingIntent broadcast=PendingIntent.getBroadcast(this,100,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        calendar.add(Calendar.DAY_OF_MONTH,-2);
        final String n=format.format(calendar.getTime());
        Log.e("notification0", "onCreate: "+book[0].getB() );
       if (book[0].getB()!=0)
       {
           Log.e("notification", "onCreate: " );
           alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                   calendar.getTimeInMillis(),
                   86400000,broadcast);

         }
*/
        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
