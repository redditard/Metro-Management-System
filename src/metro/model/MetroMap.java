package metro.model;

import java.util.*;

public class MetroMap {
    private Map<Station, List<Connection>> adjacencyList;
    private Map<String, Station> stationNameMap;
    
    public MetroMap() {
        adjacencyList = new HashMap<>();
        stationNameMap = new HashMap<>();
    }
    
    public void addStation(Station station) {
        adjacencyList.putIfAbsent(station, new ArrayList<>());
        stationNameMap.put(station.getName().toLowerCase(), station);
    }
    
    public void addConnection(Station source, Station destination, int distance) {
        adjacencyList.get(source).add(new Connection(destination, distance));
        adjacencyList.get(destination).add(new Connection(source, distance)); // Assuming bidirectional connections
    }
    
    public Station getStationByName(String name) {
        return stationNameMap.get(name.toLowerCase());
    }
    
    public List<Station> findShortestPath(Station source, Station destination) {
        Map<Station, Integer> distances = new HashMap<>();
        Map<Station, Station> previousStations = new HashMap<>();
        PriorityQueue<Station> queue = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        Set<Station> visited = new HashSet<>();
        
        // Initialize distances
        for (Station station : adjacencyList.keySet()) {
            distances.put(station, Integer.MAX_VALUE);
        }
        distances.put(source, 0);
        queue.add(source);
        
        // Dijkstra's algorithm
        while (!queue.isEmpty()) {
            Station current = queue.poll();
            
            if (current.equals(destination)) {
                break;
            }
            
            if (visited.contains(current)) {
                continue;
            }
            
            visited.add(current);
            
            for (Connection connection : adjacencyList.get(current)) {
                Station neighbor = connection.getDestination();
                int newDistance = distances.get(current) + connection.getDistance();
                
                if (newDistance < distances.get(neighbor)) {
                    distances.put(neighbor, newDistance);
                    previousStations.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }
        
        // Build path
        List<Station> path = new ArrayList<>();
        Station current = destination;
        
        while (current != null) {
            path.add(current);
            current = previousStations.get(current);
        }
        
        Collections.reverse(path);
        return path.isEmpty() || !path.get(0).equals(source) ? Collections.emptyList() : path;
    }
    
    public int calculateDistance(List<Station> path) {
        int totalDistance = 0;
        
        for (int i = 0; i < path.size() - 1; i++) {
            Station current = path.get(i);
            Station next = path.get(i + 1);
            
            for (Connection connection : adjacencyList.get(current)) {
                if (connection.getDestination().equals(next)) {
                    totalDistance += connection.getDistance();
                    break;
                }
            }
        }
        
        return totalDistance;
    }
    
    // Getter for the adjacency list (needed for visualization)
    public Map<Station, List<Connection>> getAdjacencyList() {
        return adjacencyList;
    }

    // Getter for the station name map (optional, but might be useful)
    public Map<String, Station> getStationNameMap() {
        return stationNameMap;
    }

    public static class Connection { // Changed to public static for access from visualizer
        private Station destination;
        private int distance;
        
        public Connection(Station destination, int distance) {
            this.destination = destination;
            this.distance = distance;
        }
        
        public Station getDestination() {
            return destination;
        }
        
        public int getDistance() {
            return distance;
        }
    }
}
