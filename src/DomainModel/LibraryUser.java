package DomainModel;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.util.Objects;
//TODO: pensa a come implementare per bene card per composizione (diamante pieno)
//Deve essere creata qua dentro
public class LibraryUser {
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


        public LibraryUserBuilder setCard(int id, LocalTime issueDate, LocalTime expirationDate) {
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

}
