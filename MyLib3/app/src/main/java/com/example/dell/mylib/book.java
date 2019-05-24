package com.example.dell.mylib;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Random;

public class book implements Parcelable {
    String name,imageurl,date;
     ArrayList<String> author,publisher;
     int b=0;
    int c=new Random().nextInt(5);
     public book(){}
    public book(String name,String imageurl,ArrayList<String> author,ArrayList<String> publisher,String date,int b){
        this.name=name;
        this.imageurl=imageurl;
        this.date=date;
        this.author=author;
        this.publisher=publisher;
        this.b=b;
    }

    protected book(Parcel in) {
        name = in.readString();
        imageurl = in.readString();
        date = in.readString();
        author = in.createStringArrayList();
        publisher = in.createStringArrayList();
        b = in.readInt();
        c=in.readInt();
    }

    public int getB() {
        return b;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public void setB(int b) {
        this.b = b;
    }

    public static final Creator<book> CREATOR = new Creator<book>() {
        @Override
        public book createFromParcel(Parcel in) {
            return new book(in);
        }

        @Override
        public book[] newArray(int size) {
            return new book[size];
        }
    };

    public String getDate() {
        return date;
    }

    public ArrayList<String> getPublisher() {
        return publisher;
    }

    public ArrayList<String> getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }
    public String getImageurl() {
        return imageurl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(imageurl);
        dest.writeString(date);
        dest.writeStringList(author);
        dest.writeStringList(publisher);
        dest.writeInt(b);
        dest.writeInt(c);
    }
}
