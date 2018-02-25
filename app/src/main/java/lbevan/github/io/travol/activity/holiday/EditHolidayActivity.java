package lbevan.github.io.travol.activity.holiday;

import android.os.Bundle;
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
import lbevan.github.io.travol.domain.entity.Holiday;
import lbevan.github.io.travol.domain.persistence.Database;

public class EditHolidayActivity extends AppCompatActivity implements DatePickerFragment.OnDateSetListener {

    private Holiday holiday;

    private EditText title;
    private TextInputLayout inputLayoutTitle;

    private EditText location;
    private TextInputLayout inputLayoutLocation;

    private EditText startDate;
    private Button startDateClearButton;
    private TextInputLayout inputLayoutStartDate;

    private EditText endDate;
    private Button endDateClearButton;
    private TextInputLayout inputLayoutEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_holiday);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // bind the view components
        title = findViewById(R.id.text_title);
        inputLayoutTitle = findViewById(R.id.input_layout_text_title);

        location = findViewById(R.id.text_location);
        inputLayoutLocation = findViewById(R.id.input_layout_text_location);

        startDate = findViewById(R.id.text_start_date);
        startDateClearButton = findViewById(R.id.btn_clear_start_date);
        inputLayoutStartDate = findViewById(R.id.input_layout_text_start_date);

        endDate = findViewById(R.id.text_end_date);
        endDateClearButton = findViewById(R.id.btn_clear_end_date);
        inputLayoutEndDate = findViewById(R.id.input_layout_text_end_date);

        // if the holiday is passed in, we are editing
        holiday = getIntent().getParcelableExtra("holiday");
        if(null != holiday) {
            // set the title to editing
            this.setTitle(R.string.title_activity_edit_holiday);

            // set the values to edit
            setExistingFieldValues();
        } else {
            // set the title to creating
            this.setTitle(R.string.title_activity_create_holiday);
        }

        final int startDateFieldId = startDate.getId();
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(startDateFieldId);
            }
        });

        startDateClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDate.setText("");
            }
        });

        final int endDateFieldId = endDate.getId();
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(endDateFieldId);
            }
        });

        endDateClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endDate.setText("");
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void showDatePicker(int fieldId) {
        DialogFragment datePickerFragment = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putInt("fieldId", fieldId);
        datePickerFragment.setArguments(args);

        datePickerFragment.show(getSupportFragmentManager(), "datePicker");
    }

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

    private void setExistingFieldValues() {
        title.setText(holiday.getTitle());

        location.setText(holiday.getLocation());

        if(holiday.getStartDate() != null) {
            startDate.setText(holiday.getStartDate().toString());
        }

        if(holiday.getEndDate() != null) {
            endDate.setText(holiday.getEndDate().toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_holiday, menu);
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

    private void save() {
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

        final String startDateText = startDate.getText().toString().trim();
        if(null == startDateText || startDateText.length() == 0) {
            inputLayoutStartDate.setError(getString(R.string.error_start_date));
            isValid = false;
        }

        final String endDateText = endDate.getText().toString().trim();
        if(null == endDateText || endDateText.length() == 0) {
            inputLayoutEndDate.setError(getString(R.string.error_end_date));
            isValid = false;
        }

        if(isValid) {
            // valid, so save to db
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.UK);
            Holiday holiday = null;
            try {
                holiday = new Holiday(title.getText().toString(), location.getText().toString(), simpleDateFormat.parse(startDate.getText().toString()), simpleDateFormat.parse(endDate.getText().toString()));
            } catch(ParseException pe) {
                System.out.println("ParseException");
            }
            Database.getDatabase(getApplicationContext()).holidayDao().createHoliday(holiday);
            finish();
        }
    }
}
