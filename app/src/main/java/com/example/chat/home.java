package com.example.chat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

public class home extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    String mobile;
    SharedPreferences prf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent00 = getIntent();
        mobile = intent00.getStringExtra("mobile");
       /* FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu menu=navigationView.getMenu();
        final MenuItem address=menu.findItem(R.id.nav_address);
        final MenuItem name=menu.findItem(R.id.nav_name);
        final MenuItem dob=menu.findItem(R.id.nav_dob);
        final MenuItem preferences=menu.findItem(R.id.nav_preferences);
        final MenuItem aadhar=menu.findItem(R.id.nav_aadhar);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                 R.id.nav_name, R.id.nav_address,R.id.nav_preferences,
                R.id.nav_dob, R.id.nav_send,R.id.nav_home,R.id.nav_aadhar)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Toast.makeText(this, store.mobileno,Toast.LENGTH_SHORT).show();
        DocumentReference docRef = db.collection("users").document(store.mobileno);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                        Map<String, Object> user = document.getData();
                        //  Toast.makeText(home.this,user.get("password").toString().trim(),Toast.LENGTH_SHORT).show();
                        name.setTitle("Name : "+user.get("name").toString().trim());
                        address.setTitle("Address : "+ user.get("address").toString().trim());
                        dob.setTitle("DOB : "+user.get("dob").toString().trim());
                        preferences.setTitle("Preferences : "+user.get("preferences").toString().trim());
                        aadhar.setTitle("Aadhar :"+user.get("aadhar").toString().trim());


                        //  Toast.makeText(home.this, string[0], Toast.LENGTH_SHORT).show();

                    } else {

                    }
                } else {

                    Log.d("TAG", "get failed with ", task.getException());
                }
            }

        });
        View v = navigationView.getHeaderView(0);
        TextView emailTextView = (TextView) v.findViewById(R.id.title);
        emailTextView.setText(store.mobileno);

    }
    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        prf = getSharedPreferences("user_details",MODE_PRIVATE);
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.action_settings:
                SharedPreferences.Editor editor = prf.edit();
                editor.clear();
                editor.commit();
                Intent intent=new Intent(home.this,MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
