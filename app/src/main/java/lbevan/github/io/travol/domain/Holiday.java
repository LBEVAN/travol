package lbevan.github.io.travol.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Luke on 06/12/2017.
 */
public class Holiday implements Parcelable {

    private String name;
    private int photo;

    public Holiday(Holiday holiday) {
        this.name = holiday.getName();
        this.photo = holiday.getPhoto();
    }

    public Holiday(String name, int photo) {
        this.name = name;
        this.photo = photo;
    }

    protected Holiday(Parcel in) {
        name = in.readString();
        photo = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
