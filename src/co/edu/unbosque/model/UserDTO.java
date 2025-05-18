package co.edu.unbosque.model;

import java.io.Serializable;
import java.util.Objects;

public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String user;
    private String password;
    private byte[] salt;

    public UserDTO() {

    }

    public UserDTO(String user, String password, byte[] salt) {
        super();
        this.user = user;
        this.password = password;
        this.salt = salt;
    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
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
        return "UserDTO: " + user;
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof UserDTO))
            return false;
        UserDTO user1 = (UserDTO) o;
        return Objects.equals(user, user1.user);
    }
    public int hashCode() {
        return Objects.hash(user);

    }

}
