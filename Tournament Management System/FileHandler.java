import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler<T> {

    private final String fileName;

    public FileHandler(String fileName) {
        this.fileName = fileName;
    }

    @SuppressWarnings("unchecked")
    public List<T> loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>(); 
        }
    }

    public void saveData(List<T> data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

