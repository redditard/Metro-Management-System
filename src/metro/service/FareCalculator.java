package metro.service;

import metro.model.Station;
import java.util.List;

public class FareCalculator {
    private static final double BASE_FARE = 10.0;
    private static final double FARE_PER_STATION = 5.0;
    private static final double LINE_CHANGE_FEE = 3.0;
    
    public static double calculateFare(List<Station> route) {
        if (route == null || route.size() <= 1) {
            return 0.0;
        }
        
        double fare = BASE_FARE;
        
        // Add fare for each station traveled
        fare += (route.size() - 1) * FARE_PER_STATION;
        
        // Add fare for line changes
        String currentLine = route.get(0).getLine();
        for (int i = 1; i < route.size(); i++) {
            String nextLine = route.get(i).getLine();
            if (!currentLine.equals(nextLine)) {
                fare += LINE_CHANGE_FEE;
                currentLine = nextLine;
            }
        }
        
        return fare;
    }
}
