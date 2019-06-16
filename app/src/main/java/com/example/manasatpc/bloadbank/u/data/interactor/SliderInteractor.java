package com.example.manasatpc.bloadbank.u.data.interactor;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SliderInteractor {
    private TextView[] dottv;

    public interface OnChangedListener {
        void skip();
    }

    public void setLayouts(final int position, LinearLayout linearLayout, Context context, int[] layouts) {
        if (position == layouts.length - 1) {
        }
        setDotLayout(position, linearLayout, context, layouts);
    }

    public void setDotLayout(int page, LinearLayout linearLayout, Context context, int[] layouts) {
        linearLayout.removeAllViews();
        dottv = new TextView[layouts.length];
        for (int i = 0; i < dottv.length; i++) {
            dottv[i] = new TextView(context);
            dottv[i].setText(Html.fromHtml("&#8226"));
            dottv[i].setTextSize(30);
            dottv[i].setTextColor(Color.parseColor("#000000"));
            linearLayout.addView(dottv[i]);
        }

        //set current dot active
        if (dottv.length > 0) {
            dottv[page].setTextColor(Color.parseColor("#9da0a3"));
        }

    }

}
