package edu.my.assignment1_2102623;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //initialize variables
    private TableLayout tableLayout;
    private Button highlightedButton;
    private int numRows = 2;
    private int numCols = 2;
    private int score=0;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        tableLayout = new TableLayout(this); //set up table layout
        createButtons(); //create button for table
        highlightRandomButton();//Highlight a random button
        setContentView(tableLayout);  //set Table Layout
    }

    //method to create button into table layout using for loop for row numbers and column numbers
    private void createButtons() {
        for (int i = 0; i < numRows; i++) {
            TableRow tableRow = new TableRow(this);
            tableLayout.addView(tableRow);

            for (int j = 0; j < numCols; j++) {
                Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));

                //Set up on click listener when user select highlighted view
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (view == highlightedButton) {
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
        Button button = (Button) tableRow.getChildAt(randomCol);
        button.setBackgroundColor(Color.YELLOW);
        highlightedButton = button;
    }

    //method to increase the table size
    private void increaseTableSize() {
        if (numRows >= 6 && numCols >= 6) {
            endGame();
            return;
        }

        else {
            numCols++;
            numRows++;
        }

        tableLayout.removeAllViews();
        createButtons();
    }

    //method to show when game ends
    private void endGame() {

        Toast.makeText(this, "Game Over", Toast.LENGTH_SHORT).show();
        showNameDialog();
    }

    //Timer method to set up count down for each level
    private void startTimer() {
        timer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                endGame();
            }
        }.start();
    }

    //method to ask user enter his name
    private void showNameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Your total score is "+score+"\n Please Enter your name:");

        final EditText input = new EditText(this);
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

