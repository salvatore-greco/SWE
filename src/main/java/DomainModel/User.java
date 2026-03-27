package DomainModel;

public abstract class User {
    protected String email;
    protected String name;
    protected String surname;

    public String getEmail() {
        return email;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return email.equals(user.email) && name.equals(user.name) && surname.equals(user.surname);
    }
}
