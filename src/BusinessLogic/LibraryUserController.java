package BusinessLogic;

import DomainModel.*;
import ORM.CardDAO;
import ORM.EventDAO;
import ORM.LoanDAO;

public class LibraryUserController implements ControllerInterface {
    public LibraryUser user;

    public LibraryUserController(User user) {
        this.user = (LibraryUser) user; //FIXME: this smell
    }

    public Loan requestLoan(Book requestedBook) {
        if (user.getCard() == null) {
            throw new RuntimeException("User must have a card to request a loan"); //FIXME: use an appropriate exception
        }
        LoanDAO loanDAO = new LoanDAO();
        Loan loan = new Loan(user.getCard(), requestedBook, false, false);
        if (!loanDAO.setRequestedLoan(loan)) {
            throw new RuntimeException("Write on database failed");
        }
        return loan;
    }

    public void requestCard() {
        if (this.user.getCard() != null) {
            throw new RuntimeException("User already have a card"); //fixme: use an appropriate exception
        }
        CardDAO cardDAO = new CardDAO();
        Integer cardId = cardDAO.setRequestedCard(this.user);
        if (cardId != null)
            this.user.setCard(new Card(cardId));
        else
            throw new RuntimeException("Write on database failed"); //fixme: use an appropriate exception
    }

    public void eventBooking(Event event){
        EventDAO eventDAO = new EventDAO();
        event.addParticipant(this.user);
        eventDAO.addNewParticipant(event, this.user);
    }
}
