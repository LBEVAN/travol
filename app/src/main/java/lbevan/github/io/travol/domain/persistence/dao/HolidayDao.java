package lbevan.github.io.travol.domain.persistence.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import lbevan.github.io.travol.domain.entity.Holiday;

/**
 * Created by LBEVAN on 16/02/2018.
 */
@Dao
public interface HolidayDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createHoliday(Holiday holiday);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateHoliday(Holiday holiday);

    @Delete
    void deleteHoliday(Holiday holiday);

    @Query("SELECT * FROM holiday")
    List<Holiday> getHolidays();

    @Query("SELECT * FROM holiday where id = :id")
    Holiday getHolidayById(int id);
}
