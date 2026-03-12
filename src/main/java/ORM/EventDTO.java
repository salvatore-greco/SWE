package ORM;



import java.time.Duration;
import java.time.LocalDateTime;

public class EventDTO {

    private Integer id = null;
    private LocalDateTime startDate;
    private Duration eventDuration;
    private String name;
    private String description;
    //Primary key di room e user
    private int place;
    private String organizer;

    public EventDTO(LocalDateTime startDate, Duration eventDuration, String name, String description, int place, String organizer) {
        this.startDate = startDate;
        this.eventDuration = eventDuration;
        this.name = name;
        this.description = description;
        this.place = place;
        this.organizer = organizer;
    }

    public EventDTO(Integer id, LocalDateTime startDate, Duration eventDuration, String name, String description, int place, String organizer) {
        this.id = id;
        this.startDate = startDate;
        this.eventDuration = eventDuration;
        this.name = name;
        this.description = description;
        this.place = place;
        this.organizer = organizer;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Duration getEventDuration() {
        return eventDuration;
    }

    public void setEventDuration(Duration eventDuration) {
        this.eventDuration = eventDuration;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
