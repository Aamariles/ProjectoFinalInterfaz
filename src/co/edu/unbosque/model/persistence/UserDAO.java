package co.edu.unbosque.model.persistence;
import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import co.edu.unbosque.model.User;
import co.edu.unbosque.model.UserDTO;
public class UserDAO implements OperationDAO<UserDTO, User> {
    private ArrayList<User> userList;
    private final String DATA_FOLDER = "data";
    private final String SERIAL_FILE_NAME = DATA_FOLDER + File.separator + "users.dat";
    private static final int ITERATIONS = 1000;
    private static final int KEY_LENGTH = 256;
    private static final Random RANDOM = new Random();
    @SuppressWarnings("unchecked")
    public UserDAO() {
        File dataDir = new File(DATA_FOLDER);
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
        Object loadedData = FileManager.serializableOpenAndReadFile(SERIAL_FILE_NAME);
        if (loadedData instanceof ArrayList<?>) {
            boolean allUsers = true;
            if (((ArrayList<?>) loadedData).isEmpty()) {
                this.userList = new ArrayList<>();
            } else {
                for (Object obj : (ArrayList<?>) loadedData) {
                    if (!(obj instanceof User)) {
                        allUsers = false;
                        break;
                    }
                }
                if (allUsers) {
                    this.userList = (ArrayList<User>) loadedData;
                } else {
                    System.err.println("UserDAO: Data in " + SERIAL_FILE_NAME + " is not of type User or is mixed. Initializing new list.");
                    this.userList = new ArrayList<>();
                }
            }
        } else {
            System.out.println("UserDAO: No data file found for Users or data is not an ArrayList. Initializing new list.");
            this.userList = new ArrayList<>();
        }
        if (this.userList.isEmpty()) {
            initializeDefaultUser();
        }
    }
    private void initializeDefaultUser() {
        User searchAdmin = new User("admin", null, null);
        if (this.find(searchAdmin) == null) {
            byte[] salt = generateSalt();
            String hashedPassword = generateHash("123", salt);
            UserDTO adminUserDTO = new UserDTO("admin", hashedPassword, salt);
            this.userList.add(DataMapper.userDTOToUser(adminUserDTO));
            saveData();
            System.out.println("UserDAO: Default admin user ('admin'/'123') created and saved.");
        }
    }
    private void saveData() {
        FileManager.serializableOpenAndWriteFile(SERIAL_FILE_NAME, this.userList);
    }
    @Override
    public boolean add(UserDTO newData) {
        User userToAdd = DataMapper.userDTOToUser(newData);
        if (find(userToAdd) == null) {
            userList.add(userToAdd);
            saveData();
            return true;
        }
        return false;
    }
    public boolean registerUser(String username, String plainPassword) {
        User searchUser = new User(username, null, null);
        if (find(searchUser) != null) {
            System.out.println("UserDAO: Username '" + username + "' already exists.");
            return false;
        }
        byte[] salt = generateSalt();
        String hashedPassword = generateHash(plainPassword, salt);
        UserDTO newUserDTO = new UserDTO(username, hashedPassword, salt);
        return add(newUserDTO);
    }
    @Override
    public boolean delete(UserDTO toDelete) {
        User found = find(DataMapper.userDTOToUser(toDelete));
        if (found != null) {
            boolean removed = userList.remove(found);
            if (removed) saveData();
            return removed;
        }
        return false;
    }
    @Override
    public int delete(int index) {
        if (index < 0 || index >= userList.size()) {
            System.err.println("UserDAO: Delete by index failed. Index out of bounds: " + index);
            return -1;
        }
        userList.remove(index);
        saveData();
        return 0;
    }
    public boolean deleteByName(String username) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getuser().equals(username)) {
                userList.remove(i);
                saveData();
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean update(UserDTO previous, UserDTO newData) {
        User found = find(DataMapper.userDTOToUser(previous));
        if (found != null) {
            userList.remove(found);
            userList.add(DataMapper.userDTOToUser(newData));
            saveData();
            return true;
        }
        return false;
    }
    @Override
    public String showAll() {
        StringBuilder sb = new StringBuilder();
        if (userList.isEmpty()) {
            return " Users Empty list";
        } else {
            for (User user : userList) {
                sb.append(user.toString()).append("\n");
            }
            return sb.toString();
        }
    }
    @Override
    public ArrayList<UserDTO> getAll() {
        return DataMapper.userListToUserListDTO(userList);
    }
    @Override
    public User find(User toFind) {
        if (toFind == null || toFind.getuser() == null) return null;
        for (User user : userList) {
            if (user.getuser().equals(toFind.getuser())) {
                return user;
            }
        }
        return null;
    }
    public boolean ValidateUser(String user,String password) {
        User userFound = searchUser(user);
        if (userFound == null) {
            System.out.println("UserDAO.ValidateUser: User not found - " + user);
            return false;
        }
        byte[] salt = userFound.getSalt();
        if (salt == null) {
            System.out.println("UserDAO.ValidateUser: The salt is null for user: " + user);
            return false;
        }
        String hashEntered = generateHash(password, salt);
        String storedHash = userFound.getPassword();
        return storedHash.equals(hashEntered);
    }
    private String generateHash(String password, byte[] salt) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(),salt, ITERATIONS,KEY_LENGTH);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = skf.generateSecret(spec).getEncoded();
            return Base64.getUrlEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error to generate hash", e);
        }
    }
    private byte[] generateSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return salt;
    }
    private User searchUser(String username) {
        for (User client : userList) {
            if (client.getuser().equals(username)) {
                return client;
            }
        }
        return null;
    }
}