package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initFirebase();
        initView();
    }

    private void initFirebase() {
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("your_node_name");
    }

    private void initView() {
        Button button = findViewById(R.id.reg_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
                finish();
            }
        });
    }

    private void register() {
        String title = ((EditText) findViewById(R.id.title_et)).getText().toString();
        String contents = ((EditText) findViewById(R.id.content_et)).getText().toString();

        SingerItem newItem = new SingerItem(title, contents, -1);
        databaseReference.child("items").push().setValue(newItem);
    }
}
