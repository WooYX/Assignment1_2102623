package edu.my.assignment1_2102623;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class LevelSelection extends AppCompatActivity {
    private ImageView level1Button;
    private ImageView level2Button;
    private ImageView level3Button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selection);

                // Find views by ID
                level1Button = findViewById(R.id.level_one);
                level2Button = findViewById(R.id.level_two);
                level3Button = findViewById(R.id.level_three);
                // Set up onClickListeners for each level button
                level1Button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startGame(0);
                    }
                });

                level2Button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startGame(1);
                    }
                });

                level3Button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startGame(2);
                    }
                });
            }

            public void startGame(int level) {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("level", level);
                startActivity(intent);
                finish();
            }

}