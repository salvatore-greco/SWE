package ORM;

//enum role{
//    librarian, libraryUser, libraryAdministrator
//}

public class UserDTO {
    private String email;
    private String name;
    private String surname;
    private String role; //In lettura posso trattare un enum postgre come una stringa

    public UserDTO(String email, String name, String surname, String role) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getRole() {
        return role;
    }
}
