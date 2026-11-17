
package exercise4;

public class Dog extends Animal {

    public Dog(String name, int age) {
        super(name, age);
    }

    @Override
    public double goToWalk() {
        return getAge() * 0.5;
    }

    @Override
    public String toString() {
        return "Dog name = " + getName()
                + ", age = " + getAge();
    }
}
