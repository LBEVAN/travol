package lbevan.github.io.travol.domain.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Luke on 06/12/2017.
 */
@Entity
public class Holiday implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String location;
    private Date startDate;
    private Date endDate;

    // note: relations don't work on entity objects, then need to be pojo's, so for now just using explicit queries!
//    @Relation(parentColumn = "id", entityColumn = "holidayId")
//    private List<Photo> photos;

    public Holiday(){ }

    public Holiday(Holiday holiday) {
        this.id = holiday.getId();
        this.title = holiday.getTitle();
        this.location = holiday.getLocation();
        this.startDate = holiday.startDate;
        this.endDate = holiday.endDate;
//        this.photos = holiday.getPhotos();
    }

    public Holiday(String title, String location, Date startDate, Date endDate /*, List<Photo> photos*/) {
        this.title = title;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
//        this.photos = photos;
    }

    protected Holiday(Parcel in) {
        id = in.readInt();
        title = in.readString();
        location = in.readString();
        startDate = (Date) in.readValue(Date.class.getClassLoader());
        endDate = (Date) in.readValue(Date.class.getClassLoader());
//        photos = (List<Photo>) in.readValue(List.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(location);
        dest.writeValue(startDate);
        dest.writeValue(endDate);
//        dest.writeValue(photos);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Holiday> CREATOR = new Creator<Holiday>() {
        @Override
        public Holiday createFromParcel(Parcel in) {
            return new Holiday(in);
        }

        @Override
        public Holiday[] newArray(int size) {
            return new Holiday[size];
        }
    };

    public final int getId() {
        return id;
    }

    public final void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

//    public List<Photo> getPhotos() {
//        return photos;
//    }
//
//    public void setPhotos(List<Photo> photos) {
//        this.photos = photos;
//    }
}
