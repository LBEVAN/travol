package lbevan.github.io.travol.util;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.File;

/**
 * An asynchronous task for decoding and loading a bitmap from a file.
 * This takes direct advantage of threading to update the UI when ready.
 *
 * Created by LBEVAN on 20/02/2018.
 */
public class DecodeBitmapAsyncTask extends AsyncTask<Object, Void, Bitmap> {

    private String imagePath;
    private ImageView imageView;

    public DecodeBitmapAsyncTask(String imagePath, ImageView imageView) {
        this.imagePath = imagePath;
        this.imageView = imageView;
    }

    /**
     * Generate the bitmap
     *
     * @param objects
     * @return
     */
    @Override
    protected Bitmap doInBackground(Object[] objects) {
        int width = imageView.getWidth();
        int height = imageView.getHeight();

        return PhotoUtils.decodeSampledBitmapFromPath(imagePath, width, height);
    }

    /**
     * Update the UI view once the bitmap has been generated.
     *
     * @param bitmap the generated bitmap
     */
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        // update the ui with bitmap
        imageView.setImageBitmap(bitmap);

        // clear down references
        this.imageView = null;
        this.imagePath = null;
    }
}
