package com.example.geo_reminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DashboardActivity extends AppCompatActivity {

    AppCompatButton add_rem,add;
    EditText get_item,get_desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        add_rem = findViewById(R.id.add_reminder);
        Dialog dialog = new Dialog(DashboardActivity.this);

        add_rem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("1111");
                dialog.setContentView(R.layout.custom_dialog);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);

                Spinner spinner = (Spinner) dialog.findViewById(R.id.category_spinner);
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(DashboardActivity.this,R.array.categories, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

                get_item = dialog.findViewById(R.id.edt_item);
                get_desc = dialog.findViewById(R.id.edt_desc);
                add = dialog.findViewById(R.id.add_btn);

                //String selectedSpinnerItem = spinner.getSelectedItem().toString();
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String userId = getIntent().getStringExtra("KEY");

                        String itemName = get_item.getText().toString();
                        String description = get_desc.getText().toString();
                        String category = spinner.getSelectedItem().toString();

                        // Create a ToDoItem object
                        ToDoItem toDoItem = new ToDoItem(itemName, description, category);


                        // Get a reference to the Firebase Realtime Database
                        DatabaseReference toDoItemReference = FirebaseDatabase.getInstance().getReference("ToDoList").child(userId);

                        String itemId = toDoItemReference.push().getKey();
                        toDoItemReference.child(itemId).setValue(toDoItem);

                        Toast.makeText(DashboardActivity.this, " Successfully Added!!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }
                });
                dialog.show();
            }
        });
    }
}