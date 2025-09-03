import java.util.ArrayList;
import java.util.List;

public class TournamentManager {
    private List<Tournament> tournaments;
    private final FileHandler<Tournament> fileHandler;

    public TournamentManager(String fileName) {
        fileHandler = new FileHandler<>(fileName);
        tournaments = new ArrayList<>(fileHandler.loadData());
    }

    public List<Tournament> getAll() { return tournaments; }

    public void add(Tournament t) {
        
        if (findById(t.getId()) != null) {
            throw new IllegalArgumentException("Tournament with ID " + t.getId() + " already exists.");
        }
        tournaments.add(t);
        save();
    }

    public void update(int index, Tournament updated) {
        tournaments.set(index, updated);
        save();
    }
    
    public void update(Tournament updated) {
        for(int i = 0; i < tournaments.size(); i++) {
            if (tournaments.get(i).getId() == updated.getId()) {
                tournaments.set(i, updated);
                save();
                return;
            }
        }
    }

    public void delete(int index) {
        tournaments.remove(index);
        save();
    }
    
    private void save() {
        fileHandler.saveData(tournaments);
    }
    
    public Tournament findById(int id) {
        return tournaments.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }
}