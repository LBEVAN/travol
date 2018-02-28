package lbevan.github.io.travol.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

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
     * @param imagePath the absolute path of the photo on the device
     * @param targetWidth width of the target image holder (i.e. {@link android.widget.ImageView})
     * @param targetHeight height of the target image holder (i.e. {@link android.widget.ImageView})
     * @return the decoded and scaled {@link Bitmap}
     */
    public static Bitmap decodeSampledBitmapFromPath(String imagePath, int targetWidth, int targetHeight) {
        // first decode with inJustDecodeBounds = true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);

        // calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, targetWidth, targetHeight);

        // decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(imagePath, options);
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

    public static String copyPhotoFromUri(Context context, Uri uri) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        File imageFile = null;
        try {
            inputStream = context.getContentResolver().openInputStream(uri);
            imageFile = createImageFile(context);
            outputStream = new FileOutputStream(imageFile);
            byte[] buf = new byte[1024];
            int len;
            while((len = inputStream.read(buf)) > 0){
                outputStream.write(buf, 0, len);
            }
        } catch (FileNotFoundException ex) {
            // Error occurred while creating the File
            System.out.println(ex);
        } catch (IOException ex) {
            // Error occurred while creating the File
            System.out.println(ex);
        } finally {
            try {
                if(outputStream != null) {
                    outputStream.close();
                }
                inputStream.close();
            } catch (IOException ex) {
                // Error occurred while creating the File
                System.out.println(ex);
            }
        }

        return imageFile.getAbsolutePath();
    }

    public static File createImageFile(Context context) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        return image;
    }
}
