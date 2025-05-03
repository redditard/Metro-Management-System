package metro.service;

import metro.model.MetroMap;
import metro.model.Station;
import metro.model.Ticket;
import java.io.*;
import java.util.List;

public class MetroService {
    private MetroMap metroMap;
    
    public MetroService() {
        metroMap = new MetroMap();
        initializeMetroMap();
    }
    
    private void initializeMetroMap() {
        // Create stations
        Station s1 = new Station(1, "Central", "Blue");
        Station s2 = new Station(2, "Rajiv Chowk", "Blue");
        Station s3 = new Station(3, "Karol Bagh", "Blue");
        Station s4 = new Station(4, "Inderlok", "Red");
        Station s5 = new Station(5, "Chandni Chowk", "Yellow");
        Station s6 = new Station(6, "Civil Lines", "Yellow");
        Station s7 = new Station(7, "Kashmere Gate", "Red");
        Station s8 = new Station(8, "Connaught Place", "Yellow");
        Station s9 = new Station(9, "Janakpuri", "Blue");
        Station s10 = new Station(10, "Dwarka", "Blue");
        
        // Add stations to map
        metroMap.addStation(s1);
        metroMap.addStation(s2);
        metroMap.addStation(s3);
        metroMap.addStation(s4);
        metroMap.addStation(s5);
        metroMap.addStation(s6);
        metroMap.addStation(s7);
        metroMap.addStation(s8);
        metroMap.addStation(s9);
        metroMap.addStation(s10);
        
        // Add connections with distances
        metroMap.addConnection(s1, s2, 5);
        metroMap.addConnection(s2, s3, 4);
        metroMap.addConnection(s3, s4, 6);
        metroMap.addConnection(s4, s7, 3);
        metroMap.addConnection(s7, s5, 2);
        metroMap.addConnection(s5, s6, 4);
        metroMap.addConnection(s2, s8, 3);
        metroMap.addConnection(s8, s5, 5);
        metroMap.addConnection(s1, s9, 7);
        metroMap.addConnection(s9, s10, 8);
    }
    
    public Ticket processJourneyRequest(String passengerName, String phoneNumber, 
                                        String sourceName, String destinationName) {
        Station source = metroMap.getStationByName(sourceName);
        Station destination = metroMap.getStationByName(destinationName);
        
        if (source == null || destination == null) {
            return null;
        }
        
        List<Station> route = metroMap.findShortestPath(source, destination);
        
        if (route.isEmpty()) {
            return null;
        }
        
        double fare = FareCalculator.calculateFare(route);
        
        return new Ticket(passengerName, phoneNumber, source, destination, route, fare);
    }
    
    public void saveTicket(Ticket ticket, String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.print(ticket.getTicketDetails());
        } catch (IOException e) {
            System.err.println("Error saving ticket: " + e.getMessage());
        }
    }
    
    public String[] readJourneyRequest(String filePath) {
        String[] journeyInfo = new String[4]; // name, phone, source, destination
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            journeyInfo[0] = reader.readLine(); // Name
            journeyInfo[1] = reader.readLine(); // Phone
            journeyInfo[2] = reader.readLine(); // Source
            journeyInfo[3] = reader.readLine(); // Destination
        } catch (IOException e) {
            System.err.println("Error reading journey request: " + e.getMessage());
            return null;
        }
        
        return journeyInfo;
    }

    // Getter for the MetroMap instance (needed for visualization)
    public MetroMap getMetroMap() {
        return metroMap;
    }
}
