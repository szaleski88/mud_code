package com.tomtom.gameutils;

import com.tomtom.elements.MoveDirection;
import javafx.util.Pair;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.*;

import static com.tomtom.elements.MoveDirection.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class InputFileResolverTest {

    private List<String> modelList = Arrays.asList("r0 n:r3", "r1 s:r3 e:r2", "r2 s:r5 w:r1", "r3 n:r1 s:r0 w:r4", "r4 e:r3", "r5 n:r2 e:r6", "r6 s:r8 e:r7 w:r5", "r7 s:r9 w:r6", "r8 n:r6 e:r9", "r9 n:r7 w:r8");
    private Map<Integer, List<Pair<MoveDirection, Integer>>> modelDungeonData = new HashMap<>();

    public InputFileResolverTest() {
        for (int i = 0; i <= 9; i++) {
            modelDungeonData.put(i, new ArrayList<>());
        }
        modelDungeonData.get(0).add(pairFrom(NORTH, 3));
        modelDungeonData.get(1).add(pairFrom(SOUTH, 3));
        modelDungeonData.get(1).add(pairFrom(EAST, 2));
        modelDungeonData.get(2).add(pairFrom(SOUTH, 5));
        modelDungeonData.get(2).add(pairFrom(WEST, 1));
        modelDungeonData.get(3).add(pairFrom(NORTH, 1));
        modelDungeonData.get(3).add(pairFrom(SOUTH, 0));
        modelDungeonData.get(3).add(pairFrom(WEST, 4));
        modelDungeonData.get(4).add(pairFrom(EAST, 3));
        modelDungeonData.get(5).add(pairFrom(NORTH, 2));
        modelDungeonData.get(5).add(pairFrom(EAST, 6));
        modelDungeonData.get(6).add(pairFrom(SOUTH, 8));
        modelDungeonData.get(6).add(pairFrom(EAST, 7));
        modelDungeonData.get(6).add(pairFrom(WEST, 5));
        modelDungeonData.get(7).add(pairFrom(SOUTH, 9));
        modelDungeonData.get(7).add(pairFrom(WEST, 6));
        modelDungeonData.get(8).add(pairFrom(NORTH, 6));
        modelDungeonData.get(8).add(pairFrom(EAST, 9));
        modelDungeonData.get(9).add(pairFrom(NORTH, 7));
        modelDungeonData.get(9).add(pairFrom(WEST, 8));
    }

    private Pair<MoveDirection, Integer> pairFrom(MoveDirection md, int id) {
        return new Pair<>(md, id);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkIfExceptionThrownOnNotExistingFile() throws IOException {
        InputFileResolver.getLinesFromFile("Beng");
    }

    @Test(expected = NoSuchFileException.class)
    public void checkIfExceptionThrownOnNull() throws IOException {
        InputFileResolver.getLinesFromFile(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkIfLongInputThrows() throws IOException {
        InputFileResolver.getLinesFromFile("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
    }


    @Test
    public void checkIfReturnedListIsEqualToExpected() throws IOException {
        List<String> list = InputFileResolver.getLinesFromFile("C:\\MOJE\\MUD\\JAVA\\mud\\basic_dungeon.txt");
        assertThat(list, is(modelList));
    }

    @Test
    public void checkMapDataIsParsedAsExpected() {
        Map<Integer, List<Pair<MoveDirection, Integer>>> dungeonData = InputFileResolver.getDungeonDataFrom(modelList);
        assertThat(dungeonData, is(modelDungeonData));
    }


}
