package lbevan.github.io.travol.domain.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LBEVAN on 26/02/2018.
 */

public class HighlightPhoto implements Parcelable {

    /**
     * Absolute path of the file on the device
     */
    private String path;

    /**
     * Constructor.
     *
     * @param path absolute path of the file on the device
     */
    public HighlightPhoto(String path) {
        this.path = path;
    }

    protected HighlightPhoto(Parcel in) {
        path = in.readString();
    }

    public static final Creator<HighlightPhoto> CREATOR = new Creator<HighlightPhoto>() {
        @Override
        public HighlightPhoto createFromParcel(Parcel in) {
            return new HighlightPhoto(in);
        }

        @Override
        public HighlightPhoto[] newArray(int size) {
            return new HighlightPhoto[size];
        }
    };

    public String getPath() {
        return path;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(path);
    }
}
