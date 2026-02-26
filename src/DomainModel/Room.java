package DomainModel;

public class Room {
    private int number;
    private short seats; //TODO: controlla se va bene FAI COMBACIARE COL DB
    // valutare se aggiungere una lista di eventi che si svolgono in quella stanza
    //o stabilere se al massimo c'è un evento per giorno

    public Room(int number, short seats) {
        if (seats <= 0)
            throw new IllegalArgumentException("Seats must be positive");

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

    public int availableSeats(){
        return place.getSeats() - participants.size();
    }
}
