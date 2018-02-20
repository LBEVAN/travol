package lbevan.github.io.travol.component.photoGallery;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import lbevan.github.io.travol.R;

/**
 * Created by Luke on 19/02/2018.
 */

class PhotoGalleryViewHolder extends RecyclerView.ViewHolder {

    public GridViewImageView imageView;

    public PhotoGalleryViewHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.img_photo);
    }
}
