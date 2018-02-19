package lbevan.github.io.travol.activity.holiday;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.io.File;
import java.util.List;

import lbevan.github.io.travol.R;

/**
 * Created by Luke on 18/02/2018.
 */

public class PhotoAdapter extends BaseAdapter {

    private Context context;
    private List<File> photos;

    public PhotoAdapter(Context context, List<File> photos) {
        this.context = context;
        this.photos = photos;
    }


    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public Object getItem(int position) {
        return photos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final File file = photos.get(position);

        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.layout_photo_thumbnail, null);
        }

        final ImageView photoHolder = convertView.findViewById(R.id.img_photo);

        // Get the dimensions of the View
//        int targetW = photoHolder.getWidth();
//        int targetH = photoHolder.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), bitmapOptions);
//        int photoW = bitmapOptions.outWidth;
//        int photoH = bitmapOptions.outHeight;

//        // Determine how much to scale down the image
//        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bitmapOptions.inJustDecodeBounds = false;
//        bitmapOptions.inSampleSize = scaleFactor;
        bitmapOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), bitmapOptions);
        photoHolder.setImageBitmap(bitmap);

        return convertView;
    }
}
