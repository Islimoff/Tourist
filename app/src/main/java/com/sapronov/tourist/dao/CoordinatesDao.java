package com.sapronov.tourist.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.sapronov.tourist.model.Coordinates;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface CoordinatesDao {

    @Insert
    long insertCoordinates(Coordinates coordinates);

    @Query("SELECT * FROM coordinates")
    Maybe<List<Coordinates>> getAllCoordinates();

    @Query("SELECT * FROM coordinates WHERE id = :id")
    Coordinates getId(long id);
}
