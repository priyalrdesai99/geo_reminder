package com.example.geo_reminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
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
                dialog.setCancelable(true);

                get_item = dialog.findViewById(R.id.edt_item);
                get_desc = dialog.findViewById(R.id.edt_desc);
                add = dialog.findViewById(R.id.add_btn);

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String userId = getIntent().getStringExtra("KEY");

                        String itemName = get_item.getText().toString();
                        String description = get_desc.getText().toString();

                        // Create a ToDoItem object
                        ToDoItem toDoItem = new ToDoItem(itemName, description);

                        // Get a reference to the Firebase Realtime Database
                        DatabaseReference toDoItemReference = FirebaseDatabase.getInstance().getReference("ToDoList");

                        //String itemId = toDoItemReference.push().getKey();
                        toDoItemReference.child(userId).setValue(toDoItem);

                        Toast.makeText(DashboardActivity.this, itemName+" Successfully Added!!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        //        DatabaseReference itemStoreReference = FirebaseDatabase.getInstance().getReference("ItemStore");
//
//        ItemStore defaultItem1 = new ItemStore("bread", "Trader Joe's", 33.422580, -111.924900);
//        ItemStore defaultItem2 = new ItemStore("apple", "Safeway", 33.408986, -111.925494);
//        ItemStore defaultItem3 = new ItemStore("apple", "CVS", 33.414332, -111.925852);
//
//        String key1 = itemStoreReference.push().getKey();
//        itemStoreReference.child(key1).setValue(defaultItem1);
//
//        String key2 = itemStoreReference.push().getKey();
//        itemStoreReference.child(key2).setValue(defaultItem2);
//
//        String key3 = itemStoreReference.push().getKey();
//        itemStoreReference.child(key3).setValue(defaultItem3);
                    }
                });
                dialog.show();
            }
        });
    }
}