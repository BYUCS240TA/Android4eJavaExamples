package com.bignerdranch.android.criminalintent;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CrimeListFragment extends Fragment {

    private static final String TAG = "CrimeListFragment";

    private RecyclerView crimeRecyclerView;

    private final CrimeListViewModel crimeListViewModel = new CrimeListViewModel();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Total crimes: " + crimeListViewModel.getCrimes().size());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        crimeRecyclerView =
                view.findViewById(R.id.crime_recycler_view);
        crimeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        updateUI();

        return view;
    }

    private void updateUI() {
        List<Crime> crimes = crimeListViewModel.getCrimes();
        CrimeAdapter adapter = new CrimeAdapter(crimes);
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

        private final List<Crime> crimes;

        CrimeAdapter(List<Crime> crimes) {
            this.crimes = crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_crime, parent, false);
            return new CrimeHolder(view);
        }

        @Override
        public int getItemCount() {
            return crimes.size();
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = crimes.get(position);
            holder.bind(crime);
        }
    }

    static CrimeListFragment newInstance() {
        return new CrimeListFragment();
    }
}
