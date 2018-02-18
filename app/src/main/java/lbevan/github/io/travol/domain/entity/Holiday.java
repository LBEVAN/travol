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

    private String name;
    private Date startDate;

    private Date endDate;
    private String notes;
    private int photo;

    public Holiday(Holiday holiday) {
        this.id = holiday.getId();
        this.name = holiday.getName();
        this.startDate = holiday.startDate;
        this.endDate = holiday.endDate;
        this.photo = holiday.getPhoto();
    }

    public Holiday(String name, Date startDate, Date endDate, String notes, int photo) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.notes = notes;
        this.photo = photo;
    }

    protected Holiday(Parcel in) {
        id = in.readInt();
        name = in.readString();
        startDate = (Date) in.readValue(Date.class.getClassLoader());
        endDate = (Date) in.readValue(Date.class.getClassLoader());
        notes = in.readString();
        photo = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeValue(startDate);
        dest.writeValue(endDate);
        dest.writeString(notes);
        dest.writeInt(photo);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
