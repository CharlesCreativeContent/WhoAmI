/*
  Business class that initializes name and features for Person and it's getter methods
 */
package com.games.whoami;

public class Person {
    private String name;
    private boolean hair;
    private HairLength hairLength;
    private boolean glasses;
    private boolean cover;
    private boolean beard;

    public Person(String name) {
        this.name = name;
    }

    public Person(String name, boolean hair, HairLength hairLength, boolean glasses, boolean cover, boolean beard) {
        this(name);
        this.hair = hair;
        this.hairLength = hairLength;
        this.glasses = glasses;
        this.cover = cover;
        this.beard = beard;
    }

    public String getName() {
        return name;
    }

    public boolean hasHair() {
        return hair;
    }

    public HairLength getHairLength() {
        return hairLength;
    }

    public boolean hasGlasses() {
        return glasses;
    }

    public boolean hasCover() {
        return cover;
    }

    public boolean hasBeard() {
        return beard;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": name=" + getName()
                + ", hair=" + hasHair() + ", hairLength=" + getHairLength()
                + ", glasses=" + hasGlasses() + ", hat=" + hasCover() +
                ", beard=" + hasBeard();
    }
}