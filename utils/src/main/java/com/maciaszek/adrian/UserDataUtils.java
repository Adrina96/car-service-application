package com.maciaszek.adrian;

import enums.SortType;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class UserDataUtils {

    private UserDataUtils() {
    }

    private static Scanner sc = new Scanner(System.in);

    public static int getInt(String message) {

        if (message == null) {
            throw new AppException("get int message is null");
        }

        System.out.println(message);
        String text = sc.nextLine();

        if (!text.matches("\\d+")) {
            throw new AppException("illegal int value");
        }

        return Integer.parseInt(text);
    }

    public static double getDouble(String message) {

        if (message == null) {
            throw new AppException("get double message is null");
        }

        System.out.println(message);
        String text = sc.nextLine();

        if (!text.matches("\\d+\\.\\d+")) {
            if (!text.matches("\\d+")) {
                throw new AppException("illegal double value");
            }
        }

        return Double.parseDouble(text);
    }


    public static String getString(String message) {

        if (message == null) {
            throw new AppException("get string message is null");
        }
        System.out.println(message);
        return sc.nextLine();
    }

    public static boolean getBoolean(String message) {
        if (message == null) {
            throw new AppException("get string message is null");
        }

        System.out.println(message + "[y/n]");
        return sc.nextLine().equals("y");
    }

    public static SortType getSortType() {
        AtomicInteger counter = new AtomicInteger(1);
        Arrays
                .stream(SortType.values())
                .forEach(sortingType -> System.out.println(counter.getAndIncrement() + ". " + sortingType));
        System.out.println("Enter sorting type option number:");
        String text = sc.nextLine();

        if (!text.matches("[1-" + SortType.values().length + "]")) {
            throw new AppException("illegal sorting type option number");
        }

        return SortType.values()[Integer.parseInt(text) - 1];
    }
}

