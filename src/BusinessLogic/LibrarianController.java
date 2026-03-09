package BusinessLogic;

import DomainModel.*;
import ORM.CardDAO;
import ORM.EventDAO;
import ORM.LoanDAO;

import java.time.LocalDate;

public class LibrarianController implements ControllerInterface {
    private Librarian user;
    private static final int LOAN_DURATION = 30;

    public LibrarianController(User user) {
        this.user = (Librarian) user;
    }

    public Card createCard(LibraryUser user) {
        CardDAO cardDAO = new CardDAO();
        Integer cardId = cardDAO.setRequestedCard(user);
        if (cardId != null)
            return new Card(cardId);
        else
            throw new RuntimeException("Write on database failed");
    }

    public Event createEvent(Event event) {
        EventDAO eventDAO = new EventDAO();
        Integer id = eventDAO.setEvent(event);
        if (id != null) {
            event.setId(id);
            return event;
        } else
            throw new RuntimeException("Write on database failed");
    }

    public void cancelEvent(Event event) {
        EventDAO eventDAO = new EventDAO();
        boolean deleted = eventDAO.deleteEvent(event);

        if (!deleted) {
            throw new RuntimeException("Event not found");
        }
    }

    //TODO: valutare se è necessario un metodo per modificare un evento

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