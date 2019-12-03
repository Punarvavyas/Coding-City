package com.bignerdranch.android.codingcity.bottomnavigation.notifications;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.alespero.expandablecardview.ExpandableCardView;
import com.bignerdranch.android.codingcity.R;
import com.bignerdranch.android.codingcity.authentication.LoginActivity;
import com.bignerdranch.android.codingcity.authentication.SignUpActivity;
import com.bignerdranch.android.codingcity.enrollment.CourseEnrollmentActivity;
import com.bignerdranch.android.codingcity.setting.SettingActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;

public class ProfileFragment extends Fragment {
    //set image directory path as firebase path..
    private static final String IMAGE_DIRECTORY = "/sample_images";
    final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference rootDatabase = FirebaseDatabase.getInstance().getReference();
    final DatabaseReference myRef = rootDatabase.child("users");
    MaterialButton settings;
    private ImageView user_image;
    private ImageView edit_image;
    private TextView user_Name;
    private TextView Email;
    private EditText editedName;
    private EditText editedEmail;
    MaterialButton editProfile;
    //    private Button scoreboard;
//    private Button removeCourse;
    MaterialButton saveProfile;
    private String Name;
    private String emailId;
    private Button Delete_acc;
    private int contentView;
    private int GALLERY = 1, CAMERA = 2;
    ExpandableCardView card;
    ListView expandList;
    Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        card = root.findViewById(R.id.profile_expand);
        user_image = root.findViewById(R.id.profile_bundle);
        edit_image = root.findViewById(R.id.img_plus);
        user_Name = root.findViewById(R.id.tvName);
        Email = root.findViewById(R.id.tvEmail);
        settings = root.findViewById(R.id.btn_settings);
        editProfile = root.findViewById(R.id.btn_editProfile);
        editedName = root.findViewById(R.id.etName);
        editedEmail = root.findViewById(R.id.etEmail);
        saveProfile = root.findViewById(R.id.btn_saveProfile);
        Delete_acc = root.findViewById(R.id.btn_deleteAccount);
        card = root.findViewById(R.id.profile_expand);
        expandList = root.findViewById(R.id.expand_listview);
        context = getActivity().getApplicationContext();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


        if (currentUser != null) {
//            user_image.setImageURI(currentUser.getPhotoUrl());
//            user_image.setImageResource(R.drawable.baseline_account_circle_black_48);
//            if (currentUser.getPhotoUrl() == null) {
//                user_image.setImageURI(Uri.parse("@drawable/baseline_account_circle_black_48"));
//            }

            //Loading image using Picasso
//            Picasso.get().load(currentUser.getPhotoUrl()).into(user_image);
            user_Name.setText(currentUser.getDisplayName());
            Email.setText(currentUser.getEmail());

            editProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editProfile.setVisibility(View.GONE);
                    saveProfile.setVisibility(View.VISIBLE);
                    editedName.setText(user_Name.getText());
                    user_Name.setVisibility(View.GONE);
                    editedEmail.setText(Email.getText());
                    Email.setVisibility(View.GONE);
                    editedName.setVisibility(View.VISIBLE);
                    editedEmail.setVisibility(View.VISIBLE);
                    card.setVisibility(View.GONE);
                }
            });
            edit_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    requestMultiplePermissions();
                    showPictureDialog();
                }
            });

            saveProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    saveProfile.setVisibility(View.GONE);
                    editProfile.setVisibility(View.VISIBLE);
                    user_Name.setVisibility(View.VISIBLE);
                    user_Name.setText(editedName.getText());
                    Email.setVisibility(View.VISIBLE);
                    Email.setText(editedEmail.getText());
                    editedName.setVisibility(View.GONE);
                    editedEmail.setVisibility(View.GONE);
                    card.setVisibility(View.VISIBLE);
                    myRef.child(currentUser.getUid()).child("name").setValue(user_Name.getText().toString());
                    myRef.child(currentUser.getUid()).child("email").setValue(Email.getText().toString());

                }
            });
//            removeCourse.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    Intent i = new Intent(getActivity(), RemoveCourses.class);
//                    startActivity(i);
//
//                }
//            });
            Delete_acc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myRef.child(currentUser.getUid()).removeValue();
                    currentUser.delete();
                    Intent toSignUp = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                    startActivity(toSignUp);
                }
            });
        }
        activateListeners();
        return root;

        // return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getActivity());
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select from gallery",
                "Capture from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);
                  String path = saveImage(bitmap);
                    Toast.makeText(getActivity(), "Image Saved!", Toast.LENGTH_SHORT).show();
                    user_image.setImageBitmap(bitmap);
                    myRef.child(currentUser.getUid()).child("profileImageUri").setValue(path);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            user_image.setImageBitmap(thumbnail);
            String path= saveImage(thumbnail);
            Toast.makeText(getActivity(), "Image Saved!", Toast.LENGTH_SHORT).show();
            myRef.child(currentUser.getUid()).child("profileImageUri").setValue(path);

        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(getActivity(),
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::---&gt;" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private void requestMultiplePermissions() {
        Dexter.withActivity(getActivity())
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getActivity(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getActivity(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    private void activateListeners() {
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SettingActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Email.setText(dataSnapshot.child(currentUser.getUid()).child("email").getValue().toString());
                user_Name.setText(dataSnapshot.child(currentUser.getUid()).child("name").getValue().toString());
                String image_path = dataSnapshot.child(currentUser.getUid()).child("profileImageUri").getValue().toString();
                Uri imageFilePath = Uri.parse(image_path);
                user_image.setImageURI(imageFilePath);
                ArrayList<String> x = new ArrayList<>();
                for (DataSnapshot y : dataSnapshot.child(FirebaseAuth.getInstance().
                        getCurrentUser().getUid()).child("courses").getChildren()) {
                   if(!y.getKey().equals("starter"))

                        x.add(y.getKey());
                }
                LessonAdapter ls = new LessonAdapter(context, x, x.size());
                expandList.setAdapter(ls);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }

    private class LessonAdapter extends BaseAdapter {
        Context context;
        ArrayList<String> courseList;
        LayoutInflater inflater;
        int listSize;

        LessonAdapter(Context context, ArrayList<String> courseList, int size) {
            this.context = context;
            this.courseList = courseList;
            this.listSize = size;
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        //How many items are in the data set represented by this Adapter.
        public int getCount() {
            return listSize;
        }

        @Override
        //Get the data item associated with the specified position in the data set.
        public Object getItem(int position) {
            return courseList.get(position);
        }

        @Override
        //Get the row id associated with the specified position in the list.
        public long getItemId(int position) {
            return 0; //courseList.get(position).getId();
        }

        @Override
        //Get a View that displays the data at the specified position in the data set.
        public View getView(final int position, View convertView, ViewGroup parent) {
            View v;
            if (convertView == null) {
                v = View.inflate(parent.getContext(), R.layout.remove_course_item, null);
            } else {
                v = convertView;
            }
            TextView tv = v.findViewById(R.id.tvLessonItem);
            tv.setText(courseList.get(position));
            ImageButton mt = v.findViewById(R.id.removeIcon);

            mt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myRef.child(currentUser.getUid()).child("courses").child(courseList.get(position)).removeValue();
                }
            });
            return v;
        }
    }
}