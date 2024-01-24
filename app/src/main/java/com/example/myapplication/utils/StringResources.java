package com.example.myapplication.utils;

import android.content.Context;

public class StringResources {
    private final Context context;

    public StringResources(Context context) {
        this.context = context;
    }

    public String getString(int id){

        return context.getString(id);
    }
    public String getString(int id,String... str){
        return context.getString(id, (Object) str);
    }
}
