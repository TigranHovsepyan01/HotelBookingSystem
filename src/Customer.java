import java.io.Serializable;

public class Customer implements Serializable {
    public String name;
    public String email;
    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
