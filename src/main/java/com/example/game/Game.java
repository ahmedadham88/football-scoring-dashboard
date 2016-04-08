package com.example.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Pattern;

import com.example.entities.Goal;
import com.example.entities.Team;
import com.example.enums.Commands;
import com.example.enums.Errors;

/**
 * This is the game class were the user will start a football game, score goals, checks the score and ends the game
 *
 * @author Ahmed Abouzeid
 *
 */
public class Game {

    private boolean isGameStarted;
    private Team home;
    private Team away;

    public void run(BufferedReader br) throws IOException {
        isGameStarted = false;

        try {
            boolean stayInLoop = true;
            while (stayInLoop) {
                String input = br.readLine();
                if (input.isEmpty()) {
                    continue;
                }
                Commands inputCommand = getInputCommand(input);
                if (!isGameStarted && inputCommand.equals(Commands.ERROR)) {
                    print(Errors.COMMAND_ERROR_NOT_IN_PROGRESS.toString());
                    continue;
                } else if (!isGameStarted && !inputCommand.equals(Commands.ERROR) && !inputCommand.equals(Commands.START)) {
                    print(Errors.ORDER_ERROR.toString());
                    continue;
                }
                switch (inputCommand) {
                    case START:
                        startGame(input);
                        break;

                    case END:
                        stayInLoop = endGame(stayInLoop, input);
                        break;

                    case PRINT:
                        gameStatistics(input);
                        break;

                    case SCORE:
                        score(input);
                        break;

                    default:
                        print(Errors.COMMAND_ERROR_IN_PROGRESS.toString());
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading your input, " + e);
        } finally {
            br.close();
        }
    }

    private static void print(String s) {
        System.out.println(s);
    }

    /**
     * This method returns the command the input belongs to
     * 
     * @param input the input string from the user
     * @return the command enum
     */
    private Commands getInputCommand(String input) {
        if (Pattern.compile(Commands.START.toString()).matcher(input).matches()) {
            return Commands.START;
        } else if (Pattern.compile(Commands.SCORE.toString()).matcher(input).matches()) {
            return Commands.SCORE;
        } else if (input.equals(Commands.PRINT.toString())) {
            return Commands.PRINT;
        } else if (input.equals(Commands.END.toString())) {
            return Commands.END;
        } else {
            return Commands.ERROR;
        }
    }

    /**
     * This method checks that the game did not start before and gets the team names
     * 
     * @param input the string input from the user
     */
    private void startGame(String input) {
        if (isGameStarted) {
            print(Errors.COMMAND_ERROR_IN_PROGRESS.toString());
        } else {
            isGameStarted = true;
            input = input.replace("Start: ", "").replaceAll("'", "");
            String[] teams = input.split(" vs. ");
            home = new Team(teams[0]);
            away = new Team(teams[1]);
            print("Game Started between " + teams[0] + " and " + teams[1]);
        }
    }

    /**
     * This method prints the current game score with the teams
     * 
     * @param input the string input from the user
     */
    private void gameStatistics(String input) {
        print(home.getName() + " " + home.getScore() + " " + getGoals(home) + " vs. " + away.getName() + " "
                + away.getScore() + " " + getGoals(away));
    }

    private String getGoals(Team team) {
        if (team.getScore() > 0) {
            String result = "(";
            for (Goal goal : team.getGoals()) {
                result += goal.getPlayer() + " " + goal.getMinute() + "' ";
            }
            result = result.substring(0, result.length() - 1);
            return result + ")";
        } else {
            return "";
        }
    }

    /**
     * Ends the game and exits the loop on execution
     * 
     * @param stayInLoop boolean to know if we will further loop on user inputs or not
     * @param input the string input from the user
     * @return false if we will continue looping on user inputs and true if the game is over
     */
    private boolean endGame(boolean stayInLoop, String input) {
        print("Game Ended..");
        switch (home.getScore().compareTo(away.getScore())) {
            case 1:
                print(home.getName() + " WON!");
                break;

            case -1:
                print(away.getName() + " WON!");
                break;

            case 0:
                print("It is a DRAW!");
                break;
        }
        stayInLoop = false;
        return stayInLoop;
    }

    /**
     * This method adds the goal to the team with the specified details
     *
     * @param input the input string from the user
     */
    private void score(String input) {
        String[] scoreArray = input.split(" ");
        Integer minute = Integer.parseInt(scoreArray[0]);
        if (minute < 0 || minute > 120) {
            print(Errors.COMMAND_ERROR_IN_PROGRESS.toString());
            return;
        }
        String teamName = scoreArray[1].replaceAll("'", "");
        Goal goal = new Goal(minute, scoreArray[2]);
        if (teamName.equals(home.getName())) {
            home.addGoal(goal);
        } else if (teamName.equals(away.getName())) {
            away.addGoal(goal);
        } else {
            print(Errors.COMMAND_ERROR_IN_PROGRESS.toString());
        }
    }
}
