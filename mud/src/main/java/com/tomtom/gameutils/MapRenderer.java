package com.tomtom.gameutils;

import com.tomtom.elements.Dungeon;
import com.tomtom.elements.Player;
import com.tomtom.elements.Room;
import javafx.util.Pair;

import java.util.Map;

public class MapRenderer {


    public static void render(Dungeon dungeon, Player player) {

        Map<Integer, Room> rooms = dungeon.getRooms();
        Pair<Integer, Integer> mapOffset = dungeon.getMapOffset();
        Pair<Integer, Integer> mapSize = dungeon.getMapSize();
        String[][] mapView = new String[(mapSize.getValue() * 2) + 2][(mapSize.getKey() * 2) + 2];

        for (Map.Entry<Integer, Room> roomEntry : rooms.entrySet()) {
            Room room = roomEntry.getValue();
            int x = 2 * (room.getX() - mapOffset.getKey());
            int y = 2 * (room.getY() - mapOffset.getValue());
            int roomId = room.getRoomId();
            if(0 <= roomId  && roomId < 10) {
                mapView[y][x] = String.format("r%d", roomId);
            } else {
                mapView[y][x] = String.format("%d", roomId);
            }
            mapView[y + 1][x] = room.getSouthRoom() != null ? "| " : "  ";
            mapView[y][x + 1] = room.getEastRoom() != null ? "-" : " ";
        }

        Room occupiedRoom = rooms.get(player.getCurrentRoomId());
        int x = 2 * (occupiedRoom.getX() - mapOffset.getKey());
        int y = 2 * (occupiedRoom.getY() - mapOffset.getValue());
        mapView[y][x] = "**";

        for (int i = 0; i < mapView.length; i++) {
            for (int j = 0; j < mapView[0].length; j++) {
                System.out.print(mapView[i][j] != null ? mapView[i][j] : j % 2 == 0 ? "  " : " ");
            }
            System.out.println();
        }
    }

}
