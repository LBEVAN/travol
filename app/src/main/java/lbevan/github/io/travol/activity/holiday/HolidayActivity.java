package lbevan.github.io.travol.activity.holiday;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import lbevan.github.io.travol.R;
import lbevan.github.io.travol.domain.entity.Holiday;

public class HolidayActivity extends AppCompatActivity {

    private Holiday holiday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        holiday = getIntent().getParcelableExtra("holiday");

        setContentView(R.layout.activity_holiday);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle(holiday.getName());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EditHolidayActivity.class);

                intent.putExtra("holiday", new Holiday(holiday));
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}