package com.tomtom.gameutils;

import com.tomtom.elements.Dungeon;
import com.tomtom.elements.Player;
import com.tomtom.elements.Room;
import javafx.util.Pair;

import java.util.Map;

import static com.tomtom.elements.MoveDirection.E;
import static com.tomtom.elements.MoveDirection.S;

public class MapRenderer {


    public static void render(Dungeon dungeon, Player player) {

        Map<Integer, Room> rooms = dungeon.getRooms();
        Pair<Integer, Integer> mapOffset = dungeon.getMapOffset();
        Pair<Integer, Integer> mapSize = dungeon.getMapSize();
        String[][] mapViewElements = new String[(mapSize.getValue() * 2) + 2][(mapSize.getKey() * 2) + 2];
        StringBuilder mapToRender = new StringBuilder();
        for (Map.Entry<Integer, Room> roomEntry : rooms.entrySet()) {
            Room room = roomEntry.getValue();
            int x = 2 * (room.getX() - mapOffset.getKey());
            int y = 2 * (room.getY() - mapOffset.getValue());
            int roomId = room.getRoomId();
            if (0 <= roomId && roomId < 10) {
                mapViewElements[y][x] = String.format("r%d", roomId);
            } else {
                mapViewElements[y][x] = String.format("%d", roomId);
            }
            mapViewElements[y + 1][x] = room.getNeighboringRoomByDirection(S) != null ? "| " : "  ";
            mapViewElements[y][x + 1] = room.getNeighboringRoomByDirection(E) != null ? "-" : " ";
        }

        Room occupiedRoom = rooms.get(player.getCurrentRoomId());
        int x = 2 * (occupiedRoom.getX() - mapOffset.getKey());
        int y = 2 * (occupiedRoom.getY() - mapOffset.getValue());
        mapViewElements[y][x] = "**";

        for (int i = 0; i < mapViewElements.length; i++) {
            for (int j = 0; j < mapViewElements[0].length; j++) {
                mapToRender.append(mapViewElements[i][j] != null ? mapViewElements[i][j] : j % 2 == 0 ? "  " : " ");
            }
            mapToRender.append("\n");
        }

        System.out.println(mapToRender.toString());

    }

}
