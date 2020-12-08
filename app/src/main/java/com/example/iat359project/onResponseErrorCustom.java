package com.example.iat359project;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

public class onResponseErrorCustom implements Response.ErrorListener {
    Context context;

    onResponseErrorCustom(Context context){
        this.context = context;
    }


    @Override
    public void onErrorResponse(VolleyError error) {

    }
}
