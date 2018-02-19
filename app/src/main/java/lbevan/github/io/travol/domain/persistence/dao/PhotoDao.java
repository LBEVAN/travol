package lbevan.github.io.travol.domain.persistence.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import lbevan.github.io.travol.domain.entity.Holiday;
import lbevan.github.io.travol.domain.entity.Photo;

/**
 * Created by Luke on 18/02/2018.
 */
@Dao
public interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createPhoto(Photo photo);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updatePhoto(Photo photo);

    @Delete
    void deletePhoto(Photo photo);

    @Query("SELECT * FROM photo WHERE holidayId = :holidayId")
    List<Photo> getPhotosByHolidayId(int holidayId);
}
