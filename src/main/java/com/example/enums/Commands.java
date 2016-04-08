package com.example.enums;

public enum Commands {

    START("Start: '[a-zA-Z0-9 ]+' vs. '[a-zA-Z0-9 ]+'"), END("End"), PRINT("print"), SCORE(
            "[0-9]+ '[a-zA-Z0-9 ]+' [a-zA-Z0-9 ]+"), ERROR("error");

    private final String command;

    private Commands(String s) {
        command = s;
    }

    public String toString() {
        return this.command;
    }
}
