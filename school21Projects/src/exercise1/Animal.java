package exercise1;

public abstract class Animal {

    private String name;
    private int age;
    private Double weight;

    public Animal(String name, int age, Double weight) {
        this.name = name;
        this.age = age;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Double getWeight() {
        return weight;
    }

    public abstract double getFeedInfoKg();
}