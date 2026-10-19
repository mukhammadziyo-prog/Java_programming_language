public class RegisterBook {
    public static void main(String[]args) {
        boolean isRegistered = true;
        boolean isPremium = true;
        boolean isfree = false;
        boolean isBlocked = false;

        boolean canDownload = isRegistered && (isPremium || isfree) && !isBlocked;

        System.out.println("Can we dowload the book: "+ canDownload);
    }
}
