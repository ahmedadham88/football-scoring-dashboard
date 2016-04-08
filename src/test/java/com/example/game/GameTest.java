package com.example.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.example.enums.Errors;
import com.example.game.Game;

/**
 * Unit tests for The Game class methods
 */
public class GameTest {

    private ByteArrayOutputStream output;

    @Mock
    private BufferedReader br;

    @Before
    public void setup() throws Exception {
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        br = Mockito.mock(BufferedReader.class);
    }

    @Test
    public void gameHomeTeamWinsTest() throws IOException {
        when(br.readLine()).thenReturn("Start: 'home' vs. 'away'").thenReturn("10 'home' player1").thenReturn("End");
        Game game = new Game();
        game.run(br);
        assertTrue(output.toString().contains("home WON!"));
        assertFalse(output.toString().contains(Errors.ORDER_ERROR.toString()));
        assertFalse(output.toString().contains(Errors.COMMAND_ERROR_IN_PROGRESS.toString()));
        assertFalse(output.toString().contains(Errors.COMMAND_ERROR_NOT_IN_PROGRESS.toString()));
    }

    @Test
    public void gameAwayTeamWinsTest() throws IOException {
        when(br.readLine()).thenReturn("Start: 'home' vs. 'away'").thenReturn("10 'home' player1")
                .thenReturn("11 'away' player1").thenReturn("60 'away' player2").thenReturn("print").thenReturn("End");
        Game game = new Game();
        game.run(br);
        assertTrue(output.toString().contains("away WON!"));
        assertFalse(output.toString().contains(Errors.ORDER_ERROR.toString()));
        assertFalse(output.toString().contains(Errors.COMMAND_ERROR_IN_PROGRESS.toString()));
        assertFalse(output.toString().contains(Errors.COMMAND_ERROR_NOT_IN_PROGRESS.toString()));
    }

    @Test
    public void gameDrawTest() throws IOException {
        when(br.readLine()).thenReturn("Start: 'home' vs. 'away'").thenReturn("10 'home' player1")
                .thenReturn("11 'away' player1").thenReturn("60 'away' player2").thenReturn("print")
                .thenReturn("90 'home' player2").thenReturn("End");
        Game game = new Game();
        game.run(br);
        assertTrue(output.toString().contains("It is a DRAW!"));
        assertFalse(output.toString().contains(Errors.ORDER_ERROR.toString()));
        assertFalse(output.toString().contains(Errors.COMMAND_ERROR_IN_PROGRESS.toString()));
        assertFalse(output.toString().contains(Errors.COMMAND_ERROR_NOT_IN_PROGRESS.toString()));
    }

    @Test
    public void noGameCurrentlyInProgressErrorTest() throws IOException {
        when(br.readLine()).thenReturn("print").thenReturn("End").thenReturn("12 'home' player")
                .thenReturn("Start: 'home' vs. 'away'").thenReturn("End");
        Game game = new Game();
        game.run(br);
        assertEquals(StringUtils.countMatches(output.toString(), Errors.ORDER_ERROR.toString()), 3);
        assertFalse(output.toString().contains(Errors.COMMAND_ERROR_IN_PROGRESS.toString()));
        assertFalse(output.toString().contains(Errors.COMMAND_ERROR_NOT_IN_PROGRESS.toString()));
    }

    @Test
    public void inputErrorInGameTest() throws IOException {
        when(br.readLine()).thenReturn("Start: 'home' vs. 'away'").thenReturn("Print").thenReturn("end game")
                .thenReturn("12 home player").thenReturn("End");
        Game game = new Game();
        game.run(br);
        assertEquals(StringUtils.countMatches(output.toString(), Errors.COMMAND_ERROR_IN_PROGRESS.toString()), 3);
        assertFalse(output.toString().contains(Errors.ORDER_ERROR.toString()));
        assertFalse(output.toString().contains(Errors.COMMAND_ERROR_NOT_IN_PROGRESS.toString()));
    }

    @Test
    public void inputErrorOutOfGameTest() throws IOException {
        when(br.readLine()).thenReturn("Print").thenReturn("end game").thenReturn("12 home player")
                .thenReturn("Start: 'home' vs. 'away'").thenReturn("End");
        Game game = new Game();
        game.run(br);
        assertEquals(StringUtils.countMatches(output.toString(), Errors.COMMAND_ERROR_NOT_IN_PROGRESS.toString()), 3);
        assertFalse(output.toString().contains(Errors.ORDER_ERROR.toString()));
        assertFalse(output.toString().contains(Errors.COMMAND_ERROR_IN_PROGRESS.toString()));
    }

    @Test
    public void allErrorsTest() throws IOException {
        when(br.readLine()).thenReturn("print").thenReturn("end game").thenReturn("12 'home' player").thenReturn("Print")
                .thenReturn("End").thenReturn("12 home player").thenReturn("Start: 'home' vs. 'away'").thenReturn("Print")
                .thenReturn("end game").thenReturn("12 home player").thenReturn("End");
        Game game = new Game();
        game.run(br);
        assertEquals(StringUtils.countMatches(output.toString(), Errors.ORDER_ERROR.toString()), 3);
        assertEquals(StringUtils.countMatches(output.toString(), Errors.COMMAND_ERROR_IN_PROGRESS.toString()), 3);
        assertEquals(StringUtils.countMatches(output.toString(), Errors.COMMAND_ERROR_NOT_IN_PROGRESS.toString()), 3);
    }

    @Test
    public void startingGameTwiceTest() throws IOException {
        when(br.readLine()).thenReturn("Start: 'home' vs. 'away'").thenReturn("print").thenReturn("Start: 'home' vs. 'away'")
                .thenReturn("12 'home' player").thenReturn("End");
        Game game = new Game();
        game.run(br);
        assertEquals(StringUtils.countMatches(output.toString(), Errors.COMMAND_ERROR_IN_PROGRESS.toString()), 1);
        assertFalse(output.toString().contains(Errors.ORDER_ERROR.toString()));
        assertFalse(output.toString().contains(Errors.COMMAND_ERROR_NOT_IN_PROGRESS.toString()));
    }

    @Test
    public void goalsCalculatedWithinGameTest() throws IOException {
        // The Goal has to be scored between 0' and 120'
        when(br.readLine()).thenReturn("Start: 'home' vs. 'away'").thenReturn("10 'home' player1")
                .thenReturn("11 'away' player1").thenReturn("60 'away' player2").thenReturn("print")
                .thenReturn("90 'home' player2").thenReturn("121 'away' player1").thenReturn("200 'away' player1")
                .thenReturn("130 'away' player1").thenReturn("-1 'away' player1").thenReturn("End");
        Game game = new Game();
        game.run(br);
        assertTrue(output.toString().contains("It is a DRAW!"));
        assertFalse(output.toString().contains(Errors.ORDER_ERROR.toString()));
        assertFalse(output.toString().contains(Errors.COMMAND_ERROR_NOT_IN_PROGRESS.toString()));
        assertEquals(StringUtils.countMatches(output.toString(), Errors.COMMAND_ERROR_IN_PROGRESS.toString()), 4);
    }

    @Test
    public void checkPrintingFormatTest() throws IOException {
        when(br.readLine()).thenReturn("Start: 'home' vs. 'away'").thenReturn("10 'home' Alex").thenReturn("11 'away' Bob")
                .thenReturn("120 'away' Claudia").thenReturn("95 'home' John").thenReturn("60 'away' David")
                .thenReturn("20 'home' Caroline").thenReturn("print").thenReturn("End");
        Game game = new Game();
        game.run(br);
        String score = "home 3 (Alex 10' Caroline 20' John 95') vs. away 3 (Bob 11' David 60' Claudia 120')";
        assertTrue(output.toString().contains(score));
        assertTrue(output.toString().contains("It is a DRAW!"));
        assertFalse(output.toString().contains(Errors.ORDER_ERROR.toString()));
        assertFalse(output.toString().contains(Errors.COMMAND_ERROR_NOT_IN_PROGRESS.toString()));
        assertFalse(output.toString().contains(Errors.COMMAND_ERROR_IN_PROGRESS.toString()));
    }

    @Test
    public void emptyLinesTest() throws IOException {
        when(br.readLine()).thenReturn("").thenReturn("").thenReturn("Start: 'home' vs. 'away'").thenReturn("")
                .thenReturn("End");
        Game game = new Game();
        game.run(br);
        assertTrue(output.toString().contains("It is a DRAW!"));
        assertFalse(output.toString().contains(Errors.ORDER_ERROR.toString()));
        assertFalse(output.toString().contains(Errors.COMMAND_ERROR_NOT_IN_PROGRESS.toString()));
        assertFalse(output.toString().contains(Errors.COMMAND_ERROR_IN_PROGRESS.toString()));
    }

    @Test
    public void scoringNonRegisteredTeamsTest() throws IOException {
        when(br.readLine()).thenReturn("Start: 'home' vs. 'away'").thenReturn("10 'home' Alex").thenReturn("11 'away' Bob")
                .thenReturn("120 'non' Claudia").thenReturn("95 'master' John").thenReturn("60 'away' David")
                .thenReturn("20 'home' Caroline").thenReturn("print").thenReturn("End");
        Game game = new Game();
        game.run(br);
        assertTrue(output.toString().contains("It is a DRAW!"));
        assertFalse(output.toString().contains(Errors.ORDER_ERROR.toString()));
        assertFalse(output.toString().contains(Errors.COMMAND_ERROR_NOT_IN_PROGRESS.toString()));
        assertEquals(StringUtils.countMatches(output.toString(), Errors.COMMAND_ERROR_IN_PROGRESS.toString()), 2);
    }

    @After
    public void cleanup() throws IOException {
        System.setOut(null);
        br.close();
        output.close();
    }
}
