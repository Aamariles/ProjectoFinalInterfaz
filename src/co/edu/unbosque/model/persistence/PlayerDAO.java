package co.edu.unbosque.model.persistence;

import java.util.ArrayList;
import co.edu.unbosque.model.Player;
import co.edu.unbosque.model.PlayerDTO;
import co.edu.unbosque.model.User;

public class PlayerDAO implements OperationDAO<PlayerDTO, Player> {
	private ArrayList<Player> playersList;
	private final String TEXT_FILE_NAME = "src/files/Player.csv";
	private final String SERIAL_FILE_NAME ="src/files/Player.dat";


	public PlayerDAO() {
		playersList = new ArrayList<>();

	}

	@Override
	public String showAll() {
		String rta = "";
		if (playersList.isEmpty()) {
			return " Player Empty list";
			
		}else {
			for (Player player : playersList) {
				rta += player;
			}
			return rta;
		}
		
	}

	@Override
	public ArrayList<PlayerDTO> getAll() {
		return DataMapper.playersListToPlayersListDTO(playersList);
	}

	@Override
	public boolean add(PlayerDTO newData) {
		if (find(DataMapper.playerDTOToPlayer(newData))== null) {
			playersList.add(DataMapper.playerDTOToPlayer(newData));
			return true;
			
		}else {
			return false;
		}
	}

	@Override
	public boolean delete(PlayerDTO toDelete) {
		Player found = find(DataMapper.playerDTOToPlayer(toDelete));
		if (found != null) {
			return playersList.remove(found);
			
		}else {
		return false;
		}
	}

	@Override
	public int delete(int index) {
		if (index < 0 || index >= playersList.size()) {
			//throw new InvalidIndexException("Índice fuera de rango: " + index);
		}
		playersList.remove(index);
		return 0;
	}


	@Override
	public Player find(Player toFind) {
		Player found = null;
		if (!playersList.isEmpty()) {
			for (Player player : playersList) {
				if (player.getName().equals(toFind.getName())) {
					found = player;
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
	public boolean update(PlayerDTO previous, PlayerDTO newData) {
		Player found = find(DataMapper.playerDTOToPlayer(previous));
		if (found != null) {
			playersList.remove(found);
			playersList.add(DataMapper.playerDTOToPlayer(newData));
			return true;
			
		}else {
			return false;
		}
	}

}
