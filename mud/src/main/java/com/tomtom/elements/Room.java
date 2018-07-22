package com.tomtom.elements;


public final class Room {


    private int roomId;
    private final Room[] neighboringRooms = new Room[4];
    private Integer x;
    private Integer y;

    Room(int roomId) {
        this.roomId = roomId;
        this.x = null;
        this.y = null;
    }

    public Room getNeighboringRoomByDirection(MoveDirection moveDirection) {
        return neighboringRooms[moveDirection.getDirectionId()];
    }

    public int getRoomId() {
        return roomId;
    }

    public void setNeighboringRoom(Room room, MoveDirection moveDirection) throws InvalidInputDataException {
        Room nRoom = getNeighboringRoomByDirection(moveDirection);
        if (nRoom == null) {
            this.neighboringRooms[moveDirection.getDirectionId()] = room;
        } else {
            if (!nRoom.equals(room)) {
                throw new InvalidInputDataException(String.format("Invalid data. Current room id: %d. expected \"%s\" neighbor id %d." +
                        " In fact was: %d", this.roomId, moveDirection.name(), nRoom.roomId, room.roomId));
            }
        }
    }

    public Room[] getNeighboringRooms() {
        return neighboringRooms;
    }

    public Integer getX() {
        return x;
    }

    void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    void setY(Integer y) {
        this.y = y;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder(String.format("ROOM ID: %d\n", this.roomId));

        for (int i = 0; i < neighboringRooms.length; i++) {
            if (neighboringRooms[i] != null) {
                sb.append(String.format("\t%s=%d\n", MoveDirection.values()[i], neighboringRooms[i].getRoomId()));
            }
        }
        if (this.x != null) {
            sb.append(x.toString());
            sb.append(",\t");
        }
        if (this.y != null) {
            sb.append(y.toString());
        }
        return sb.toString();
    }

}
