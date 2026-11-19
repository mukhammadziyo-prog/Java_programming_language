package exercise1;

import java.util.Locale;

public class Dog extends Animal {

    public Dog(String name, int age, Double weight) {
        super(name, age, weight);
    }

    @Override
    public double getFeedInfoKg() {
        return getWeight() * 0.3;
    }

    @Override
    public String toString() {
        return String.format(
                Locale.US,
                "Dog name = %s, age = %d, mass = %.2f, feed = %.2f",
                getName(),
                getAge(),
                getWeight(),
                getFeedInfoKg()
        );
    }
}