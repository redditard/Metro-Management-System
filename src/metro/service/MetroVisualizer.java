package metro.service;

import metro.model.MetroMap;
import metro.model.Station;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MetroVisualizer {

    /**
     * Generates a DOT file representing the metro map for visualization.
     * @param metroMap The MetroMap object containing the data.
     * @param filePath The path where the DOT file should be saved.
     */
    public void generateMapVisualizationFile(MetroMap metroMap, String filePath) {
        String dotRepresentation = generateDotRepresentation(metroMap);
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.print(dotRepresentation);
            System.out.println("Metro map visualization saved to " + filePath);
        } catch (IOException e) {
            System.err.println("Error saving map visualization: " + e.getMessage());
        }
    }

    /**
     * Generates a string representation of the graph in DOT format.
     * @param metroMap The MetroMap object containing the data.
     * @return A string containing the graph in DOT format.
     */
    private String generateDotRepresentation(MetroMap metroMap) {
        StringBuilder dotBuilder = new StringBuilder();
        dotBuilder.append("graph MetroMap {\n");
        dotBuilder.append("  node [shape=ellipse];\n"); // Style for stations

        Set<String> addedEdges = new HashSet<>(); // To avoid duplicate edges in undirected graph
        Map<Station, List<MetroMap.Connection>> adjacencyList = metroMap.getAdjacencyList();

        // Add nodes (stations) and edges (connections)
        for (Map.Entry<Station, List<MetroMap.Connection>> entry : adjacencyList.entrySet()) {
            Station station = entry.getKey();
            // Define the node with its name and line color (using a simple color mapping)
            dotBuilder.append(String.format("  \"%d\" [label=\"%s (%s)\", color=%s, style=filled, fontcolor=white];\n",
                    station.getId(), station.getName(), station.getLine(), getLineColor(station.getLine())));

            for (MetroMap.Connection connection : entry.getValue()) {
                Station neighbor = connection.getDestination();
                int distance = connection.getDistance();

                // Create a unique key for the edge to handle undirected nature
                String edgeKey = Math.min(station.getId(), neighbor.getId()) + "-" + Math.max(station.getId(), neighbor.getId());

                if (addedEdges.add(edgeKey)) { // Add edge only if not already added
                    dotBuilder.append(String.format("  \"%d\" -- \"%d\" [label=\"%d\"];\n",
                            station.getId(), neighbor.getId(), distance));
                }
            }
        }

        dotBuilder.append("}\n");
        return dotBuilder.toString();
    }

    /**
     * Helper method to assign colors based on metro lines for visualization.
     * @param line The metro line name.
     * @return A color string compatible with DOT format.
     */
    private String getLineColor(String line) {
        switch (line.toLowerCase()) {
            case "blue": return "blue";
            case "red": return "red";
            case "yellow": return "yellow";
            case "green": return "green";
            // Add more colors as needed
            default: return "gray";
        }
    }
}
