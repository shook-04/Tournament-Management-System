import java.io.Serializable;
import java.util.Date;

public class Player implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum Gender { MALE, FEMALE }

    private int id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private Date dateOfBirth;
    private String country;
    private int tournamentId; 

    public Player() {}

    public Player(int id, String firstName, String lastName,
                  Gender gender, Date dateOfBirth, String country,
                  int tournamentId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.country = country;
        this.tournamentId = tournamentId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }
    public Date getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public int getTournamentId() { return tournamentId; }
    public void setTournamentId(int tournamentId) { this.tournamentId = tournamentId; }

    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    @Override
    public String toString() {
        return getFullName(); 
    }
}