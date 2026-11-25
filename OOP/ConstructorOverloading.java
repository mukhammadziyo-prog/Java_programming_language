public class ConstructorOverloading {
    String accountHolder;
    String accountNumber;
    double balance = 0;


    public ConstructorOverloading(String accountHolder , String accountNumber , double balance) {
        this.accountHolder = accountHolder;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public ConstructorOverloading(String accountHolder , String accountNumber){
        this(accountHolder , accountNumber , 0.0);
        }

        public  ConstructorOverloading(){
        this("Unknown" , "0000-0000" , 0.0);

        }

        void deposit(double balance){
            if(balance > 0){
                this.balance += balance;
            } else {
                System.out.println("The deposit cannot be negative");
            }
        }

        void displayInfo(){
            System.out.println("Account Holder's Name: " + accountHolder);
            System.out.println("Account Number: " + accountNumber);
            System.out.println("Current balance: " + balance);
        }
    }

class Main {
    public static void main(String[] args) {
        // 3-konstruktor (Default)
        ConstructorOverloading myObj1 = new ConstructorOverloading();

        // 2-konstruktor (Balanssiz)
        ConstructorOverloading myObj2 = new ConstructorOverloading("Ali Valiyev", "1234-5678");
        myObj2.deposit(500.0); // Balansga 500 qo'shamiz

        // 1-konstruktor (To'liq)
        ConstructorOverloading myObj3 = new ConstructorOverloading("Dilshod Karimov", "9876-5432", 1200.0);

        // Natijalarni chiqarish
        myObj1.displayInfo();
        System.out.println("-----------------");
        myObj2.displayInfo();
        System.out.println("-----------------");
        myObj3.displayInfo();
    }
}

