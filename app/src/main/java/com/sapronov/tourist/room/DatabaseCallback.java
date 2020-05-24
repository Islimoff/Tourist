package com.sapronov.tourist.room;

import com.sapronov.tourist.model.Coordinates;

import java.util.List;

public interface DatabaseCallback {

    void onCoordinatesLoaded(List<Coordinates> coordinates);
}
