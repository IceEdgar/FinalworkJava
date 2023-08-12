import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

class ToyList {
    HashMap<Integer, Toy> toys = new HashMap<>();
    String toyFilepath;
    File f = new File();
    protected int maxKey;

    public ToyList() {
        this.readFromFile("./toylist.txt");
        this.maxKey = Collections.max(toys.keySet());
    }

    void addToyList(Collection<Toy> newtoys) {
        for (Toy t : newtoys) {
            this.addToy(t);
        }
    }

    int addToy(Toy t) {
        int finalId = t.id;
        if (toys.containsKey(t.id)) {
            finalId = ++maxKey;
            t.setId(finalId);
        }
        toys.put(t.id, t);
        return finalId;
    }

    void removeToy(int idNum) {
        toys.remove(idNum);
    }

    void readFromFile(String filepath) {
        this.toyFilepath = filepath;
        for (String line : f.readToys(filepath)) {
            String[] toyParams = line.split(" ", 4);
            int toyId = Integer.parseInt(toyParams[0]);
            toys.put(toyId, new Toy(toyId,
                    Double.parseDouble(toyParams[1]),
                    toyParams[3],
                    Integer.parseInt(toyParams[2])));
        }

    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Toy t : toys.values()) {
            sb.append(t.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}

class Toy implements Comparable<Toy> {
    int id;
    static int idCount;
    double chanceWeight;
    String name;
    int quantity;

    private double chance;

    public Toy(int id, double chanceWeight, String name, int quantity) {
        this.id = id;
        this.chanceWeight = chanceWeight;
        this.name = name;
        this.quantity = quantity;
        this.chance = 0;
    }

    public Toy(double chanceWeight, String name, int quantity) {
        this(idCount++, chanceWeight, name, quantity);
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString() {
        return String.format("id:%d %s, вероятность %.1f, кол-во %d, категория редкости %.2f", this.id, this.name,
                this.chanceWeight, this.quantity, this.chance);
    }

    public double getChance() {
        return chance;
    }

    public void setChance(double chance) {
        this.chance = chance;
    }

    public int compareTo(Toy o) {
        if (o.chance == this.chance) {
            return 0;
        }
        return this.chance < o.chance ? -1 : 1;
    }
}