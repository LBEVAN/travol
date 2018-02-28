package lbevan.github.io.travol.activity.holiday;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.util.List;

import lbevan.github.io.travol.R;
import lbevan.github.io.travol.activity.main.MainActivity;
import lbevan.github.io.travol.component.notes.NotesFragment;
import lbevan.github.io.travol.component.photoGallery.PhotoGalleryFragment;
import lbevan.github.io.travol.domain.entity.HighlightPhoto;
import lbevan.github.io.travol.domain.entity.Holiday;
import lbevan.github.io.travol.domain.entity.Note;
import lbevan.github.io.travol.domain.entity.Photo;
import lbevan.github.io.travol.domain.persistence.Database;
import lbevan.github.io.travol.util.DecodeBitmapAsyncTask;
import lbevan.github.io.travol.util.FileSystemUtils;

public class HolidayActivity extends AppCompatActivity implements
        PhotoGalleryFragment.PhotoGalleryInteractionListener,
        HolidayDetailsFragment.OnFragmentInteractionListener,
        NotesFragment.OnFragmentInteractionListener{

    private static final int REQUEST_EDIT_HOLIDAY = 1;

    private Holiday holiday;

    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;

    private ImageView collapsingImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        holiday = getIntent().getParcelableExtra("holiday");

        setContentView(R.layout.activity_holiday);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        collapsingImageView = findViewById(R.id.img_collapsing);

        setCollapsingImage();

        setTitle(holiday.getTitle());

        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        viewPager = findViewById(R.id.container);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EditHolidayActivity.class);

                intent.putExtra("holiday", holiday);
                startActivityForResult(intent, REQUEST_EDIT_HOLIDAY);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            if(requestCode == REQUEST_EDIT_HOLIDAY) {
                holiday = data.getParcelableExtra("Holiday");
                Database.getDatabase(this).holidayDao().updateHoliday(holiday);
                viewPager.getAdapter().notifyDataSetChanged();
                setCollapsingImage();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) { }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch(position) {
                case 0:
                    return HolidayDetailsFragment.newInstance(holiday);
                case 2:
                    return PhotoGalleryFragment.newInstance(Database.getDatabase(getApplicationContext()).photoDao().getPhotosByHolidayId(holiday.getId()));
                default:
                    return MainActivity.PlaceholderFragment.newInstance(position + 1);
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAddPhotoToGallery(String imagePath) {
        // create the photo and save it to the selected holiday
        Photo photo = new Photo(holiday.getId(), imagePath);
        Database.getDatabase(getApplicationContext()).photoDao().createPhoto(photo);

        // todo: notifiysatetchanged?
        // reload the photos and update the gallery
        List<Photo> photos = Database.getDatabase(getApplicationContext()).photoDao().getPhotosByHolidayId(holiday.getId());
        PhotoGalleryFragment photoGalleryFragment = (PhotoGalleryFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + viewPager.getCurrentItem());
        photoGalleryFragment.updateGallery(photos);
    }

    /**
     * {@inheritDoc}
     *
     * Save the note.
     */
    @Override
    public Note onCreateNoteResult(Note note) {
        note.setHolidayId(holiday.getId());
        Long id = Database.getDatabase(this).noteDao().saveNote(note);
        note.setId(id);
        return note;
    }

    /**
     * {@inheritDoc}
     *
     * Save the note.
     */
    @Override
    public void onEditNoteResult(Note note) {
        Database.getDatabase(this).noteDao().saveNote(note);
    }

    private void setCollapsingImage() {
        final HighlightPhoto highlightPhoto = holiday.getHighlightPhoto();
        if(highlightPhoto == null || highlightPhoto.getPath().equals("")) {
            // set a default image
            collapsingImageView.setImageResource(R.drawable.default_holiday_image);
        } else {
            collapsingImageView.post(new Runnable() {
                @Override
                public void run() {
                    new DecodeBitmapAsyncTask(highlightPhoto.getPath(), collapsingImageView).execute();
                }
            });
        }
    }
}