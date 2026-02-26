package DomainModel;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.Duration;

public class Event {
    private LocalDateTime startDate;
    private Duration eventDuration;
    private String name;
    private String description;
    private Room place;
    private Librarian organizer;
    private ArrayList<LibraryUser> participants;

    public Event(LocalDateTime startDate, Duration eventDuration, String name, String description, Room place, Librarian organizer) {
        if (eventDuration.isNegative() || eventDuration.isZero())
            throw new IllegalArgumentException("Event duration must be positive");

        this.startDate = startDate;
        this.eventDuration = eventDuration;
        this.name = name;
        this.description = description;
        this.place = place;
        this.organizer = organizer;
        this.participants = new ArrayList<>();

        place.scheduleEvent(this);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setDate(LocalTime startDate) {
        this.startDate = startDate;
    }

    public Duration getEventDuration() {
        return eventDuration;
    }

    public void setEventDuration(Duration eventDuration) {
        if (eventDuration.isNegative() || eventDuration.isZero())
            throw new IllegalArgumentException("Event duration must be positive");
        this.eventDuration = eventDuration;
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
            System.out.println("There are " + place.availableSeats() + " seats available");
        else
            throw new IllegalStateException("Room is full");
    }

     public void removeParticipant(LibraryUser participant){
        participants.remove(participant);
    }

    public LocalDateTime getEndDate() {
        return startDate.plus(eventDuration);
    }

    public boolean overlaps(Event other) {
        return !(getEndDate().isBefore(other.startDate) || startDate.isAfter(other.getEndDate()));
    }

    public int availableSeats(){
        return place.getSeats() - participants.size();
    }
}