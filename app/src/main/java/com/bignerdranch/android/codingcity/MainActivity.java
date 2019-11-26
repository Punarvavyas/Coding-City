package com.bignerdranch.android.codingcity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bignerdranch.android.codingcity.authentication.UserLogin;
import com.bignerdranch.android.codingcity.enrollment.SearchActivity;
import com.bignerdranch.android.codingcity.setting.SettingActivity;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    MenuItem loginOption;
    Menu mainMenu;
    DatabaseReference rootDatabase = FirebaseDatabase.getInstance().getReference();
    DatabaseReference myRef = rootDatabase.child("Courses");
    Button signInButton;
    private static final int RC_SIGN_IN = 123;
    FirebaseUser user;
    Button help;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginOption = findViewById(R.id.login);
        help = findViewById(R.id.button_help);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Help.class));
            }
        });
//        signInButton = findViewById(R.id.signin);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_leaderboard, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_top_right, menu);
//        mainMenu = menu;
//        loginOption = mainMenu.findItem(R.id.login);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Log.e("menu item", "" + item.getTitle());
        switch (item.getItemId()) {
            case R.id.search_item:
                Intent myIntent = new Intent(getBaseContext(), SearchActivity.class);
//              myIntent.putExtra("key", value); //Optional parameters
                startActivity(myIntent);
                return true;
            case R.id.setting:
                Intent intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                return true;
//            case R.id.login:
//                if(user == null) {
//                    List<AuthUI.IdpConfig> providers = Arrays.asList(
//                            new AuthUI.IdpConfig.EmailBuilder().build(),
//                            new AuthUI.IdpConfig.PhoneBuilder().build());
//// Create and launch sign-in intent
//                    startActivityForResult(
//                            AuthUI.getInstance()
//                                    .createSignInIntentBuilder()
//                                    .setIsSmartLockEnabled(false)
//                                    .setAvailableProviders(providers)
//                                    .build(),
//                            RC_SIGN_IN);
//                }
//                else {
////                    signInButton.setText("Sign In");
//                    AuthUI.getInstance()
//                            .signOut(MainActivity.this)
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    Toast.makeText(MainActivity.this, "Signed Out", Toast.LENGTH_LONG).show();
//                                }
//                            });
//                    loginOption.setTitle("Log in");
//                    user = null;
//                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Log.e("ds", postSnapshot.toString());// values fetched
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("main activity", "Failed to read value.", error.toException());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IdpResponse idpResponse = IdpResponse.fromResultIntent(data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                // Successfully signed in
                loginOption.setTitle("Log out");
                user = FirebaseAuth.getInstance().getCurrentUser();
                if(idpResponse.isNewUser()) {
                    rootDatabase.child("users").child(user.getUid()).child("courses").child("starter").setValue("");
                }
                UserLogin.getInstance(getApplicationContext()).setUser(user);
//                signInButton.setText("Sign Out");
            } else {
                Toast.makeText(this, "Sign in failed, plaease try again", Toast.LENGTH_LONG).show();
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
            }
        }
    }
}
