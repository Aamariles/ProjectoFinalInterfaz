package co.edu.unbosque.model;
import java.io.Serializable;
import java.util.ArrayList;
public class Coach implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int experience;
    private String specialty;
    private String path;
    private ArrayList<Team> teamsList;
    public Coach() {
    }
    public Coach(String name, int experience, String specialty, String path, ArrayList<Team> teamsList) {
        super();
        this.name = name;
        this.experience = experience;
        this.specialty = specialty;
        this.path = path;
        this.teamsList = teamsList;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getExperience() {
        return experience;
    }
    public void setExperience(int experience) {
        this.experience = experience;
    }
    public String getSpecialty() {
        return specialty;
    }
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public ArrayList<Team> getTeamsList() {
        return teamsList;
    }
    public void setTeamsList(ArrayList<Team> teamsList) {
        this.teamsList = teamsList;
    }
    @Override
    public String toString() {
        return "Coach [\nname=" + name + ", \nexperience=" + experience + ", \nspecialty=" + specialty + ", \npath=" + path
                + ", \nteamsList=" + teamsList + "]";
    }
}