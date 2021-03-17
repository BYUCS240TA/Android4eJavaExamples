package com.bignerdranch.android.criminalintent;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment {

    private static final String ARG_DATE = "date";

    interface Callbacks {
        void onDateSelected(Date date);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Date resultDate = new GregorianCalendar(year, month, dayOfMonth).getTime();
                Callbacks targetFragment = (Callbacks) getTargetFragment();
                targetFragment.onDateSelected(resultDate);
            }
        };

        Date date = (Date) getArguments().getSerializable(ARG_DATE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int initialYear = calendar.get(Calendar.YEAR);
        int initialMonth = calendar.get(Calendar.MONTH);
        int initialDate = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(
            requireContext(),
            dateListener,
            initialYear,
            initialMonth,
            initialDate);
    }

    static DatePickerFragment newInstance(Date date) {
        DatePickerFragment fragment = new DatePickerFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_DATE, date);
        fragment.setArguments(bundle);

        return fragment;
    }
}