/*
  Initializes character database, implements random shuffle for Person collection
  Signs contract with Character class and implements it's methods
 */
package com.games.whoami;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class CharacterDatabase implements Character {

    private List<Person> characterDB = new CharacterLoader("data/character-data.csv").load();
    private Collection<Person> people = characterDB;

    public CharacterDatabase() throws IOException {
    }

    @Override
    public Person randomPerson() {
        Collections.shuffle(characterDB);
        return characterDB.get(11);
    }

    @Override
    public Collection<Person> filterByName(String name) {
        people = characterDB.stream()
                .filter(chars -> chars.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
        return people;
    }

    @Override
    public Collection<Person> filterByHair(boolean hair) {
        people = people.stream()
                .filter(chars -> chars.hasHair() == hair)
                .collect(Collectors.toList());
        return people;
    }

    @Override
    public Collection<Person> filterByHairLength(HairLength hairLength) {
        people = people.stream()
                .filter(chars -> chars.getHairLength().equals(hairLength))
                .collect(Collectors.toList());
        return people;
    }

    @Override
    public Collection<Person> filterByGlasses(boolean glasses) {
        people = people.stream()
                .filter(chars -> chars.hasGlasses() == glasses)
                .collect(Collectors.toList());
        return people;
    }

    @Override
    public Collection<Person> filterByCover(boolean cover) {
        people = people.stream()
                .filter(chars -> chars.hasCover() == cover)
                .collect(Collectors.toList());
        return people;
    }

    @Override
    public Collection<Person> filterByBeard(boolean beard) {
        people = people.stream()
                .filter(chars -> chars.hasBeard() == beard)
                .collect(Collectors.toList());
        return people;
    }

    @Override
    public int size() {
        return people.size();
    }

    public Collection<Person> getAll() {
        return Collections.unmodifiableCollection(characterDB);
    }
}