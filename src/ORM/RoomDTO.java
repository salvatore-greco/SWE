package ORM;

public class RoomDTO {
    private int number;
    private short seats;

    public RoomDTO(int number,  short seats) {
        this.number = number;
        this.seats = seats;
    }

    public int getNumber() {
        return number;
    }

    public short getSeats() {
        return seats;
    }
}