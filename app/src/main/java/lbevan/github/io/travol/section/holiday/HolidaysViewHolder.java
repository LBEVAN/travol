package lbevan.github.io.travol.section.holiday;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import lbevan.github.io.travol.R;

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
        Toast.makeText(view.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();
    }
}
