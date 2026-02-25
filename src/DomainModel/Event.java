package DomainModel;

import java.time.LocalTime;

public class Event {
    private LocalTime date;
    private String name;
    private String description;
    private Room place;


    public Event(LocalTime date, String name, String description, Room place) {
        this.date = date;
        this.name = name;
        this.description = description;
        this.place = place;
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
}
