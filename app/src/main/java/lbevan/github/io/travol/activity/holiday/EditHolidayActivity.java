package lbevan.github.io.travol.activity.holiday;

import android.os.Bundle;
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
import lbevan.github.io.travol.domain.persistence.Database;
import lbevan.github.io.travol.domain.entity.Holiday;

public class EditHolidayActivity extends AppCompatActivity implements DatePickerFragment.OnDateSetListener {

    private Holiday holiday;

    private EditText title;
    private EditText startDate;
    private Button startDateClearButton;
    private EditText endDate;
    private Button endDateClearButton;
    private EditText notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_holiday);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // bind the view components
        title = findViewById(R.id.text_title);
        startDate = findViewById(R.id.text_start_date);
        startDateClearButton = findViewById(R.id.btn_clear_start_date);
        endDate = findViewById(R.id.text_end_date);
        endDateClearButton = findViewById(R.id.btn_clear_end_date);
        notes = findViewById(R.id.text_notes);

        // if the holiday is passed in, we are editing
        holiday = getIntent().getParcelableExtra("holiday");
        if(null != holiday) {
            setExistingFieldValues();
        }

        // set hints for fields
        title.setHint(R.string.hint_title);

        startDate.setHint(R.string.hint_start_date);
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

        endDate.setHint(R.string.hint_end_date);
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

        notes.setHint(R.string.hint_notes);
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
        title.setText(holiday.getName());

        if(holiday.getStartDate() != null) {
            startDate.setText(holiday.getStartDate().toString());
        }

        if(holiday.getEndDate() != null) {
            endDate.setText(holiday.getEndDate().toString());
        }

        notes.setText(holiday.getNotes());
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
            System.out.println("saving!");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.UK);
            Holiday holiday = null;
            try {
                holiday = new Holiday(title.getText().toString(), simpleDateFormat.parse(startDate.getText().toString()), simpleDateFormat.parse(endDate.getText().toString()), notes.getText().toString(), R.drawable.newyork);
            } catch(ParseException pe) {
                System.out.println("ParseException");
            }
            Database.getDatabase(getApplicationContext()).holidayDao().createHoliday(holiday);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
