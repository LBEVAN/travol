package lbevan.github.io.travol.domain.persistence.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import lbevan.github.io.travol.domain.entity.Note;

/**
 * Created by LBEVAN on 23/02/2018.
 */
@Dao
public interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createNote(Note note);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void updateNote(Note note);

    @Delete
    void deleteNote(Note note);

    @Query("SELECT * FROM note WHERE holidayId = :holidayId")
    List<Note> getNotesByHolidayId(int holidayId);
}
