package lbevan.github.io.travol.component.place;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import lbevan.github.io.travol.R;
import lbevan.github.io.travol.activity.holiday.HolidayActivity;
import lbevan.github.io.travol.activity.place.PlaceActivity;
import lbevan.github.io.travol.domain.entity.Place;

/**
 * Created by Luke on 25/02/2018.
 */

public class PlacesViewAdapter extends RecyclerView.Adapter<PlacesViewAdapter.ViewHolder> {

    private Context context;
    private List<Place> places;

    public PlacesViewAdapter(Context context, List<Place> places) {
        this.places = places;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_place, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        Place place = places.get(position);
        viewHolder.place = place;
        viewHolder.placeTitle.setText(place.getTitle());
        viewHolder.placeLocation.setText(place.getLocation());

//        // todo: choose a photo from the list
//        List<Photo> photos = Database.getDatabase(context).photoDao().getPhotosByHolidayId(holiday.getId());
//
//        if(photos == null || photos.size() == 0) {
//            // set a default image
            viewHolder.placeHighlightedPhoto.setImageResource(R.drawable.default_holiday_image);
//        } else {
//            // todo: give ability to choose a photo to render!
//            Photo photo = photos.get(0);
//            final File file = FileSystemUtils.getPhotoFileByFileName(context, photo.getFileName());
//
//            holder.holidayHighlightedPhoto.post(new Runnable() {
//                @Override
//                public void run() {
//                    new DecodeBitmapAsyncTask(file, holder.holidayHighlightedPhoto).execute();
//                }
//            });
//        }
    }

    @Override
    public int getItemCount() {
        return this.places.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private View view;
        private Place place;
        private TextView placeTitle;
        private TextView placeLocation;
        private ImageView placeHighlightedPhoto;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            itemView.setOnClickListener(this);
            placeTitle = itemView.findViewById(R.id.text_place_title);
            placeLocation = itemView.findViewById(R.id.text_place_location);
            placeHighlightedPhoto = itemView.findViewById(R.id.img_place_highlighted_photo);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), PlaceActivity.class);

            intent.putExtra("Place", place);
            view.getContext().startActivity(intent);
        }
    }
}
