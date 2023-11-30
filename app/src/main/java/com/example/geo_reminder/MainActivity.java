package com.example.geo_reminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText SourceText = findViewById(R.id.src);
        EditText DestinationText = findViewById(R.id.dest);
        Button button = findViewById(R.id.submitBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String src = SourceText.getText().toString().trim();
                String dest = DestinationText.getText().toString().trim();
                if(src.equals("") && dest.equals("")){
                    Toast.makeText(getApplicationContext(),"Enter Both Source and Destination",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(v.getContext(), MapsActivity.class);
                    intent.putExtra("SOURCE",src);
                    intent.putExtra("DESTINATION",dest);
                    startActivity(intent);
                }
            }
        });
    }
}