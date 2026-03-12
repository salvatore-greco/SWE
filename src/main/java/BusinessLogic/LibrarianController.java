package BusinessLogic;

import DomainModel.*;
import Exception.data.UserNotFoundException;
import ORM.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LibrarianController implements ControllerInterface {
    private Librarian user;
    private static final int LOAN_DURATION = 30;

    public LibrarianController(User user) {
        this.user = (Librarian) user;
    }

    public Card createCard(LibraryUser user) {
        CardDAO cardDAO = new CardDAO();
        Card card;

        // User ha già fatto richiesta della tessera
        if (user.getCard().getIssueDate() != null) {
            card = cardDAO.createCardFromRequest(user);
        } else
            card = cardDAO.createCard(user);

        if (card == null)
            throw new RuntimeException("Write on database failed");

        return card;
    }

    public Event createEvent(EventDTO eventDTO) {
        UserDAO userDAO = new UserDAO();
        RoomDAO roomDAO = new RoomDAO();
        EventDAO eventDAO = new EventDAO();
        UserDTO organizer;
        if (eventDTO.getStartDate().isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException("Event start date cannot be in the past");
        if (eventDTO.getEventDuration().isNegative() || eventDTO.getEventDuration().isZero())
            throw new IllegalArgumentException("Event duration must be positive");
        if (eventDTO.getStartDate().plus(eventDTO.getEventDuration()).isAfter(eventDTO.getStartDate().plusDays(1)))
            throw new IllegalArgumentException("Event cannot finish the day after (library probably close earlier)");
        // questi check possono essere delegati alla vista
        // ad esempio, con django potremmo usare ModelForm e mostrarlo con CreateView
        // li metto per completezza, ma in base a come viene costruita la ui possono essere tolti
        try {
            organizer = userDAO.getUserByEmail(eventDTO.getOrganizer());
        } catch (UserNotFoundException e) {
            throw new IllegalArgumentException("Organizer must exist");
        }
        Room room = roomDAO.getRoomByNumber(eventDTO.getPlace());
        if (room == null){
            throw new IllegalArgumentException("Room must exist");
        }
        if (room instanceof StudyRoom){
            throw new IllegalArgumentException("Room must be an Event Room");
        }

        Event eventToBeCreated = new Event.EventBuilder()
                .setName(eventDTO.getName())
                .setDescription(eventDTO.getDescription())
                .setEventDuration(eventDTO.getEventDuration())
                .setStartDate(eventDTO.getStartDate())
                .setOrganizer(new Librarian(organizer.getName(), organizer.getEmail(), organizer.getSurname()))
                .setPlace((EventRoom) room).build();

        ((EventRoom) room).scheduleEvent(eventToBeCreated);

        //se arrivo qua significa che la stanza è disponibile, posso scrivere l'evento nel db
        Integer id = eventDAO.setEvent(eventToBeCreated);
        if (id!= null){
            eventToBeCreated.setId(id);
            return eventToBeCreated;
        }
        else {
            throw new RuntimeException("Write on db failed");
        }
    }

    public void cancelEvent(Event event) {
        EventDAO eventDAO = new EventDAO();
        boolean deleted = eventDAO.deleteEvent(event);

        if (!deleted) {
            throw new RuntimeException("Event not found");
        }
    }

    public void modifyEvent(Event event) {
        EventDAO eventDAO = new EventDAO();
        boolean edited = eventDAO.editEvent(event);
        if (!edited) {
            throw new RuntimeException("Event not found");
        }
    }

    //FIXME: questo un bibliotecario non lo può fare!!!
    public void requestLoan(Loan loan) {
        LoanDAO loanDAO = new LoanDAO();
        if (loanDAO.isBookLoaned(loan.getBook().getCode())) {
            throw new RuntimeException("Book is already loaned");
        }

        boolean created = loanDAO.setRequestedLoan(loan);
        if (!created) {
            throw new RuntimeException("Loan request failed");
        }
    }

    public void grantLoan(Loan loan) {
        LoanDAO loanDAO = new LoanDAO();
        LocalDate now = LocalDate.now();
        LocalDate endTime = now.plusDays(LOAN_DURATION);
        boolean updated = loanDAO.grantLoan(loan, now, endTime);
        if (!updated) {
            throw new IllegalStateException("Loan not found");
        }
    }

    public void endLoan(Loan loan) {
        LoanDAO loanDAO = new LoanDAO();
        boolean updated = loanDAO.endLoan(loan);
        if (!updated) {
            throw new IllegalStateException("Loan not found");
        }
    }
}