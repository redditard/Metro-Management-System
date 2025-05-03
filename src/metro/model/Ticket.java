package metro.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Ticket {
    private String passengerName;
    private String phoneNumber;
    private Station source;
    private Station destination;
    private List<Station> route;
    private double fare;
    private LocalDateTime timestamp;
    
    public Ticket(String passengerName, String phoneNumber, Station source, Station destination, 
                  List<Station> route, double fare) {
        this.passengerName = passengerName;
        this.phoneNumber = phoneNumber;
        this.source = source;
        this.destination = destination;
        this.route = route;
        this.fare = fare;
        this.timestamp = LocalDateTime.now();
    }
    
    public String getTicketDetails() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        StringBuilder sb = new StringBuilder();
        
        sb.append("==============================================\n");
        sb.append("             METRO TICKET                     \n");
        sb.append("==============================================\n");
        sb.append("Passenger: ").append(passengerName).append("\n");
        sb.append("Phone: ").append(phoneNumber).append("\n");
        sb.append("Date: ").append(timestamp.format(formatter)).append("\n");
        sb.append("----------------------------------------------\n");
        sb.append("From: ").append(source).append("\n");
        sb.append("To: ").append(destination).append("\n");
        sb.append("----------------------------------------------\n");
        sb.append("Route:\n");
        
        for (int i = 0; i < route.size(); i++) {
            sb.append(i + 1).append(". ").append(route.get(i)).append("\n");
        }
        
        sb.append("----------------------------------------------\n");
        sb.append("Number of stations: ").append(route.size() - 1).append("\n");
        sb.append("Fare: Rs. ").append(String.format("%.2f", fare)).append("\n");
        sb.append("==============================================\n");
        
        return sb.toString();
    }
}
