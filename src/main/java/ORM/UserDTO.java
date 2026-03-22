package ORM;
import BusinessLogic.role;


public class UserDTO {
    private String email;
    private String name;
    private String surname;
    private role role;
    private String password;

    // sembra un telescopic constructor ma non so se è appropriato usare un builder
    public UserDTO(String email, String name, String surname, role role, String password) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.password = password;
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

    public role getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }
}
