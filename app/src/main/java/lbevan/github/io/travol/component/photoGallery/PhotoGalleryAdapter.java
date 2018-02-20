package lbevan.github.io.travol.component.photoGallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.List;

import lbevan.github.io.travol.R;
import lbevan.github.io.travol.domain.entity.Photo;
import lbevan.github.io.travol.util.FileSystemUtils;

/**
 * Created by Luke on 19/02/2018.
 */

class PhotoGalleryAdapter extends RecyclerView.Adapter<PhotoGalleryViewHolder> {

    private Context context;
    private List<Photo> photos;

    private RecyclerView recyclerView;

    public PhotoGalleryAdapter(Context context, List<Photo> photos) {
        this.context = context;
        this.photos = photos;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        this.recyclerView = recyclerView;
    }

    @Override
    public PhotoGalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, null);
        PhotoGalleryViewHolder photoGalleryViewHolder = new PhotoGalleryViewHolder(view);
        return photoGalleryViewHolder;
    }

    @Override
    public void onBindViewHolder(PhotoGalleryViewHolder holder, int position) {
        Bitmap bitmap = getBitmapForPhoto(photos.get(position));
        holder.imageView.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    private Bitmap getBitmapForPhoto(Photo photo) {
        // Get the dimensions of the View
        int targetW = 250;
        int targetH = 250;

        File file = FileSystemUtils.getPhotoFileByFileName(context, photo.getFileName());

        Bitmap bitmap = decodeSampledBitmapFromFile(file,targetW, targetH);
        return bitmap;

//        // Get the dimensions of the bitmap
//        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
//        bitmapOptions.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(file.getAbsolutePath(), bitmapOptions);
//        int photoW = bitmapOptions.outWidth;
//        int photoH = bitmapOptions.outHeight;
//
//        // Determine how much to scale down the image
//        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);
//
//        // Decode the image file into a Bitmap sized to fill the View
//        bitmapOptions.inJustDecodeBounds = false;
//        bitmapOptions.inSampleSize = scaleFactor;
//        bitmapOptions.inPurgeable = true;
//
//        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), bitmapOptions);
//        return bitmap;
    }

    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    private static Bitmap decodeSampledBitmapFromFile(File file, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(file.getAbsolutePath(), options);
    }
}
