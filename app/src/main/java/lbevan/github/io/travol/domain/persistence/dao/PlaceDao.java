package lbevan.github.io.travol.domain.persistence.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import lbevan.github.io.travol.domain.entity.Place;

/**
 * Created by Luke on 25/02/2018.
 */
@Dao
public interface PlaceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long savePlace(Place place);

    @Update
    void updatePlace(Place place);

    @Delete
    void deletePlace(Place place);

    @Query("SELECT * FROM place")
    List<Place> getPlaces();

//    @Query("SELECT * FROM place WHERE holidayId = :holidayId")
//    List<Place> getPlacesByHolidayId(int holidayId);
}
