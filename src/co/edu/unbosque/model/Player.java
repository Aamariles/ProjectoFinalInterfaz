package co.edu.unbosque.model;

import java.io.Serializable;

public class Player implements Serializable{
	private static final long serialVersionUID = 1L;
	private String name;
	private String gender;
	private int experience;
	private Team team;
	private String city;
	private String country;
	
	
	public Player() {

	}


	public Player(String name, String gender, int experience, Team team, String city, String country) {
		super();
		this.name = name;
		this.gender = gender;
		this.experience = experience;
		this.team = team;
		this.city = city;
		this.country = country;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public int getExperience() {
		return experience;
	}


	public void setExperience(int experience) {
		this.experience = experience;
	}


	public Team getTeam() {
		return team;
	}


	public void setTeam(Team team) {
		this.team = team;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	@Override
	public String toString() {
		return "Player [\nname=" + name + ", \ngender=" + gender + ", \nexperience=" + experience + ", \nteam=" + team
				+ ", \ncity=" + city + ", \ncountry=" + country + "]";
	}

}
