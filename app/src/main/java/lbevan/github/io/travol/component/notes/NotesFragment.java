package lbevan.github.io.travol.component.notes;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import lbevan.github.io.travol.R;
import lbevan.github.io.travol.domain.entity.Note;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class NotesFragment extends Fragment {

    private OnListFragmentInteractionListener mListener;

    private List<Note> notes;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NotesFragment() {
    }

    public static NotesFragment newInstance(List<Note> notes) {
        NotesFragment fragment = new NotesFragment();
        Bundle args = new Bundle();
        notes.add(new Note("This is my first note", "This is the description of my first note. Please click me for more information!"));
        notes.add(new Note("This is my first note", "This is the description of my first note. Please click me for more information!"));
        notes.add(new Note("This is my first note", "This is the description of my first note. Please click me for more information!"));
        notes.add(new Note("This is my first note", "This is the description of my first note. Please click me for more information!"));
        notes.add(new Note("This is my first note", "This is the description of my first note. Please click me for more information!"));
        notes.add(new Note("This is my first note", "This is the description of my first note. Please click me for more information!"));
        notes.add(new Note("This is my first note", "This is the description of my first note. Please click me for more information!"));
        notes.add(new Note("This is my first note", "This is the description of my first note. Please click me for more information!"));
        notes.add(new Note("This is my first note", "This is the description of my first note. Please click me for more information!"));
        notes.add(new Note("This is my first note", "This is the description of my first note. Please click me for more information!"));
        args.putParcelableArrayList("NOTES", (ArrayList<? extends Parcelable>) notes);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            notes = getArguments().getParcelableArrayList("NOTES");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
//            if (mColumnCount <= 1) {
//                recyclerView.setLayoutManager(new LinearLayoutManager(context));
//            } else {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
//            }
            recyclerView.setAdapter(new NotesRecyclerViewAdapter(notes, mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Note item);
    }
}
