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
        if (dto == null) return null;
        Player entity;
        entity = new Player(dto.getName(), dto.getGender(), dto.getExperience(), dto.getTeam(), dto.getCity(),
                dto.getCountry());
        return entity;
    }
    public static PlayerDTO playerToPlayerDTO(Player entity) {
        if (entity == null) return null;
        PlayerDTO dto;
        dto = new PlayerDTO(entity.getName(), entity.getGender(), entity.getExperience(), entity.getTeam(),
                entity.getCity(), entity.getCountry());
        return dto;
    }
    public static ArrayList<PlayerDTO> playersListToPlayersListDTO(ArrayList<Player> entityList) {
        ArrayList<PlayerDTO> dtoList = new ArrayList<>();
        if (entityList == null) return dtoList;
        for (Player player : entityList) {
            if (player != null) {
                dtoList.add(playerToPlayerDTO(player));
            }
        }
        return dtoList;
    }
    public static ArrayList<Player> playersListDTOToPlayersList(ArrayList<PlayerDTO> dtoList) {
        ArrayList<Player> entityList = new ArrayList<>();
        if (dtoList == null) return entityList;
        for (PlayerDTO playerDTO : dtoList) {
            if (playerDTO != null) {
                entityList.add(playerDTOToPlayer(playerDTO));
            }
        }
        return entityList;
    }
    public static Game gameDTOToGame(GameDTO dto) {
        if (dto == null) return null;
        Game entity;
        entity = new Game(dto.getGameTitle(), dto.getPlayersList(), dto.getDate(), dto.getTeamsList());
        return entity;
    }
    public static GameDTO gameToGameDTO(Game entity) {
        if (entity == null) return null;
        GameDTO dto;
        dto = new GameDTO(entity.getGameTitle(), entity.getPlayersList(), entity.getDate(), entity.getTeamsList());
        return dto;
    }
    public static ArrayList<GameDTO> gamesListToGamesListDTO(ArrayList<Game> entityList) {
        ArrayList<GameDTO> dtoList = new ArrayList<>();
        if (entityList == null) return dtoList;
        for (Game game : entityList) {
            if (game != null) {
                dtoList.add(gameToGameDTO(game));
            }
        }
        return dtoList;
    }
    public static ArrayList<Game> gamesListDTOToGamesList(ArrayList<GameDTO> dtoList) {
        ArrayList<Game> entityList = new ArrayList<>();
        if (dtoList == null) return entityList;
        for (GameDTO gameDTO : dtoList) {
            if (gameDTO != null) {
                entityList.add(gameDTOToGame(gameDTO));
            }
        }
        return entityList;
    }
    public static User userDTOToUser(UserDTO dto) {
        if (dto == null) return null;
        User entity;
        entity = new User(dto.getUser(), dto.getPassword(), dto.getSalt());
        return entity;
    }
    public static UserDTO userToUserDTO(User entity) {
        if (entity == null) return null;
        UserDTO dto;
        dto = new UserDTO(entity.getuser(), entity.getPassword(), entity.getSalt());
        return dto;
    }
    public static ArrayList<UserDTO> userListToUserListDTO(ArrayList<User> entityList) {
        ArrayList<UserDTO> dtoList = new ArrayList<>();
        if (entityList == null) return dtoList;
        for (User user : entityList) {
            if (user != null) {
                dtoList.add(userToUserDTO(user));
            }
        }
        return dtoList;
    }
    public static ArrayList<User> userListDTOToUserList(ArrayList<UserDTO> dtoList) {
        ArrayList<User> entityList = new ArrayList<>();
        if (dtoList == null) return entityList;
        for (UserDTO userDTO : dtoList) {
            if (userDTO != null) {
                entityList.add(userDTOToUser(userDTO));
            }
        }
        return entityList;
    }
    public static Coach coachDTOToCoach(CoachDTO dto) {
        if (dto == null) return null;
        Coach entity;
        entity = new Coach(dto.getName(), dto.getExperience(), dto.getSpecialty(), dto.getPath(), dto.getTeamsList());
        return entity;
    }
    public static CoachDTO coachToCoachDTO(Coach entity) {
        if (entity == null) return null;
        CoachDTO dto;
        dto = new CoachDTO(entity.getName(), entity.getExperience(), entity.getSpecialty(), entity.getPath(), entity.getTeamsList());
        return dto;
    }
    public static ArrayList<CoachDTO> coachesListToCoachesListDTO(ArrayList<Coach> entityList) {
        ArrayList<CoachDTO> dtoList = new ArrayList<>();
        if (entityList == null) return dtoList;
        for (Coach coach : entityList) {
            if (coach != null) {
                dtoList.add(coachToCoachDTO(coach));
            }
        }
        return dtoList;
    }
    public static ArrayList<Coach> coachesListDTOToCoachesList(ArrayList<CoachDTO> dtoList) {
        ArrayList<Coach> entityList = new ArrayList<>();
        if (dtoList == null) return entityList;
        for (CoachDTO coachDTO : dtoList) {
            if (coachDTO != null) {
                entityList.add(coachDTOToCoach(coachDTO));
            }
        }
        return entityList;
    }
    public static Team teamDTOToTeam(TeamDTO dto) {
        if (dto == null) return null;
        Team entity;
        entity = new Team(dto.getTournamentFormat(), dto.getTeamName(), dto.getCoach());
        return entity;
    }
    public static TeamDTO teamToTeamDTO(Team entity) {
        if (entity == null) return null;
        TeamDTO dto;
        dto = new TeamDTO(entity.getTournamentFormat(), entity.getTeamName(), entity.getCoach());
        return dto;
    }
    public static ArrayList<TeamDTO> teamsListToTeamsListDTO(ArrayList<Team> entityList) {
        ArrayList<TeamDTO> dtoList = new ArrayList<>();
        if (entityList == null) return dtoList;
        for (Team team : entityList) {
            if (team != null) {
                dtoList.add(teamToTeamDTO(team));
            }
        }
        return dtoList;
    }
    public static ArrayList<Team> teamsListDTOToTeamsList(ArrayList<TeamDTO> dtoList) {
        ArrayList<Team> entityList = new ArrayList<>();
        if (dtoList == null) return entityList;
        for (TeamDTO teamDTO : dtoList) {
            if (teamDTO != null) {
                entityList.add(teamDTOToTeam(teamDTO));
            }
        }
        return entityList;
    }
}