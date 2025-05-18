package co.edu.unbosque.model;

import java.io.Serializable;

public class Team  implements Serializable {
	private static final long serialVersionUID = 1L;
	private String tournamentFormat ;
	private String teamName;
	private Coach coach;
	
	public Team() {
	}

	public Team(String tournamentFormat, String teamName, Coach coach) {
		super();
		this.tournamentFormat = tournamentFormat;
		this.teamName = teamName;
		this.coach = coach;
	}

	public String getTournamentFormat() {
		return tournamentFormat;
	}

	public void setTournamentFormat(String tournamentFormat) {
		this.tournamentFormat = tournamentFormat;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public Coach getCoach() {
		return coach;
	}

	public void setCoach(Coach coach) {
		this.coach = coach;
	}

	@Override
	public String toString() {
		return "Team [\ntournamentFormat=" + tournamentFormat + ", \nteamName=" + teamName + ", \ncoach=" + coach + "]";
	}

}
