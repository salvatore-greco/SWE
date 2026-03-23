package BusinessLogic;

import DomainModel.*;
import ORM.CardDAO;
import ORM.EventDAO;
import ORM.LoanDAO;
import ORM.RoomDAO;

import java.time.LocalDate;

public class LibraryUserController implements ControllerInterface {

    private LibraryUser user;

    public LibraryUserController(User user) {
        this.user = (LibraryUser) user;
        this.user.setCard(new CardDAO().getCardByEmail(this.user.getEmail()));
    }

    public LibraryUser getUser() {
        return user;
    }
    public Loan requestLoan(Book requestedBook) {
        LoanDAO loanDAO = new LoanDAO();
        if (user.getCard() == null) {
            throw new RuntimeException("User must have a card to request a loan"); //FIXME: use an appropriate exception
        }
        if (user.getCard().getExpirationDate().isBefore(LocalDate.now())){
            throw new RuntimeException("User card is expired"); //FIXME: use an appropriate exception
        }
        if (loanDAO.isBookLoaned(requestedBook.getCode()))
            throw new RuntimeException("Book is already loaned");
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
        if (!eventDAO.addNewParticipant(event, this.user)){
            throw new RuntimeException("Write on database failed");
        }
        event.addParticipant(this.user);
    }

    public void reserveSeatStudyRoom(StudyRoom room){
        RoomDAO roomDAO = new RoomDAO();
        if (!roomDAO.addSeatReservationStudyRoom(room, this.user)) {
            throw new RuntimeException("Write on database failed");
        }
        room.reserveSeat(this.user);
    }

    public void leaveSeatStudyRoom(StudyRoom room){
        RoomDAO roomDAO = new RoomDAO();
        if (!roomDAO.removeSeatReservationStudyRoom(room, this.user)) {
            throw new RuntimeException("Write on database failed");
        }
        room.leaveSeat(this.user);
    }

    public void cancelEventBooking(Event event){
        EventDAO eventDAO = new EventDAO();
        if(!eventDAO.removeParticipant(event, this.user)){
            throw new RuntimeException("Write on database failed");
        }
        event.removeParticipant(this.user);
    }

}
