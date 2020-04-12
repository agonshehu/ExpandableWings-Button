package com.expandablewings;

import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ExpandableWings expandableWings = findViewById(R.id.expandableWings);
        expandableWings.setOnWingClickListener(new ExpandableWings.OnWingClick() {
            @Override
            public void onClick(ExpandableWings.Wings wings) {
                switch (wings) {
                    case RIGHT:
                        Toast.makeText(MainActivity.this, "Right", Toast.LENGTH_SHORT).show();
                        //expandableWings.toggleFab();
                        break;
                    case LEFT:
                        Toast.makeText(MainActivity.this, "Left", Toast.LENGTH_SHORT).show();
                        // expandableWings.collapseView();
                        break;
                }
            }
        });
    }
}