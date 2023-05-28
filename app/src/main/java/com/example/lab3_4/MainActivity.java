package com.example.lab3_4;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private String name;
    private String size;
    private String tag;
    private Button buttonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonNext = findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSecondActivity();
            }
        });
    }

    public void openSecondActivity() {
        EditText editTextType = findViewById(R.id.editTextType);
        EditText editTextArea = findViewById(R.id.editTextArea);
        EditText editTextLocation = findViewById(R.id.editTextLocation);

        name = editTextType.getText().toString();
        size = editTextArea.getText().toString();
        tag = editTextLocation.getText().toString();

        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("type", name);
        intent.putExtra("area", size);
        intent.putExtra("location", tag);
        startActivity(intent);
    }
}