package lbevan.github.io.travol.domain.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Luke on 25/02/2018.
 */
@Entity
public class Place implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String title;
    private String location;
    private Date date;

    // List<Note> notes;
    // List<Companion> people;
    // List<Photo> photos;

    public Place(String title, String location, Date date) {
        this.title = title;
        this.location = location;
        this.date = date;
    }

    protected Place(Parcel in) {
        id = in.readLong();
        title = in.readString();
        location = in.readString();
    }

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(location);
        dest.writeValue(date);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
