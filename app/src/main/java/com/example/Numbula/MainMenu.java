package com.example.Numbula;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;

// Icon Font is Sofadi One

public class MainMenu extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        ((TextView) findViewById(R.id.instructions)).setText( "Press a tile to decrease itself and all edge adjacent tiles by 1. Undo negative tiles."
                        +"\n"+"Get all tiles to 0 to clear the board.");
    }
}
