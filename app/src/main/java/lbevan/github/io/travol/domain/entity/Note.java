package lbevan.github.io.travol.domain.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LBEVAN on 23/02/2018.
 */
@Entity(foreignKeys = {
        @ForeignKey(
                entity = Holiday.class,
                parentColumns = ("id"),
                childColumns = "holidayId")
})
public class Note implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private int holidayId;

    private String title;
    private String contents;

    public Note(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    protected Note(Parcel in) {
        id = in.readLong();
        holidayId = in.readInt();
        title = in.readString();
        contents = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeInt(holidayId);
        dest.writeString(title);
        dest.writeString(contents);
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getHolidayId() {
        return holidayId;
    }

    public void setHolidayId(int holidayId) {
        this.holidayId = holidayId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
