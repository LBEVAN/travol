package lbevan.github.io.travol.component.photoGallery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.List;

import lbevan.github.io.travol.R;
import lbevan.github.io.travol.domain.entity.Photo;
import lbevan.github.io.travol.util.DecodeBitmapAsyncTask;
import lbevan.github.io.travol.util.FileSystemUtils;

/**
 * Created by Luke on 19/02/2018.
 */

class PhotoGalleryAdapter extends RecyclerView.Adapter<PhotoGalleryViewHolder> {

    private Context context;
    private List<Photo> photos;

    public PhotoGalleryAdapter(Context context, List<Photo> photos) {
        this.context = context;
        this.photos = photos;
    }

    @Override
    public PhotoGalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, null);
        PhotoGalleryViewHolder photoGalleryViewHolder = new PhotoGalleryViewHolder(view);
        return photoGalleryViewHolder;
    }

    @Override
    public void onBindViewHolder(final PhotoGalleryViewHolder holder, int position) {
        final String imagePath = photos.get(position).getImagePath();

        holder.imageView.post(new Runnable() {
            @Override
            public void run() {
                new DecodeBitmapAsyncTask(imagePath, holder.imageView).execute();
            }
        });
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }
}
