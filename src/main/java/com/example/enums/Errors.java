package com.example.enums;

public enum Errors {

    ORDER_ERROR("No game currently in progress"), COMMAND_ERROR_IN_PROGRESS(
            "input error - please type 'print' for game details"), COMMAND_ERROR_NOT_IN_PROGRESS(
                    "input error - please start a game through typing 'Start: '<Name of Home Team>' vs. '<Name of Away Team>'");

    private final String error;

    private Errors(String s) {
        error = s;
    }

    public String toString() {
        return this.error;
    }
}
