package co.edu.unbosque.model.persistence;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import co.edu.unbosque.model.User;
import co.edu.unbosque.model.UserDTO;


public class UserDAO implements OperationDAO<UserDTO,User> {
	private ArrayList<User> userList;
	private final String TEXT_FILE_NAME = "src/files/User.csv";
	private final String SERIAL_FILE_NAME ="src/files/User.dat";
	private static final String AES_KEY = "Prog18";
	private static final int ITERATIONS = 1000;
	private static final int Key_LENGTH = 256;
	private static final Random RAMDOM = new Random();
	
	public UserDAO() {
		userList = new ArrayList<>();
		
	}

	@Override
	public String showAll() {
		String rta = "";
		if (userList.isEmpty()) {
			return " Users Empty list";
			
		}else {
			for (User user : userList) {
				rta += user;
			}
			return rta;
		}
		
	}

	@Override
	public ArrayList<UserDTO> getAll() {
		return DataMapper.userListToUserListDTO(userList);
	}

	@Override
	public boolean add(UserDTO newData) {
		if (find(DataMapper.userDTOToUser(newData))== null) {
			userList.add(DataMapper.userDTOToUser(newData));
			return true;
			
		}else {
			return false;
		}
	}

	@Override
	public boolean delete(UserDTO toDelete) {
		User found = find(DataMapper.userDTOToUser(toDelete));
		if (found != null) {
			return userList.remove(found);
			
		}else {
			return false;
		}
	}
	
	@Override
	public int delete(int index) {
		if (index < 0 || index >= userList.size()) {
			//throw new InvalidIndexException("Índice fuera de rango: " + index);
		}
		userList.remove(index);
		return 0;
	}
	
	public boolean deleteByName(String username) {
		for (int i = 0; i < userList.size(); i++) {
			if (userList.get(i).getuser().equals(username)) {
				try {
					delete(i); // Usar el método eliminar por índice
					return true;
				} catch (Exception e) {
					System.err.println("Error al eliminar usuario: " + e.getMessage());
					return false;
				}
			}
		}
		return false; // Usuario no encontrado
	}


   
	@Override
	public User find(User toFind) {
		User found = null;
		if (!userList.isEmpty()) {
			for (User user : userList) {
				if (user.getuser().equals(toFind.getuser())) {
					found = user;
					return found;
					
				}else {
					continue;
				}
				
			}
			
		}else {
			return null;
		}
		return null;
	}

	@Override
	public boolean update(UserDTO previous, UserDTO newData) {
		User found = find(DataMapper.userDTOToUser(previous));
		if (found != null) {
			userList.remove(found);
			userList.add(DataMapper.userDTOToUser(newData));
			return true;
			
		}else {
			return false;
		}
		
	}
	public boolean ValidateUser(String user,String password) {
		User userFound = searchUser(user);
		if (userFound == null) {
			System.out.println("User not found");
			return false;
		}
		byte[] salt = userFound.getSalt();
		if (salt == null) {
			System.out.println("The salt is null for user: " +user);
			return false;
			
		}
		String hashEntered = generateHash(password, salt);
		String storedHash = userFound.getPassword();
		
		System.out.println("Hash entered: " + hashEntered);
		System.out.println("Stored hash: " + storedHash);
		
		return storedHash.equals(hashEntered);
		
	}
	
	private String generateHash(String password, byte[] salt) {
		try {
			PBEKeySpec spec = new PBEKeySpec(password.toCharArray(),salt, ITERATIONS,Key_LENGTH);
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			byte[] hash = skf.generateSecret(spec).getEncoded();
			return Base64.getUrlEncoder().encodeToString(hash);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new RuntimeException("Error to generate hash", e);
			
		}
	}
	
	private byte[] generateSalt() {
		byte[] salt = new byte[16];
		RAMDOM.nextBytes(salt);
		return salt;
	}

	private User searchUser(String user) {
		for (User client : userList) {
			if (client.getuser().equals(user)) {
				return client;
			}
		}
		return null;
	}

	

}
		
