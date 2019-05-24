package com.example.dell.mylib.response;

import java.util.ArrayList;

public class APIresponse {
    ArrayList<docs> docs;
 //   long num_found;
    public APIresponse(ArrayList<docs> docs ){
        this.docs=docs;
   //     this.num_found=num_found;
    }

   // public long getNum_found() {
   //     return num_found;
   // }

    public ArrayList<docs> getDocs() {
        return docs;
    }
}
