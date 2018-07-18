package com.tomtom;

import com.tomtom.elements.Dungeon;
import com.tomtom.elements.Player;
import com.tomtom.gameutils.ConfigDataResolver;
import com.tomtom.gameutils.ConsoleHandler;
import com.tomtom.gameutils.MapRenderer;
import com.tomtom.interfaces.IMove;
import com.tomtom.mockData.MockDungeon;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Properties;

public class Game {

    private Player player;
    private Dungeon dungeon;
    private final static Logger logger = Logger.getLogger(Game.class);
    private void setPlayer(Player player) {
        this.player = player;
    }

    private void initializeSampleCorridor() {
        this.dungeon = new MockDungeon();
    }

    private void getNextAction() {
        ConsoleHandler.printPossibleOptionsFor(player);
        IMove.MoveDirection moveDirection = ConsoleHandler.askForMoveDirection();
        player.move(moveDirection);
    }


    public static void main(String[] args) {
        java.util.logging.Logger.getLogger("com.almworks.sqlite4java").setLevel(java.util.logging.Level.WARNING);
        logger.info("------------------------------------------------");
        String configFilepath = args[0];
        File configFile = new File(configFilepath);
        if (configFile.exists()) {
            try{
                Properties properties = ConfigDataResolver.loadFile(configFile);
                logger.info("Config file loaded: \t" + configFilepath);

            } catch(FileNotFoundException e){
                logger.warn("Config file not found.");
            }
        }

        Game game = new Game();
        game.initializeSampleCorridor();

        Player player = new Player();
        player.setCurrentRoom(game.dungeon.getRoom(0));

        game.setPlayer(player);
        ConsoleHandler.printGreeting();

        System.out.println();
        while (true) {
            System.out.println("------------------------------");
            MapRenderer.render(game.dungeon, game.player);
            game.getNextAction();
        }
    }

}
