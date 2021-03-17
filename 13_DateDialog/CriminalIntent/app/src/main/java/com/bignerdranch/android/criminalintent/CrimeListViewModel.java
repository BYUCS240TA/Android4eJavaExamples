package com.bignerdranch.android.criminalintent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class CrimeListViewModel extends ViewModel {

    private CrimeRepository crimeRepository = CrimeRepository.get();
    private LiveData<List<Crime>> crimeListLiveData = crimeRepository.getCrimes();

    public CrimeRepository getCrimeRepository() {
        return crimeRepository;
    }

    public LiveData<List<Crime>> getCrimeListLiveData() {
        return crimeListLiveData;
    }
}