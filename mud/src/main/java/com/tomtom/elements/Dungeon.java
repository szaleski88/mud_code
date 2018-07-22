package com.tomtom.elements;


import javafx.util.Pair;
import org.apache.log4j.Logger;

import java.util.*;

import static com.tomtom.elements.MoveDirection.*;
import static java.lang.Math.abs;
import static java.lang.Math.min;

public class Dungeon {

    private TreeMap<Integer, Room> rooms = new TreeMap<>();
    private Pair<Integer, Integer> mapOffset = new Pair<>(0,0);
    private Pair<Integer, Integer> mapSize = new Pair<>(0,0);
    private final static Logger logger = Logger.getLogger(Dungeon.class);

    protected void addDungeonEntrance(Integer id) {
        Room entry = new Room(id);
        entry.setX(0);
        entry.setY(0);
        updateMapOffset(entry);
        this.rooms.put(id, entry);
    }


    protected void addRoom(Integer currentRoomId, MoveDirection moveDirection, Integer addedRoomId) throws InvalidInputDataException {
        this.rooms.putIfAbsent(addedRoomId, new Room(addedRoomId));
        this.rooms.putIfAbsent(currentRoomId, new Room(currentRoomId));
        Room currentRoom = this.rooms.get(currentRoomId);
        Room addedRoom = this.rooms.get(addedRoomId);
        currentRoom.setNeighboringRoom(addedRoom, moveDirection);
    }

    private void calculateMapOffset() {
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

    public void createDungeonFromInputFileContent(Map<Integer, List<Pair<MoveDirection, Integer>>> dungeonData) throws InvalidInputDataException {
        if(dungeonData == null){
            throw new InvalidInputDataException("Input Cannot be null!");
        }

        for (Map.Entry<Integer, List<Pair<MoveDirection, Integer>>> mapEntry : dungeonData.entrySet()) {
            Integer currentRoomId = mapEntry.getKey();
            if (rooms.isEmpty()) {
                addDungeonEntrance(currentRoomId);
            }
            List<Pair<MoveDirection, Integer>> value = mapEntry.getValue();

            for (Pair<MoveDirection, Integer> neighboringRoom : value) {

                try {
                    if (checkIfConnectionValid(dungeonData, currentRoomId, neighboringRoom)) {
                        addRoom(currentRoomId, neighboringRoom.getKey(), neighboringRoom.getValue());
                    }
                } catch (NullPointerException | InvalidInputDataException exc) {
                    logger.error(String.format("invalid connection between: %d & %s%d", currentRoomId, neighboringRoom.getKey(), neighboringRoom.getValue()), exc);
                    throw exc;
                }
            }
        }
        fillCoordinates();
        calculateMapSize();
        deleteDisjointRooms();
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
        TreeMap<Integer, Room> cleanedRooms = new TreeMap<>(this.rooms);

        for (Map.Entry<Integer, Room> mapEntry : this.rooms.entrySet()) {
            Room currentRoom = mapEntry.getValue();
            if (currentRoom.getX() == null || currentRoom.getY() == null) {
                cleanedRooms.remove(mapEntry.getKey());
            }
        }

        this.rooms = cleanedRooms;
    }

    protected void fillCoordinates() {
        Room firstRoom = rooms.firstEntry().getValue();
        firstRoom.setX(0);
        firstRoom.setY(0);
        mapOffset = new Pair<>(0,0);

        Stack<Room> roomStack = new Stack<>();

        roomStack.push(firstRoom);

        while (!roomStack.empty()) {
            Room currentRoom = roomStack.pop();
            Room[] neighboringRooms = currentRoom.getNeighboringRooms();

            for (int i = 0; i < neighboringRooms.length; i++) {
                Room nRoom = neighboringRooms[i];
                if (nRoom != null && nRoom.getY() == null && nRoom.getX() == null) {
                    setCoordinates(nRoom, i, currentRoom);
                    updateMapOffset(nRoom);
                    roomStack.push(nRoom);
                }
            }
        }
    }

    private void updateMapOffset(Room nRoom) {
        Integer x = nRoom.getX();
        Integer y = nRoom.getY();
        if (x < mapOffset.getKey() || y < mapOffset.getValue()) {
            mapOffset = new Pair<>(min(x, mapOffset.getKey()), min(y, mapOffset.getValue()));
        }
    }

    private void setCoordinates(Room nRoom, int i, Room currentRoom) {
        Integer x = currentRoom.getX();
        Integer y = currentRoom.getY();

        switch (i) {
            case 0:
                nRoom.setX(x);
                nRoom.setY(y - 1);
                break;
            case 1:
                nRoom.setX(x);
                nRoom.setY(y + 1);
                break;
            case 2:
                nRoom.setX(x - 1);
                nRoom.setY(y);
                break;
            case 3:
                nRoom.setX(x + 1);
                nRoom.setY(y);
                break;
        }
    }

}
