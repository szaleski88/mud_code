package com.tomtom.gameutils;

import com.tomtom.elements.MoveDirection;
import com.tomtom.elements.Player;

import java.util.Scanner;
import java.util.stream.Collectors;

import static com.tomtom.elements.MoveDirection.findByFirstLetter;

public class ConsoleHandler {

    private static Scanner scanner = new Scanner(System.in);

    public static void printPossibleOptionsFor(Player player) {
        String availableDirections = player.getAvailableMoves().stream()
//                                                                .map(MoveDirection::getDirectionId)
                                                                .map(md -> md.toString().toLowerCase())
                                                                .collect(Collectors.joining(""));
        Integer currentRoomId = player.getCurrentRoomId();


        System.out.printf("you are in room r%d\npossible moves: %s\n",
                currentRoomId,
                availableDirections
        );
    }

    public static MoveDirection askForMoveDirection() {
        MoveDirection choice;
        do {
            System.out.print("your choice: ");
            while (!scanner.hasNextLine()) {
                System.out.printf("THANKS FOR PLAYING %s!!!\n", System.getProperty("user.name"));
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
                        ".----------------.  .----------------.  .----------------.\n"
//                       + "by: Sebastian Zaleski\n\n"
        );
        System.out.printf("WELCOME %s!!!\n\n", System.getProperty("user.name"));
    }


}
