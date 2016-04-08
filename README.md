# Football Scoring Dashboard

### Overview
This application prints out a scoring dashboard as text during a football match. The football scoring dashboard would have output the following at the 80th minute in the 1966 Football world cup final between England and West Germany: **England 2 (Hurst 18' Peters 78') vs. West Germany 1 (Haller 12')**.

The Application accepts the following commands:

  - **Start: '<Home_Team>' vs. '<Away_Team>'** to start the game and any command before it is not relevant
  - **<minute> '<Team_Name>' Player** to score a goal
  - **print** to show the current statistics of the match
  - **End** to end the game and show you who won

The following are the error messages that you may face:

  - **No game currently in progress**, when you enter one of the aforementioned commands before starting the game command
  - **input error - please type 'print' for game details**, and that happens in one of the following cases:
    - When you enter an unknown command and the game is in progress
    - When you start a game within another game
    - When you try to score a goal in a minute not between 0' and 120'
    - When you try to score a goal for a team that you did not define before
  - **input error - please start a game through typing 'Start: '<Name of Home Team>' vs. '<Name of Away Team>'**, when you enter an unknown command and the game is not started

### Assumptions

  1. If the user presses **Enter** anytime during the game, it will pretend that it is an empty input and continue waiting for the next user input.
  2. If the user tries to start a new game within an ongoing game, an error will be shown advicing the user to enter the command **print** to see the summary.
  3. Team names can only have Alphabetic characters, numerals and/or spaces.
  4. The commands are case-sensitive.
  5. The user will score respecting the increment of time (if he scores at minute 20 then 10, the input will be processed). However, the time must be an Integer and between 0 and 120 (since the game can go to extra time). Also, stoppage time is not taken into consideration.

### Prerequisites

  - Java 8
  - Maven

### Procedure

You can either use the command line as follows:

```sh
mvn clean install
```
```sh
java -jar target/football-scoring-dashboard-<version>.jar
```
**OR**

Import the project as a maven project in any IDE (e.g. Eclipse) and run it

