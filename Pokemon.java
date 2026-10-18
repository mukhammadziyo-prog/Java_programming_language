public class Pokemon {
    // 1. Maydonlar main-dan tashqarida bo'ladi
    private String name;
    private int level;

    // 2. Konstruktor ham main-dan tashqarida bo'ladi
    public Pokemon(String name, int level) {
        this.name = name;
        this.level = level;
    }

    // 3. Metod ham main-dan tashqarida bo'ladi
    public void attack() {
        System.out.println(this.name + " attack!");
    }

    // 4. MAIN METODI - faqat dasturni yurgizish va obyekt yaratish uchun!
    public static void main(String[] args) {
        // Bu yerda yangi pokemon yaratamiz (Xuddi JS dagi new Pokemon...)
        Pokemon pika = new Pokemon("Pikachu", 99);

        // Uni hujum qildiramiz
        pika.attack();
    }
}
