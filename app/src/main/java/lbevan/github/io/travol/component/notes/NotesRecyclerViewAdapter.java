package lbevan.github.io.travol.component.notes;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import lbevan.github.io.travol.R;
import lbevan.github.io.travol.domain.entity.Note;

/**
 * {@link RecyclerView.Adapter} implementation for {@link Note}.
 */
public class NotesRecyclerViewAdapter extends RecyclerView.Adapter<NotesRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Note> notes;
    private List<Note> selectedNotes;

    /**
     * Constructor.
     *
     * @param context parent context
     * @param notes list of notes to display
     * @param selectedNotes list of selected notes
     */
    public NotesRecyclerViewAdapter(Context context, List<Note> notes, List<Note> selectedNotes) {
        this.context = context;
        this.notes = notes;
        this.selectedNotes = selectedNotes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_notes, parent, false);
        return new ViewHolder(view);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.note = notes.get(position);
        holder.title.setText(notes.get(position).getTitle());
        holder.contents.setText(notes.get(position).getContents());

        // if the current note is selected, alter its appearance
        if(selectedNotes.contains(notes.get(position))) {
            holder.layout.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
        } else {
            holder.layout.setBackgroundColor(Color.WHITE);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getItemCount() {
        return notes.size();
    }

    /**
     * Notes ViewHolder implementation.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        public Note note;

        public final View view;
        public final LinearLayout layout;
        public final TextView title;
        public final TextView contents;


        /**
         * Constructor.
         *
         * @param view
         */
        public ViewHolder(View view) {
            super(view);
            this.view = view;

            layout = view.findViewById(R.id.layout_note);
            title = view.findViewById(R.id.text_title);
            contents = view.findViewById(R.id.text_contents);
        }
    }
}
