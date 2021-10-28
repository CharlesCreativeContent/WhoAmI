/*
  Controller class that generates random mystery person
  Initializes game logic and win methods
 */
package com.games.whoami.controller;

import com.apps.util.Prompter;
import com.games.whoami.Character;
import com.games.whoami.CharacterDatabase;
import com.games.whoami.Person;

import java.io.IOException;

public class Game {
    GameHelper gameHelper = new GameHelper();

    public Game() throws IOException {
    }

    public void run(Prompter prompter) {

        try {
            Character character = new CharacterDatabase();
            // creating random/mystery
            Person mysteryPerson = character.randomPerson();
            // System.out.println("Assigned random person: " + mysteryPerson);

            gameHelper.printer.welcome();
            gameHelper.gameLogic(mysteryPerson, prompter);
            gameHelper.win(mysteryPerson.getName());
            Thread.sleep(9000L);
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}