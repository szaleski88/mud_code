package com.tomtom.elements;

import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static com.tomtom.elements.MoveDirection.*;
import static org.junit.Assert.assertEquals;

public class DungeonTest {
    private Dungeon dungeon;

    @Before
    public void setUp() throws Exception {
        dungeon = new Dungeon();
        try {
            dungeon.addRoom(0, N, 3);
            dungeon.addRoom(1, S, 3);
            dungeon.addRoom(1, E, 2);
            dungeon.addRoom(2, S, 5);
            dungeon.addRoom(2, W, 1);
            dungeon.addRoom(3, N, 1);
            dungeon.addRoom(3, S, 0);
            dungeon.addRoom(3, W, 4);
            dungeon.addRoom(4, E, 3);
            dungeon.addRoom(5, N, 2);
            dungeon.addRoom(5, E, 6);
            dungeon.addRoom(6, S, 8);
            dungeon.addRoom(6, E, 7);
            dungeon.addRoom(6, W, 5);
            dungeon.addRoom(7, S, 9);
            dungeon.addRoom(7, W, 6);
            dungeon.addRoom(8, N, 6);
            dungeon.addRoom(8, E, 9);
            dungeon.addRoom(9, N, 7);
            dungeon.addRoom(9, W, 8);
        } catch (InvalidInputDataException e) {
            e.printStackTrace();
        }
        dungeon.fillCoordinates();
    }

    @Test
    public void addRoom() {
    }

    @Test
    public void calculateMapSizeValuesOk() {
        dungeon.calculateMapSize();
        assertEquals(new Pair<>(-1, -2), dungeon.getMapOffset());
    }

    @Test
    public void calculateMapSizeWorksWhenOffsetIsNull() throws NoSuchFieldException, IllegalAccessException {
        Field mapOffset = dungeon.getClass().getDeclaredField("mapOffset");
        mapOffset.setAccessible(true);
        mapOffset.set(dungeon, null);

        dungeon.calculateMapSize();
        assertEquals(new Pair<>(-1, -2), dungeon.getMapOffset());
    }


    @Test(expected = InvalidInputDataException.class)
    public void createDungeonFromInputFileContentThrowsOnNull() throws InvalidInputDataException {
        dungeon.createDungeonFromInputFileContent(null);
    }

    @Test
    public void fillCoordinates() {
    }
}