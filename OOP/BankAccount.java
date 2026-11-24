package TestPro5;

public class BankAccount {
    // 1. Atributlar
    String accountHolder;
    String accountNumber;
    double balance; // Konstruktor qiymat bergani uchun "= 0" shart emas

    // 2. Konstruktor (Atributlardan darhol keyin turishi tavsiya etiladi)
    public BankAccount(String accountHolder, String accountNumber, double balance) {
        this.accountHolder = accountHolder;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    // 3. Metodlar
    void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Hisobingizga " + amount + " so'm qo'shildi.");
        } else {
            System.out.println("Amount cannot be negative or zero!");
        }
    }

    void withdraw(double amount) {
        // <= belgisi hisobdagi bor pulni to'liq yechishga ruxsat beradi
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Yechildi: " + amount + " | Joriy balans: " + balance);
        } else {
            System.out.println("Insufficient funds or invalid amount!");
        }
    }

    void displayInfo() {
        System.out.println("\n--- Account Info ---");
        System.out.println("Account Holder's name: " + accountHolder);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Current balance: " + balance);
    }
}

class Main {
    public static void main(String[] args) {
        BankAccount account1 = new BankAccount("Ali Valiyev", "UZ1234", 500000.0);
        BankAccount account2 = new BankAccount("Saddam Husayn", "UZ12345", 100000.0);

        account1.deposit(50000.0);
        account1.withdraw(30000.0);
        account1.displayInfo(); // Ma'lumotlarni ekranga chiqarish

        account2.deposit(40000.0);
        account2.withdraw(140000.0); // Bor pulni to'liq yechib ko'ramiz
        account2.displayInfo();
    }
}
