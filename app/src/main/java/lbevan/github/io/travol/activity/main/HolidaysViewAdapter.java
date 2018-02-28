package lbevan.github.io.travol.activity.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.List;

import lbevan.github.io.travol.R;
import lbevan.github.io.travol.domain.entity.HighlightPhoto;
import lbevan.github.io.travol.domain.entity.Holiday;
import lbevan.github.io.travol.domain.entity.Photo;
import lbevan.github.io.travol.domain.persistence.Database;
import lbevan.github.io.travol.util.DecodeBitmapAsyncTask;
import lbevan.github.io.travol.util.FileSystemUtils;

/**
 * Created by Luke on 06/12/2017.
 */
class HolidaysViewAdapter extends RecyclerView.Adapter<HolidaysViewHolder> {

    private Context context;
    private List<Holiday> itemList;

    public HolidaysViewAdapter(Context context, List<Holiday> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public HolidaysViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_holiday, null);
        HolidaysViewHolder rcv = new HolidaysViewHolder(view);
        return rcv;
    }

    @Override
    public void onBindViewHolder(final HolidaysViewHolder holder, int position) {
        Holiday holiday = itemList.get(position);
        holder.holiday = holiday;
        holder.holidayTitle.setText(holiday.getTitle());
        holder.holidayLocation.setText(holiday.getLocation());

        final HighlightPhoto highlightPhoto = holiday.getHighlightPhoto();
        if(highlightPhoto == null || highlightPhoto.getPath().equals("")) {
            // set a default image
            holder.holidayHighlightedPhoto.setImageResource(R.drawable.default_holiday_image);
        } else {
            holder.holidayHighlightedPhoto.post(new Runnable() {
                @Override
                public void run() {
                    new DecodeBitmapAsyncTask(highlightPhoto.getPath(), holder.holidayHighlightedPhoto).execute();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
