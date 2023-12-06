package com.example.geo_reminder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForgetPasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForgetPasswordFragment extends Fragment {

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String FIRSTNAME = "firstName";
    public static final String LASTNAME = "lastName";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";
    public static final String ISLOGGEDIN = "isLoggedIn";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    SharedPreferences sharedpreferences;
    String dbEmail, dbPhone, dbFirstName, dbLastName;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView email, phone, otp;
    private Button sendOTP, verifyBtn;
    private FirebaseAuth mAuth;
    private String verify;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks

            // initializing our callbacks for on
            // verification callback method.
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        // below method is used when
        // OTP is sent from Firebase
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            // when we receive the OTP it
            // contains a unique id which
            // we are storing in our string
            // which we have already created.
            verify = s;
        }

        // this method is called when user
        // receive OTP from Firebase.
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            // below line is used for getting OTP code
            // which is sent in phone auth credentials.
            final String code = phoneAuthCredential.getSmsCode();

            // checking if the code
            // is null or not.
            if (code != null) {
                // if the code is not null then
                // we are setting that code to
                // our OTP edittext field.
                otp.setText(code);

                // after setting this code
                // to OTP edittext field we
                // are calling our verifycode method.
                verifyCode(code);
            }
        }

        // this method is called when firebase doesn't
        // sends our OTP code due to any error or issue.
        @Override
        public void onVerificationFailed(FirebaseException e) {
            // displaying error message with firebase exception.
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    public ForgetPasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ForgetPasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ForgetPasswordFragment newInstance(String param1, String param2) {
        ForgetPasswordFragment fragment = new ForgetPasswordFragment();
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
        View v = inflater.inflate(R.layout.fragment_forget_password, container, false);
        email = v.findViewById(R.id.forgetPasswordEmail);
        sendOTP = v.findViewById(R.id.sendOTP);
        phone = v.findViewById(R.id.forgetPasswordPhone);
        otp = v.findViewById(R.id.forgetPasswordOTP);
        verifyBtn = v.findViewById(R.id.verifyBtn);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("UserInfo");


        mAuth = FirebaseAuth.getInstance();

        sendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(email.getText().toString())) {
                    //Toast.makeText(getActivity(), "Please enter email!!", Toast.LENGTH_SHORT).show();
                    //email.setError("Please enter email!!");
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Empty Field !!")
                            .setMessage("Please enter email.")
                            .show();
                } else {
                    Query query = databaseReference.orderByChild("email").equalTo(email.getText().toString());
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
                                    dbEmail = (String) messageSnapshot.child("email").getValue();
                                    dbPhone = (String) messageSnapshot.child("phone").getValue();
                                    dbFirstName = (String) messageSnapshot.child("firstName").getValue();
                                    dbLastName = (String) messageSnapshot.child("lastName").getValue();


                                    if (dbEmail.equals(email.getText().toString()) && dbPhone.equals(phone.getText().toString())) {

                                        sendVerificationCode("+91" + dbPhone);


                                    } else if (!dbPhone.equals(phone.getText().toString())) {
                                        //Toast.makeText(getActivity(), "Phone number is not correct!!", Toast.LENGTH_SHORT).show();
                                        //phone.setError("Phone number is not correct!!");
                                        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                                                .setIcon(android.R.drawable.ic_dialog_alert)
                                                .setTitle("Incorrect Phone !!")
                                                .setMessage("Please enter a registered phone.")
                                                .show();
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

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(otp.getText().toString())) {
                    // if the OTP text field is empty display
                    // a message to user to enter OTP
                    //Toast.makeText(getActivity(), "Please enter OTP", Toast.LENGTH_SHORT).show();
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("OTP !!")
                            .setMessage("Please enter it.")
                            .show();
                } else {
                    // if OTP field is not empty calling
                    // method to verify the OTP.
                    verifyCode(otp.getText().toString());
                }
            }
        });

        return v;
    }

    private void sendVerificationCode(String number) {
        // this method is used for getting
        // OTP on user phone number.
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)            // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(getActivity())                 // Activity (for callback binding)
                        .setCallbacks(mCallBack)           // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    // below method is use to verify code from Firebase.
    private void verifyCode(String code) {
        // below line is used for getting
        // credentials from our verification id and code.
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verify, code);

        // after getting credential we are
        // calling sign in method.
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        // inside this method we are checking if
        // the code entered is correct or not.
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // if the code is correct and the task is successful
                            // we are sending our user to new activity.
                            saveData();
                            Intent i = new Intent(getActivity(), DashboardActivity.class);
                            startActivity(i);

                        } else {
                            // if the code is not correct then we are
                            // displaying an error message to the user.
                            Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void saveData() {
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(FIRSTNAME, dbFirstName);
        editor.putString(LASTNAME, dbLastName);
        editor.putString(EMAIL, dbEmail);
        editor.putString(PHONE, dbPhone);
        editor.putString(ISLOGGEDIN, "true");
        editor.commit();
    }
}