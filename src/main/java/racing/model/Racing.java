package racing.model;

import java.util.List;

public class Racing {

    private Cars cars = new Cars();
    private RandomMovingStrategy randomMovingStrategy = new RandomMovingStrategy();

    public Racing(String[] carNames) {
        this.cars.prepareCars(carNames);
    }

    public List<Car> winnerCarList() {
        return cars.findWinner();
    }

    public List<Car> game() {
        return cars.carsMove(randomMovingStrategy);
    }
}
