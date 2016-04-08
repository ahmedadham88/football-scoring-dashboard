package com.example.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * This class defines a Team that has the following attributes: Name, Score and Goal
 *
 * @author Ahmed Abouzeid
 *
 */
public class Team {

    private String name;

    private Integer score;

    private List<Goal> goals;

    public Team(String name) {
        this.name = name;
        this.score = 0;
        this.goals = new ArrayList<Goal>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public List<Goal> getGoals() {
        return goals;
    }

    public void setGoals(List<Goal> goals) {
        this.goals = goals;
    }

    /**
     * This method add goals to the ArrayList of goals of the team and increments the score by one
     *
     * @param goal The Goal object that contains the name of the scorer and the minute
     */
    public void addGoal(Goal goal) {
        this.goals.add(goal);
        this.score++;
        Collections.sort(this.goals, Comparator.comparing(Goal::getMinute));
    }
}
