package com.tomtom.sampleData;

import com.tomtom.elements.Dungeon;
import com.tomtom.elements.InvalidInputDataException;

import static com.tomtom.elements.MoveDirection.*;

public class sampleDungeon extends Dungeon {


    public sampleDungeon() {
        initializeSampleCorridor();
        fillCoordinates();
        calculateMapSize();
    }

    private void initializeSampleCorridor() {
        this.addDungeonEntrance(0);
        try {
            this.addRoom(0, N, 3);
        this.addRoom(1, S, 3);
        this.addRoom(1, E, 2);
        this.addRoom(2, S, 5);
        this.addRoom(2, W, 1);
        this.addRoom(3, N, 1);
        this.addRoom(3, S, 0);
        this.addRoom(3, W, 4);
        this.addRoom(4, E, 3);
        this.addRoom(5, N, 2);
        this.addRoom(5, E, 6);
        this.addRoom(6, S, 8);
        this.addRoom(6, E, 7);
        this.addRoom(6, W, 5);
        this.addRoom(7, S, 9);
        this.addRoom(7, W, 6);
        this.addRoom(8, N, 6);
        this.addRoom(8, E, 9);
        this.addRoom(9, N, 7);
        this.addRoom(9, W, 8);
        } catch (InvalidInputDataException e) {
            e.printStackTrace();
        }
    }
}
