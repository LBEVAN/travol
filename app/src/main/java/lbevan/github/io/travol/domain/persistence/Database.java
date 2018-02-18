package lbevan.github.io.travol.domain.persistence;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import lbevan.github.io.travol.domain.entity.Holiday;
import lbevan.github.io.travol.domain.persistence.converter.DateTypeConverter;
import lbevan.github.io.travol.domain.persistence.dao.HolidayDao;

/**
 * Created by LBEVAN on 16/02/2018.
 */
@android.arch.persistence.room.Database(
        entities = {Holiday.class},
        version = 1,
        exportSchema = false
)
@TypeConverters({DateTypeConverter.class})
public abstract class Database extends RoomDatabase {

    private static Database INSTANCE;

    public abstract HolidayDao holidayDao();

    public static Database getDatabase(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, Database.class, "travol")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}