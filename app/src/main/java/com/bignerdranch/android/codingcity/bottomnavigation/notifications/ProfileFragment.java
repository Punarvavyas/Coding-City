package com.bignerdranch.android.codingcity.bottomnavigation.notifications;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bignerdranch.android.codingcity.MainActivity;
import com.bignerdranch.android.codingcity.R;
import com.bignerdranch.android.codingcity.UserLogin;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseUser;
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
import java.util.Calendar;
import java.util.List;
import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.firebase.ui.auth.AuthUI.TAG;
import static com.firebase.ui.auth.AuthUI.getApplicationContext;
import static com.firebase.ui.auth.AuthUI.getInstance;

public class ProfileFragment extends Fragment {

  //  private ProfileViewModel profileViewModel;
    private ImageView user_image;
    private ImageView edit_image;
    private TextView  user_Name;
    private TextView Email;
    private Button editProfile;
//    private Button scoreboard;
    private Button removeCourse;
    private EditText editedName;
    private EditText editedEmail;
    private Button saveProfile;
    private String Name;
    private String emailId;
    private Button Delete_acc;
  private int contentView;
//  private static final String TAG = MainActivity.class.getSimpleName();
//  public static final int REQUEST_IMAGE = 100;
  //set image directory path as firebase path..
  private static final String IMAGE_DIRECTORY = "/demonuts";
  private int GALLERY = 1, CAMERA = 2;




  public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
    //    profileViewModel =
    //            ViewModelProviders.of(this).get(ProfileViewModel.class);
      View root = inflater.inflate(R.layout.fragment_profile, container, false);
     //   final TextView textView = root.findViewById(R.id.text_profile);
     //   profileViewModel.getText().observe(this, new Observer<String>() {
     //       @Override
       //     public void onChanged(@Nullable String s) {
         //       textView.setText(s);
         //       editProfile = root.findViewById(R.id.btn_editProfile);
        //    }
       // });
        user_image=root.findViewById(R.id.img_profile);
        edit_image=root.findViewById(R.id.img_plus);
        user_Name= root.findViewById(R.id.tvName);
        Email=root.findViewById(R.id.tvEmail);
//        scoreboard = root.findViewById(R.id.btn_scoreboard);
        removeCourse=root.findViewById(R.id.btn_removeCourse);
        editProfile=root.findViewById(R.id.btn_editProfile);
        editedName=root.findViewById(R.id.etName);
        editedEmail=root.findViewById(R.id.etEmail);
        saveProfile=root.findViewById(R.id.btn_saveProfile);
        Delete_acc=root.findViewById(R.id.btn_deleteAccount);
    // created firebase instance
    UserLogin mInstance = UserLogin.getInstance(getContext());
       //get the current user from firebase
        final FirebaseUser CurrentUser = mInstance.getUser();
      //to print user name from firebase
    if(CurrentUser==null)
    {
//put default values n give sign in button
//      user_image.setImageURI(Uri.parse("https://picsum.photos/id/975/200/200"));
     user_image.setImageURI(Uri.parse("@drawable/baseline_account_circle_black_48"));

      user_Name.setText("Not Signed");
      Email.setText("coding city");
    }
    else {
//      editedName.setVisibility(View.GONE);
//      editedEmail.setVisibility(View.GONE);
      user_image.setImageURI(CurrentUser.getPhotoUrl());
      if(CurrentUser.getPhotoUrl()== null)
      {
        user_image.setImageURI(Uri.parse("@drawable/baseline_account_circle_black_48"));
      }
//      ImageView imageView = findViewById(R.id.imageView2);
//      String imageUrl = "https://via.placeholder.com/500";

      //Loading image using Picasso
      Picasso.get().load(CurrentUser.getPhotoUrl()).into(user_image);
      user_Name.setText(CurrentUser.getDisplayName());
      Email.setText(CurrentUser.getEmail());


//      scoreboard.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//          Intent intent = new Intent(getContext(), ScoreTableActivity.class);
//          startActivity(intent);
//          //setContentView(R.layout.fragment_score);
//        }
//      });
      editProfile.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          user_Name.setVisibility(View.GONE);
          Email.setVisibility(View.GONE);
          editedName.setVisibility(View.VISIBLE);
          editedEmail.setVisibility(View.VISIBLE);
          String Name=editedName.getText().toString();
          String emailId=editedEmail.getText().toString();

//          .setVisibility(View.VISIBLE);
//          editText.setText(edititemname);
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
          editedName.setVisibility(View.GONE);
          editedEmail.setVisibility(View.GONE);
          user_Name.setVisibility(View.VISIBLE);
          Email.setVisibility(View.VISIBLE);
          user_Name.setText(Name);
          Email.setText(emailId);


        }
      });
      removeCourse.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent i = new Intent(getActivity(), RemoveCourses.class);
            startActivity(i);
            ((Activity) getActivity()).overridePendingTransition(0, 0);


          }
      });
      //check the code again
      Delete_acc.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          CurrentUser.delete();
        }
      });
    }

        return root;

       // return inflater.inflate(R.layout.fragment_profile, container, false);
    }
  private void showPictureDialog(){
    AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getTargetFragment().getActivity());
    pictureDialog.setTitle("Select Action");
    String[] pictureDialogItems = {
            "Select photo from gallery",
            "Capture photo from camera" };
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
          Bitmap bitmap = MediaStore.Images.Media.getBitmap(getTargetFragment().getActivity().getContentResolver(), contentURI);
          String path = saveImage(bitmap);
          Toast.makeText(getTargetFragment().getActivity(), "Image Saved!", Toast.LENGTH_SHORT).show();
          edit_image.setImageBitmap(bitmap);

        } catch (IOException e) {
          e.printStackTrace();
          Toast.makeText(getTargetFragment().getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
        }
      }

    } else if (requestCode == CAMERA) {
      Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
      edit_image.setImageBitmap(thumbnail);
      saveImage(thumbnail);
      Toast.makeText(getTargetFragment().getActivity(), "Image Saved!", Toast.LENGTH_SHORT).show();
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
      MediaScannerConnection.scanFile(getTargetFragment().getActivity(),
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

  private void  requestMultiplePermissions(){
    Dexter.withActivity(getActivity())
            .withPermissions(
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(new MultiplePermissionsListener() {
              @Override
              public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (report.areAllPermissionsGranted()) {
                  Toast.makeText(getTargetFragment().getActivity(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getTargetFragment().getActivity(), "Some Error! ", Toast.LENGTH_SHORT).show();
              }
            })
            .onSameThread()
            .check();
  }

}