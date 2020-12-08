package com.example.iat359project;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class AsyncTaskDatabaseLogIn extends AsyncTask<String,Void,String> implements StringRequestResponse{
    AsyncResponse delegate;
    Context context;
    String status;
    RequestQueue requestQueue;
    String requestUrl;

    AsyncTaskDatabaseLogIn (Context context, AsyncResponse asyncResponse){
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
        requestUrl ="https://project-359-team.000webhostapp.com/loginAccount.php";
        delegate = asyncResponse;
    }
    @Override
    protected String doInBackground(String... params) {
        final String username = params[0];
        final String password = params[1];
        status = "fail";
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl,
//                new onResponseCustom(context, this) {
//                    @Override
//                    public void onResponse(String response) {
//                        // Display the first 500 characters of the response string.
////                        if (response.toLowerCase().equals("success")){
////                            Log.d("logging in",response);
////                            stringRequestResponse.exportResponse(response.toLowerCase());
////                        }
////                        else if (response.toLowerCase().equals("fail")){
////                            Log.d("logging in","fail");
////                            stringRequestResponse.exportResponse(response.toLowerCase());
////                        }
////                        else {
////                            stringRequestResponse.exportResponse("fail");
////                        }
//                    }
//                }, new onResponseErrorCustom(context,this) {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("logging in", error.toString());
//                status = "fail";
//            }
//        })
//            {
//                @Override
//                protected Map<String, String> getParams () throws AuthFailureError
//                {
//                    Map<String, String> prms = new HashMap<>();
//                    prms.put("sentUsername", username);
//                    prms.put("sentPassword", password);
//
//                    return prms;
//                }
//            };
//        requestQueue.add(stringRequest);
        return status;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {
        delegate.exportOutput(result);
        Log.d("Log in status",status);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    public String returnStatus(){
        return status;
    }


    @Override
    public void exportResponse(String response) {
        status = response;
    }
}
