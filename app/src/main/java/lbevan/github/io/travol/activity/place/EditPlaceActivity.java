package lbevan.github.io.travol.activity.place;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import lbevan.github.io.travol.R;
import lbevan.github.io.travol.component.datePicker.DatePickerFragment;
import lbevan.github.io.travol.domain.entity.Place;

public class EditPlaceActivity extends AppCompatActivity implements DatePickerFragment.OnDateSetListener {

    private Place place;

    private TextInputLayout inputLayoutTitle;
    private TextInputEditText title;

    private TextInputLayout inputLayoutLocation;
    private TextInputEditText location;

    private TextInputLayout inputLayoutDate;
    private TextInputEditText date;
    private Button dateClearButton;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_place);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // bind the view components
        title = findViewById(R.id.text_title);
        inputLayoutTitle = findViewById(R.id.input_layout_text_title);

        location = findViewById(R.id.text_location);
        inputLayoutLocation = findViewById(R.id.input_layout_text_location);

        date = findViewById(R.id.text_date);
        dateClearButton = findViewById(R.id.btn_clear_date);
        inputLayoutDate = findViewById(R.id.input_layout_text_date);

        // if the place is passed in, we are editing
        place = getIntent().getParcelableExtra("Place");
        if(null != place) {
            // set the title to editing
            this.setTitle(R.string.title_activity_edit_Place);

            // set the values to edit
            setExistingFieldValues();
        } else {
            // set the title to creating
            this.setTitle(R.string.title_activity_create_place);
        }

        final int dateFieldId = date.getId();
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(dateFieldId);
            }
        });

        dateClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date.setText("");
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day, int fieldId) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.UK);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);

        EditText field = findViewById(fieldId);
        field.setText(simpleDateFormat.format(cal.getTime()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_place, menu);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.btn_save) {
            process();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Display the date picker fragment, binding the specified field.
     *
     * @param fieldId id of the field to bind the result to
     */
    private void showDatePicker(int fieldId) {
        DialogFragment datePickerFragment = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putInt("fieldId", fieldId);
        datePickerFragment.setArguments(args);

        datePickerFragment.show(getSupportFragmentManager(), "datePicker");
    }

    /**
     * Set the existing input field values from the specified place object.
     */
    private void setExistingFieldValues() {
        title.setText(place.getTitle());

        location.setText(place.getLocation());

        if(place.getDate() != null) {
            date.setText(place.getDate().toString());
        }
    }

    /**
     * Process the entered values.
     * If they are valid, create/update the Place and finish.
     */
    private void process() {
        boolean isValid = true;

        final String titleText = title.getText().toString().trim();
        if(null == titleText || titleText.length() == 0) {
            inputLayoutTitle.setError(getString(R.string.error_title));
            isValid = false;
        }

        final String locationText = location.getText().toString().trim();
        if(null == locationText || locationText.length() == 0) {
            inputLayoutLocation.setError(getString(R.string.error_location));
            isValid = false;
        }

        final String dateText = date.getText().toString().trim();
        if(null == dateText || dateText.length() == 0) {
            inputLayoutDate.setError(getString(R.string.error_date));
            isValid = false;
        }

        if(isValid) {
            // valid, so create/update
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.UK);
            Place place = null;
            try {
                place = new Place(title.getText().toString(), location.getText().toString(), simpleDateFormat.parse(date.getText().toString()));
            } catch(ParseException pe) {
                System.out.println("ParseException");
            }

            // return the place
            Intent data = new Intent();
            data.putExtra("Place", place);
            setResult(RESULT_OK, data);

            // finish activity
            finish();
        }
    }
}
