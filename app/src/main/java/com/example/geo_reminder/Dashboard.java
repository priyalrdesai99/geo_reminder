package com.example.geo_reminder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        String userId = getIntent().getStringExtra("KEY");

        String itemName = "Example Item"; // Replace with actual value
        String description = "Example Description"; // Replace with actual value

        // Create a ToDoItem object
        ToDoItem toDoItem = new ToDoItem(itemName, description);

        // Get a reference to the Firebase Realtime Database
        DatabaseReference toDoItemReference = FirebaseDatabase.getInstance().getReference("ToDoList");

        //String itemId = toDoItemReference.push().getKey();
        toDoItemReference.child(userId).setValue(toDoItem);

        Toast.makeText(this, "To-Do Item added successfully", Toast.LENGTH_SHORT).show();

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

}

