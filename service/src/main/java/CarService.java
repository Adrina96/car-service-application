import averangeBigDecimal.AverangeBD;
import com.maciaszek.adrian.AppException;
import com.maciaszek.adrian.Car;
import com.maciaszek.adrian.CarJsonConverter;
import com.maciaszek.adrian.CarValidator;
import com.maciaszek.adrian.enums.Color;
import enums.SortType;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class CarService {
    private final List<Car> cars;

    public CarService(String filename) {
        this.cars = readDataFronJson(filename);
    }

    private List<Car> readDataFronJson(String filename) {

        CarValidator carValidator = new CarValidator();
        AtomicInteger counter = new AtomicInteger(1);

        return new CarJsonConverter(filename)
                .fromJson()
                .orElseThrow(() -> new AppException("cars parsing from json has failed"))
                .stream()
                .filter(car -> {
                    Map<String, String> erros = carValidator.validate(car);

                    if (carValidator.hasErrors()) {
                        System.out.println("Validation errors for car no. " + counter.get());
                        erros.forEach((k, v) -> System.out.println(k + " " + v));
                    }
                    counter.incrementAndGet();
                    return !carValidator.hasErrors();
                }).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return cars.stream().map(Car::toString).collect(Collectors.joining("\n"));
    }

    /**
     * @param sortType   type of sort
     * @param descending is sorted collection in descending order
     * @return list of cars sorted by given criteria
     */
    public List<Car> sort(SortType sortType, boolean descending) {

        if (sortType == null) {
            throw new AppException("car service - sort - sort type is null");
        }

        List<Car> sortedCars = switch (sortType) {
            case COLOR -> cars.stream().sorted(Comparator.comparing(Car::getColor)).collect(Collectors.toList());
            case PRICE -> cars.stream().sorted(Comparator.comparing(Car::getPrice)).collect(Collectors.toList());
            case MILEAGE -> cars.stream().sorted(Comparator.comparing(Car::getMileage)).collect(Collectors.toList());
            case MODEL -> cars.stream().sorted(Comparator.comparing(Car::getModel)).collect(Collectors.toList());
        };

        if (descending) {
            Collections.reverse(sortedCars);
        }

        return sortedCars;
    }


    /**
     * @param mileage
     * @return cars with mileage grathen than
     */

    public List<Car> findMileagesGreaterThan(double mileage) {

        if (mileage <= 0) {
            throw new AppException("car service - findMileagesGreaterThan - mileage is not correct");
        }

        return cars
                .stream()
                .filter(car -> car.getMileage() > mileage)
                .collect(Collectors.toList());
    }

    /**
     * @return map of cars where key is car color and value is sum of cars with this color
     */
    public Map<Color, Long> mapOfCars() {
        return cars
                .stream()
                .collect(Collectors.groupingBy(Car::getColor, Collectors.counting()))
                .entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Long::max, LinkedHashMap::new));
    }


    /**
     * @return map of cars where key is car model and value is the most expensive car that model
     */

    public Map<String, Car> mapOfExpensiveCars() {
        return cars
                .stream()
                .collect(Collectors.groupingBy(
                        k -> k.getModel(),
                        Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparing(k -> k.getPrice())), car -> car.orElseThrow())));
    }


    public void statistics() {
        averagePriceofCar();
        minPriceOfCar();
        maxPriceOfCar();
        mileageStatistics();

    }

    private void averagePriceofCar() {
        System.out.println("The average price of cars  " + cars.stream().map(Car::getPrice).collect(new AverangeBD()));
    }

    private void minPriceOfCar() {
        cars.stream()
                .map(Car::getPrice)
                .min(Comparator.naturalOrder())
                .ifPresent(m -> System.out.println("The lowest price of the car  " + m));

    }

    private void maxPriceOfCar() {
        cars.stream()
                .map(Car::getPrice)
                .max(Comparator.naturalOrder())
                .ifPresent(m -> System.out.println("The highest price of the car  " + m));

    }

    private void mileageStatistics() {
        DoubleSummaryStatistics dsMileage = cars
                .stream()
                .collect(Collectors.summarizingDouble(c -> c.getMileage()));
        System.out.println("The average mileage of car " + dsMileage.getAverage());
        System.out.println("The lowest mileage of car " + dsMileage.getMin());
        System.out.println("The highest mileage of car " + dsMileage.getMax());
    }


    public List<Car> mostExpensive() {
        return cars
                .stream()
                .collect(Collectors.groupingBy(Car::getPrice))
                .entrySet().stream()
                .max(Comparator.comparing(Map.Entry::getKey))
                .orElseThrow(() -> new AppException("cannot find max price cars"))
                .getValue();
    }

    /**
     * @return list of cars with sorted components list
     */
    public List<Car> withSortedComponents() {
        return cars
                .stream()
                .peek(car -> car.setComponents(car.getComponents().stream().sorted().collect(Collectors.toList())))
                .collect(Collectors.toList());
    }


    /**
     * @return map with component as key and list of cars with this component as value
     */
    public Map<String, List<Car>> carsWithComponents() {

        return cars.stream()
                .flatMap(car -> car.getComponents().stream())
                .distinct()
                .collect(Collectors.toMap(
                        component -> component,
                        components -> cars.stream().filter(car -> car.getComponents().contains(components)).collect(Collectors.toList())));
    }


    /**
     * @param priceA
     * @param priceB
     * @return list of cars contains between priceA - priceB
     */
    public List carsWithPriceAB(BigDecimal priceA, BigDecimal priceB) {

        // BigDecimal
        if (priceA.compareTo(priceB) > 0 || priceA.compareTo(BigDecimal.ZERO) == 0 || priceB.compareTo(BigDecimal.ZERO) == 0) {
            throw new AppException("carsWithPriceAB - WRONG priceA OR priceB");
        }
        return cars
                .stream()
                .filter((c) -> c.getPrice().compareTo(priceA) > 0 && c.getPrice().compareTo(priceB) < 0)
                .sorted(Comparator.comparing(Car::getModel))
                .collect(Collectors.toList());

    }
}
