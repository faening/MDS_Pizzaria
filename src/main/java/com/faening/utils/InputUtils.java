package com.faening.utils;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputUtils {
    private static final Scanner scanner = new Scanner(System.in);

    public static String getValidStringInput(String message) {
        String input = null;
        boolean validInput = false;

        while (!validInput) {
            System.out.print(message);
            input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                validInput = true;
            } else {
                System.out.println(Messages.INVALID_INPUT);
            }
        }

        return input;
    }

    public static long getValidLongInput(String message) {
        long number = 0;
        boolean validInput = false;

        while (!validInput) {
            System.out.print(message);
            try {
                number = scanner.nextLong();
                scanner.nextLine();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println(Messages.INVALID_INPUT);
                scanner.next();
            }
        }

        return number;
    }

    public static float getValidFloatInput(String message) {
        float number = 0f;
        boolean validInput = false;

        while (!validInput) {
            System.out.print(message);
            try {
                number = scanner.nextFloat();
                scanner.nextLine();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println(Messages.INVALID_INPUT);
                scanner.next();
            }
        }

        return number;
    }
}
