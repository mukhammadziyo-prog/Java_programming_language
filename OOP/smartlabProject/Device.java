package smartlabProject;

public class Device {
    private String id;
    private String model;
    private int batteryPercent;
    private boolean available;

    public Device(String id, String model) {
        this(id, model, 100, true);
    }

    public Device(String id, String model, int batteryPercent) {
        this(id, model, batteryPercent, true);
    }

    public Device(String id, String model, int batteryPercent, boolean available) {
        this.id = id;
        this.model = model;
        this.batteryPercent = Math.max(0, Math.min(100, batteryPercent));
        this.available = available;
    }

    public void assignToUser(String userName) {
        if (!available) {
            System.out.println(id + " hozir band.");
            return;
        }
        if (batteryPercent < 15) {
            System.out.println(id + " batareyasi juda kam (" + batteryPercent + "%). Berib bo‘lmaydi.");
            return;
        }
        available = false;
        System.out.println(id + " (" + model + ") " + userName + " ga biriktirildi.");
    }

    public void charge(int amount) {
        if (amount > 0) {
            batteryPercent = Math.min(100, batteryPercent + amount);
        }
    }

    public String getStatus() {
        return "ID: " + id +
                " | Model: " + model +
                " | Battery: " + batteryPercent + "%" +
                " | Available: " + available;
    }
}