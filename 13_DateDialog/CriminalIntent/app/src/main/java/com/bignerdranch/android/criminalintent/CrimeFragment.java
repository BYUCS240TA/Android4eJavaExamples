package com.bignerdranch.android.criminalintent;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import java.util.Date;
import java.util.UUID;

public class CrimeFragment extends Fragment implements DatePickerFragment.Callbacks {

    private static final String ARG_CRIME_ID = "crime_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;

    private Crime crime;
    private EditText titleField;
    private Button dateButton;
    private CheckBox solvedCheckBox;
    private CrimeDetailViewModel crimeDetailViewModel = new CrimeDetailViewModel();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        crime = new Crime();
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        crimeDetailViewModel.loadCrime(crimeId);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime, container, false);

        titleField = view.findViewById(R.id.crime_title);
        dateButton = view.findViewById(R.id.crime_date);
        solvedCheckBox = view.findViewById(R.id.crime_solved);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        crimeDetailViewModel.loadCrime(crimeId);
        crimeDetailViewModel.getCrimeLiveData().observe(getViewLifecycleOwner(), new Observer<Crime>() {
            @Override
            public void onChanged(Crime crime) {
                CrimeFragment.this.crime = crime;
                updateUI();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        titleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence sequence, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence sequence, int start, int before, int count) {
                crime.setTitle(sequence.toString());
            }

            @Override
            public void afterTextChanged(Editable sequence) {
                // This one too
            }
        });

        solvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                crime.setSolved(isChecked);
            }
        });

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment fragment = DatePickerFragment.newInstance(crime.getDate());
                fragment.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                fragment.show(CrimeFragment.this.requireFragmentManager(), DIALOG_DATE);
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        crimeDetailViewModel.saveCrime(crime);
    }

    @Override
    public void onDateSelected(Date date) {
        crime.setDate(date);
        updateUI();
    }

    private void updateUI() {
        titleField.setText(crime.getTitle());
        dateButton.setText(crime.getDate().toString());
        solvedCheckBox.setChecked(crime.isSolved());
        solvedCheckBox.jumpDrawablesToCurrentState();
    }

    static CrimeFragment newInstance(UUID crimeId) {
        CrimeFragment fragment = new CrimeFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_CRIME_ID, crimeId);
        fragment.setArguments(bundle);

        return fragment;
    }
}