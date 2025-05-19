package co.edu.unbosque.model.persistence;

import java.util.ArrayList;
import co.edu.unbosque.model.Game;
import co.edu.unbosque.model.GameDTO;
import co.edu.unbosque.model.Team;
import co.edu.unbosque.model.TeamDTO;
import co.edu.unbosque.model.Coach;
import co.edu.unbosque.model.CoachDTO;
import co.edu.unbosque.model.Player;
import co.edu.unbosque.model.PlayerDTO;
import co.edu.unbosque.model.User;
import co.edu.unbosque.model.UserDTO;

public class DataMapper {
	public static Player playerDTOToPlayer(PlayerDTO dto) {
		Player entity;
		entity = new Player(dto.getName(), dto.getGender(), dto.getExperience(), dto.getTeam(), dto.getCity(),
				dto.getCountry());
		return entity;
	}

	public static PlayerDTO playerToPlayerDTO(Player entity) {
		PlayerDTO dto;
		dto = new PlayerDTO(entity.getName(), entity.getGender(), entity.getExperience(), entity.getTeam(),
				entity.getCity(), entity.getCountry());
		return dto;
	}

	public static ArrayList<PlayerDTO> playersListToPlayersListDTO(ArrayList<Player> entityList) {
		ArrayList<PlayerDTO> dtoList = new ArrayList<>();
		for (Player player : entityList) {
			dtoList.add(new PlayerDTO(player.getName(), player.getGender(), player.getExperience(), player.getTeam(),
					player.getCity(), player.getCountry()));
		}
		return dtoList;
	}

	public static ArrayList<Player> playersListDTOToPlayersList(ArrayList<PlayerDTO> dtoList) {
		ArrayList<Player> entityList = new ArrayList<>();
		for (PlayerDTO playerDTO : dtoList) {
			entityList.add(new Player(playerDTO.getName(), playerDTO.getGender(), playerDTO.getExperience(),
					playerDTO.getTeam(), playerDTO.getCity(), playerDTO.getCountry()));
		}
		return entityList;
	}

	public static Game gameDTOToGame(GameDTO dto) {
		Game entity;
		entity = new Game(dto.getGameTitle(), dto.getPlayersList(), dto.getDate(), dto.getTeamsList());
		return entity;
	}

	public static GameDTO gameToGameDTO(Game entity) {
		GameDTO dto;
		dto = new GameDTO(entity.getGameTitle(), entity.getPlayersList(), entity.getDate(), entity.getTeamsList());
		return dto;
	}

	public static ArrayList<GameDTO> gamesListToGamesListDTO(ArrayList<Game> entityList) {
		ArrayList<GameDTO> dtoList = new ArrayList<>();
		for (Game game : entityList) {
			dtoList.add(new GameDTO(game.getGameTitle(), game.getPlayersList(), game.getDate(), game.getTeamsList()));
		}
		return dtoList;
	}

	public static ArrayList<Game> gamesListDTOToGamesList(ArrayList<GameDTO> dtoList) {
		ArrayList<Game> entityList = new ArrayList<>();
		for (GameDTO gameDTO : dtoList) {
			entityList.add(new Game(gameDTO.getGameTitle(), gameDTO.getPlayersList(), gameDTO.getDate(),
					gameDTO.getTeamsList()));
		}
		return entityList;
	}

	public static User userDTOToUser(UserDTO dto) {
		User entity;
		entity = new User(dto.getUser(), dto.getPassword(), dto.getSalt());
		return entity;
	}

	public static UserDTO userToUserDTO(User entity) {
		UserDTO dto;
		dto = new UserDTO(entity.getuser(), entity.getPassword(), entity.getSalt());
		return dto;
	}

	public static ArrayList<UserDTO> userListToUserListDTO(ArrayList<User> entityList) {
		ArrayList<UserDTO> dtoList = new ArrayList<>();
		for (User user : entityList) {
			dtoList.add(new UserDTO(user.getuser(), user.getPassword(), user.getSalt()));
		}
		return dtoList;
	}

	public static ArrayList<User> userListDTOToUserList(ArrayList<UserDTO> dtoList) {
		ArrayList<User> entityList = new ArrayList<>();
		for (UserDTO userDTO : dtoList) {
			entityList.add(new User(userDTO.getUser(), userDTO.getPassword(), userDTO.getSalt()));
		}
		return entityList;
	}

	public static Coach coachDTOToCoach(CoachDTO dto) {
		Coach entity;
		entity = new Coach(dto.getName(),dto.getExperience(), dto.getSpecialty(), dto.getPath(), dto.getTeamsList());
		return entity;
	}

	public static CoachDTO coachToCoachDTO(Coach entity) {
		CoachDTO dto;
		dto = new CoachDTO(entity.getName(),entity.getExperience(), entity.getSpecialty(), entity.getPath(), entity.getTeamsList());
		return dto;
	}

	public static ArrayList<CoachDTO> coachesListToCoachesListDTO(ArrayList<Coach> entityList) {
		ArrayList<CoachDTO> dtoList = new ArrayList<>();
		for (Coach coach : entityList) {
			dtoList.add(
					new CoachDTO(coach.getName(),coach.getExperience(), coach.getSpecialty(), coach.getPath(), coach.getTeamsList()));
		}
		return dtoList;
	}

	public static ArrayList<Coach> coachesListDTOToCoachesList(ArrayList<CoachDTO> dtoList) {
		ArrayList<Coach> entityList = new ArrayList<>();
		for (CoachDTO coachDTO : dtoList) {
			entityList.add(new Coach(coachDTO.getName(),coachDTO.getExperience(), coachDTO.getSpecialty(), coachDTO.getPath(),
					coachDTO.getTeamsList()));
		}
		return entityList;
	}

	public static Team teamDTOToTeam(TeamDTO dto) {
		Team entity;
		entity = new Team(dto.getTournamentFormat(), dto.getTeamName(), dto.getCoach());
		return entity;
	}

	public static TeamDTO teamToTeamDTO(Team entity) {
		TeamDTO dto;
		dto = new TeamDTO(entity.getTournamentFormat(), entity.getTeamName(), entity.getCoach());
		return dto;
	}

	public static ArrayList<TeamDTO> teamsListToTeamsListDTO(ArrayList<Team> entityList) {
		ArrayList<TeamDTO> dtoList = new ArrayList<>();
		for (Team team : entityList) {
			dtoList.add(new TeamDTO(team.getTournamentFormat(), team.getTeamName(), team.getCoach()));
		}
		return dtoList;
	}

	public static ArrayList<Team> teamsListDTOToTeamsList(ArrayList<TeamDTO> dtoList) {
		ArrayList<Team> entityList = new ArrayList<>();
		for (TeamDTO teamDTO : dtoList) {
			entityList.add(new Team(teamDTO.getTournamentFormat(), teamDTO.getTeamName(), teamDTO.getCoach()));
		}
		return entityList;
	}

}
