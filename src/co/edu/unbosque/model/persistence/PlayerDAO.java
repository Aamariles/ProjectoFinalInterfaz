package co.edu.unbosque.model.persistence;
import java.io.File;
import java.util.ArrayList;
import co.edu.unbosque.model.Player;
import co.edu.unbosque.model.PlayerDTO;
import co.edu.unbosque.model.Team;
public class PlayerDAO implements OperationDAO<PlayerDTO, Player> {
    private ArrayList<Player> playersList;
    private final String DATA_FOLDER = "data";
    private final String SERIAL_FILE_NAME = DATA_FOLDER + File.separator + "players.dat";
    @SuppressWarnings("unchecked")
    public PlayerDAO() {
        File dataDir = new File(DATA_FOLDER);
        if (!dataDir.exists()) dataDir.mkdirs();
        Object loadedData = FileManager.serializableOpenAndReadFile(SERIAL_FILE_NAME);
        if (loadedData instanceof ArrayList<?> && !((ArrayList<?>)loadedData).isEmpty() && ((ArrayList<?>)loadedData).get(0) instanceof Player) {
            this.playersList = (ArrayList<Player>) loadedData;
        } else {
            this.playersList = new ArrayList<>();
            initializeDefaultData();
        }
    }
    private void initializeDefaultData() {
        Team sampleTeam = new Team("Liga Pro", "Los Invencibles", null);
        add(new PlayerDTO("Juan 'El Mago' Silva", "Masculino", 5, sampleTeam, "Bogotá", "Colombia"));
        add(new PlayerDTO("Ana 'La Artillera' Diaz", "Femenino", 7, sampleTeam, "Medellín", "Colombia"));
        System.out.println("PlayerDAO: Default players created and saved.");
    }
    private void saveData() {
        FileManager.serializableOpenAndWriteFile(SERIAL_FILE_NAME, this.playersList);
    }
    @Override
    public String showAll() {
        if (playersList.isEmpty()) return "Player list is empty.";
        StringBuilder sb = new StringBuilder();
        for(Player p : playersList) sb.append(p.toString()).append("\n");
        return sb.toString();
    }
    @Override
    public ArrayList<PlayerDTO> getAll() {
        return DataMapper.playersListToPlayersListDTO(playersList);
    }
    @Override
    public boolean add(PlayerDTO newData) {
        if (find(DataMapper.playerDTOToPlayer(newData)) == null) {
            playersList.add(DataMapper.playerDTOToPlayer(newData));
            saveData();
            return true;
        }
        return false;
    }
    @Override
    public boolean delete(PlayerDTO toDelete) {
        Player found = find(DataMapper.playerDTOToPlayer(toDelete));
        if (found != null) {
            boolean removed = playersList.remove(found);
            if (removed) saveData();
            return removed;
        }
        return false;
    }
    @Override
    public int delete(int index) {
        if (index < 0 || index >= playersList.size()) return -1;
        playersList.remove(index);
        saveData();
        return 0;
    }
    @Override
    public Player find(Player toFind) {
        if (toFind == null || toFind.getName() == null) return null;
        for (Player player : playersList) {
            if (player.getName().equals(toFind.getName())) {
                return player;
            }
        }
        return null;
    }
    @Override
    public boolean update(PlayerDTO previous, PlayerDTO newData) {
        Player found = find(DataMapper.playerDTOToPlayer(previous));
        if (found != null) {
            playersList.remove(found);
            playersList.add(DataMapper.playerDTOToPlayer(newData));
            saveData();
            return true;
        }
        return false;
    }
}