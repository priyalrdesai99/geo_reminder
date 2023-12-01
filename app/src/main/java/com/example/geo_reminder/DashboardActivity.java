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

                        String item = get_item.getText().toString();
                        String desc = get_desc.getText().toString();

                        Toast.makeText(DashboardActivity.this, item+" Successfully Added!!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }
}