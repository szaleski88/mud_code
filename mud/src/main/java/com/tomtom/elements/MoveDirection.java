package com.tomtom.elements;

public enum MoveDirection {
    N(0), S(1), W(2), E(3);

    private int directionId;

    MoveDirection(int directionId) {
        this.directionId = directionId;
    }

    public int getDirectionId() {
        return directionId;
    }

    public static MoveDirection findByFirstLetter(String letter) {
        for (MoveDirection enumValue : MoveDirection.values()) {
            if (enumValue.name().equalsIgnoreCase(letter)) {
                return enumValue;
            }
        }
        return null;
    }

    public static MoveDirection getOppositeDirection(MoveDirection moveDirection) {
        switch (moveDirection) {
            case W:
                return E;
            case E:
                return W;
            case N:
                return S;
            case S:
                return N;
            default:
                return null;
        }
    }
}