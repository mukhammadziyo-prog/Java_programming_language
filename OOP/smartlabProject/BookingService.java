package smartlabProject;
public class BookingService {
    public static void main(String[] args) {
        // 2 ta LabRoom obyektini yaratamiz
        LabRoom lab1 = new LabRoom("LAB-101", 25);
        LabRoom lab2 = new LabRoom("LAB-202", 30);
        LabRoom lab3 = new LabRoom("LAB" , 4);

        // Umumiy xonalar sonini static metod orqali tekshiramiz
        System.out.println("Jami yaratilgan xonalar: " + LabRoom.getTotalRooms());
        System.out.println("-----------------------------------");

        // Boshlang'ich holatni ko'ramiz
        System.out.println(lab1.getRoomInfo());

        // 1-marta band qilish (muvaffaqiyatli)
        lab1.bookRoom();
        System.out.println(lab1.getRoomInfo());

        // 2-marta band qilishga urinish (xabar chiqadi)
        lab1.bookRoom();

        // Xonani bo'shatish va qayta tekshirish
        lab1.releaseRoom();
        System.out.println(lab1.getRoomInfo());
    }
}
