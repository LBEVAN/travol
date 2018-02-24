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
import lbevan.github.io.travol.domain.entity.Holiday;
import lbevan.github.io.travol.domain.entity.Note;
import lbevan.github.io.travol.domain.entity.Photo;
import lbevan.github.io.travol.domain.persistence.Database;
import lbevan.github.io.travol.util.DecodeBitmapAsyncTask;
import lbevan.github.io.travol.util.FileSystemUtils;

public class HolidayActivity extends AppCompatActivity implements
        PhotoGalleryFragment.PhotoGalleryInteractionListener,
        HolidayDetailsFragment.OnFragmentInteractionListener,
        NotesFragment.OnListFragmentInteractionListener {

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

        List<Photo> photos = Database.getDatabase(getApplicationContext()).photoDao().getPhotosByHolidayId(holiday.getId());

        if(photos == null || photos.size() == 0) {
            collapsingImageView.setImageDrawable(getResources().getDrawable(R.drawable.default_holiday_image));
        } else {
            final File file = FileSystemUtils.getPhotoFileByFileName(getApplicationContext(), photos.get(0).getFileName());
            collapsingImageView.post(new Runnable() {
                @Override
                public void run() {
                    new DecodeBitmapAsyncTask(file, collapsingImageView).execute();
                }
            });
        }

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

    @Override
    public void onFragmentInteraction(Uri uri) { }

    @Override
    public void onListFragmentInteraction(Note note) {

    }

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
    public void onAddPhotoToGallery(File imageFile) {
        // create the photo and save it to the selected holiday
        Photo photo = new Photo(holiday.getId(), imageFile.getName());
        Database.getDatabase(getApplicationContext()).photoDao().createPhoto(photo);

        // reload the photos and update the gallery
        List<Photo> photos = Database.getDatabase(getApplicationContext()).photoDao().getPhotosByHolidayId(holiday.getId());
        PhotoGalleryFragment photoGalleryFragment = (PhotoGalleryFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + viewPager.getCurrentItem());
        photoGalleryFragment.updateGallery(photos);
    }
}