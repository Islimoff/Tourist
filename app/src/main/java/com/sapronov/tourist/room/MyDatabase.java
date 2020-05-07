package com.sapronov.tourist.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.sapronov.tourist.dao.CoordinatesDao;
import com.sapronov.tourist.model.Coordinates;

@Database(entities = {Coordinates.class},version = 1)
public abstract class MyDatabase extends RoomDatabase {

    public abstract CoordinatesDao getCoordinatesDao();
}
