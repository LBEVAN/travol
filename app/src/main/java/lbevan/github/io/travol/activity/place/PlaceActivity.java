package lbevan.github.io.travol.activity.place;

import android.content.Intent;
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

import lbevan.github.io.travol.R;
import lbevan.github.io.travol.activity.main.MainActivity;
import lbevan.github.io.travol.domain.entity.Place;

/**
 * Activity for viewing and performing a set of actions on a specified {@link Place}
 *
 * Created by Luke on 26/02/2018.
 */
public class PlaceActivity extends AppCompatActivity {

    private Place place;

    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;

    private ImageView collapsingImageView;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        place = getIntent().getParcelableExtra("Place");

        setContentView(R.layout.activity_place);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        collapsingImageView = findViewById(R.id.img_collapsing);

        // todo: photo
//        List<Photo> photos = Database.getDatabase(getApplicationContext()).photoDao().getPhotosByHolidayId(place.getId());
//
//        if(photos == null || photos.size() == 0) {
            collapsingImageView.setImageDrawable(getResources().getDrawable(R.drawable.default_holiday_image));
//        } else {
//            final File file = FileSystemUtils.getPhotoFileByFileName(getApplicationContext(), photos.get(0).getFileName());
//            collapsingImageView.post(new Runnable() {
//                @Override
//                public void run() {
//                    new DecodeBitmapAsyncTask(file, collapsingImageView).execute();
//                }
//            });
//        }

        setTitle(place.getTitle());

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
                Intent intent = new Intent(view.getContext(), EditPlaceActivity.class);
                intent.putExtra("Place", place);
                view.getContext().startActivity(intent);
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
                    return MainActivity.PlaceholderFragment.newInstance(position + 1);
                case 2:
//                    return PhotoGalleryFragment.newInstance(Database.getDatabase(getApplicationContext()).photoDao().getPhotosByHolidayId(holiday.getId()));
                    return MainActivity.PlaceholderFragment.newInstance(position + 1);
                default:
                    return MainActivity.PlaceholderFragment.newInstance(position + 1);
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public void onAddPhotoToGallery(File imageFile) {
//        // create the photo and save it to the selected holiday
//        Photo photo = new Photo(holiday.getId(), imageFile.getName());
//        Database.getDatabase(getApplicationContext()).photoDao().createPhoto(photo);
//
//        // reload the photos and update the gallery
//        List<Photo> photos = Database.getDatabase(getApplicationContext()).photoDao().getPhotosByHolidayId(holiday.getId());
//        PhotoGalleryFragment photoGalleryFragment = (PhotoGalleryFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + viewPager.getCurrentItem());
//        photoGalleryFragment.updateGallery(photos);
//    }

//    /**
//     * {@inheritDoc}
//     *
//     * Save the note.
//     */
//    @Override
//    public Note onCreateNoteResult(Note note) {
//        note.setHolidayId(holiday.getId());
//        Long id = Database.getDatabase(this).noteDao().saveNote(note);
//        note.setId(id);
//        return note;
//    }

//    /**
//     * {@inheritDoc}
//     *
//     * Save the note.
//     */
//    @Override
//    public void onEditNoteResult(Note note) {
//        Database.getDatabase(this).noteDao().saveNote(note);
//    }
}