package testPro;

public class BankAccount {
    String ownerName;
    double balance = 0;

    void deposit(double deposit){
        balance = balance + deposit;
        System.out.println(deposit + " so'm qo'shildi");
    }
    void withdraw(double amount){
        if(balance >= amount){
            balance -= amount;
            System.out.println(amount + " so'm pul yechildi.");
        } else {
            System.out.println("Mablag yetarli emas");
        }
    }
    void checkbalance(){
        System.out.println("Your current balance is: "+ balance);
    }
}