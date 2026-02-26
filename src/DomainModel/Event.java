package DomainModel;

import java.time.LocalTime;

public class Event {
    private LocalTime date;
    private String name;
    private String description;
    private Room place;
    private Librarian organizer;
    private ArrayList<LibraryUser> participants;

    public Event(LocalTime date, String name, String description, Room place, Librarian organizer) {
        this.date = date; // valutare se usare una data di inizio e una di fine
        this.name = name;
        this.description = description;
        this.place = place;
        this.organizer = organizer;
        this.participants = new ArrayList<>();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalTime getDate() {
        return date;
    }

    public void setDate(LocalTime date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Room getPlace() {
        return place;
    }

    public void setPlace(Room place) {
        this.place = place;
    }

    public Librarian getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Librarian organizer) {
        this.organizer = organizer;
    }

    public ArrayList<LibraryUser> getParticipants() {
        return participants;
    }

    public void addParticipant(LibraryUser participant){
        if(participants.size() < place.getSeats())
            participants.add(participant);
            println("There are " + place.availableSeats() + " seats available");
        else
            throw new IllegalStateException("Room is full");
    }

     public void removeParticipant(LibraryUser participant){
        participants.remove(participant);
    }
}