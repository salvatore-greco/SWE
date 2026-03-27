package DomainModel;


public class LibraryAdministrator extends User {
//    private String name;
//    private String surname;
//    private String email;
    private Library libraryManaged;

    public Library getLibraryManaged() {
        return libraryManaged;
    }

    public LibraryAdministrator setLibraryManaged(Library libraryManaged) {
        this.libraryManaged = libraryManaged;
        return this;
    }

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

    public LibraryAdministrator(String name, String surname, String email, Library libraryManaged) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.libraryManaged = libraryManaged;
    }

    public LibraryAdministrator(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    @Override
    public boolean equals(Object obj){
        if (getClass() != obj.getClass()) return false;
        return super.equals(obj) && libraryManaged.equals(((LibraryAdministrator) obj).libraryManaged);
    }
}
