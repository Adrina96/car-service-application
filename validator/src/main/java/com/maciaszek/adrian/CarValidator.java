package com.maciaszek.adrian;


import java.util.HashMap;
import java.util.Map;


public class CarValidator {
    private final Map<String, String> errors = new HashMap<>();

    public Map<String, String> validate(Car car) {

        errors.clear();

        if (car == null) {
            errors.put("car", "object is null");
        }

        if (!isModelValid(car)) {
            errors.put("model", "should contains only upper cases: " + car.getModel());
        }

        if (!isPriceValid(car)) {
            errors.put("price", "price should be greater than 0: " + car.getPrice());
        }
        if (!isColorValid(car)) {
            errors.put("color", "color is null");
        }
        if(isComponentsValid(car)){
            errors.put("components","components is empty");
        }

        return errors;
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    private boolean isModelValid(Car car) {
        return car.getModel() != null && car.getModel().matches("[A-Z]+");
    }

    private boolean isPriceValid(Car car) {
        return car.getPrice() != null && car.getPrice().doubleValue() >= 0;
    }

    private boolean isColorValid(Car car) {

        return car.getColor() != null;

    }
    private boolean isComponentsValid(Car car){
        return car.getComponents().isEmpty();
    }
}
