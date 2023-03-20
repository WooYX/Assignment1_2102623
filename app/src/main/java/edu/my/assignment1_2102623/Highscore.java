package edu.my.assignment1_2102623;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import java.util.ArrayList;

import edu.my.assignment1_2102623.Score;

public class Highscore extends AppCompatActivity {

    //initialize variables
    private TextView tv_score;
    private int YourScore;
    private String YourName;
    private ArrayList<Score> scoreList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        tv_score = findViewById(R.id.tv_score);
        tv_score.setTextSize(20);
        //Load Score from shared preferences
        SharedPreferences preferences = getSharedPreferences("PREPS",0);
        YourScore = preferences.getInt("YourScore",0);
        YourName = preferences.getString("YourName","");

        //Add current score to database
        addScoreToDatabase(YourName, YourScore);

        //Retrieve scores from database and display
        getScoresFromDatabase();
        displayScores();
    }

    //method to trigger when back button was pressed
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }
    //method to load database and add score into it
    private void addScoreToDatabase(String name, int score) {
        SQLiteDatabase db = openOrCreateDatabase("HighScoreDB", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Scores(Name VARCHAR, Score INTEGER);");
        db.execSQL("INSERT INTO Scores(Name, Score) VALUES('"+name+"', "+score+");");

        //Retrieve scores from database and sort by descending score
        Cursor cursor = db.rawQuery("SELECT * FROM Scores ORDER BY Score DESC;", null);
        scoreList.clear();
        while (cursor.moveToNext()) {
            String playerName = cursor.getString(0);
            int playerScore = cursor.getInt(1);
            Score player = new Score(playerName, playerScore);
            scoreList.add(player);
        }

        //Limit the database size at 25
        if (scoreList.size() > 25) {
            int deleteCount = scoreList.size() - 25;
            for (int i = 0; i < deleteCount; i++) {
                Score scoreToDelete = scoreList.get(scoreList.size() - 1);
                db.execSQL("DELETE FROM Scores WHERE Name='"+scoreToDelete.getName()+"' AND Score="+scoreToDelete.getScore()+";");
                scoreList.remove(scoreToDelete);
            }
        }

        db.close();
    }
    //method to retrieve scores and names from database
    private void getScoresFromDatabase() {
        SQLiteDatabase db = openOrCreateDatabase("HighScoreDB", MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("SELECT * FROM Scores ORDER BY Score DESC;", null);
        scoreList.clear();
        while (cursor.moveToNext()) {
            String playerName = cursor.getString(0);
            int playerScore = cursor.getInt(1);
            Score player = new Score(playerName, playerScore);
            scoreList.add(player);
        }
        db.close();
    }

    //method to display retrieved data
    private void displayScores() {
        StringBuilder sb = new StringBuilder();
        sb.append("High Scores:\n\n");
        for (int i = 0; i < scoreList.size(); i++) {
            Score score = scoreList.get(i);
            sb.append(i+1).append(". ").append(score.getName()).append(": ").append(score.getScore()).append("\n");
        }
        tv_score.setText(sb.toString());
    }
}

