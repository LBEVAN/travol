package lbevan.github.io.travol.component.notes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import lbevan.github.io.travol.R;
import lbevan.github.io.travol.component.notes.NotesFragment.OnListFragmentInteractionListener;
import lbevan.github.io.travol.domain.entity.Note;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Note} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class NotesRecyclerViewAdapter extends RecyclerView.Adapter<NotesRecyclerViewAdapter.ViewHolder> {

    private final List<Note> notes;
    private final OnListFragmentInteractionListener mListener;

    public NotesRecyclerViewAdapter(List<Note> notes, OnListFragmentInteractionListener listener) {
        this.notes = notes;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_notes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.note = notes.get(position);
        holder.title.setText(notes.get(position).getTitle());
        holder.contents.setText(notes.get(position).getContents());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.note);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View view;
        public final TextView title;
        public final TextView contents;
        public Note note;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            title = view.findViewById(R.id.text_title);
            contents = view.findViewById(R.id.text_contents);
        }
    }
}
