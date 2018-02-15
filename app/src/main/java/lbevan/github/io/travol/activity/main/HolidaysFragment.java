package lbevan.github.io.travol.activity.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import lbevan.github.io.travol.R;
import lbevan.github.io.travol.domain.Holiday;

/**
 * Created by Luke on 06/12/2017.
 */
public class HolidaysFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_holidays, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new HolidaysViewAdapter(getContext(), getAllItemList()));

        return view;
    }

    private List<Holiday> getAllItemList(){

        List<Holiday> allItems = new ArrayList<Holiday>();
        allItems.add(new Holiday("United States", R.drawable.newyork));
        allItems.add(new Holiday("United States", R.drawable.newyork));
        allItems.add(new Holiday("United States", R.drawable.newyork));
        allItems.add(new Holiday("United States", R.drawable.newyork));
        allItems.add(new Holiday("United States", R.drawable.newyork));
        allItems.add(new Holiday("United States", R.drawable.newyork));
        allItems.add(new Holiday("United States", R.drawable.newyork));

//        allItems.add(new ItemObject("Canada", R.drawable.canada));
//        allItems.add(new ItemObject("United Kingdom", R.drawable.uk));
//        allItems.add(new ItemObject("Germany", R.drawable.germany));
//        allItems.add(new ItemObject("Sweden", R.drawable.sweden));

        return allItems;
    }
}
