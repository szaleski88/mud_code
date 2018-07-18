package com.tomtom.gameutils;

import com.tomtom.interfaces.IMove;
import javafx.util.Pair;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.*;

import static com.tomtom.interfaces.IMove.MoveDirection.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ConfigDataResolverTest {

    private List<String> modelList = Arrays.asList("r0 n:r3", "r1 s:r3 e:r2", "r2 s:r5 w:r1", "r3 n:r1 s:r0 w:r4", "r4 e:r3", "r5 n:r2 e:r6", "r6 s:r8 e:r7 w:r5", "r7 s:r9 w:r6", "r8 n:r6 e:r9", "r9 n:r7 w:r8");
    private Map<Integer, List<Pair<IMove.MoveDirection, Integer>>> modelDungeonData = new HashMap<>();

    public ConfigDataResolverTest(){
        for (int i = 0; i <= 9; i++) {
            modelDungeonData.put(i, new ArrayList<>());
        }
        modelDungeonData.get(0).add(pairFrom(NORTH,3));
        modelDungeonData.get(1).add(pairFrom(SOUTH,3));
        modelDungeonData.get(1).add(pairFrom(EAST,2));
        modelDungeonData.get(2).add(pairFrom(SOUTH,5));
        modelDungeonData.get(2).add(pairFrom(WEST,1));
        modelDungeonData.get(3).add(pairFrom(NORTH,1));
        modelDungeonData.get(3).add(pairFrom(SOUTH,0));
        modelDungeonData.get(3).add(pairFrom(WEST,4));
        modelDungeonData.get(4).add(pairFrom(EAST,3));
        modelDungeonData.get(5).add(pairFrom(NORTH,2));
        modelDungeonData.get(5).add(pairFrom(EAST,6));
        modelDungeonData.get(6).add(pairFrom(SOUTH,8));
        modelDungeonData.get(6).add(pairFrom(EAST,7));
        modelDungeonData.get(6).add(pairFrom(WEST,5));
        modelDungeonData.get(7).add(pairFrom(SOUTH,9));
        modelDungeonData.get(7).add(pairFrom(WEST,6));
        modelDungeonData.get(8).add(pairFrom(NORTH,6));
        modelDungeonData.get(8).add(pairFrom(EAST,9));
        modelDungeonData.get(9).add(pairFrom(NORTH,7));
        modelDungeonData.get(9).add(pairFrom(WEST,8));
    }

    private Pair<IMove.MoveDirection,Integer> pairFrom(IMove.MoveDirection md, int id) {
        return new Pair<IMove.MoveDirection, Integer>(md, id);
    }

    @Test(expected = NoSuchFileException.class)
    public void checkIfExceptionThrownOnNotExistingFile() throws IOException {
        ConfigDataResolver.getLinesFromFile("Beng");
    }


    @Test
    public void checkIfReturnedListIsEqualToExpected() throws IOException {
        List<String> list = ConfigDataResolver.getLinesFromFile("C:\\MOJE\\MUD\\JAVA\\mud\\basic_dungeon.txt");
        assertThat(list, is(modelList));
    }

    @Test
    public void checkMapDataIsParsedAsExpected(){
        Map<Integer, List<Pair<IMove.MoveDirection, Integer>>> dungeonData = ConfigDataResolver.getDungeonDataFrom(modelList);
        assertThat(dungeonData, is(modelDungeonData));
    }




}
