package lbevan.github.io.travol.activity.note;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import lbevan.github.io.travol.R;
import lbevan.github.io.travol.domain.entity.Note;

public class EditNoteActivity extends AppCompatActivity {

    private Note note;

    private TextInputLayout inputLayoutTitle;
    private TextInputEditText title;

    private TextInputLayout inputLayoutContents;
    private TextInputEditText contents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // bind view components
        inputLayoutTitle = findViewById(R.id.input_layout_text_title);
        title = findViewById(R.id.edit_text_title);

        inputLayoutContents = findViewById(R.id.input_layout_text_contents);
        contents = findViewById(R.id.edit_text_contents);

        // if the note is passed in, we are editing
        note = getIntent().getParcelableExtra("Note");
        if(null != note) {
            // set the title to editing
            this.setTitle(R.string.title_activity_edit_note);

            // set the values to edit
            setExistingFieldValues();
        } else {
            // set the title to creating
            this.setTitle(R.string.title_activity_create_note);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.btn_save) {
            save();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Set existing field values from the specified note.
     */
    private void setExistingFieldValues() {
        title.setText(note.getTitle());
        contents.setText(note.getContents());
    }

    /**
     * Save the entered values
     */
    private void save() {
        boolean isValid = true;

        final String titleText = title.getText().toString().trim();
        if(null == titleText || titleText.length() == 0) {
            inputLayoutTitle.setError(getString(R.string.error_title));
            isValid = false;
        }

        final String contentsText = contents.getText().toString().trim();
        if(null == contentsText || contentsText.length() == 0) {
            inputLayoutContents.setError(getString(R.string.error_contents));
            isValid = false;
        }

        if(isValid) {
            // valid, so save to db
            if(null != note) {
                note.setTitle(title.getText().toString());
                note.setContents(contents.getText().toString());
            } else {
                note = new Note(title.getText().toString(), contents.getText().toString());
            }

            // return the note
            Intent data = new Intent();
            data.putExtra("Note", note);
            setResult(RESULT_OK, data);

            // finish activity
            finish();
        }
    }
}
