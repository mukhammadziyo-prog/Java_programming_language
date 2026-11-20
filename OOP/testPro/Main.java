package testPro;
public class Main{

    public static void main(String[] args){
        BankAccount userAccount1 = new BankAccount();

        userAccount1.ownerName = "Muhammadziyo";
        userAccount1.balance = 100000;

        userAccount1.deposit(50000);
        userAccount1.withdraw(50000);
        userAccount1.withdraw(50000);
        userAccount1.withdraw(40000);
        userAccount1.checkbalance();

    }
}