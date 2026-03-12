package DomainModel;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.Duration;

public class Event {

    private Integer id;
    private LocalDateTime startDate;
    private Duration eventDuration;
    private String name;
    private String description;
    private EventRoom place;
    private Librarian organizer;
    private ArrayList<LibraryUser> participants;

    public static class EventBuilder {
        private Integer id = null; ;
        private LocalDateTime startDate;
        private Duration eventDuration;
        private String name;
        private String description;
        private EventRoom place;
        private Librarian organizer;

        public EventBuilder setStartDate(LocalDateTime startDate) {
            this.startDate = startDate;
            return this;
        }

        public EventBuilder setEventDuration(Duration eventDuration) {
            this.eventDuration = eventDuration;
            return this;
        }

        public EventBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public EventBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public EventBuilder setPlace(EventRoom place) {
            this.place = place;
            return this;
        }

        public EventBuilder setOrganizer(Librarian organizer) {
            this.organizer = organizer;
            return this;
        }

        public EventBuilder setId(Integer id) {
            this.id = id;
            return this;
        }

        public Event build() {
            if (startDate == null)
                throw new IllegalStateException("Start date required");

            if (eventDuration == null || eventDuration.isNegative() || eventDuration.isZero())
                throw new IllegalArgumentException("Event duration must be positive");

            if (name == null || name.isBlank())
                throw new IllegalStateException("Name required");

            if (place == null)
                throw new IllegalStateException("Room required");

            if (organizer == null)
                throw new IllegalStateException("Organizer required");

//            if (!place.isAvailable(startDate, eventDuration))
//                throw new IllegalStateException("Room not available");

            return new Event(this);
        }
    }

    private Event(EventBuilder builder) {
        this.id = builder.id;
        this.startDate = builder.startDate;
        this.eventDuration = builder.eventDuration;
        this.name = builder.name;
        this.description = builder.description;
        this.place = builder.place;
        this.organizer = builder.organizer;
        this.participants = new ArrayList<>();

        place.scheduleEvent(this);
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public Integer getId() {
        return id;
    }

    public Duration getEventDuration() {
        return eventDuration;
    }

    public String getName() {
        return name;
    }

    public Room getPlace() {
        return place;
    }

    public Librarian getOrganizer() {
        return organizer;
    }

    public ArrayList<LibraryUser> getParticipants() {
        return participants;
    }

    public Event setId(Integer id) {
        this.id = id;
        return this;
    }
    public void addParticipant(LibraryUser participant){
        if(participants.contains(participant))
            throw new IllegalStateException("User already registered for the event");

        if(participants.size() < place.getSeats())
            participants.add(participant);
            //System.out.println("There are " + place.getAvailableSeats() + " seats available");
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

    public int getAvailableSeats(){
        return place.getSeats() - participants.size();
    }
}