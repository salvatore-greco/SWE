package BusinessLogic;

import DomainModel.*;
import ORM.CardDAO;
import ORM.EventDAO;
import ORM.LoanDAO;

import java.time.LocalDate;

public class LibraryUserController implements ControllerInterface {
    public LibraryUser user;

    public LibraryUserController(User user) {
        this.user = (LibraryUser) user;
    }

    public Loan requestLoan(Book requestedBook) {
        if (user.getCard() == null) {
            throw new RuntimeException("User must have a card to request a loan"); //FIXME: use an appropriate exception
        }
        if (user.getCard().getExpirationDate().isBefore(LocalDate.now())){
            throw new RuntimeException("User card is expired"); //FIXME: use an appropriate exception
        }
        LoanDAO loanDAO = new LoanDAO();
        Loan loan = new Loan(user.getCard(), requestedBook, false, false);
        if (!loanDAO.setRequestedLoan(loan)) {
            throw new RuntimeException("Write on database failed");
        }
        return loan;
    }

    public void requestCard() {
        if (this.user.getCard() != null) { //Null check necessario perchè l'user può non avere una tessera.
            throw new RuntimeException("User already have a card"); //fixme: use an appropriate exception
        }
        CardDAO cardDAO = new CardDAO();
        Card card = cardDAO.setRequestedCard(this.user);
        this.user.setCard(card);
        //TODO: surround with try catch when a specific exception is implemented
    }

    public void eventBooking(Event event){
        EventDAO eventDAO = new EventDAO();
        event.addParticipant(this.user);
        eventDAO.addNewParticipant(event, this.user);
    }

    public void reserveSeatStudyRoom(StudyRoom room){
        room.reserveSeat(this.user);
    }

    public void leaveSeatStudyRoom(StudyRoom room){
        room.leaveSeat(this.user);
    }

    public void cancelEventBooking(Event event){
        EventDAO eventDAO = new EventDAO();
        event.removeParticipant(this.user);
        eventDAO.removeParticipant(event, this.user);
    }

}
