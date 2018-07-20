package com.tomtom.elements;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Player implements IMove {

    private Room currentRoom;

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    @Override
    public boolean moveIsValid(MoveDirection moveDirection) {
        return currentRoom.getRoomByDirection(moveDirection) != null;
    }

    @Override
    public void move(MoveDirection moveDirection) {
        if (moveIsValid(moveDirection)) {
            currentRoom = currentRoom.getRoomByDirection(moveDirection);
        }
    }

    @Override
    public List<MoveDirection> getAvailableMoves() {
        return Stream.of(MoveDirection.values()).filter(this::moveIsValid).collect(Collectors.toList());
    }

    public Integer getCurrentRoomId() {
        return currentRoom.getRoomId();
    }
}
