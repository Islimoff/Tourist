package com.sapronov.tourist.room;

import android.content.Context;

import androidx.room.Room;

import com.sapronov.tourist.model.Coordinates;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class RoomDb {

    private MyDatabase myDatabase;
    private static RoomDb roomDb;

    private RoomDb(Context context) {
        myDatabase = Room.databaseBuilder(context,
                MyDatabase.class, "populus-database")
                .allowMainThreadQueries()
                .build();
    }

    public static RoomDb getDb(Context context) {
        if (roomDb == null) {
            roomDb = new RoomDb(context);
        }
        return roomDb;
    }

    public void addCoordinates(Coordinates coordinates) {
        Single.fromCallable(() -> myDatabase.getCoordinatesDao().getId(coordinates.getId()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Long>() {
                    @Override
                    public void onSuccess(Long aLong) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Completable.fromRunnable(new Runnable() {
                            @Override
                            public void run() {
                                myDatabase.getCoordinatesDao().insertCoordinates(coordinates);
                            }
                        })
                                .subscribeOn(Schedulers.io())
                                .subscribe();
                    }
                });

    }

    public void getAllCoordinates(final DatabaseCallback callback) {
        myDatabase.getCoordinatesDao().getAllCoordinates()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableMaybeObserver<List<Coordinates>>() {
                    @Override
                    public void onSuccess(List<Coordinates> coordinates) {
                        callback.onCoordinatesLoaded(coordinates);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
