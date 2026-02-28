package DomainModel;


public class LibraryAdministrator implements User {
    private String name;
    private String surname;
    private String email;

    public String getName() {
        return name;
    }

    public LibraryAdministrator setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public LibraryAdministrator setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public LibraryAdministrator setEmail(String email) {
        this.email = email;
        return this;
    }

    public LibraryAdministrator(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }
}
