package com.games.whoami;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class CharacterDatabaseTest {
    CharacterDatabase characterDB;

    @Before
    public void setup() throws IOException {
        characterDB = new CharacterDatabase();
    }

    @Test
    public void filterByName_shouldReturnListWithSelectedName_whenValidName() {
        List<Person> jKList = (List<Person>) characterDB.filterByName("jk");
        List<Person> gandiList = (List<Person>) characterDB.filterByName("gandi");
        assertEquals(1, jKList.size());
        assertEquals(1, gandiList.size());
    }

    @Test
    public void filterByHair_shouldReturnListWithSelectedHair_whenValidList() {
        Collection<Person> noHairList = characterDB.filterByHair(true);
        Collection<Person> hairList = characterDB.filterByHair(false);
        noHairList.forEach(person -> assertTrue(person.hasHair()));
        hairList.forEach(person -> assertFalse(person.hasHair()));
    }

    @Test
    public void filterByCover_shouldReturnListWithSelectedCover_whenValidList() {
        Collection<Person> noCoverList = characterDB.filterByCover(true);
        Collection<Person> coverList = characterDB.filterByCover(false);
        noCoverList.forEach(person -> assertTrue(person.hasCover()));
        coverList.forEach(person -> assertFalse(person.hasCover()));
    }

    @Test
    public void filterByBeard_shouldReturnListWithSelectedBeard_whenValidList() {
        Collection<Person> noBeardList = characterDB.filterByBeard(true);
        Collection<Person> beardList = characterDB.filterByBeard(false);
        noBeardList.forEach(person -> assertTrue(person.hasBeard()));
        beardList.forEach(person -> assertFalse(person.hasBeard()));
    }

    @Test
    public void filterByGlasses_shouldReturnListWithSelectedGlasses_whenValidList() {
        Collection<Person> noGlassesList = characterDB.filterByGlasses(true);
        Collection<Person> glassesList = characterDB.filterByGlasses(false);
        noGlassesList.forEach(person -> assertTrue(person.hasGlasses()));
        glassesList.forEach(person -> assertFalse(person.hasGlasses()));
    }

    @Test
    public void filterByHairLength_shouldReturnListWithSelectedHairLength_whenValidList() {
        Collection<Person> noHairList = characterDB.filterByHairLength(HairLength.BALD);
        Collection<Person> shortHairList = characterDB.filterByHairLength(HairLength.SHORT);
        Collection<Person> mediumHairList = characterDB.filterByHairLength(HairLength.MEDIUM);
        Collection<Person> longHairList = characterDB.filterByHairLength(HairLength.LONG);

        noHairList.forEach(person -> assertEquals(HairLength.BALD, person.getHairLength()));
        shortHairList.forEach(person -> assertEquals(HairLength.SHORT, person.getHairLength()));
        mediumHairList.forEach(person -> assertEquals(HairLength.MEDIUM, person.getHairLength()));
        longHairList.forEach(person -> assertEquals(HairLength.LONG, person.getHairLength()));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getAll_shouldThrowException_whenModified() throws UnsupportedOperationException {
        characterDB.getAll().clear();
    }
}