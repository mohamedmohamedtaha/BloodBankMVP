package com.example.manasatpc.bloadbank.u.helper;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

//This class for  create DatePickerDialog for select date of birth and last date for donate
public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    String date = null;
    Bundle bundle = new Bundle();

    public SelectDateFragment() {
        setArguments(bundle);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar getDate = Calendar.getInstance();
        int startYear = getDate.get(Calendar.YEAR);
        int startMonth = getDate.get(Calendar.MONTH);
        int startDay = getDate.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, startYear, startMonth, startDay);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        date = year + "/" + (month + 1) + "/" + dayOfMonth;
        bundle.putString("date", date);
        Toast.makeText(getActivity(), date, Toast.LENGTH_SHORT).show();
    }
}
