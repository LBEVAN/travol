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
    private String imagePath;

    public Photo(int holidayId, String imagePath) {
        this.holidayId = holidayId;
        this.imagePath = imagePath;
    }

    protected Photo(Parcel in) {
        id = in.readInt();
        holidayId = in.readInt();
        imagePath = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(holidayId);
        dest.writeString(imagePath);
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
