package com.tomtom.gameutils;

import com.tomtom.elements.Player;
import com.tomtom.interfaces.IMove;

import java.util.Scanner;
import java.util.stream.Collectors;

import static com.tomtom.interfaces.IMove.MoveDirection.findByFirstLetter;

public class ConsoleHandler {

    static Scanner scanner = new Scanner(System.in);

    public static void printPossibleOptionsFor(Player player) {
        String availableDirections = player.getAvailableMoves().stream().map(IMove.MoveDirection::getDirection).collect(Collectors.joining(""));
        Integer currentRoomId = player.getCurrentRoomId();


        System.out.printf("you are in room r%d\npossible moves: %s\n",
                currentRoomId,
                availableDirections
        );
    }

    public static IMove.MoveDirection askForMoveDirection() {
        IMove.MoveDirection choice;
        boolean printedGoodbye = false;
        do {
            System.out.print("your choice: ");
            while (!scanner.hasNextLine() && !printedGoodbye) {
                System.out.printf("THANKS FOR PLAYING %s!!!\n", System.getProperty("user.name"));
                printedGoodbye = true;
            }
            choice = findByFirstLetter(scanner.nextLine());
        } while (choice == null);

        return choice;
    }

    public static void printGreeting() {
        System.out.println(
                ".----------------.  .----------------.  .----------------. \n" +
                        "| .--------------. || .--------------. || .--------------. |\n" +
                        "| | ____    ____ | || | _____  _____ | || |  ________    | |\n" +
                        "| ||_   \\  /   _|| || ||_   _||_   _|| || | |_   ___ `.  | |\n" +
                        "| |  |   \\/   |  | || |  | |    | |  | || |   | |   `. \\ | |\n" +
                        "| |  | |\\  /| |  | || |  | '    ' |  | || |   | |    | | | |\n" +
                        "| | _| |_\\/_| |_ | || |   \\ `--' /   | || |  _| |___.' / | |\n" +
                        "| ||_____||_____|| || |    `.__.'    | || | |________.'  | |\n" +
                        "| |              | || |              | || |              | |\n" +
                        "| '--------------' || '--------------' || '--------------' |\n " +
                        ".----------------.  .----------------.  .----------------.\n" +
                        "by: Sebastian Zaleski\n\n");
        System.out.printf("WELCOME %s!!!\n\n", System.getProperty("user.name"));
    }


}
