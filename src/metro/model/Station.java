package metro.model;

public class Station {
    private int id;
    private String name;
    private String line;
    
    public Station(int id, String name, String line) {
        this.id = id;
        this.name = name;
        this.line = line;
    }
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getLine() {
        return line;
    }
    
    @Override
    public String toString() {
        return name + " (" + line + " Line)";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Station station = (Station) obj;
        return id == station.id;
    }
    
    @Override
    public int hashCode() {
        return id;
    }
}
