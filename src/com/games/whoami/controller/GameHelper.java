/*
  Helper class to Game class
  The Game logic, Prompter and Printer classes are implemented
 */
package com.games.whoami.controller;

import com.apps.util.Prompter;
import com.games.whoami.Character;
import com.games.whoami.CharacterDatabase;
import com.games.whoami.HairLength;
import com.games.whoami.Person;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

import java.util.stream.Collectors;

class GameHelper {
    Printer printer;
    Character character;
    Collection<Person> currentList = new ArrayList<>();
    Instant startTime;
    int steps = 0;
    int losses = 0;

    GameHelper() throws IOException {
        printer = new Printer();
        character = new CharacterDatabase();
        currentList.addAll(character.getAll());
        startTime = Instant.now();
    }

    void gameLogic(Person mysteryPerson, Prompter prompter) {
        while (stillPlaying()) {
            // make prompt calls
            String nameOrFeatureSelection = prompter.prompt(printer.start, "[1-3]", printer.invalid);
            steps++;
            if (nameOrFeature(nameOrFeatureSelection, "1")) {
                printer.printList(getCollect());
                String name = prompter.prompt(printer.chooseName);
                steps++;
                if (pickedRightPerson(mysteryPerson, name)) {
                    break;
                } else {
                    losses++;
                    printer.incorrect();
                }
            } else if (nameOrFeature(nameOrFeatureSelection, "2")) {
                checkByFeatures(prompter, mysteryPerson);
            } else {
                printer.suspects();
            }
        }
    }

    private void checkByFeatures(Prompter prompter, Person mysteryPerson) {
        String featureSelected = prompter.prompt(printer.chooseFeature, "[1-5]", printer.invalid);
        int featureSelection = Integer.parseInt(featureSelected);

        printer.choice(playerSelection(featureSelection));

        steps++;
        if (featureSelection != 2) {
            checkByFeaturesNotHairLength(prompter, featureSelection, mysteryPerson);
        } else {
            checkByHairLength(prompter, mysteryPerson);
        }
    }

    private void checkByFeaturesNotHairLength(Prompter prompter, int featureSelection, Person mysteryPerson) {
        String playerChoice = prompter.prompt(printer.chooseBoolean(playerSelection(featureSelection)), "true|false|True|False", printer.invalid);
        boolean playerInput = Boolean.parseBoolean(playerChoice);

        String guess;
        steps++;
        if (mysteryPropertyCorrect(mysteryPerson, featureSelection, playerInput)) {
            currentList = playerOptionNames(character, featureSelection, playerInput);
            guess = printer.correct;
        } else {
            losses++;
            currentList = playerOptionNames(character, featureSelection, mysterySelection(featureSelection, mysteryPerson));
            guess = printer.incorrect;
        }
        System.out.println(guess);
    }

    private void checkByHairLength(Prompter prompter, Person mysteryPerson) {
        String playerChoice = prompter.prompt(printer.chooseHairLength, "[1-4]", printer.invalid);
        int playerInput = Integer.parseInt(playerChoice);

        steps++;
        if (hairLengthEqual(mysteryPerson, playerInput)) {
            currentList = character.filterByHairLength(hairSelection(playerInput));
            printer.correct();
        } else {
            printer.incorrect();
            currentList = currentList.stream()
                    .filter(person -> !hairLengthEqual(person, playerInput))
                    .collect(Collectors.toList());
        }
    }

    private boolean mysteryPropertyCorrect(Person mysteryPerson, int featureSelection, boolean playerInput) {
        return Boolean.compare(playerInput, mysterySelection(featureSelection, mysteryPerson)) == 0;
    }

    private boolean pickedRightPerson(Person mysteryPerson, String name) {
        return mysteryPerson.getName().equalsIgnoreCase(name);
    }

    private List<String> getCollect() {
        return currentList.stream().map(Person::getName).collect(Collectors.toList());
    }

    private boolean hairLengthEqual(Person mysteryPerson, int playerInput) {
        return mysteryPerson.getHairLength().equals(hairSelection(playerInput));
    }

    private boolean nameOrFeature(String nameOrFeature, String s) {
        return nameOrFeature.equals(s);
    }

    private boolean stillPlaying() {
        return character.size() != 1;
    }

    void win(String mysteryPerson) {
        long gameTime = Duration.between(startTime, Instant.now()).toSeconds();
        System.out.println(printer.win + " " + mysteryPerson);
        System.out.println("Steps Taken: " + steps);
        System.out.println("Wrong Guesses: " + losses);
        System.out.println("Time: " + (int) Math.floor(gameTime / 60) + " minutes " + (gameTime % 60) + " seconds.");
    }

    String playerSelection(int selection) {
        String choice = null;
        switch (selection) {
            case 1:
                choice = "Hair";
                break;
            case 2:
                choice = "HairLength";
                break;
            case 3:
                choice = "Glasses";
                break;
            case 4:
                choice = "Cover";
                break;
            case 5:
                choice = "Beard";
                break;
        }
        return choice;
    }

    boolean mysterySelection(int selection, Person mysteryPerson) {
        boolean choice = false;
        switch (selection) {
            case 1:
                choice = mysteryPerson.hasHair();
                break;
            case 3:
                choice = mysteryPerson.hasGlasses();
                break;
            case 4:
                choice = mysteryPerson.hasCover();
                break;
            case 5:
                choice = mysteryPerson.hasBeard();
                break;
        }
        return choice;
    }

    Collection<Person> playerOptionNames(Character character, int selection, boolean playerInput) {
        Collection<Person> people = new ArrayList<>();
        switch (selection) {
            case 1:
                people = character.filterByHair(playerInput);
                break;
            case 3:
                people = character.filterByGlasses(playerInput);
                break;
            case 4:
                people = character.filterByCover(playerInput);
                break;
            case 5:
                people = character.filterByBeard(playerInput);
                break;
        }
        return people;
    }

    HairLength hairSelection(int selection) {
        HairLength hairLength = null;
        switch (selection) {
            case 1:
                hairLength = HairLength.SHORT;
                break;
            case 2:
                hairLength = HairLength.MEDIUM;
                break;
            case 3:
                hairLength = HairLength.LONG;
                break;
            case 4:
                hairLength = HairLength.BALD;
                break;
        }
        return hairLength;
    }

    //InnerClass
    class Printer {
        Path dataFilePath = Path.of("data/messages.csv");
        Path promptFilePath = Path.of("data/prompt.csv");
        List<String> messages;
        List<String> prompts;
        String[] banner;
        String[] rules;
        String win;
        String invalid;
        String start;
        String chooseName;
        String chooseFeature;
        String correct;
        String incorrect;
        String chooseHairLength;

        Printer() throws IOException {
            this.load();
        }

        void load() throws IOException {
            this.messages = Files.lines(dataFilePath).collect(Collectors.toList());
            this.prompts = Files.lines(promptFilePath).collect(Collectors.toList());
            banner = messages.get(0).split("/n");
            rules = messages.get(1).split("/n");
            win = messages.get(2);
            correct = messages.get(3);
            incorrect = messages.get(4);
            invalid = prompts.get(0);
            start = "\n" + prompts.get(2) + "\n";
            chooseName = "\n" + prompts.get(3) + "\n";
            chooseFeature = prompts.get(4) + "\n" + prompts.get(5) + "\n";
            chooseHairLength = prompts.get(8) + "\n" + prompts.get(9) + "\n";
        }

        void welcome() {
            Arrays.stream(banner).forEach(System.out::println);
            Arrays.stream(rules).forEach(System.out::println);
        }

        private void correct() {
            System.out.println(" " + printer.correct);
        }

        private void incorrect() {
            System.out.println(printer.incorrect);
        }

        void printList(Collection<String> names) {
            names.forEach(System.out::println);
        }

        void choice(String selected) {
            System.out.println(selected + " selected.");
        }

        String chooseBoolean(String selected) {
            return prompts.get(6) + selected + prompts.get(7) + "\n";
        }

        private void suspects() {
            System.out.println("Current Suspects: ");
            printer.printList(getCollect());
        }
    }
}