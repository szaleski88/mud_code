package com.tomtom.gameutils;

import com.tomtom.interfaces.IMove;
import javafx.util.Pair;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.tomtom.interfaces.IMove.MoveDirection.findByFirstLetter;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class ConfigDataResolver {

    private static final String rowRegex = "[r]?\\d+(\\s+[nswe]:[r]?\\d+)*";
    private static final Logger logger = Logger.getLogger(ConfigDataResolver.class);

    public static Properties loadFile(File filename) throws FileNotFoundException {

        Properties properties = new Properties();
        FileInputStream input = new FileInputStream(filename);
        try {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static Map<Integer, List<Pair<IMove.MoveDirection, Integer>>> parseContentOf(String filename, String propertyName) throws InvalidPropertiesFormatException, FileNotFoundException {

        Properties properties = loadFile(new File(filename));
        String propertyFileContent = properties.getProperty(propertyName);

        if (isBlank(propertyFileContent)) {
            InvalidPropertiesFormatException invalidPropertiesFormatException = new InvalidPropertiesFormatException("EMPTY CONTENT!");
            logger.error(invalidPropertiesFormatException);
            throw invalidPropertiesFormatException;
        }

        Map<Integer, List<Pair<IMove.MoveDirection, Integer>>> mapProperties = new HashMap<>();

        List<List<String>> listOfRows = Arrays.stream(propertyFileContent.split("\n"))
                .filter(row -> row.matches(rowRegex))
                .map(row -> Arrays.asList(row.replace("r", "").split("\\s+")))
                .collect(Collectors.toList());

        for (List<String> row : listOfRows) {
            Integer roomId = Integer.parseInt(row.get(0));
            mapProperties.putIfAbsent(roomId, new ArrayList<>());
            List<Pair<IMove.MoveDirection, Integer>> roomsNeighborhood = mapProperties.compute(roomId, (k, v) -> new ArrayList<>(v));
            for (String values : row.subList(1, row.size())) {
                String[] split = values.split(":");
                roomsNeighborhood.add(new Pair<>(findByFirstLetter(split[0]), Integer.parseInt(split[1])));
            }
        }
        return mapProperties;
    }


    public static void main(String[] args) {
        try {
            Map<Integer, List<Pair<IMove.MoveDirection, Integer>>> first_board =
                    ConfigDataResolver.parseContentOf(args[0], "test_board2");
//                        cfr.parseContentOf(null);
            System.out.println(first_board);
        } catch (InvalidPropertiesFormatException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
