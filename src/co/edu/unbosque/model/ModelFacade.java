package co.edu.unbosque.model;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import co.edu.unbosque.model.persistence.CoachDAO;
import co.edu.unbosque.model.persistence.EmailServiceImpl;
import co.edu.unbosque.model.persistence.GameDAO;
import co.edu.unbosque.model.persistence.PlayerDAO;
import co.edu.unbosque.model.persistence.TeamDAO;
import co.edu.unbosque.model.persistence.UserDAO;
import co.edu.unbosque.util.exception.EmptyStringException;
import co.edu.unbosque.util.exception.ExceptionChecker;

public class ModelFacade {
	private UserDAO userDAO;
	private CoachDAO coachDAO;
	private TeamDAO teamDAO;
	private PlayerDAO playerDAO;
	private GameDAO gameDAO;
	private EmailServiceImpl emailServiceImpl;


	public ModelFacade() {
		userDAO = new UserDAO();
		coachDAO = new CoachDAO();
		teamDAO = new TeamDAO();
		playerDAO = new PlayerDAO();
		gameDAO = new GameDAO();
		emailServiceImpl = new EmailServiceImpl();
	}

	public boolean validateUser(String user, String password) {
		if (user == null || password == null) {
			return false;
		}
		return userDAO.ValidateUser(user, password);
	}

	public void createUser(String user, String password) throws EmptyStringException {
		ExceptionChecker.checkEmptyString(user, "El usuario no puede estar vacío");
		ExceptionChecker.checkEmptyString(password, "La contraseña no puede estar vacía");
		byte[] salt = generateSalt();
		String passwordHash = generateHash(password, salt);
		userDAO.add(new UserDTO(user, passwordHash, salt));
		
	}

	public boolean deleteByName(String username) {
		try {
			// Primero intentar eliminar el usuario
			if (userDAO.deleteByName(username)) {
				return true;
			}
			return false; // No se encontró el usuario
		} catch (Exception e) {
			System.err.println("Error to delete user: " + e.getMessage());
			return false;
		}
	}

	public int updateUser(String username, String password) throws EmptyStringException {
		ExceptionChecker.checkEmptyString(username, "El usuario no puede estar vacío");
		ExceptionChecker.checkEmptyString(password, "La contraseña no puede estar vacía");

		int index = searchUserIndex(username);
		if (index == -1)
			return 1; // Usuario no encontrado

		try {
			ArrayList<UserDTO> users = userDAO.getAll();
			UserDTO previousUser = users.get(index);

			User updatedUser = createUpdatedUser(username, password);
			UserDTO newUser = new UserDTO(updatedUser.getuser(), updatedUser.getPassword(), updatedUser.getSalt());

			userDAO.update(previousUser, newUser);
			return 0; // Éxito
		} catch (Exception e) {
			System.err.println("Error al actualizar usuario: " + e.getMessage());
			return 2; // Error en actualización
		}
	}

	private int searchUserIndex(String username) {
		ArrayList<UserDTO> userList = userDAO.getAll();
		for (int i = 0; i < userList.size(); i++) {
			if (userList.get(i).getUser().equals(username)) {
				return i;
			}
		}
		return -1;
	}

	private User createUpdatedUser(String username, String password) {
		byte[] newSalt = generateSalt();
		String newHash = generateHash(password, newSalt);
		return new User(username, newHash, newSalt);
	}

	private byte[] generateSalt() {
		byte[] salt = new byte[16];
		new SecureRandom().nextBytes(salt);
		return salt;
	}

	private String generateHash(String password, byte[] salt) {
		try {
			PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 1000, 256);
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			return Base64.getEncoder().encodeToString(skf.generateSecret(spec).getEncoded());
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new RuntimeException("Error to generate a hash", e);
		}
	}

	public ArrayList<UserDTO> listUsers() {
		return userDAO.getAll();
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public CoachDAO getCoachDAO() {
		return coachDAO;
	}

	public void setCoachDAO(CoachDAO coachDAO) {
		this.coachDAO = coachDAO;
	}

	public TeamDAO getTeamDAO() {
		return teamDAO;
	}

	public void setTeamDAO(TeamDAO teamDAO) {
		this.teamDAO = teamDAO;
	}

	public PlayerDAO getPlayerDAO() {
		return playerDAO;
	}

	public void setPlayerDAO(PlayerDAO playerDAO) {
		this.playerDAO = playerDAO;
	}

	public GameDAO getGameDAO() {
		return gameDAO;
	}

	public void setGameDAO(GameDAO gameDAO) {
		this.gameDAO = gameDAO;
	}
	public EmailServiceImpl getEmailServiceImpl() {
		return emailServiceImpl;
	}

	public void setEmailServiceImpl(EmailServiceImpl emailServiceImpl) {
		this.emailServiceImpl = emailServiceImpl;
	}

}