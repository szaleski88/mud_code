package com.tomtom.elements;

import java.util.List;

public interface IMove {

    boolean moveIsValid(MoveDirection moveDirection);

    void move(MoveDirection moveDirection);

    List<MoveDirection> getAvailableMoves();
}
