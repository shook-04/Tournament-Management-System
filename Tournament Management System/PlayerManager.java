import java.util.ArrayList;
import java.util.List;

public class PlayerManager {
    private List<Player> players;
    private final FileHandler<Player> fileHandler;

    public PlayerManager(String fileName) {
        fileHandler = new FileHandler<>(fileName);
        players = new ArrayList<>(fileHandler.loadData());
    }

    public List<Player> getAll() { return players; }

    public void add(Player p) {
        
        if (findById(p.getId()) != null) {
            throw new IllegalArgumentException("Player with ID " + p.getId() + " already exists.");
        }
        players.add(p);
        save();
    }

    public void update(int index, Player updated) {
        players.set(index, updated);
        save();
    }

    public void delete(int index) {
        players.remove(index);
        save();
    }
    
    private void save() {
        fileHandler.saveData(players);
    }
    
    public Player findById(int id) {
        return players.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }
}