package com.tomtom;

import com.tomtom.elements.Dungeon;
import com.tomtom.elements.Player;
import com.tomtom.gameutils.ConsoleHandler;
import com.tomtom.gameutils.InputFileResolver;
import com.tomtom.gameutils.MapRenderer;
import com.tomtom.interfaces.IMove;
import com.tomtom.sampleData.sampleDungeon;
import javafx.util.Pair;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.tomtom.gameutils.InputFileResolver.getLinesFromFile;

public class Game {

    private Player player;
    private Dungeon dungeon;
    private final static Logger logger = Logger.getLogger("mud_logger");
    private static Map<Integer, List<Pair<IMove.MoveDirection, Integer>>> dungeonData;

    private void setPlayer(Player player) {
        this.player = player;
    }

    private Dungeon initializeSampleCorridor() {
        return new sampleDungeon();
    }

    private void getNextAction() {
        ConsoleHandler.printPossibleOptionsFor(player);
        IMove.MoveDirection moveDirection = ConsoleHandler.askForMoveDirection();
        player.move(moveDirection);
        logger.info(String.format("Player goes:\n%s", moveDirection.name()));
    }

    private void setDungeon(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    private static Map<Integer, List<Pair<IMove.MoveDirection, Integer>>> loadDungeonDataFromFile(String inputDungeonConfigFile) {
        try {
            dungeonData = InputFileResolver.getDungeonDataFrom(getLinesFromFile(inputDungeonConfigFile));
        } catch (IOException exc) {
            logger.error(String.format("Error while loading Dungeon Data from input file: \"%s\"\n%s", inputDungeonConfigFile, exc.getMessage()));

        } catch (IllegalArgumentException exc) {
            logger.error(String.format("Error while loading Dungeon Data from input file: \"%s\"", inputDungeonConfigFile));
            logger.error(exc.getMessage());
        }
        return dungeonData;
    }

    public static void main(String[] args) {
        java.util.logging.Logger.getLogger("com.almworks.sqlite4java").setLevel(java.util.logging.Level.WARNING);
        logger.info("------------------------------------------------");

        if (args.length > 0) {
            loadDungeonDataFromFile(args[0]);
        }

        Game game = new Game();
        Dungeon dungeon = new Dungeon();

        if (dungeonData == null) {
            dungeon = game.initializeSampleCorridor();
            logger.info("NO INPUT PROVIDED: Initialized game with sample dungeon.");
        } else {
            dungeon.createDungeonInputFileContent(dungeonData);
        }
        game.setDungeon(dungeon);

        Player player = new Player();
        player.setCurrentRoom(game.dungeon.getRoom(0));

        game.setPlayer(player);

        ConsoleHandler.printGreeting();
        logger.info(String.format("Player:\t%s", System.getProperty("user.name")));
        System.out.println();

        // GAME LOOP
        while (true) {
            System.out.println("------------------------------");
            MapRenderer.render(game.dungeon, game.player);
            game.getNextAction();
        }
    }


}
