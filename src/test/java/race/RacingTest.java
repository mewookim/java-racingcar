package race;

import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import racing.RacingGame;
import racing.model.Car;
import racing.model.Cars;
import racing.model.Racing;
import sun.util.resources.cldr.ar.CalendarData_ar_SY;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RacingTest {

    RacingGame racingGame = new RacingGame();
    Racing racing = new Racing();
    List<Car> cars = new ArrayList<>();

    @BeforeEach
    void setting() {
        racing.prepareRacing("test1,test2,test3", 5);
        racingGame.game(racing,1);
        cars = racing.participants().carList();
    }


    @Test
    @DisplayName("move condition test")
    void randomValueTest() {
        assertThatThrownBy(() -> {
            racingGame.moveCondition(10);
        }).isInstanceOf(IllegalStateException.class);
    }

    @ParameterizedTest
    @DisplayName("game end test")
    @CsvSource(value = {"3:false", "5:true"}, delimiter = ':')
    void gameEndTest(int input, boolean expected) {
       assertThat(racingGame.game(racing, input)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"3:false", "5:true"}, delimiter = ':')
    @DisplayName("car move Test")
    void carMoveTest(int randomValue, boolean expected) {
        assertThat(racingGame.run(cars.get(0), randomValue)).isEqualTo(expected);
    }

    @Test
    @DisplayName("winner test")
    void winnerTest() {
        List<Car> testCars = new ArrayList<>();
        testCars.add(new Car("test1", 10));
        testCars.add(new Car("test2", 30));
        testCars.add(new Car("test3", 20));
        cars = testCars;
        racing = new Racing(cars);
        assertThat(racing.winner().get(0).getCarInfo()).isEqualTo("test2");
    }


    @Test
    @DisplayName("more than one winner test")
    void moreThanOneWinnerTest() {
        List<Car> testCars = new ArrayList<>();
        testCars.add(new Car("test1", 30));
        testCars.add(new Car("test2", 30));
        testCars.add(new Car("test3", 30));
        cars = testCars;
        racing = new Racing(cars);
        Assertions.assertThat(racing.winner().stream())
                .extracting(Car::getCarInfo)
                .containsExactly("test1", "test2", "test3");
    }

}
