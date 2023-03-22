package edu.my.assignment1_2102623;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //initialize variables
    private TableLayout tableLayout;
    private ImageView highlightedButton;
    private int level;
    private int numRows =1;
    private int numCols =1;
    private int score=0;
    public CountDownTimer timer;
    private static final String GAME_OVER_MESSAGE = "Game Over";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        level = intent.getIntExtra("level", 0);
        tableLayout = new TableLayout(this);//set up table layout
        tableLayout.setGravity(Gravity.CENTER);
        tableLayout.setBackgroundResource(R.drawable.cool);
        createButtons(); //create button for table
        highlightRandomButton();//Highlight a random button

        setContentView(tableLayout);  //set Table Layout
    }


//method to create button into table layout using for loop for row numbers and column numbers
private void createButtons() {
    for (int i = 0; i < numRows; i++) {
        TableRow tableRow = new TableRow(this);
        tableLayout.addView(tableRow);

        for (int j = 0; j < numCols+level; j++) {
            ImageView button = new ImageView(this);
            button.setBackgroundResource(R.drawable.ultramanicon);

            // Set the layout parameters to be square
            int size = getResources().getDisplayMetrics().widthPixels / numCols+level;
            button.setLayoutParams(new TableRow.LayoutParams(size, size));

            // Set up on click listener when user select highlighted view
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (view == highlightedButton) {
                        highlightedButton.setBackgroundResource(R.drawable.ultramanicon);
                        AddScore();
                        increaseTableSize();
                        highlightRandomButton();
                        startTimer();
                    }
                }
            });
            tableRow.addView(button);
        }
    }
}




    //method to randomly highlight a button inside table
    private void highlightRandomButton() {
        int randomRow = (int) (Math.random() * numRows);
        int randomCol = (int) (Math.random() * numCols);
        TableRow tableRow = (TableRow) tableLayout.getChildAt(randomRow);
        ImageView button = (ImageView) tableRow.getChildAt(randomCol);
        button.setBackgroundResource(R.drawable.enemy);
        highlightedButton = button;
    }

    //method to increase the table size
    private void increaseTableSize() {
        if (numRows >= level+7 && numCols >= level+7) {
            endGame(GAME_OVER_MESSAGE);
            return;
        }

        // Wait for timer to finish before increasing table size
        if (timer != null) {
            timer.cancel();
            timer = null;
            return;
        }

        numCols++;
        numRows++;

        tableLayout.removeAllViews();
        createButtons();
        startTimer();
    }

    //Timer method to set up count down for each level
    private void startTimer() {
        timer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                increaseTableSize();
            }
        }.start();
    }


    //method to show when game ends
    private void endGame(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        showNameDialog();
    }

    private void showNameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Your total score is " + score + "\n Please Enter your name:");

        EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = input.getText().toString();

                SharedPreferences preferences = getSharedPreferences("PREPS", 0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("YourScore", getScore());
                editor.putString("YourName", name);
                editor.apply();

                Intent intent = new Intent(getApplicationContext(), Highscore.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                finish(); // finish the activity when the user clicks cancel
            }
        });

        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                finish(); // finish the activity when the user cancels the dialog
            }
        });

        builder.show();
    }

    //Method to add score
    public void AddScore(){
        score+=1;
        setScore(score);
    }

    //method to update score
    public void setScore(int score) {
        this.score = score;
    }

    //method to get score
    public int getScore(){
        return score;
    }

}

