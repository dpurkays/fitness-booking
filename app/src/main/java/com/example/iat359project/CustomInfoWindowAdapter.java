package com.example.iat359project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
/*
Customized infoWindowAdapter for gym markers
 */
public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter{
    private final View mWindow;
    private Context mContext;

    public CustomInfoWindowAdapter(Context context) {
        mContext = context;
        mWindow = LayoutInflater.from(context).inflate(R.layout.custom_info_window, null);
    }

    private void renderWindowText(Marker marker, View view) {
        Button buttonBooking;
        String title = marker.getTitle();
        TextView textViewTitle = (TextView) view.findViewById(R.id.title);

        if(!title.equals("")) {
            textViewTitle.setText(title);
        }

        String snippet = marker.getSnippet();
        TextView textViewSnippet = (TextView) view.findViewById(R.id.snippet);

        if(!snippet.equals("")) {
            textViewSnippet.setText(snippet);
        }


    }

    @Override
    public View getInfoWindow(Marker marker) {
        renderWindowText(marker, mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        renderWindowText(marker, mWindow);
        return mWindow;
    }
}
