package DomainModel;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class LibraryUser implements User {
    private String name;
    private String surname;
    private String email;
    private Card card;

    public static class LibraryUserBuilder{
        private String name;
        private String surname;
        private String email;
        private Card card;

        public LibraryUserBuilder setName(String name){
            this.name = name;
            return this;
        }

        public LibraryUserBuilder setSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public LibraryUserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }


        public LibraryUserBuilder setCard(int id, LocalDate issueDate, LocalDate expirationDate) {
            if (issueDate == null || expirationDate == null)
                this.card = new Card(id);
            else
                this.card = new Card(id, issueDate, expirationDate);
            return this;
        }

        public LibraryUser build(){
            return new LibraryUser(this);
        }
    }
    private LibraryUser(LibraryUserBuilder builder) {
        this.name = builder.name;
        this.surname = builder.surname;
        this.email = builder.email;
        this.card = builder.card;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
