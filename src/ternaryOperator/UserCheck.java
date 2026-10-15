public class UserCheck{
    public static void main(String[]args) {

        String user = "admin";
        int password = 123456;

        if(user.equals("admin")) {

            if(password == 123456) {

                System.out.println("welcome");
            } else {
                System.out.println("Parol xato");
            }
        } else {
            System.out.println("There is no user");
        }

    }
}