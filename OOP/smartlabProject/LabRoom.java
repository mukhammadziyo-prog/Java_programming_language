package smartlabProject;
public class LabRoom {
    private final String roomCode;
    private int capacity;
    private boolean occupied;

    private static int totalRooms = 0;

    public LabRoom(String roomCode , int capacity){
        this.roomCode = roomCode;
        this.capacity = capacity;
        this.occupied = false;
        totalRooms ++;
    }
    public static int getTotalRooms(){
        return totalRooms;
    }
    public void bookRoom(){
        if(!occupied){
            occupied = true;
            System.out.println(roomCode + " Sonli Xona Band Qilindi.");
        } else {
            System.out.println("Xatolik " + roomCode + " Sonli Xona Band Qilingan");
        }
    }

    public void releaseRoom(){
        occupied = false;
        System.out.println("Xona Bo'shatildi.");
    }

    public String getRoomInfo(){
        String status = occupied ? "Band" : "Bo'sh";
        return "Xona Kodi: " + roomCode + " | Sigimi: " + capacity + " | Holati: " + status;
    }
}