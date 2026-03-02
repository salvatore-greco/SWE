package BusinessLogic;

import DomainModel.*;
import ORM.CardDAO;
import ORM.EventDAO;

public class LibrarianController implements ControllerInterface{
    private Librarian librarian;
    private CardDAO cardDAO = new CardDAO();
    private EventDAO eventDAO = new EventDAO();

    public LibrarianController(User user){
        this.user = (Librarian) user;
    }

    public Card createCard(LibraryUser user){
        Integer cardId = cardDAO.setRequestedCard(user);
        if (cardId != null)
            return new Card(cardId);
        else
            throw new RuntimeException("Write on database failed");
    }

    public Event createEvent(Event event){
        Integer id = eventDAO.setEvent(event);
        if (id != null) {
            event.setId(id);
            return event;
        } else
            throw new RuntimeException("Write on database failed");

    }
}
