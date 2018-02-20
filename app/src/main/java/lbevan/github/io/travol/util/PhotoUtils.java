package lbevan.github.io.travol.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;

/**
 * Created by LBEVAN on 20/02/2018.
 */

public class PhotoUtils {

    /**
     * Decode and load a scaled down image from the specified file.
     * The output will fit the dimensions specified.
     *
     * See: https://developer.android.com/topic/performance/graphics/load-bitmap.html
     *
     * @param file the {@link File} to load the image from
     * @param targetWidth width of the target image holder (i.e. {@link android.widget.ImageView})
     * @param targetHeight height of the target image holder (i.e. {@link android.widget.ImageView})
     * @return the decoded and scaled {@link Bitmap}
     */
    public static Bitmap decodeSampledBitmapFromFile(File file, int targetWidth, int targetHeight) {
        // first decode with inJustDecodeBounds = true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);

        // calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, targetWidth, targetHeight);

        // decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(file.getAbsolutePath(), options);
    }

    /**
     * Calculate a usable sample size given the required dimensions.
     *
     * @param options bitmap options
     * @param targetWidth width of the target image holder (i.e. {@link android.widget.ImageView})
     * @param targetHeight height of the target image holder (i.e. {@link android.widget.ImageView})
     * @return
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int targetWidth, int targetHeight) {
        // get the raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;

        int inSampleSize = 1;

        if (height > targetHeight || width > targetWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= targetHeight
                    && (halfWidth / inSampleSize) >= targetWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
