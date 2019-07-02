package com.maciaszek.adrian;

public class Main {
    public static void main(String[] args) {

        final var filename = "cars.json";
        var menuService = new MenuService(filename);
        menuService.mainMenu();



    }
}