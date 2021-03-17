package com.bignerdranch.android.criminalintent;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.UUID;

public class CrimeDetailViewModel extends ViewModel {

    private CrimeRepository crimeRepository = CrimeRepository.get();
    private MutableLiveData<UUID> crimeIdLiveData = new MutableLiveData<>();

    private LiveData<Crime> crimeLiveData  =
        Transformations.switchMap(crimeIdLiveData, new Function<UUID, LiveData<Crime>>() {
            @Override
            public LiveData<Crime> apply(UUID crimeId) {
                return crimeRepository.getCrime(crimeId);
            }
        });

    public LiveData<Crime> getCrimeLiveData() {
        return crimeLiveData;
    }

    void loadCrime(UUID crimeId) {
        crimeIdLiveData.setValue(crimeId);
    }

    void saveCrime(Crime crime) {
        crimeRepository.updateCrime(crime);
    }
}