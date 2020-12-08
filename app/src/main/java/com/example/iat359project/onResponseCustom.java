package com.example.iat359project;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.Response.Listener;

public class onResponseCustom implements Listener<String> {
    Context context;
    StringRequestResponse stringRequestResponse;

    onResponseCustom(Context context, StringRequestResponse stringRequestResponse){
        this.context = context;
        this.stringRequestResponse = stringRequestResponse;
    }

    @Override
    public void onResponse(String response) {
    }

}
