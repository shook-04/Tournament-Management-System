import java.io.Serializable;
import java.util.Date;

public class Tournament implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private Date startDate;
    private Date endDate;
    private int numOfRounds;
    private int typeId; 
    private String surface;

    public Tournament() {}

    public Tournament(int id, String name, Date startDate, Date endDate,
                      int numOfRounds, int typeId, String surface) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numOfRounds = numOfRounds;
        this.typeId = typeId;
        this.surface = surface;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
    public int getNumOfRounds() { return numOfRounds; }
    public void setNumOfRounds(int numOfRounds) { this.numOfRounds = numOfRounds; }
    public int getTypeId() { return typeId; }
    public void setTypeId(int typeId) { this.typeId = typeId; }
    public String getSurface() { return surface; }
    public void setSurface(String surface) { this.surface = surface; }
    
    @Override
    public String toString() {
        return this.name; 
    }
}