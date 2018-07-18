package com.tomtom.interfaces;

import java.util.List;

public interface IMove {

    enum MoveDirection {NORTH("n"), SOUTH("s"), WEST("w"), EAST("e");

    private String direction;

    MoveDirection(String direction){
        this.direction = direction;
    }

        public String getDirection() {
            return direction;
        }

        public static MoveDirection findByFirstLetter(String letter) {
            for (MoveDirection enumValue : MoveDirection.values()) {
                if (enumValue.direction.equalsIgnoreCase(letter)) {
                    return enumValue;
                }
            }
            return null;
        }
    }

    boolean moveIsValid(MoveDirection moveDirection);

    void move(MoveDirection moveDirection);

    List<MoveDirection> getAvailableMoves();
}
