package com.tomtom.elements;

import com.tomtom.sampleData.sampleDungeon;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.tomtom.gameutils.ConsoleHandler;

import static com.tomtom.interfaces.IMove.MoveDirection.*;

public class PlayerTest {
    private Dungeon dungeon;
    private Player player = new Player();

    @Before
    public void initialize(){
        dungeon = new sampleDungeon();
        System.out.println(dungeon.getMapOffset());
    }

    @Test
    public void moveNorthIsPossible() {
        player.setCurrentRoom(dungeon.getRoom(0));
        Assert.assertTrue(player.moveIsValid(NORTH));
    }

    @Test
    public void moveNorthIsNotPossible() {
        player.setCurrentRoom(dungeon.getRoom(1));
        Assert.assertFalse(player.moveIsValid(NORTH));
    }

    @Test
    public void moveSouthIsPossible() {
        player.setCurrentRoom(dungeon.getRoom(3));
        Assert.assertTrue(player.moveIsValid(SOUTH));
    }

    @Test
    public void moveSouthIsNotPossible() {
        player.setCurrentRoom(dungeon.getRoom(0));
        Assert.assertFalse(player.moveIsValid(SOUTH));
    }

    @Test
    public void moveWestIsPossible() {
        player.setCurrentRoom(dungeon.getRoom(3));
        Assert.assertTrue(player.moveIsValid(WEST));
    }

    @Test
    public void moveWestIsNotPossible() {
        player.setCurrentRoom(dungeon.getRoom(4));
        Assert.assertFalse(player.moveIsValid(WEST));
    }

    @Test
    public void moveEastIsPossible() {
        player.setCurrentRoom(dungeon.getRoom(4));
        Assert.assertFalse(player.moveIsValid(SOUTH));
    }

    @Test
    public void moveEastIsNotPossible() {
        player.setCurrentRoom(dungeon.getRoom(3));
        Assert.assertFalse(player.moveIsValid(EAST));
    }

    @Test
    public void printBasicMessage(){
        player.setCurrentRoom(dungeon.getRoom(3));
        ConsoleHandler.printPossibleOptionsFor(player);
        }

}
