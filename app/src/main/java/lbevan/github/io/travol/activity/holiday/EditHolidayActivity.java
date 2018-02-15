package lbevan.github.io.travol.activity.holiday;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import lbevan.github.io.travol.R;
import lbevan.github.io.travol.component.datePicker.DatePickerFragment;

public class EditHolidayActivity extends AppCompatActivity implements DatePickerFragment.OnDateSetListener {

    private EditText title;
    private EditText startDate;
    private EditText endDate;
    private EditText notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_holiday);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // set hints for fields
        title = findViewById(R.id.text_title);
        title.setHint(R.string.hint_title);

        startDate = findViewById(R.id.text_start_date);
        startDate.setHint(R.string.hint_start_date);
        final int startDateFieldId = startDate.getId();
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(startDateFieldId);
            }
        });

        endDate = findViewById(R.id.text_end_date);
        endDate.setHint(R.string.hint_end_date);
        final int endDateFieldId = endDate.getId();
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(endDateFieldId);
            }
        });

        notes = findViewById(R.id.text_notes);
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
}
