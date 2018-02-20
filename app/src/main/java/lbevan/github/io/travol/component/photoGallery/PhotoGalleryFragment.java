package lbevan.github.io.travol.component.photoGallery;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lbevan.github.io.travol.R;
import lbevan.github.io.travol.domain.entity.Photo;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PhotoGalleryFragment.PhotoGalleryInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PhotoGalleryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhotoGalleryFragment extends Fragment {

    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_CHOOSE_PHOTO = 2;

    private PhotoGalleryInteractionListener photoGalleryInteractionListener;

    private RecyclerView recyclerView;

    private List<Photo> photos;
    private String currentPhotoPath;

    public PhotoGalleryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param photos List of photos to display
     * @return A new instance of fragment PhotoGalleryFragment.
     */
    public static PhotoGalleryFragment newInstance(List<Photo> photos) {
        PhotoGalleryFragment fragment = new PhotoGalleryFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("PHOTOS", (ArrayList<? extends Parcelable>) photos);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            photos = getArguments().getParcelableArrayList("PHOTOS");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_photo_gallery_actions, menu);

        MenuItem takePhotoItem = menu.findItem(R.id.btn_take_photo);
        takePhotoItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                dispatchTakePhotoIntent();
                return true;
            }
        });

        MenuItem choosePhotoItem = menu.findItem(R.id.btn_choose_photo);
        choosePhotoItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                dispatchChoosePhotoIntent();
                return true;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photo_gallery, container, false);
        setHasOptionsMenu(true);

        recyclerView = view.findViewById(R.id.recycler_view);
        bindRecyclerView();

        return view;
    }

    private void bindRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(new PhotoGalleryAdapter(getContext(), photos));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PhotoGalleryInteractionListener) {
            photoGalleryInteractionListener = (PhotoGalleryInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAddPhotoToGalleryListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        photoGalleryInteractionListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        bindRecyclerView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            if(requestCode == REQUEST_CHOOSE_PHOTO) {
                if(data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();
                    int currentItem = 0;
                    while(currentItem < count) {
                        Uri imageUri = data.getClipData().getItemAt(currentItem).getUri();
                        File imageFile = copyPhotoFromUri(imageUri);
                        photoGalleryInteractionListener.onAddPhotoToGallery(imageFile);
                        currentItem = currentItem + 1;
                    }
                } else if(data.getData() != null) {
                    Uri imageUri = data.getData();
                    File imageFile = copyPhotoFromUri(imageUri);
                    photoGalleryInteractionListener.onAddPhotoToGallery(imageFile);
                }
            } else if(requestCode == REQUEST_TAKE_PHOTO) {
                File imageFile = new File(currentPhotoPath);
                photoGalleryInteractionListener.onAddPhotoToGallery(imageFile);
            }
        }

        if(resultCode == RESULT_CANCELED) {
            if(requestCode == REQUEST_TAKE_PHOTO) {
                // request photo action cancelled, so delete temp file
                File file = new File(currentPhotoPath);
                file.delete();
            }
        }
    }

    private File copyPhotoFromUri(Uri uri) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        File imageFile = null;
        try {
            inputStream = getContext().getContentResolver().openInputStream(uri);
            imageFile = createImageFile();
            outputStream = new FileOutputStream(imageFile);
            byte[] buf = new byte[1024];
            int len;
            while((len = inputStream.read(buf)) > 0){
                outputStream.write(buf, 0, len);
            }
        } catch (FileNotFoundException ex) {
            // Error occurred while creating the File
            System.out.println(ex);
        } catch (IOException ex) {
            // Error occurred while creating the File
            System.out.println(ex);
        } finally {
            try {
                if(outputStream != null) {
                    outputStream.close();
                }
                inputStream.close();
            } catch (IOException ex) {
                // Error occurred while creating the File
                System.out.println(ex);
            }
        }

        return imageFile;
    }

    private void dispatchChoosePhotoIntent() {
        Intent intent = new Intent();
        // Show only images, no videos or anything else
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CHOOSE_PHOTO);
    }

    private void dispatchTakePhotoIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                System.out.println(ex);
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoUri = FileProvider.getUriForFile(getContext(),
                        "lbevan.github.io.travol.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
//                galleryAddPic();
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    // TODO: this will add the picture to the gallery (only if made public)
    // TODO: see if we want to do this later on, currently just saving to app dir
//    private void galleryAddPic() {
//        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        File f = new File(currentPhotoPath);
//        Uri contentUri = Uri.fromFile(f);
//        mediaScanIntent.setData(contentUri);
//        getActivity().sendBroadcast(mediaScanIntent);
//    }

    public void updateGallery(List<Photo> photos) {
        this.photos = photos;
        bindRecyclerView();
    }

    /**
     * Interface defining callback operations to be
     * handled when interacting with the photo gallery.
     */
    public interface PhotoGalleryInteractionListener {

        /**
         * Callback method for when adding a photo to the gallery.
         *
         * @param photoFile the file of the photo added on the filesystem
         */
        void onAddPhotoToGallery(File photoFile);
    }
}
