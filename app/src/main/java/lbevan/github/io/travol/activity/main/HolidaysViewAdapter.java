package lbevan.github.io.travol.activity.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import lbevan.github.io.travol.R;
import lbevan.github.io.travol.domain.entity.Holiday;

/**
 * Created by Luke on 06/12/2017.
 */
public class HolidaysViewAdapter extends RecyclerView.Adapter<HolidaysViewHolder> {

    private Context context;
    private List<Holiday> itemList;

    public HolidaysViewAdapter(Context context, List<Holiday> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public HolidaysViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_holiday, null);
        HolidaysViewHolder rcv = new HolidaysViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(HolidaysViewHolder holder, int position) {
        Holiday holiday = itemList.get(position);
        holder.holiday = holiday;
        holder.countryName.setText(holiday.getName());

        // todo: choose a photo from the list
        holder.countryPhoto.setImageResource(R.drawable.newyork);
//        if(holiday.getPhotos() != null && holiday.getPhotos().size() > 0) {
//            holder.countryPhoto.setImageResource(holiday.getPhotos().get(0));
//        } else {
//
//        }
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
