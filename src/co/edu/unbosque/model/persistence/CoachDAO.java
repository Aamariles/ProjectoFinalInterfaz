package co.edu.unbosque.model.persistence;

import java.util.ArrayList;

import co.edu.unbosque.model.Coach;
import co.edu.unbosque.model.CoachDTO;
import co.edu.unbosque.model.Player;

public class CoachDAO implements OperationDAO<CoachDTO, Coach>{
	private ArrayList<Coach> coachesList;
	private final String TEXT_FILE_NAME = "src/files/Coach.csv";
	private final String SERIAL_FILE_NAME ="src/files/Coach.dat";
	
	public CoachDAO() {
		coachesList = new ArrayList<>()	;	
	}
	

	@Override
	public String showAll() {
		String rta = "";
		if (coachesList.isEmpty()) {
			return " Coach Empty list";
			
		}else {
			for (Coach coach : coachesList) {
				rta += coach;
			}
			return rta;
		}
		
	}

	@Override
	public ArrayList<CoachDTO> getAll() {
		return DataMapper.coachesListToCoachesListDTO(coachesList);
	}

	@Override
	public boolean add(CoachDTO newData) {
	 if (find(DataMapper.coachDTOToCoach(newData))== null) {
			coachesList.add(DataMapper.coachDTOToCoach(newData));
			return true;
			
		}else {
			return false;
		}
	}

	@Override
	public boolean delete(CoachDTO toDelete) {
		Coach found = find(DataMapper.coachDTOToCoach(toDelete));
		if (found != null) {
			return coachesList.remove(found);
			
		}else {
		return false;
		}
	}
	
	@Override
	public int delete(int index) {
		if (index < 0 || index >= coachesList.size()) {
			//throw new InvalidIndexException("Índice fuera de rango: " + index);
		}
		coachesList.remove(index);
		return 0;
	}

	@Override
	public Coach find(Coach toFind) {
		Coach found = null;
		if (!coachesList.isEmpty()) {
			for (Coach coach : coachesList) {
				if (coach.getName().equals(toFind.getName())) {
					found = coach;
					return found;

				} else {
					continue;
				}

			}

		} else {
			return null;
		}
		return null;
	}

	@Override
	public boolean update(CoachDTO previous, CoachDTO newData) {
		Coach found = find(DataMapper.coachDTOToCoach(previous));
		if (found != null) {
			coachesList.remove(found);
		    coachesList.add(DataMapper.coachDTOToCoach(newData));
			return true;
			
		}else {
			return false;
		}
		
	}
	
}
