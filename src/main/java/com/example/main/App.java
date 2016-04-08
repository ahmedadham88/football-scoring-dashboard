package com.example.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.example.game.Game;

/**
 * This is the main app that calls the game class
 *
 * @author Ahmed Abouzeid
 *
 */
public class App {

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to Football Scoring Dashboard, Please Start the Game...");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Game game = new Game();
        game.run(br);
    }
}
