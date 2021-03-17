package com.bignerdranch.android.criminalintent;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.bignerdranch.android.criminalintent.database.CrimeDao;
import com.bignerdranch.android.criminalintent.database.CrimeDatabase;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class CrimeRepository {

    private static final String DATABASE_NAME = "crime-database";

    private static CrimeRepository instance;

    private final CrimeDao crimeDao;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    static void initialize(Context context) {
        if(instance == null) {
            instance = new CrimeRepository(context);
        }
    }

    static CrimeRepository get() {
        if(instance == null) {
            throw new IllegalStateException("CrimeRepository must be initialized");
        } else {
            return instance;
        }
    }

    private CrimeRepository(Context context) {
        CrimeDatabase database = Room.databaseBuilder(
                context.getApplicationContext(),
                CrimeDatabase.class,
                DATABASE_NAME
        ).build();

        crimeDao =  database.crimeDao();
    }

    LiveData<List<Crime>> getCrimes() {
        return crimeDao.getCrimes();
    }

    LiveData<Crime> getCrime(UUID id) {
      return crimeDao.getCrime(id);
    }

    void updateCrime(final Crime crime) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                crimeDao.updateCrime(crime);
            }
        });
    }

    void addCrime(final Crime crime) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                crimeDao.addCrime(crime);
            }
        });
    }
}