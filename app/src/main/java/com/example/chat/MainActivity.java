package com.example.chat;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText mobile;
    Button submit1;
    TextView signup;
    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        submit1= findViewById(R.id.submit);
        this.signup= findViewById(R.id.signup);
        super.onCreate(savedInstanceState);
        pref = getSharedPreferences("user_details",MODE_PRIVATE);
        if(pref.contains("mobile") && pref.contains("password")){
            store.mobileno=pref.getString("mobile","");
            store.password1=pref.getString("password","");

            Intent intent2=new Intent(MainActivity.this,home.class);
            intent2.putExtra("mobile",pref.getString("mobile",""));
            startActivity(intent2);
        }

        setContentView(R.layout.activity_main);
        findViewById(R.id.signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,otp.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.forgot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Forgot_password.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                final EditText mobile1=findViewById(R.id.mobile);
                final EditText password=findViewById(R.id.password);
                if(mobile1.getText().toString().trim().isEmpty() || mobile1.getText().toString().trim().length() !=10){
                    mobile1.setError("Enter valid mobile number");
                    mobile1.requestFocus();
                    return;
                }
                if(password.getText().toString().trim().isEmpty()){
                    password.setError("Enter correct password");
                    password.requestFocus();
                    return;
                }
                DocumentReference docRef = db.collection("users").document(mobile1.getText().toString().trim());
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                               Map<String, Object> user = document.getData();
                                if(user.get("password").equals(password.getText().toString().trim())){

                                    store.mobileno=mobile1.getText().toString().trim();
                                    store.password1=password.getText().toString().trim();
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putString("mobile",mobile1.getText().toString().trim());
                                    editor.putString("password",password.getText().toString().trim());
                                    editor.commit();
                                    //Toast.makeText(MainActivity.this,store.mobileno,Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(MainActivity.this,home.class);
                                    intent.putExtra("mobile",mobile1.getText().toString().trim());
                                    startActivity(intent);
                                }
                                else{
                                    password.setError("the password is incorrect try forgot password");
                                    password.requestFocus();
                                    return;
                                }
                            } else {
                                mobile1.setError("user dosent exist please sign up");
                                mobile1.requestFocus();
                                return;
                            }
                        } else {
                            Log.d("TAG", "get failed with ", task.getException());
                        }
                    }
                });


            }
        });
    }


    // testing master push

}
