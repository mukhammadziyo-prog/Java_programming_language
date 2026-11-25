public class SkriptForOverloadConstuctor {
    String accountHolder;
    String accountNumber;
    double balance = 0;

    public SkriptForOverloadConstuctor(String accountHolder , String accountNumber , double balance){
        this.accountHolder = accountHolder;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public SkriptForOverloadConstuctor(String accountHolder , String accountNumber){
        this(accountHolder , accountNumber , 0.0);
    }
    public SkriptForOverloadConstuctor(){
        this("Unknown" , "0000-0000" , 0.0);
    }
}