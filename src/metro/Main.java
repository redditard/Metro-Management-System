package metro;

import metro.model.Ticket;
import metro.service.MetroService;

import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {
    public static void main(String[] args) {
        MetroService metroService = new MetroService();
        Scanner scanner = new Scanner(System.in);
        int choice = 0; // Initialize choice

        System.out.println("METRO MANAGEMENT SYSTEM");
        System.out.println("======================");

        do {
            System.out.println("\nChoose an option:");
            System.out.println("1. Enter journey details manually");
            System.out.println("2. Read journey details from file");
            System.out.println("3. Exit"); // Changed option 3
            System.out.print("Enter choice (1-3): "); // Changed range

            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                Ticket ticket = null; // Reset ticket for each loop iteration

                switch (choice) {
                    case 1:
                        System.out.print("Enter passenger name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter phone number: ");
                        String phone = scanner.nextLine();
                        System.out.print("Enter source station: ");
                        String source = scanner.nextLine();
                        System.out.print("Enter destination station: ");
                        String destination = scanner.nextLine();
                        ticket = metroService.processJourneyRequest(name, phone, source, destination);
                        break;
                    case 2:
                        System.out.print("Enter input file path (e.g., input.txt): ");
                        String inputFile = scanner.nextLine();
                        String[] journeyInfo = metroService.readJourneyRequest(inputFile);
                        if (journeyInfo != null) {
                            // Basic validation if needed
                            if (journeyInfo.length == 4 && journeyInfo[0] != null && journeyInfo[1] != null && journeyInfo[2] != null && journeyInfo[3] != null) {
                                ticket = metroService.processJourneyRequest(
                                    journeyInfo[0], journeyInfo[1], journeyInfo[2], journeyInfo[3]);
                            } else {
                                System.out.println("\nError: Input file format is incorrect or incomplete.");
                            }
                        } else {
                             System.out.println("\nError reading input file.");
                        }
                        break;
                    case 3: // Changed case number
                        System.out.println("Exiting application.");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 3."); // Changed range
                        continue; // Go back to menu
                }

                // Process ticket only if generated (choice 1 or 2)
                if (ticket != null) {
                    System.out.println("\nTicket generated successfully!");
                    System.out.print("Enter file path to save ticket (e.g., ticket.txt): ");
                    String ticketOutputFile = scanner.nextLine();
                    metroService.saveTicket(ticket, ticketOutputFile);
                    System.out.println("Ticket saved to " + ticketOutputFile);
                    System.out.println("\n" + ticket.getTicketDetails());
                } else if (choice == 1 || choice == 2) {
                    // Error message handled within case 2 for file reading issues
                    if (choice == 1) { // Only show generic error for manual input if ticket is null
                         System.out.println("\nError generating ticket. Please check station names and try again.");
                    }
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // consume the invalid input
                choice = 0; // Reset choice to continue loop
            }

        } while (choice != 3); // Changed exit condition

        scanner.close();
    }
}
