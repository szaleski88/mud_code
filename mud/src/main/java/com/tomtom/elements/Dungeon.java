package com.tomtom.elements;

import com.tomtom.interfaces.IMove;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tomtom.interfaces.IMove.MoveDirection.*;
import static java.lang.Math.abs;

public class Dungeon {

    private Map<Integer, Room> rooms = new HashMap<>();
    private Pair<Integer, Integer> mapOffset;
    private Pair<Integer, Integer> mapSize;

    protected void addDungeonEntrance(Integer id) {
        Room entry = new Room(id);
        entry.setX(0);
        entry.setY(0);
        mapOffset = new Pair<>(0, 0);
        this.rooms.put(id, entry);
    }


    protected void addRoom(Integer currentRoomId, IMove.MoveDirection moveDirection, Integer addedRoomId) {
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
        mapOffset = new Pair<>(this.rooms.values().stream().mapToInt(Room::getX).min().orElse(0),
                this.rooms.values().stream().mapToInt(Room::getY).min().orElse(0));
    }

    protected void calculateMapSize() {
        if(mapOffset == null){
            calculateMapOffset();
        }
        mapSize = new Pair<>(abs(mapOffset.getKey() - this.rooms.values().stream().mapToInt(Room::getX).max().orElse(0)),
                abs(mapOffset.getValue() - this.rooms.values().stream().mapToInt(Room::getY).max().orElse(0)));
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

    public void createDungeonInputFileContent(Map<Integer, List<Pair<IMove.MoveDirection, Integer>>> dungeonData) {
        assert dungeonData != null;

        for (Map.Entry<Integer, List<Pair<IMove.MoveDirection, Integer>> > mapEntry: dungeonData.entrySet()) {
            Integer currentRoomId = mapEntry.getKey();
            if(rooms.isEmpty()) {
                addDungeonEntrance(currentRoomId);
            }
            List<Pair<IMove.MoveDirection, Integer>> value = mapEntry.getValue();
            for (Pair<IMove.MoveDirection, Integer> neighboors : value){
                addRoom(currentRoomId, neighboors.getKey(), neighboors.getValue());
            }
        }
        calculateMapOffset();
        calculateMapSize();
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
