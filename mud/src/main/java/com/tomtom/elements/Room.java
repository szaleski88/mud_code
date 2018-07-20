package com.tomtom.elements;


import com.tomtom.interfaces.IMove;

public final class Room {

    private Integer roomId;
    private Room northRoom;
    private Room southRoom;
    private Room eastRoom;
    private Room westRoom;
    private Integer x;
    private Integer y;

    Room(Integer roomId) {
        this.roomId = roomId;
        this.x = null;
        this.y = null;
    }

    public Room getNorthRoom() {
        return northRoom;
    }

    public void setNorthRoom(Room northRoom) {
        if (this.northRoom == null) {
            this.northRoom = northRoom;
            if (this.x == null && this.y == null && northRoom.getX() != null && northRoom.getY() != null) {
                this.x = northRoom.getX();
                this.y = northRoom.getY() + 1;
            }
        }
    }

    public Room getSouthRoom() {
        return southRoom;
    }

    public void setSouthRoom(Room southRoom) {
        if (this.southRoom == null) {
            this.southRoom = southRoom;
            if (this.x == null && this.y == null && southRoom.getX() != null && southRoom.getY() != null) {
                this.x = southRoom.getX();
                this.y = southRoom.getY() - 1;
            }
        }
    }

    public Room getEastRoom() {
        return eastRoom;
    }

    public void setEastRoom(Room eastRoom) {
        if (this.eastRoom == null) {
            this.eastRoom = eastRoom;
            if (this.x == null && this.y == null && eastRoom.getX() != null && eastRoom.getY() != null) {
                this.x = eastRoom.getX() - 1;
                this.y = eastRoom.getY();
            }
        }
    }

    public Room getWestRoom() {
        return westRoom;
    }

    public void setWestRoom(Room westRoom) {
        if (this.westRoom == null) {
            this.westRoom = westRoom;
            if (this.x == null && this.y == null && westRoom.getX() != null && westRoom.getY() != null) {
                this.x = westRoom.getX() + 1;
                this.y = westRoom.getY();
            }
        }
    }

    public Room getRoomByDirection(IMove.MoveDirection moveDirection) {
        switch (moveDirection) {
            case EAST:
                return eastRoom;
            case WEST:
                return westRoom;
            case NORTH:
                return northRoom;
            case SOUTH:
                return southRoom;
            default:
                return null;
        }
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
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
        if (this.northRoom != null) {
            sb.append(String.format("\tnorthRoom=%d\n", northRoom.getRoomId()));
        }
        if (this.eastRoom != null) {
            sb.append(String.format("\teastRoom=%d\n", eastRoom.getRoomId()));
        }
        if (this.southRoom != null) {
            sb.append(String.format("\tsouthRoom=%d\n", southRoom.getRoomId()));
        }
        if (this.westRoom != null) {
            sb.append(String.format("\twestRoom=%d\n", westRoom.getRoomId()));
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
