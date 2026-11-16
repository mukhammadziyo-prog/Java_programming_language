package exercise1;

import java.util.Locale;

public class Cat extends Animal {

    public Cat(String name, int age, Double weight) {
        super(name, age, weight);
    }

    @Override
    public double getFeedInfoKg() {
        return getWeight() * 0.1;
    }

    @Override
    public String toString() {
        return String.format(
                Locale.US,
                "Cat name = %s, age = %d, mass = %.2f, feed = %.2f",
                getName(),
                getAge(),
                getWeight(),
                getFeedInfoKg()
        );
    }
}