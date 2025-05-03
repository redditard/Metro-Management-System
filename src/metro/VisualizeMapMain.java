package metro;

import metro.model.MetroMap;
import metro.service.MetroService;
import metro.service.MetroVisualizer;

import java.util.Scanner;

public class VisualizeMapMain {

    public static void main(String[] args) {
        // We need MetroService to get the initialized MetroMap
        MetroService metroService = new MetroService();
        MetroMap metroMap = metroService.getMetroMap(); // Get the map data

        MetroVisualizer visualizer = new MetroVisualizer();
        Scanner scanner = new Scanner(System.in);

        System.out.println("METRO MAP VISUALIZATION");
        System.out.println("=======================");
        System.out.print("Enter file path to save visualization (e.g., metro_map.dot): ");
        String dotOutputFile = scanner.nextLine();

        visualizer.generateMapVisualizationFile(metroMap, dotOutputFile);

        System.out.println("\nTo generate an image from the DOT file, install Graphviz (graphviz.org)");
        System.out.println("Then run: dot -Tpng " + dotOutputFile + " -o map_image.png");

        scanner.close();
    }
}
