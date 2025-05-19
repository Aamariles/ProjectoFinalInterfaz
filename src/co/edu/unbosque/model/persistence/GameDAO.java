package co.edu.unbosque.model.persistence;

import java.util.ArrayList;

import co.edu.unbosque.model.Game;
import co.edu.unbosque.model.GameDTO;
import co.edu.unbosque.model.User;

public class GameDAO implements OperationDAO<GameDTO, Game> {
	private ArrayList<Game> gamesList;
	private final String TEXT_FILE_NAME = "src/files/Game.csv";
	private final String SERIAL_FILE_NAME = "src/files/Game.dat";

	public GameDAO() {
		gamesList = new ArrayList<>();

		/*
		 * if (gamesList.isEmpty()) { gamesList.add(new Game(SERIAL_FILE_NAME,
		 * "Apex Legends")); gamesList.add(new Game("Gran Turismo")); gamesList.add(new
		 * Game("Rocket League")); gamesList.add(new Game ("FIFA")); gamesList.add(new
		 * Game("Formula 1"));
		 */

	}

	@Override
	public String showAll() {
		String rta = "";
		if (gamesList.isEmpty()) {
			return " game Empty list";

		} else {
			for (Game game : gamesList) {
				rta += game;
			}
			return rta;
		}

	}

	@Override
	public ArrayList<GameDTO> getAll() {
		return DataMapper.gamesListToGamesListDTO(gamesList);
	}

	@Override
	public boolean add(GameDTO newData) {
		if (find(DataMapper.gameDTOToGame(newData)) == null) {
			gamesList.add(DataMapper.gameDTOToGame(newData));
			return true;

		} else {
			return false;

		}
	}

	@Override
	public boolean delete(GameDTO toDelete) {
		Game found = find(DataMapper.gameDTOToGame(toDelete));
		if (found != null) {
			return gamesList.remove(found);

		} else {
			return false;
		}
	}

	@Override
	public int delete(int index) {
		if (index < 0 || index >= gamesList.size()) {
			// throw new InvalidIndexException("Índice fuera de rango: " + index);
		}
		gamesList.remove(index);
		return 0;
	}

	@Override
	public Game find(Game toFind) {
		Game found = null;
		if (!gamesList.isEmpty()) {
			for (Game game : gamesList) {
				if (game.getGameTitle().equals(toFind.getGameTitle())) {
					found = game;
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
	public boolean update(GameDTO previous, GameDTO newData) {
		Game found = find(DataMapper.gameDTOToGame(previous));
		if (found != null) {
			gamesList.remove(found);
			gamesList.add(DataMapper.gameDTOToGame(newData));
			return true;

		} else {
			return false;
		}
	}
}
