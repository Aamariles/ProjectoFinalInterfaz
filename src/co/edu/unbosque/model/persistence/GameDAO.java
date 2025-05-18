package co.edu.unbosque.model.persistence;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDate;
import co.edu.unbosque.model.Game;
import co.edu.unbosque.model.GameDTO;
import co.edu.unbosque.model.Player;
import co.edu.unbosque.model.Team;
public class GameDAO implements OperationDAO<GameDTO, Game> {
    private ArrayList<Game> gamesList;
    private final String DATA_FOLDER = "data";
    private final String SERIAL_FILE_NAME = DATA_FOLDER + File.separator + "games.dat";
    @SuppressWarnings("unchecked")
    public GameDAO() {
        File dataDir = new File(DATA_FOLDER);
        if (!dataDir.exists()) dataDir.mkdirs();
        Object loadedData = FileManager.serializableOpenAndReadFile(SERIAL_FILE_NAME);
        if (loadedData instanceof ArrayList<?> && !((ArrayList<?>)loadedData).isEmpty() && ((ArrayList<?>)loadedData).get(0) instanceof Game) {
            this.gamesList = (ArrayList<Game>) loadedData;
        } else {
            this.gamesList = new ArrayList<>();
            initializeDefaultData();
        }
    }
    private void initializeDefaultData() {
        Team teamAlpha = new Team("FIFA League", "Alpha Gamers", null);
        Team teamBeta = new Team("FIFA League", "Beta Warriors", null);
        ArrayList<Team> match1Teams = new ArrayList<>();
        match1Teams.add(teamAlpha);
        match1Teams.add(teamBeta);
        Player p1 = new Player("Juan", "M", 5, teamAlpha, "Bogota", "COL");
        Player p2 = new Player("Maria", "F", 4, teamBeta, "Medellin", "COL");
        ArrayList<Player> match1Players = new ArrayList<>();
        match1Players.add(p1);
        match1Players.add(p2);
        add(new GameDTO("FIFA 2025 - Final", match1Players, LocalDate.of(2025, 12, 20), match1Teams));
        add(new GameDTO("Rocket League - Semifinal", new ArrayList<>(), LocalDate.of(2025, 11, 15), new ArrayList<>()));
        System.out.println("GameDAO: Default games created and saved.");
    }
    private void saveData() {
        FileManager.serializableOpenAndWriteFile(SERIAL_FILE_NAME, this.gamesList);
    }
    @Override
    public String showAll() {
        if (gamesList.isEmpty()) return "Game list is empty.";
        StringBuilder sb = new StringBuilder();
        for(Game g : gamesList) sb.append(g.toString()).append("\n");
        return sb.toString();
    }
    @Override
    public ArrayList<GameDTO> getAll() {
        return DataMapper.gamesListToGamesListDTO(gamesList);
    }
    @Override
    public boolean add(GameDTO newData) {
        if (find(DataMapper.gameDTOToGame(newData)) == null) {
            gamesList.add(DataMapper.gameDTOToGame(newData));
            saveData();
            return true;
        }
        return false;
    }
    @Override
    public boolean delete(GameDTO toDelete) {
        Game found = find(DataMapper.gameDTOToGame(toDelete));
        if (found != null) {
            boolean removed = gamesList.remove(found);
            if (removed) saveData();
            return removed;
        }
        return false;
    }
    @Override
    public int delete(int index) {
        if (index < 0 || index >= gamesList.size()) return -1;
        gamesList.remove(index);
        saveData();
        return 0;
    }
    @Override
    public Game find(Game toFind) {
        if (toFind == null || toFind.getGameTitle() == null || toFind.getDate() == null) return null;
        for (Game game : gamesList) {
            if (game.getGameTitle().equals(toFind.getGameTitle()) && game.getDate().equals(toFind.getDate())) {
                return game;
            }
        }
        return null;
    }
    @Override
    public boolean update(GameDTO previous, GameDTO newData) {
        Game found = find(DataMapper.gameDTOToGame(previous));
        if (found != null) {
            gamesList.remove(found);
            gamesList.add(DataMapper.gameDTOToGame(newData));
            saveData();
            return true;
        }
        return false;
    }
}