package DomainModel;

public class Room {
    private int number;
    private short seats; //TODO: controlla se va bene FAI COMBACIARE COL DB

    public Room(int number, short seats) {
        this.number = number;
        this.seats = seats;
    }

    public int getNumber() {
        return number;
    }

    public short getSeats() {
        return seats;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setSeats(short seats) {
        this.seats = seats;
    }
}
