package lbevan.github.io.travol.component.place;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lbevan.github.io.travol.R;
import lbevan.github.io.travol.activity.holiday.EditHolidayActivity;
import lbevan.github.io.travol.domain.persistence.Database;

/**
 * Created by Luke on 25/02/2018.
 */
public class PlacesFragment extends Fragment {

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_holidays, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        bindRecyclerView();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        bindRecyclerView();
    }

    private void bindRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new PlacesViewAdapter(getContext(), Database.getDatabase(getContext()).placeDao().getPlaces()));
    }
}
