package lbevan.github.io.travol.domain.persistence.converter;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import lbevan.github.io.travol.domain.entity.Photo;

/**
 * Created by Luke on 18/02/2018.
 */

public class ListTypeConverter {

    static Gson gson = new Gson();

    @TypeConverter
    public static List<Photo> stringToPhotoList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Photo>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String photoListToString(List<Photo> objects) {
        return gson.toJson(objects);
    }
}
