package co.edu.unbosque.model.persistence;

import java.util.ArrayList;

import co.edu.unbosque.model.Team;
import co.edu.unbosque.model.TeamDTO;
import co.edu.unbosque.model.User;

public class TeamDAO implements OperationDAO<TeamDTO, Team> {
	private ArrayList<Team> teamsList;
	private final String TEXT_FILE_NAME = "src/files/Team.csv";
	private final String SERIAL_FILE_NAME = "src/files/Team.dat";

	public TeamDAO() {
		teamsList = new ArrayList<>();
	}

	@Override
	public String showAll() {
		String rta = "";
		if (teamsList.isEmpty()) {
			return " Team Empty list";

		} else {
			for (Team team : teamsList) {
				rta += team;
			}
			return rta;
		}
	}

	@Override
	public ArrayList<TeamDTO> getAll() {
		return DataMapper.teamsListToTeamsListDTO(teamsList);
	}

	@Override
	public boolean add(TeamDTO newData) {
		if (find(DataMapper.teamDTOToTeam(newData)) == null) {
			teamsList.add(DataMapper.teamDTOToTeam(newData));
			return true;

		} else {
			return false;
		}
	}

	@Override
	public boolean delete(TeamDTO toDelete) {
		Team found = find(DataMapper.teamDTOToTeam(toDelete));
		if (found != null) {
			return teamsList.remove(found);

		} else {
			return false;
		}

	}

	@Override
	public int delete(int index) {
		if (index < 0 || index >= teamsList.size()) {
			// throw new InvalidIndexException("Índice fuera de rango: " + index);
		}
		teamsList.remove(index);
		return 0;
	}

	@Override
	public Team find(Team toFind) {
		Team found = null;
		if (!teamsList.isEmpty()) {
			for (Team team : teamsList) {
				if (team.getTeamName().equals(toFind.getTeamName())) {
					found = team;
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
	public boolean update(TeamDTO previous, TeamDTO newData) {
		Team found = find(DataMapper.teamDTOToTeam(previous));
		if (found != null) {
			teamsList.remove(found);
			teamsList.add(DataMapper.teamDTOToTeam(newData));
			return true;

		} else {
			return false;
		}

	}
}
