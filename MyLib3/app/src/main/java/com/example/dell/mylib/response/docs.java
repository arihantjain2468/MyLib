package com.example.dell.mylib.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Random;

public class docs implements Parcelable {
String title,cover_edition_key,date;
ArrayList<String> author_name;
ArrayList<String> publisher;
int a=0;
int b=0;

int c=new Random().nextInt(5);
public docs(){}
public docs(String title,String cover_edition_key,ArrayList<String> author_name,ArrayList<String> publisher,int a,int b){
    this.title=title;
    this.cover_edition_key=cover_edition_key;
    this.publisher=publisher;
    this.author_name=author_name;
    this.a=a;
    this.b=b;
}
/*
    public docs(String title,String cover_edition_key,ArrayList<String> author_name,ArrayList<String> publisher,String date,int a,int b){
        this.title=title;
        this.cover_edition_key=cover_edition_key;
        this.publisher=publisher;
        this.author_name=author_name;
        this.a=a;
        this.b=b;
        this.date=date;
    }
*/

    protected docs(Parcel in) {
        title = in.readString();
        cover_edition_key = in.readString();
        author_name = in.createStringArrayList();
        publisher = in.createStringArrayList();
        a = in.readInt();
        b=in.readInt();
        c=in.readInt();
        date=in.readString();
    }

    public static final Creator<docs> CREATOR = new Creator<docs>() {
        @Override
        public docs createFromParcel(Parcel in) {
            return new docs(in);
        }

        @Override
        public docs[] newArray(int size) {
            return new docs[size];
        }
    };

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<String> getAuthor_name() {
        return author_name;
    }


    public ArrayList<String> getPublisher() {
        return publisher;
    }


    public String getCover_edition_key() {
        return cover_edition_key;
    }

    public String gettitle() {
        return title;
    }

    public void setCover_edition_key(String cover_edition_key) {
        this.cover_edition_key = cover_edition_key;
    }

    public void settitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(cover_edition_key);
        dest.writeStringList(author_name);
        dest.writeStringList(publisher);
        dest.writeInt(a);
        dest.writeInt(b);
        dest.writeInt(c);
        dest.writeString(date);
    }
}
