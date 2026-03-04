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

    public void cancelEvent(Event event){
        boolean deleted = eventDAO.deleteEvent(event);

        if(!deleted){
            throw new RuntimeException("Event not found");
        }
    }

    //TODO: valutare se è necessario un metodo per modificare un evento

    public void requestLoan(Loan loan){
        if(loanDAO.isBookLoaned(loan.getBook().getCode())){
            throw new RuntimeException("Book is already loaned");
        }

        boolean created = loanDAO.setRequestedLoan(loan);
        if(!created){
            throw new RuntimeException("Loan request failed");
        }
    }

    public void grantLoan(Loan loan){
        LocalDateTime now = LocalTime.now();
        LocalDateTime endTime = now.plusDays(30); // TODO: valutare come gestire la durata del prestito
        boolean updated = loanDAO.grantLoan(loan);
        if(!updated){
            throw new IllegalStateException("Loan not found");
        }
    }

     public void endLoan(Loan loan){
        boolean updated = loanDAO.endLoan(loan);
        if(!updated){
            throw new IllegalStateException("Loan not found");
        }
    }
}