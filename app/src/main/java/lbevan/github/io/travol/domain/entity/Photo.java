package lbevan.github.io.travol.domain.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;


/**
 * Created by Luke on 18/02/2018.
 */
@Entity(foreignKeys = {
        @ForeignKey(
                entity = Photo.class,
                parentColumns = ("id"),
                childColumns = "holidayId")
    }
)
public class Photo implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int holidayId;
    private String fileName;

    /* This is the corresponding file on the device, should be injected when needed */
    @Ignore
    private File file;

    public Photo(int holidayId, String fileName) {
        this.holidayId = holidayId;
        this.fileName = fileName;
    }

    protected Photo(Parcel in) {
        id = in.readInt();
        holidayId = in.readInt();
        fileName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(holidayId);
        dest.writeString(fileName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHolidayId() {
        return holidayId;
    }

    public void setHolidayId(int holidayId) {
        this.holidayId = holidayId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
