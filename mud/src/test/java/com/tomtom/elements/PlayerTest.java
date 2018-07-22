package com.tomtom.elements;

import com.tomtom.sampleData.sampleDungeon;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.tomtom.gameutils.ConsoleHandler;

import static com.tomtom.elements.MoveDirection.*;

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
        Assert.assertTrue(player.moveIsValid(N));
    }

    @Test
    public void moveNorthIsNotPossible() {
        player.setCurrentRoom(dungeon.getRoom(1));
        Assert.assertFalse(player.moveIsValid(N));
    }

    @Test
    public void moveSouthIsPossible() {
        player.setCurrentRoom(dungeon.getRoom(3));
        Assert.assertTrue(player.moveIsValid(S));
    }

    @Test
    public void moveSouthIsNotPossible() {
        player.setCurrentRoom(dungeon.getRoom(0));
        Assert.assertFalse(player.moveIsValid(S));
    }

    @Test
    public void moveWestIsPossible() {
        player.setCurrentRoom(dungeon.getRoom(3));
        Assert.assertTrue(player.moveIsValid(W));
    }

    @Test
    public void moveWestIsNotPossible() {
        player.setCurrentRoom(dungeon.getRoom(4));
        Assert.assertFalse(player.moveIsValid(W));
    }

    @Test
    public void moveEastIsPossible() {
        player.setCurrentRoom(dungeon.getRoom(4));
        Assert.assertFalse(player.moveIsValid(S));
    }

    @Test
    public void moveEastIsNotPossible() {
        player.setCurrentRoom(dungeon.getRoom(3));
        Assert.assertFalse(player.moveIsValid(E));
    }

    @Test
    public void printBasicMessage(){
        player.setCurrentRoom(dungeon.getRoom(3));
        ConsoleHandler.printPossibleOptionsFor(player);
        }

}
