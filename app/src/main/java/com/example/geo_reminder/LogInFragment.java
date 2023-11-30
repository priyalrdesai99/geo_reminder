package com.example.geo_reminder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LogInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogInFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText EdtEmail;
    private EditText EdtPassword;
    private Button btnLogin;
    private TextView NewAccount, tv;
    private TextView forgetPassword;
    private SignUpFragment signUpFragment;
    private ForgetPasswordFragment forgetPasswordFragment;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    UserInfo userInfo;

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String FIRSTNAME = "firstName";
    public static final String LASTNAME = "lastName";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";
    public static final String PASSWORD = "password";
    public static final String ISLOGGEDIN = "isLoggedIn";
    public static final String NOTIFICATION = "notification";
    public static final String KEY = "key";
    SharedPreferences sharedpreferences;

    public LogInFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LogInFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LogInFragment newInstance(String param1, String param2) {
        LogInFragment fragment = new LogInFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);
        EdtEmail = view.findViewById(R.id.edt_email);
        EdtPassword = view.findViewById(R.id.edt_pass);
        btnLogin = view.findViewById(R.id.login_btn);
        NewAccount = view.findViewById(R.id.new_acc);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("UserInfo");
        forgetPassword = view.findViewById(R.id.forget_pass);
        userInfo = new UserInfo();


        NewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                signUpFragment = new SignUpFragment();
                transaction.replace(R.id.flMain, signUpFragment);
                transaction.commit();
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                forgetPasswordFragment = new ForgetPasswordFragment();
                transaction.replace(R.id.flMain, forgetPasswordFragment);
                transaction.commit();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(EdtEmail.getText().toString()) || TextUtils.isEmpty(EdtPassword.getText().toString())) {
                    //Toast.makeText(getActivity(), "Please fill all the fields!!", Toast.LENGTH_SHORT).show();
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Empty Field Detected")
                            .setMessage("Please fill all the fields.")
                            .show();
                } else {
                    Query query = databaseReference.orderByChild("email").equalTo(EdtEmail.getText().toString());
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
                                    String dbPassword = (String) messageSnapshot.child("password").getValue();
                                    if (dbPassword.equals(EdtPassword.getText().toString())) {

                                        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedpreferences.edit();
                                        editor.putString(KEY, (String) messageSnapshot.getKey());
                                        editor.putString(FIRSTNAME, (String) messageSnapshot.child("firstName").getValue());
                                        editor.putString(LASTNAME, (String) messageSnapshot.child("lastName").getValue());
                                        editor.putString(EMAIL, (String) messageSnapshot.child("email").getValue());
                                        editor.putString(PHONE, (String) messageSnapshot.child("phone").getValue());
                                        editor.putString(PASSWORD, dbPassword);
                                        editor.putString(ISLOGGEDIN, "true");
                                        editor.putString(NOTIFICATION, "false");
                                        editor.commit();
                                        Toast.makeText(getActivity(), "Login successful XD", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getActivity(), Dashboard.class);
                                        // intent.putExtra();
                                        startActivity(intent);
                                        getActivity().finish();
                                    }
                                    else
                                    {
                                        //Toast.makeText(getActivity(), "Incorrect Password", Toast.LENGTH_SHORT).show();
                                        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                                                .setIcon(android.R.drawable.ic_dialog_alert)
                                                .setTitle("Incorrect Password !!")
                                                //.setMessage("Password should consist of at least 8 letters containing Uppercase, Lowercase, Digits and Special Characters.")
                                                .show();
                                        //EdtPassword.setError("Incorrect password!!");


                                    }
                                }
                            } else {
                                //Toast.makeText(getActivity(), "Account doesn't exist!!", Toast.LENGTH_SHORT).show();
                                AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setTitle("Account Doesn't Exists !!")
                                        .setMessage("Enter verified Email.")
                                        .show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
        return view;
    }
}