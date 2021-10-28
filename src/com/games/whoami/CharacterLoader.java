/*
  Loads database from character-data.csv file
 */
package com.games.whoami;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CharacterLoader {
    private Path dataFilePath;

    public CharacterLoader(String dataFilepath) {
        this.dataFilePath = Path.of(dataFilepath);
    }

    public List<Person> load() throws IOException {
        List<Person> result = new ArrayList<>();
        Files.lines(dataFilePath).forEach(line -> {
            String[] tokens = line.split(",");

            String name = tokens[0];
            boolean hair = Boolean.parseBoolean(tokens[1]);
            HairLength hairLength = HairLength.valueOf(tokens[2]);
            boolean glasses = Boolean.parseBoolean(tokens[3]);
            boolean cover = Boolean.parseBoolean(tokens[4]);
            boolean beard = Boolean.parseBoolean(tokens[5]);

            result.add(new Person(name, hair, hairLength, glasses, cover, beard));
        });
        return result;
    }
}