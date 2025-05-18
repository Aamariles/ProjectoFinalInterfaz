package co.edu.unbosque.model;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String user;
    private String password;
    private byte[] salt;

    public User() {

    }

    public User(String user, String password, byte[] salt) {
        super();
        this.user = user;
        this.password = password;
        this.salt = salt;
    }

    public String getuser() {
        return user;
    }

    public void setuser(String user) {
        if (user == null || user.trim().isEmpty()) {

        }
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    @Override
    public String toString() {
        return "User: " + user;
    }
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof User))
            return false;
        User user1 = (User) o;
        return Objects.equals(user, user1.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }
}