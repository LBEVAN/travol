package lbevan.github.io.travol.activity.main;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import lbevan.github.io.travol.activity.holiday.HolidayActivity;
import lbevan.github.io.travol.R;
import lbevan.github.io.travol.domain.Holiday;

/**
 * Created by Luke on 06/12/2017.
 */
public class HolidaysViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView countryName;
    public ImageView countryPhoto;

    public HolidaysViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        countryName = (TextView)itemView.findViewById(R.id.country_name);
        countryPhoto = (ImageView)itemView.findViewById(R.id.country_photo);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), HolidayActivity.class);

        intent.putExtra("holiday", new Holiday(countryName.getText().toString(), R.drawable.newyork));
        view.getContext().startActivity(intent);
    }
}
