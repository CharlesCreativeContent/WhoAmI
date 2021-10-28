package com.games.whoami.controller;

import com.games.whoami.Character;
import com.games.whoami.CharacterDatabase;
import com.games.whoami.HairLength;
import com.games.whoami.Person;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Collection;

public class GameTest {
    GameHelper gameHelper;

    @Before
    public void init() throws IOException {
        gameHelper = new GameHelper();
    }

    @Test
    public void playerSelection_shouldReturnString_whenSelectionValidInteger() {
        assertEquals("Hair", gameHelper.playerSelection(1));
        assertEquals("HairLength", gameHelper.playerSelection(2));
        assertEquals("Glasses", gameHelper.playerSelection(3));
        assertEquals("Cover", gameHelper.playerSelection(4));
        assertEquals("Beard", gameHelper.playerSelection(5));
    }

    @Test
    public void playerSelection_shouldReturnNull_whenSelectionInvalidInteger() {
        assertNull(gameHelper.playerSelection(0));
        assertNull(gameHelper.playerSelection(100));
    }

    @Test
    public void playerOptionNames_shouldReturnFilteredNameList_whenListNotEmpty() throws IOException {
        Character character = new CharacterDatabase();
        Collection<Person> hairList= gameHelper.playerOptionNames(character, 1, true);
        Collection<Person> noGlassesList = gameHelper.playerOptionNames(character, 3, false);
        Collection<Person> coverList = gameHelper.playerOptionNames(character, 4, true);
        Collection<Person> noBeardList = gameHelper.playerOptionNames(character, 5, false);
        hairList.forEach(person -> assertTrue(person.hasHair()));
        noGlassesList.forEach(person -> assertFalse(person.hasGlasses()));
        coverList.forEach(person -> assertTrue(person.hasCover()));
        noBeardList.forEach(person -> assertFalse(person.hasBeard()));
    }

    @Test
    public void mysterySelection_shouldReturnTrue_whenValidSelection(){
        Person person = new Person("Shawn", true, HairLength.BALD, true, true, true);
        assertTrue(gameHelper.mysterySelection(1, person));
        assertTrue(gameHelper.mysterySelection(3, person));
        assertTrue(gameHelper.mysterySelection(4, person));
        assertTrue(gameHelper.mysterySelection(5, person));
    }

    @Test
    public void hairSelection_shouldReturnHair_whenValid(){
        assertEquals(gameHelper.hairSelection(1),HairLength.SHORT);
        assertEquals(gameHelper.hairSelection(2), HairLength.MEDIUM);
        assertEquals(gameHelper.hairSelection(3), HairLength.LONG);
        assertEquals(gameHelper.hairSelection(4), HairLength.BALD);
    }

    @Test
    public void hairSelection_shouldReturnNull_whenInvalid(){
        assertNull(gameHelper.hairSelection(6));
        assertNull(gameHelper.hairSelection(-1));
    }

}