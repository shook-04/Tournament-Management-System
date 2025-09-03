import java.util.ArrayList;
import java.util.List;

public class TournamentTypeManager 
{
    private final List<TournamentType> types;
    private final FileHandler<TournamentType> fileHandler;

    public TournamentTypeManager(String fileName) 
    {
        fileHandler = new FileHandler<>(fileName);
        types = new ArrayList<>(fileHandler.loadData());
    }

    public List<TournamentType> getAll() { return types; }

    public void add(TournamentType t) 
    {
        types.add(t);
        fileHandler.saveData(types);
    }

    public void update(int index, TournamentType updated) 
    {
        types.set(index, updated);
        fileHandler.saveData(types);
    }

    public void delete(int index) 
    {
        types.remove(index);
        fileHandler.saveData(types);
    }
}

