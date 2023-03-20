
package edu.my.assignment1_2102623;

public class Score {
    //initialize variable
    private int id;
    private String name;
    private int score;

    public Score() {}

    //setup score for retrieval
    public Score(String name, int score) {
        this.name = name;
        this.score = score;
    }
    //get id method
    public int getId() {
        return id;
    }
    //set id method
    public void setId(int id) {
        this.id = id;
    }
    //get name method
    public String getName() {
        return name;
    }
    //set name method
    public void setName(String name) {
        this.name = name;
    }
    //get score method
    public int getScore() {
        return score;
    }
    //set score method
    public void setScore(int score) {
        this.score = score;
    }
}
