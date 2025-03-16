import java.util.*;

enum VehicleType {
    CAR, BIKE, TRUCK
}

class Vehicle {
    private String licensePlate;
    private VehicleType type;

    public Vehicle(String licensePlate, VehicleType type) {
        this.licensePlate = licensePlate;
        this.type = type;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public VehicleType getType() {
        return type;
    }
}

class ParkingSlot {
    private int slotNumber;
    private boolean isOccupied;

    public ParkingSlot(int slotNumber) {
        this.slotNumber = slotNumber;
        this.isOccupied = false;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void occupy() {
        isOccupied = true;
    }

    public void vacate() {
        isOccupied = false;
    }
}

class Ticket {
    private static int counter = 1;
    private String ticketNumber;
    private Vehicle vehicle;
    private long entryTime;

    public Ticket(Vehicle vehicle) {
        this.ticketNumber = "TICKET-" + counter++;
        this.vehicle = vehicle;
        this.entryTime = System.currentTimeMillis();
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public long getEntryTime() {
        return entryTime;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
}

class ParkingService {
    private List<ParkingSlot> slots;
    private Map<String, Ticket> activeTickets;

    public ParkingService(int numSlots) {
        slots = new ArrayList<>();
        activeTickets = new HashMap<>();
        for (int i = 1; i <= numSlots; i++) {
            slots.add(new ParkingSlot(i));
        }
    }

    public Ticket parkVehicle(Vehicle vehicle) {
        for (ParkingSlot slot : slots) {
            if (!slot.isOccupied()) {
                slot.occupy();
                Ticket ticket = new Ticket(vehicle);
                activeTickets.put(ticket.getTicketNumber(), ticket);
                System.out.println("Vehicle parked. Ticket issued: " + ticket.getTicketNumber());
                return ticket;
            }
        }
        System.out.println("No available parking slots!");
        return null;
    }

    public double unparkVehicle(String ticketNumber) {
        Ticket ticket = activeTickets.remove(ticketNumber);
        if (ticket != null) {
            long duration = (System.currentTimeMillis() - ticket.getEntryTime()) / 1000;
            double charge = duration * 0.05;
            System.out.println("Vehicle exited. Charge: $" + charge);
            return charge;
        } else {
            System.out.println("Invalid ticket number!");
            return 0;
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ParkingService parkingService = new ParkingService(30);

        while (true) {
            System.out.println("1. Park Vehicle\n2. Unpark Vehicle\n3. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter vehicle license plate: ");
                    String plate = scanner.nextLine();
                    System.out.print("Enter vehicle type (CAR/BIKE/TRUCK): ");
                    VehicleType type = VehicleType.valueOf(scanner.nextLine().toUpperCase());
                    Vehicle vehicle = new Vehicle(plate, type);
                    parkingService.parkVehicle(vehicle);
                    break;
                case 2:
                    System.out.print("Enter ticket number: ");
                    String ticketNumber = scanner.nextLine();
                    parkingService.unparkVehicle(ticketNumber);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
