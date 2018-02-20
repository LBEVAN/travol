package lbevan.github.io.travol.activity.main;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import lbevan.github.io.travol.R;
import lbevan.github.io.travol.activity.holiday.HolidayActivity;
import lbevan.github.io.travol.domain.entity.Holiday;

/**
 * Created by Luke on 06/12/2017.
 */
class HolidaysViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public Holiday holiday;

    public TextView holidayTitle;
    public ImageView holidayHighlightedPhoto;

    public HolidaysViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        holidayTitle = itemView.findViewById(R.id.text_holiday_title);
        holidayHighlightedPhoto = itemView.findViewById(R.id.img_holiday_highlighted_photo);
    }

    // todo figure out if this shpuld be delegated back to the activity?
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), HolidayActivity.class);

        intent.putExtra("holiday", holiday);
        view.getContext().startActivity(intent);
    }
}
