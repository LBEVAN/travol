package lbevan.github.io.travol.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by Luke on 19/02/2018.
 */

public class FileSystemUtils {

    /**
     * Get the File object for a photo, given its file name.
     * Searches in the picture directory and assumes that the file name with a time stamp is unique.
     *
     * @param context
     * @param fileName
     * @return The File containing the photo information
     */
    public static File getPhotoFileByFileName(Context context, final String fileName) {
        // search all app photos
        File appPhotosDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File fileToReturn = null;
        for(File file : appPhotosDir.listFiles()) {
            if(file.getName().equals(fileName)) {
                fileToReturn = file;
            }
        }

        return fileToReturn;

    }
}
