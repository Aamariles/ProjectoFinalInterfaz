package co.edu.unbosque.model.persistence;

import java.io.File;
import java.util.ArrayList;
import co.edu.unbosque.model.Coach;
import co.edu.unbosque.model.CoachDTO;
import co.edu.unbosque.model.Team;

public class CoachDAO implements OperationDAO<CoachDTO, Coach> {
    private ArrayList<Coach> coachesList;
    private final String DATA_FOLDER = "data";
    private final String SERIAL_FILE_NAME = DATA_FOLDER + File.separator + "coaches.dat";

    @SuppressWarnings("unchecked")
    public CoachDAO() {
        File dataDir = new File(DATA_FOLDER);
        if (!dataDir.exists()) dataDir.mkdirs();

        Object loadedData = FileManager.serializableOpenAndReadFile(SERIAL_FILE_NAME);
        if (loadedData instanceof ArrayList<?> && !((ArrayList<?>)loadedData).isEmpty() && ((ArrayList<?>)loadedData).get(0) instanceof Coach) {
            this.coachesList = (ArrayList<Coach>) loadedData;
        } else {
            this.coachesList = new ArrayList<>();
            initializeDefaultData();
        }
    }

    private void initializeDefaultData() {
        // Crear algunos equipos de ejemplo para los coaches
        Team teamA = new Team("Liga Pro", "Los Cóndores", null);
        Team teamB = new Team("Liga Pro", "Las Águilas", null);
        ArrayList<Team> coach1Teams = new ArrayList<>();
        coach1Teams.add(teamA);

        add(new CoachDTO("Carlos 'El Estratega' Perez", 10, "Tácticas Ofensivas", "/imagenes/coach_perez.png", coach1Teams));
        add(new CoachDTO("Ana 'La Motivadora' Gomez", 8, "Desarrollo de Talento", "/imagenes/coach_gomez.png", new ArrayList<>()));

        System.out.println("CoachDAO: Default coaches created and saved.");
    }

    private void saveData() {
        FileManager.serializableOpenAndWriteFile(SERIAL_FILE_NAME, this.coachesList);
    }

    @Override
    public String showAll() {
        // ... (implementación similar a UserDAO)
        if (coachesList.isEmpty()) return "Coach list is empty.";
        StringBuilder sb = new StringBuilder();
        for(Coach c : coachesList) sb.append(c.toString()).append("\n");
        return sb.toString();
    }

    @Override
    public ArrayList<CoachDTO> getAll() {
        return DataMapper.coachesListToCoachesListDTO(coachesList);
    }

    @Override
    public boolean add(CoachDTO newData) {
        if (find(DataMapper.coachDTOToCoach(newData)) == null) {
            coachesList.add(DataMapper.coachDTOToCoach(newData));
            saveData();
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(CoachDTO toDelete) {
        Coach found = find(DataMapper.coachDTOToCoach(toDelete));
        if (found != null) {
            boolean removed = coachesList.remove(found);
            if (removed) saveData();
            return removed;
        }
        return false;
    }

    @Override
    public int delete(int index) {
        if (index < 0 || index >= coachesList.size()) return -1;
        coachesList.remove(index);
        saveData();
        return 0;
    }

    @Override
    public Coach find(Coach toFind) {
        if (toFind == null || toFind.getName() == null) return null;
        for (Coach coach : coachesList) {
            if (coach.getName().equals(toFind.getName())) {
                return coach;
            }
        }
        return null;
    }

    @Override
    public boolean update(CoachDTO previous, CoachDTO newData) {
        Coach found = find(DataMapper.coachDTOToCoach(previous));
        if (found != null) {
            coachesList.remove(found);
            coachesList.add(DataMapper.coachDTOToCoach(newData));
            saveData();
            return true;
        }
        return false;
    }
}