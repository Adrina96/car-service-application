package com.maciaszek.adrian;

import com.maciaszek.adrian.enums.SortType;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class MenuService {

    private final CarService carService;

    public MenuService(String filename) {
        carService = new CarService(filename);
    }

    public void mainMenu() {

        do {
            int option = printMenu();
            switch (option) {
                case 1 -> option1();
                case 2 -> option2();
                case 3 -> option3();
                case 4 -> option4();
                case 5 -> option5();
                case 6 -> option6();
                case 7 -> option7();
                case 8 -> option8();
                case 9 -> option9();
                // ...
                case 10 -> {
                    System.out.println("Have a nice day!");
                    return;
                }
                default -> System.out.println("No option with given number");
            }
        } while (true);

    }

    private int printMenu() {
        System.out.println("1. Show cars");
        System.out.println("2. Sort cars");
        System.out.println("3. Show Cars with milleage grathen than..");
        System.out.println("4. Show Sum of cars with the same Color");
        System.out.println("5. Show The most expensive car of a given model");
        System.out.println("6. Show Cars stratistics");
        System.out.println("7. Show The most expensive car/cars");
        System.out.println("8. Sort car components");
        System.out.println("9. Show cars with price between a - b");
        System.out.println("10. End of app");
        return UserDataUtils.getInt("Choose your option:");
    }

    private void option1() {
        System.out.println(carService.toString());
    }

    private void option2() {
        boolean isDescending = UserDataUtils.getBoolean("Descending");
        SortType sortType = UserDataUtils.getSortType();
        carService.sort(sortType, isDescending).forEach(System.out::println);
    }

    private void option3() {
        double milleage = UserDataUtils.getDouble("Milleage");
        carService.findMileagesGreaterThan(milleage).forEach(System.out::println);
    }

    private void option4() {
        carService.mapOfCars().forEach((k, v) -> System.out.println(k + " " + v));
    }

    private void option5() {
        carService.mapOfExpensiveCars().forEach((k, v) -> System.out.println(k + " " + v));
    }

    private void option6() {
        carService.statistics();
    }

    private void option7() {
        carService.mostExpensive().forEach(System.out::println);
    }

    private void option8() {
        carService.withSortedComponents().forEach(System.out::println);

    }

    private void option9() {

        double priceA = 0.0;
        double priceB = 0.0;

        do {
            priceA = UserDataUtils.getDouble("Price from: ");
            priceB = UserDataUtils.getDouble("Price to: ");
        } while (priceA > priceB);

        carService.carsWithPriceAB(new BigDecimal(priceA), new BigDecimal(priceB)).forEach(System.out::println);


    }
}

