package com.example.chat.ui.send;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.chat.R;
import com.example.chat.home;
import com.example.chat.store;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Long.decode;
import static java.lang.Long.parseLong;

public class SendFragment extends Fragment {

    private SendViewModel sendViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendViewModel =
                ViewModelProviders.of(this).get(SendViewModel.class);
        View root = inflater.inflate(R.layout.fragment_send, container, false);
        final EditText name = root.findViewById(R.id.name);
        final EditText dob = root.findViewById(R.id.dob);
        final EditText address = root.findViewById(R.id.address);
        final EditText preferences = root.findViewById(R.id.preferences);
        final EditText aadhar = root.findViewById(R.id.aadhar);
        final CalendarView calendarView = root.findViewById(R.id.calendarView);
        final Calendar calendar=Calendar.getInstance();
        calendarView.setOnDateChangeListener(
                        new CalendarView
                                .OnDateChangeListener() {
                            @Override

                            // In this Listener have one method
                            // and in this method we will
                            // get the value of DAYS, MONTH, YEARS
                            public void onSelectedDayChange(
                                    @NonNull CalendarView view,
                                    int year,
                                    int month,
                                    int dayOfMonth)
                            {

                                // Store the value of date with
                                // format in String type Variable
                                // Add 1 in month because month
                                // index is start with 0
                                String Date
                                        = dayOfMonth + "-"
                                        + (month + 1) + "-" + year;
                                // set this date in TextView for Display
                                dob.setText(Date);
                            }
                        });










        sendViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
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
                                name.setText(user.get("name").toString().trim());
                                address.setText( user.get("address").toString().trim());
                                preferences.setText(user.get("preferences").toString().trim());
                                aadhar.setText(user.get("aadhar").toString().trim());
                                String s=user.get("dob").toString().trim();
                                if(s!="") {
                                    int i = s.indexOf('-');
                                    int cdate = Integer.valueOf(s.substring(0, i));
                                    int j = s.indexOf('-', i + 1);
                                    int cmonth = Integer.valueOf(s.substring(i + 1, j));
                                    int cyear = Integer.valueOf(s.substring(j + 1));


                                    calendar.set(Calendar.MONTH, cmonth-1);
                                    calendar.set(Calendar.YEAR, cyear);
                                    calendar.set(Calendar.DATE, cdate);
                                    calendarView.setDate(calendar.getTimeInMillis(), false, false);
                                    dob.setText(cdate+"-"+cmonth+"-"+cyear);
                                }

                                //  Toast.makeText(home.this, string[0], Toast.LENGTH_SHORT).show();

                            } else {

                            }
                        } else {

                            Log.d("TAG", "get failed with ", task.getException());
                        }
                    }

                });


            }

        });
        aadhar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                // Insert char where needed.


                char space = ' ';
                if (s.length() > 0 && (s.length() % 5) == 0) {
                    final char c = s.charAt(s.length() - 1);
                    if (space == c) {
                        s.delete(s.length() - 1, s.length());
                    }
                }
                // Insert char where needed.
                if (s.length() > 0 && (s.length() % 5) == 0) {
                    char c = s.charAt(s.length() - 1);
                    // Only if its a digit where there should be a space we insert a space
                    if (Character.isDigit(c) && TextUtils.split(s.toString(), String.valueOf(space)).length <= 3) {
                        s.insert(s.length() - 1, String.valueOf(space));
                    }
                }
                if (s.length() > 0 && (s.length() >=15)) {
                    final char c = s.charAt(s.length() - 1);

                        s.delete(s.length() - 1, s.length());

                }
            }
            });

        root.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, Object> user = new HashMap<>();
                user.put("dob",dob.getText().toString().trim());
                user.put("name", name.getText().toString().trim());
                user.put("preferences", preferences.getText().toString().trim());
                user.put("address", address.getText().toString().trim());
                user.put("password",store.password1);
                user.put("aadhar", aadhar.getText().toString().trim());
// Add a new document with a generated ID
                db.collection("users").document(store.mobileno).set(user);

                Intent intent4 = new Intent(getActivity(), home.class);
                startActivity(intent4);
            }
        });
        return root;

    }
}