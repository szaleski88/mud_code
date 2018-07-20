package com.tomtom.gameutils;

import com.tomtom.interfaces.IMove;
import javafx.util.Pair;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.tomtom.interfaces.IMove.MoveDirection.findByFirstLetter;

public class InputFileResolver {

    private static final String inputFilenameRegex = ".*(?i)(dungeon).*\\.txt";
    private static final String rowRegex = "[r]?\\d+(\\s+[nswe]:[r]?\\d+)*";

    /**
     * GET LIST OF NOT EMPTY LINES FROM EXISTING FILE.
     *
     * @param filePath
     * @return List of not empty lines
     * @throws IOException              IF NOT EXIST
     * @throws IllegalArgumentException IF NAME DOES NOT MATCH REGEX
     */
    public static List<String> getLinesFromFile(String filePath) throws IOException, IllegalArgumentException {
        if (StringUtils.isBlank(filePath)) {
            throw new NoSuchFileException(String.format("File \"%s\" does not exist!", filePath));
        }
        if (!filePath.matches(inputFilenameRegex)) {
            throw new IllegalArgumentException(String.format("WON'T LOAD! Suspicious file: \"%s\"\n\tFile should be *.txt with \"dungeon\" in its name!", filePath));
        }

        if (checkIfExists(filePath)) {
            List<String> lines = new ArrayList<>();
            try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
                stream.filter(line -> !StringUtils.isBlank(line)).forEach(line -> lines.add(line.trim()));
            }
            return lines;
        } else {
            throw new NoSuchFileException(String.format("File \"%s\" does not exist!", filePath));
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
        Map<Integer, List<Pair<IMove.MoveDirection, Integer>>> dungeonData = new TreeMap<>();

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
        return dungeonData.isEmpty() ? null : dungeonData;
    }


    public static void main(String[] args) {
        try {
            InputFileResolver.getLinesFromFile("beng");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
