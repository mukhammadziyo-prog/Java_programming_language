package Rent_a_Car;

public class Car {
    String model;
    String plateNumber;
    double dailyPrice;
    boolean isRented;

    // Konstruktorda isRented avtomatik false bo'ladi
    public Car(String model, String plateNumber, double dailyPrice) {
        this.model = model;
        this.plateNumber = plateNumber;
        this.dailyPrice = dailyPrice;
        this.isRented = false;
    }

    void rentCar(int days) {
        if (!isRented && days > 0) {
            isRented = true; // <--- MUHIM: Mashina endi band!
            double sumPrice = dailyPrice * days;
            System.out.println(model + " " + days + " kunga ijaraga berildi. Jami summa: " + sumPrice + " so'm.");
        } else if (isRented) {
            System.out.println("Kechirasiz, " + model + " (" + plateNumber + ") hozircha band!");
        } else {
            System.out.println("Kunlar soni 0 dan katta bo'lishi kerak!");
        }
    }

    void returnCar() {
        if (isRented) {
            isRented = false;
            System.out.println(model + " ijaradan qaytarildi. Endi u bo'sh.");
        } else {
            System.out.println(model + " allaqachon avtoparkda turibdi.");
        }
    }

    void showStatus() {
        String status = isRented ? "Band" : "Bo'sh"; // true/false o'rniga tushunarli so'z
        System.out.println("\n-- AvtoPark Holati --");
        System.out.println("Model: " + model);
        System.out.println("Avtoraqam: " + plateNumber);
        System.out.println("Kunlik ijara narxi: " + dailyPrice + " so'm");
        System.out.println("Holati: " + status);
    }
}

class Main {
    public static void main(String[] args) {
        Car car1 = new Car("Chevrolet Malibu 2", "01 M 001 MZ", 500000.0);

        car1.rentCar(3);    // Ijaraga berildi
        car1.rentCar(2);    // Tekshiramiz: band deyishi kerak
        car1.returnCar();   // Qaytarildi
        car1.rentCar(1);    // Qayta ijaraga berildi
        car1.showStatus();  // Holati: Band bo'lib chiqadi
    }
}
