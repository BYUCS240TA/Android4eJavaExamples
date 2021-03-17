package com.bignerdranch.android.criminalintent;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeListFragment extends Fragment {

    private static final String TAG = "CrimeListFragment";

    private RecyclerView crimeRecyclerView;
    private final CrimeAdapter adapter = new CrimeAdapter(new ArrayList<Crime>());

    private final CrimeListViewModel crimeListViewModel = new CrimeListViewModel();

    private Callbacks callbacks;

    interface Callbacks {
        void onCrimeSelected(UUID crimeId);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callbacks = (context instanceof Callbacks) ? (Callbacks) context : null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        crimeRecyclerView =
                view.findViewById(R.id.crime_recycler_view);
        crimeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        crimeRecyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        crimeListViewModel.getCrimeListLiveData().observe(getViewLifecycleOwner(), new Observer<List<Crime>>() {
            @Override
            public void onChanged(List<Crime> crimes) {
                Log.i(TAG, "Got crimeLiveData " + crimes.size());
                updateUI(crimes);
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
    }

    private void updateUI(List<Crime> crimes) {
        adapter.setCrimes(crimes);
        crimeRecyclerView.setAdapter(adapter);
    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Crime crime;

        private final TextView titleTextView = itemView.findViewById(R.id.crime_title);
        private final TextView dateTextView = itemView.findViewById(R.id.crime_date);
        private final ImageView solvedImageView = itemView.findViewById(R.id.crime_solved);

        CrimeHolder(View view) {
            super(view);
            itemView.setOnClickListener(this);
        }

        void bind(Crime crime) {
            this.crime = crime;
            titleTextView.setText(crime.getTitle());
            dateTextView.setText(crime.getDate().toString());
            solvedImageView.setVisibility(crime.isSolved() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getContext(), crime.getTitle() + " clicked!", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

        private List<Crime> crimes;

        CrimeAdapter(List<Crime> crimes) {
            this.crimes = crimes;
        }

        public void setCrimes(List<Crime> crimes) {
            this.crimes = crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_crime, parent, false);
            return new CrimeHolder(view);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = crimes.get(position);
            holder.bind(crime);
        }

        @Override
        public int getItemCount() {
            return crimes.size();
        }
    }

    static CrimeListFragment newInstance() {
        return new CrimeListFragment();
    }
}