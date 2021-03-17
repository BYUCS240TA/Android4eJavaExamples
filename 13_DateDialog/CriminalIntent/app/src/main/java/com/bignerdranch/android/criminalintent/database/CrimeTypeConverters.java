package com.bignerdranch.android.criminalintent.database;

import androidx.room.TypeConverter;

import java.util.Date;
import java.util.UUID;

public class CrimeTypeConverters {
    @TypeConverter
    public Long fromDate(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public Date toDate(Long millisSinceEpoch) {
        return millisSinceEpoch == null ? null : new Date(millisSinceEpoch);
    }

    @TypeConverter
    public UUID toUUID(String uuid) {
        return uuid == null ? null : UUID.fromString(uuid);
    }

    @TypeConverter
    public String fromUUID(UUID uuid) {
        return uuid == null ? null : uuid.toString();
    }
}