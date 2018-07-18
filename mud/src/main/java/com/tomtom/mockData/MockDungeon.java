package com.tomtom.mockData;

import com.tomtom.elements.Dungeon;

import static com.tomtom.interfaces.IMove.MoveDirection.*;
import static com.tomtom.interfaces.IMove.MoveDirection.NORTH;
import static com.tomtom.interfaces.IMove.MoveDirection.WEST;

public class MockDungeon extends Dungeon {


    public MockDungeon() {
        initializeSampleCorridor();
        calculateMapOffset();
        calculateMapSize();
    }

    private void initializeSampleCorridor() {
        this.addCorridorEntry(0);
        this.addRoom(0, NORTH, 3);
        this.addRoom(1, SOUTH, 3);
        this.addRoom(1, EAST, 2);
        this.addRoom(2, SOUTH, 5);
        this.addRoom(2, WEST, 1);
        this.addRoom(3, NORTH, 1);
        this.addRoom(3, SOUTH, 0);
        this.addRoom(3, WEST, 4);
        this.addRoom(4, EAST, 3);
        this.addRoom(5, NORTH, 2);
        this.addRoom(5, EAST, 6);
        this.addRoom(6, SOUTH, 8);
        this.addRoom(6, EAST, 7);
        this.addRoom(6, WEST, 5);
        this.addRoom(7, SOUTH, 9);
        this.addRoom(7, WEST, 6);
        this.addRoom(8, NORTH, 6);
        this.addRoom(8, EAST, 9);
        this.addRoom(9, NORTH, 7);
        this.addRoom(9, WEST, 8);
    }
}
