package co.edu.unbosque.model.persistence;
import java.io.File;
import java.util.ArrayList;
import co.edu.unbosque.model.Team;
import co.edu.unbosque.model.TeamDTO;
import co.edu.unbosque.model.Coach;
public class TeamDAO implements OperationDAO<TeamDTO, Team> {
    private ArrayList<Team> teamsList;
    private final String DATA_FOLDER = "data";
    private final String SERIAL_FILE_NAME = DATA_FOLDER + File.separator + "teams.dat";
    @SuppressWarnings("unchecked")
    public TeamDAO() {
        File dataDir = new File(DATA_FOLDER);
        if (!dataDir.exists()) dataDir.mkdirs();
        Object loadedData = FileManager.serializableOpenAndReadFile(SERIAL_FILE_NAME);
        if (loadedData instanceof ArrayList<?> && !((ArrayList<?>)loadedData).isEmpty() && ((ArrayList<?>)loadedData).get(0) instanceof Team) {
            this.teamsList = (ArrayList<Team>) loadedData;
        } else {
            this.teamsList = new ArrayList<>();
            initializeDefaultData();
        }
    }
    private void initializeDefaultData() {
        Coach coach1 = new Coach("Ricardo 'El Profe' Gareis", 15, "Estrategia Global", null, new ArrayList<>());
        add(new TeamDTO("FIFA Champions League", "Los Galácticos FC", coach1));
        add(new TeamDTO("Rocket League Masters", "Quantum Strikers", null));
        System.out.println("TeamDAO: Default teams created and saved.");
    }
    private void saveData() {
        FileManager.serializableOpenAndWriteFile(SERIAL_FILE_NAME, this.teamsList);
    }
    @Override
    public String showAll() {
        if (teamsList.isEmpty()) return "Team list is empty.";
        StringBuilder sb = new StringBuilder();
        for(Team t : teamsList) sb.append(t.toString()).append("\n");
        return sb.toString();
    }
    @Override
    public ArrayList<TeamDTO> getAll() {
        return DataMapper.teamsListToTeamsListDTO(teamsList);
    }
    @Override
    public boolean add(TeamDTO newData) {
        if (find(DataMapper.teamDTOToTeam(newData)) == null) {
            teamsList.add(DataMapper.teamDTOToTeam(newData));
            saveData();
            return true;
        }
        return false;
    }
    @Override
    public boolean delete(TeamDTO toDelete) {
        Team found = find(DataMapper.teamDTOToTeam(toDelete));
        if (found != null) {
            boolean removed = teamsList.remove(found);
            if (removed) saveData();
            return removed;
        }
        return false;
    }
    @Override
    public int delete(int index) {
        if (index < 0 || index >= teamsList.size()) return -1;
        teamsList.remove(index);
        saveData();
        return 0;
    }
    @Override
    public Team find(Team toFind) {
        if (toFind == null || toFind.getTeamName() == null) return null;
        for (Team team : teamsList) {
            if (team.getTeamName().equals(toFind.getTeamName())) {
                return team;
            }
        }
        return null;
    }
    @Override
    public boolean update(TeamDTO previous, TeamDTO newData) {
        Team found = find(DataMapper.teamDTOToTeam(previous));
        if (found != null) {
            teamsList.remove(found);
            teamsList.add(DataMapper.teamDTOToTeam(newData));
            saveData();
            return true;
        }
        return false;
    }
}