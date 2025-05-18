package co.edu.unbosque.model;
import co.edu.unbosque.model.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Random;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;


public class ModelFacade {
    private UserDAO userDAO;
    private CoachDAO coachDAO;
    private GameDAO gameDAO;
    private PlayerDAO playerDAO;
    private TeamDAO teamDAO;


    private static final int ITERATIONS = 1000;
    private static final int KEY_LENGTH = 256;
    private static final Random RANDOM = new Random();


    public ModelFacade() {

        this.userDAO = new UserDAO();
        this.coachDAO = new CoachDAO();
        this.gameDAO = new GameDAO();
        this.playerDAO = new PlayerDAO();
        this.teamDAO = new TeamDAO();

    }


    private byte[] generateSaltInternal() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return salt;
    }


    private String generateHashInternal(String password, byte[] salt) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = skf.generateSecret(spec).getEncoded();
            return Base64.getUrlEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error generating hash in ModelFacade", e);
        }
    }


    private void addTestAdminUserIfNotPresent() {
        User searchUser = new User("admin", null, null);
        if (userDAO.find(searchUser) == null) {
            byte[] salt = generateSaltInternal();
            String hashedPassword = generateHashInternal("123", salt);
            UserDTO adminUserDTO = new UserDTO("admin", hashedPassword, salt);
            if (userDAO.add(adminUserDTO)) { // add en UserDAO ya guarda
                System.out.println("ModelFacade: Default admin user ('admin'/'123') created.");
            }
        }
    }



    public boolean validateUserCredentials(String username, String password) {
        return userDAO.ValidateUser(username, password);
    }

    public boolean registerUser(String username, String plainPassword) {
        return userDAO.registerUser(username, plainPassword);
    }


    public Object[] getDashboardSummaryData() {
        int activeTournaments = 0; // Placeholder
        String upcomingMatchInfo = "Upcoming: N/A";
        String topPlayerName = "Top Player: N/A";


        return new Object[]{upcomingMatchInfo, String.valueOf(activeTournaments) + " Active", topPlayerName};
    }

    public List<Object[]> getDashboardRecentMatches() {
        List<GameDTO> recentGamesDTO = gameDAO.getAll(); // Debería filtrarse por recientes y limitarse
        List<Object[]> viewData = new ArrayList<>();


        if (recentGamesDTO != null) {
            for (GameDTO game : recentGamesDTO.stream().limit(5).collect(Collectors.toList())) {
                String matchDescription = game.getGameTitle();
                if (game.getTeamsList() != null && !game.getTeamsList().isEmpty()) {
                    matchDescription = game.getTeamsList().stream()
                            .map(team -> team != null ? team.getTeamName() : "Equipo Desconocido")
                            .collect(Collectors.joining(" vs "));
                }

                viewData.add(new Object[]{
                        matchDescription,
                        game.getDate() != null ? game.getDate().toString() : "N/A",
                        "Status/Result Placeholder"
                });
            }
        }
        return viewData;
    }

    public List<Object[]> getTournamentsListForOverview() {
        System.out.println("ModelFacade: Fetching tournaments list (placeholder - needs TournamentDAO).");
        List<Object[]> data = new ArrayList<>();

        data.add(new Object[]{"Copa Leyendas (FIFA)", "En curso", "/imagenes/Fifa.png", "16 equipos", "FIFA", "Nov 2025", true});
        data.add(new Object[]{"Reto Gran Turismo", "Próximo", "/imagenes/GranTurismo.png", "32 pilotos", "Gran Turismo", "Dic 2025", false});
        data.add(new Object[]{"F1 E-Prix", "Registrando", "/imagenes/Formula1.png", "20 equipos", "Formula 1", "Ene 2026", true});
        data.add(new Object[]{"Rocket League Championship", "Finalizado", "/imagenes/RocketLeague.png", "8 equipos", "Rocket League", "Oct 2025", false});
        return data;
    }



    public List<TeamDTO> getAllTeamDTOs() {
        return teamDAO.getAll();
    }

    public boolean addTeam(TeamDTO teamDTO) {

        return teamDAO.add(teamDTO);
    }
    // ... otros métodos para Teams (find, update, delete)


    public List<GameDTO> getAllMatchDTOs() {
        return gameDAO.getAll();
    }

    public boolean addMatch(GameDTO gameDTO) {
        return gameDAO.add(gameDTO);
    }
    // ... otros métodos para Matches (find, update, delete)


    public List<PlayerDTO> getAllPlayerDTOs() {
        return playerDAO.getAll();
    }
    public boolean addPlayer(PlayerDTO playerDTO) {

        return playerDAO.add(playerDTO);
    }
    // ... otros métodos para Players (find, update, delete, addPlayerToTeam)

    // --- Coaches Data ---
    public List<CoachDTO> getAllCoachDTOs() {
        return coachDAO.getAll();
    }
    public boolean addCoach(CoachDTO coachDTO) {
        return coachDAO.add(coachDTO);
    }
    // ... Otros métodos para Coaches ...
}
