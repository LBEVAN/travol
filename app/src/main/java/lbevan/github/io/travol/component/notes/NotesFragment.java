package lbevan.github.io.travol.component.notes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lbevan.github.io.travol.R;
import lbevan.github.io.travol.activity.note.EditNoteActivity;
import lbevan.github.io.travol.domain.entity.Note;
import lbevan.github.io.travol.domain.persistence.Database;

import static android.app.Activity.RESULT_OK;

/**
 * Fragment to display a list of {@link Note}.
 */
public class NotesFragment extends Fragment {

    private static final int REQUEST_EDIT_NOTE = 1;
    private static final int REQUEST_CREATE_NOTE = 2;

    private OnFragmentInteractionListener onFragmentInteractionListener;

    private List<Note> notes;
    private List<Note> selectedNotes = new ArrayList<>();

    private ActionMode actionMode;
    private boolean isSelectionMode = false;
    private NotesRecyclerViewAdapter notesRecyclerViewAdapter;

    private int indexEditing = -1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NotesFragment() { }

    /**
     * Static creator.
     *
     * @param notes list of notes to display
     * @return {@link NotesFragment}
     */
    public static NotesFragment newInstance(List<Note> notes) {
        NotesFragment fragment = new NotesFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("NOTES", (ArrayList<? extends Parcelable>) notes);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            notes = getArguments().getParcelableArrayList("NOTES");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);
        setHasOptionsMenu(true);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(linearLayoutManager);

            notesRecyclerViewAdapter = new NotesRecyclerViewAdapter(getContext(), notes, selectedNotes);
            recyclerView.setAdapter(notesRecyclerViewAdapter);

            recyclerView.addOnItemTouchListener(new CustomOnItemTouchListener(getContext(), recyclerView, new CustomOnItemTouchListener.OnItemClickListener() {

                @Override
                public void onItemClick(View view, int position) {
                    if (isSelectionMode) {
                        onSelect(position);
                    } else {
                        indexEditing = position;
                        dispatchEditNoteIntent(notes.get(position));
                    }
                }

                @Override
                public void onItemLongClick(View view, int position) {
                    if (!isSelectionMode) {
                        isSelectionMode = true;

                        if (actionMode == null) {
                            actionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(actionModeCallback);
                        }
                    }

                    onSelect(position);
                }
            }));

            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                    linearLayoutManager.getOrientation());
            recyclerView.addItemDecoration(dividerItemDecoration);
        }
        return view;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            Note note = data.getParcelableExtra("Note");
            if(requestCode == REQUEST_EDIT_NOTE) {
                notes.set(indexEditing, note);
                notesRecyclerViewAdapter.notifyDataSetChanged();
                onFragmentInteractionListener.onEditNoteResult(note);
            } else if(requestCode == REQUEST_CREATE_NOTE) {
                note = onFragmentInteractionListener.onCreateNoteResult(note);
                notes.add(note);
                notesRecyclerViewAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_holiday_details_actions, menu);

        MenuItem addNoteItem = menu.findItem(R.id.btn_create_note);
        addNoteItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                dispatchAddNoteIntent();
                return true;
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            onFragmentInteractionListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDetach() {
        super.onDetach();
        onFragmentInteractionListener = null;
    }

    /**
     * On note select handler.
     * Add/remove note from lists and notify view of changes.
     *
     * @param position position of note in list
     */
    private void onSelect(int position) {
        if (actionMode != null) {
            // add/remove the selected item
            if (selectedNotes.contains(notes.get(position))) {
                selectedNotes.remove(notes.get(position));
            } else {
                selectedNotes.add(notes.get(position));
            }

            // update the selected counter
            if (selectedNotes.size() > 0) {
                actionMode.setTitle("" + selectedNotes.size());
            } else {
                actionMode.setTitle("");
            }

            // notify view
            notesRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Start the Edit Note activity.
     *
     * @param selectedNote the note to edit
     */
    private void dispatchEditNoteIntent(Note selectedNote) {
        Intent intent = new Intent(getActivity(), EditNoteActivity.class);
        intent.putExtra("Note", selectedNote);
        startActivityForResult(intent, REQUEST_EDIT_NOTE);
    }

    /**
     * Start the Add Note activity.
     */
    private void dispatchAddNoteIntent() {
        Intent intent = new Intent(getActivity(), EditNoteActivity.class);
        startActivityForResult(intent, REQUEST_CREATE_NOTE);
    }


    /**
     * {@Actyio}
     */
    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate the context menu
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_context_note, menu);
            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.btn_delete:
                    onDelete();
                    return true;
                default:
                    return false;
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
            isSelectionMode = false;
            selectedNotes.removeAll(selectedNotes);
            notesRecyclerViewAdapter.notifyDataSetChanged();
        }

        /**
         * On delete event handler.
         * Delete the selected notes from the db and current list,
         * notify the view to update and then finish the action mode.
         */
        private void onDelete() {
            for (Iterator<Note> noteIterator = notes.iterator(); noteIterator.hasNext();){
                Note note = noteIterator.next();
                for(Note selectedNote : selectedNotes) {
                    if(note.getId() == selectedNote.getId()) {
                        noteIterator.remove();
                        Database.getDatabase(getContext()).noteDao().deleteNote(note);
                        break;
                    }
                }
            }

            notesRecyclerViewAdapter.notifyDataSetChanged();
            actionMode.finish();
        }
    };

    /**
     * Note actions for the parent activity to handle.
     */
    public interface OnFragmentInteractionListener {

        /**
         * Handle result of creating a new note object.
         *
         * @param note the note to create
         * @return the saved note with it's Id.
         */
        Note onCreateNoteResult(Note note);

        /**
         * Handle result of editing an existing note.
         *
         * @param note the edited note
         */
        void onEditNoteResult(Note note);
    }
}
