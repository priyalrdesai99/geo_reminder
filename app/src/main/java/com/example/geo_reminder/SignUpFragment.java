package com.example.geo_reminder;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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

import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class SignUpFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private TextView login;
    private FragmentTransaction transaction;
    LogInFragment lif;
    private EditText fName, lName, phone, email, password, confirmPassword;
    private Button createAccountBtn;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    UserInfo userInfo;

    public SignUpFragment() {
    }
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
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
        View v = inflater.inflate(R.layout.fragment_sign_up, container, false);
        fName = v.findViewById(R.id.firstName);
        lName = v.findViewById(R.id.lastName);
        email = v.findViewById(R.id.email);
        phone = v.findViewById(R.id.phone);
        password = v.findViewById(R.id.password);
        confirmPassword = v.findViewById(R.id.confirmPassword);
        createAccountBtn = v.findViewById(R.id.createAccountBtn);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("UserInfo");
        userInfo = new UserInfo();

        /**
         * Changes signup fragment with login fragment
         * */

        login = v.findViewById(R.id.logInBtn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction = getActivity().getSupportFragmentManager().beginTransaction();
                lif = new LogInFragment();
                transaction.replace(R.id.flMain, lif);
                transaction.commit();
            }
        });
        /**
         * saves the data to data base
         */
        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * email validation
                 */
                String regexEmail = "^[A-Za-z0-9+.]+@(.+)$";
                Pattern patternEmail = Pattern.compile(regexEmail);
                Matcher emailMatcher = patternEmail.matcher(email.getText().toString());

                /**
                 * phone number validation
                 */
                String regexPhone = "(0/91)?[7-9][0-9]{9}";
                Pattern patternPhone = Pattern.compile(regexPhone);
                Matcher phoneMatcher = patternPhone.matcher(phone.getText().toString());

                /**
                 * password validation
                 */
                String regexPassword = "^(?=.*[0-9])"
                        + "(?=.*[a-z])(?=.*[A-Z])"
                        + "(?=.*[@#$%^&+=])"
                        + "(?=\\S+$).{8,12}$";
                ;
                Pattern patternPassword = Pattern.compile(regexPassword);
                Matcher passwordMatcher = patternPassword.matcher(password.getText().toString());

                if (fName.getText().toString().length() == 0 || lName.getText().toString().length() == 0 || email.getText().toString().length() == 0 || phone.getText().toString().length() == 0 || password.getText().toString().length() == 0 || confirmPassword.getText().toString().length() == 0) {
                    //Toast.makeText(getActivity(), "Please fill all the fileds!!", Toast.LENGTH_SHORT).show();
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Empty Fields !!")
                            .setMessage("Please fill all the fields.")
                            .show();
                } else if (!emailMatcher.matches()) {
                    //Toast.makeText(getActivity(), "Email format is incorrect!!", Toast.LENGTH_SHORT).show();
                    //email.setError("Email format is incorrect!!");
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Email format is Incorrect !!")
                            .setMessage("Enter a valid email address.")
                            .show();
                } else if (!phoneMatcher.matches()) {
                    //Toast.makeText(getActivity(), "Phone number format is incorrect!!", Toast.LENGTH_SHORT).show();
                    //phone.setError("Phone number format is incorrect!!");
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Phone format is Incorrect !!")
                            .setMessage("Enter a valid phone number.")
                            .show();
                } else if (!passwordMatcher.matches()) {
                    //Toast.makeText(getActivity(), "Password format is incorrect!!", Toast.LENGTH_SHORT).show();
                    //password.setError("Password format is incorrect!!");
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Incorrect Password !!")
                            .setMessage("Password should consist of at least 8 letters containing Uppercase, Lowercase, Digits and Special Characters.")
                            .show();
                } else if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
                    //Toast.makeText(getActivity(), "confirm password doesn't match!!", Toast.LENGTH_SHORT).show();
                    //confirmPassword.setError("confirm password doesn't match!!");
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Incorrect Password !!")
                            .setMessage("Confirm Password doesn't match.")
                            .show();
                } else {
//                    Toast.makeText(getActivity(), "sending data.....", Toast.LENGTH_SHORT).show();
                    Query query = databaseReference.orderByChild("email").equalTo(email.getText().toString());
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
                                    //Toast.makeText(getActivity(),"Email is already registered!!" , Toast.LENGTH_SHORT).show();
                                    //email.setError("Email is already registered!!");
                                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .setTitle("Duplication !!")
                                            .setMessage("Email is already registered.")
                                            .show();
                                }
                            } else {
                                addDatatoFirebase(fName.getText().toString(), lName.getText().toString(), email.getText().toString(), phone.getText().toString(), password.getText().toString());
                                Toast.makeText(getActivity(), "Account created successfully!!", Toast.LENGTH_SHORT).show();
                                transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                lif = new LogInFragment();
                                transaction.replace(R.id.flMain, lif);
//                                transaction.addToBackStack(null);
                                transaction.commit();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
//                Toast.makeText(getActivity(), ".....", Toast.LENGTH_SHORT).show();
            }
        });


        return v;
    }
    private void addDatatoFirebase(String fName, String lName, String email, String phone, String password) {
        userInfo.setFirstName(fName);
        userInfo.setLastName(lName);
        userInfo.setEmail(email);
        userInfo.setPhone(phone);
        userInfo.setPassword(password);

        String uId = databaseReference.push().getKey();
        databaseReference.child(uId).setValue(userInfo);

    }
}