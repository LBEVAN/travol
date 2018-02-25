package lbevan.github.io.travol.activity.holiday;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

import lbevan.github.io.travol.R;
import lbevan.github.io.travol.activity.note.EditNoteActivity;
import lbevan.github.io.travol.component.notes.NotesFragment;
import lbevan.github.io.travol.domain.entity.Holiday;
import lbevan.github.io.travol.domain.entity.Note;
import lbevan.github.io.travol.domain.persistence.Database;

import static android.app.Activity.RESULT_OK;

/**\xo
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HolidayDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HolidayDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HolidayDetailsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private Holiday holiday;

    private TextView location;
    private TextView startDate;
    private TextView endDate;

    public HolidayDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param holiday the holiday
     * @return A new instance of fragment HolidayDetailsFragment.
     */
    public static HolidayDetailsFragment newInstance(Holiday holiday) {
        HolidayDetailsFragment fragment = new HolidayDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable("holiday", holiday);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            holiday = getArguments().getParcelable("holiday");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_holiday_details, container, false);
        setHasOptionsMenu(true);

        location = view.findViewById(R.id.text_location);
        location.setText(holiday.getLocation());

        startDate = view.findViewById(R.id.text_start_date);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.UK);
        startDate.setText(simpleDateFormat.format(holiday.getStartDate()));

        endDate = view.findViewById(R.id.text_end_date);
        endDate.setText(simpleDateFormat.format(holiday.getEndDate()));

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Fragment notesFragment = NotesFragment.newInstance(Database.getDatabase(getContext()).noteDao().getNotesByHolidayId(holiday.getId()));
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.notes_fragment_container, notesFragment).commit();
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.menu_holiday_details_actions, menu);
//
//        MenuItem addNoteItem = menu.findItem(R.id.btn_add_note);
//        addNoteItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem menuItem) {
//                dispatchAddNoteIntent();
//                return true;
//            }
//        });
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
