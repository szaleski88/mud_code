package com.tomtom.elements;

import com.tomtom.interfaces.IMove.MoveDirection;
import javafx.util.Pair;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tomtom.interfaces.IMove.MoveDirection.*;
import static java.lang.Math.abs;

public class Dungeon {

    private Map<Integer, Room> rooms = new HashMap<>();
    private Pair<Integer, Integer> mapOffset;
    private Pair<Integer, Integer> mapSize;
    private final static Logger logger = Logger.getLogger(Dungeon.class);

    protected void addDungeonEntrance(Integer id) {
        Room entry = new Room(id);
        entry.setX(0);
        entry.setY(0);
        mapOffset = new Pair<>(0, 0);
        this.rooms.put(id, entry);
    }


    protected void addRoom(Integer currentRoomId, MoveDirection moveDirection, Integer addedRoomId) {
        this.rooms.putIfAbsent(addedRoomId, new Room(addedRoomId));
        this.rooms.putIfAbsent(currentRoomId, new Room(currentRoomId));
        Room currentRoom = this.rooms.get(currentRoomId);
        Room addedRoom = this.rooms.get(addedRoomId);
        switch (moveDirection) {
            case EAST:
                currentRoom.setEastRoom(addedRoom);
                addedRoom.setWestRoom(currentRoom);
                break;
            case WEST:
                currentRoom.setWestRoom(addedRoom);
                addedRoom.setEastRoom(currentRoom);
                break;
            case NORTH:
                currentRoom.setNorthRoom(addedRoom);
                addedRoom.setSouthRoom(currentRoom);
                break;
            case SOUTH:
                currentRoom.setSouthRoom(addedRoom);
                addedRoom.setNorthRoom(currentRoom);
                break;
        }
    }

    protected void calculateMapOffset() {
        try {
            int minX = this.rooms.values().stream()
                    .mapToInt(Room::getX)
                    .min()
                    .orElse(0);

            int minY = this.rooms.values().stream()
                    .mapToInt(Room::getY)
                    .min()
                    .orElse(0);

            mapOffset = new Pair<>(minX, minY);
        } catch (Exception e) {
            logger.error("ERROR WHILE CALCULATING MAP OFFSET!", e);
        }
    }

    protected void calculateMapSize() {
        if (mapOffset == null) {
            calculateMapOffset();
        }

        int xRange = abs(mapOffset.getKey() - this.rooms.values().stream()
                .filter(room -> room.getX() != null && room.getY() != null)
                .mapToInt(Room::getX)
                .max()
                .orElse(0));

        int yRange = abs(mapOffset.getValue() - this.rooms.values().stream()
                .filter(room -> room.getX() != null && room.getY() != null)
                .mapToInt(Room::getY)
                .max()
                .orElse(0));

        mapSize = new Pair<>(xRange, yRange);
    }

    public Room getRoom(Integer index) {
        return rooms.get(index);
    }

    public Map<Integer, Room> getRooms() {
        return rooms;
    }

    public Pair<Integer, Integer> getMapOffset() {
        return mapOffset;
    }

    public Pair<Integer, Integer> getMapSize() {
        return mapSize;
    }

    public void createDungeonInputFileContent(Map<Integer, List<Pair<MoveDirection, Integer>>> dungeonData) {
        assert dungeonData != null;

        for (Map.Entry<Integer, List<Pair<MoveDirection, Integer>>> mapEntry : dungeonData.entrySet()) {
            Integer currentRoomId = mapEntry.getKey();
            if (rooms.isEmpty()) {
                addDungeonEntrance(currentRoomId);
            }
            List<Pair<MoveDirection, Integer>> value = mapEntry.getValue();

            for (Pair<MoveDirection, Integer> neighboringRoom : value) {
                if (checkIfConnectionValid(dungeonData, currentRoomId, neighboringRoom)) {
                    addRoom(currentRoomId, neighboringRoom.getKey(), neighboringRoom.getValue());
                }
            }
        }
        deleteDisjointRooms();
        calculateMapOffset();
        calculateMapSize();
    }

    private boolean checkIfConnectionValid(Map<Integer, List<Pair<MoveDirection, Integer>>> dungeonData,
                                           Integer currentRoomId,
                                           Pair<MoveDirection, Integer> neighboor) {

        MoveDirection oppositeDirection = getOppositeDirection(neighboor.getKey());

        List<Pair<MoveDirection, Integer>> neighboringRooms = dungeonData.get(neighboor.getValue());

        return neighboringRooms.stream()
                .anyMatch(pair -> pair.getKey().equals(oppositeDirection) && pair.getValue().equals(currentRoomId));
    }

    private void deleteDisjointRooms() {
        Map<Integer, Room> cleanedRooms = new HashMap<>(this.rooms);

        for (Map.Entry<Integer, Room> mapEntry : this.rooms.entrySet()) {
            Room currentRoom = mapEntry.getValue();
            if (currentRoom.getX() == null || currentRoom.getY() == null) {
                cleanedRooms.remove(mapEntry.getKey());
            }
        }

        this.rooms = cleanedRooms;
    }


    public static void main(String[] args) {
        Dungeon dungeon = new Dungeon();
        dungeon.addDungeonEntrance(0);
        dungeon.addRoom(0, NORTH, 3);
        dungeon.addRoom(1, SOUTH, 3);
        dungeon.addRoom(1, EAST, 2);
        dungeon.addRoom(2, SOUTH, 5);
        dungeon.addRoom(2, WEST, 1);
        dungeon.addRoom(3, NORTH, 1);
        dungeon.addRoom(3, SOUTH, 0);
        dungeon.addRoom(3, WEST, 4);
        dungeon.addRoom(4, EAST, 3);
        dungeon.addRoom(5, NORTH, 2);
        dungeon.addRoom(5, EAST, 6);
        dungeon.addRoom(6, SOUTH, 8);
        dungeon.addRoom(6, EAST, 7);
        dungeon.addRoom(6, WEST, 5);
        dungeon.addRoom(7, SOUTH, 9);
        dungeon.addRoom(7, WEST, 6);
        dungeon.addRoom(8, NORTH, 6);
        dungeon.addRoom(8, EAST, 9);
        dungeon.addRoom(9, NORTH, 7);
        dungeon.addRoom(9, WEST, 8);

        for (Map.Entry<Integer, Room> room : dungeon.rooms.entrySet()) {
            System.out.println(room.getKey().toString() + room.getValue());
        }
        dungeon.calculateMapOffset();
    }


}
