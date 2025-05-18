package co.edu.unbosque.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class GameDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String gameTitle;
    private ArrayList<Player> playersList;
    private LocalDate date;
    private ArrayList<Team> teamsList;

    public GameDTO() {
    }

    public GameDTO(String gameTitle, ArrayList<Player> playersList, LocalDate date, ArrayList<Team> teamsList) {
        super();
        this.gameTitle = gameTitle;
        this.playersList = playersList;
        this.date = date;
        this.teamsList = teamsList;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public ArrayList<Player> getPlayersList() {
        return playersList;
    }

    public void setPlayersList(ArrayList<Player> playersList) {
        this.playersList = playersList;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ArrayList<Team> getTeamsList() {
        return teamsList;
    }

    public void setTeamsList(ArrayList<Team> teamsList) {
        this.teamsList = teamsList;
    }

    @Override
    public String toString() {
        return "GameDTO [\ngameTitle=" + gameTitle + ", \nplayersList=" + playersList + ", ndate=" + date + ", \nteamsList="
                + teamsList + "]";
    }

}
