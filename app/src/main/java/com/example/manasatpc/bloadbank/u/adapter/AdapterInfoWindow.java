package com.example.manasatpc.bloadbank.u.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.manasatpc.bloadbank.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class AdapterInfoWindow implements GoogleMap.InfoWindowAdapter {
    private final View mWindow;
    private Context context;

    public AdapterInfoWindow(Context context) {
        this.context = context;
        mWindow = LayoutInflater.from(context).inflate(R.layout.custom_info_map, null);
    }

    private void rendowWindowText(Marker marker, View view) {
        String title = marker.getTitle();
        TextView TVTitle = (TextView) view.findViewById(R.id.title);
        if (!title.equals("")) {
            TVTitle.setText(title);
        }
        String snippet = marker.getSnippet();
        TextView TVSnippet = (TextView) view.findViewById(R.id.snippet);
        if (snippet !=null) {
            TVSnippet.setText(snippet);
        }
    }

    @Override
    public View getInfoWindow(Marker marker) {
        rendowWindowText(marker, mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        rendowWindowText(marker, mWindow);
        return mWindow;
    }
}
