package com.tomtom.gameutils;

import com.tomtom.interfaces.IMove;
import javafx.util.Pair;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.tomtom.interfaces.IMove.MoveDirection.findByFirstLetter;

public class ConfigDataResolver {

    private static final String rowRegex = "[r]?\\d+(\\s+[nswe]:[r]?\\d+)*";
    private static final Logger logger = Logger.getLogger(ConfigDataResolver.class);


    /**
     * GET LIST OF NOT EMPTY LINES FROM EXISTING FILE.
     *
     * @param filePath path to input file
     * @return List of not empty lines
     * @throws IOException IF NOT EXIST
     */
    public static List<String> getLinesFromFile(String filePath) throws IOException {
        if (checkIfExists(filePath)) {
            List<String> lines = new ArrayList<>();
            try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
                stream.filter(line -> !StringUtils.isBlank(line)).forEach(line -> lines.add(line.trim()));
            }
            return lines;
        } else {
            NoSuchFileException exc = new NoSuchFileException(String.format("File \"%s\" does not exist!", filePath));
            logger.error(exc);
            throw exc;
        }
    }

    private static boolean checkIfExists(String fileName) {
        return new File(fileName).exists();
    }

    /**
     * CREATE MAP WITH ROOM-ID AS KEY AND LIST OF PAIRS<DIRECTION - TO-ROOM-ID>
     *
     * @param inputFileContent lines not empty from input file
     * @return map with roomId as key and list of direction-roomid pairs as value
     */
    public static Map<Integer, List<Pair<IMove.MoveDirection, Integer>>> getDungeonDataFrom(List<String> inputFileContent) {

        Map<Integer, List<Pair<IMove.MoveDirection, Integer>>> dungeonData = new HashMap<>();

        List<List<String>> linesWithCorrectFormat = inputFileContent.stream()
                .filter(row -> row.matches(rowRegex))
                .map(row -> Arrays.asList(row.replace("r", "").split("\\s+")))
                .collect(Collectors.toList());

        for (List<String> line : linesWithCorrectFormat) {
            Integer roomId = Integer.parseInt(line.get(0));
            dungeonData.putIfAbsent(roomId, new ArrayList<>());
            List<Pair<IMove.MoveDirection, Integer>> roomsNeighborhood = dungeonData.compute(roomId, (k, v) -> new ArrayList<>(v));
            for (String values : line.subList(1, line.size())) {
                String[] split = values.split(":");
                roomsNeighborhood.add(new Pair<>(findByFirstLetter(split[0]), Integer.parseInt(split[1])));
            }
        }
        return dungeonData;
    }


    public static void main(String[] args) {
        try {
            ConfigDataResolver.getLinesFromFile("beng");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
