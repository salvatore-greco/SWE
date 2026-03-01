package ORM;

public class EventDTO {
    private int id;
    private String name;
    private String description;
    private LocalDateTime date;
    private Duration duration;

    public EventDTO(int id, String name, String description, LocalDateTime date, Duration duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Duration getDuration() {
        return duration;
    }
}