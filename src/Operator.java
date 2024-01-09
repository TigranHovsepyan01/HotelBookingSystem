import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Operator {

    private final BookingSystem bookingSystem;
    private List<Customer> customers;

    private List<Room> rooms;

    public Operator(BookingSystem bookingSystem) {
        this.bookingSystem = bookingSystem;
        rooms = bookingSystem.getRooms();
        customers = bookingSystem.getCustomers();
    }

    public void startProgram() {
        System.out.println("Hello Welcome Our Hotel");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String message = """
                    1. Add Room
                    2. Add Customer
                    3. Book Room
                    4. Generate Report for Room
                    5. Save State to File
                    6. Load State from File
                    7. Exit
                    """;
            System.out.println(message);
            String operation = scanner.nextLine();
            switch (operation) {
                case "1" -> addRoom();
                case "2" -> addCustomer();
                case "3" -> bookRoom();
                case "4" -> generateReportForRoom();
                case "5" -> saveStateToFile();
                case "6" -> loadStateToFile();
                case "7" -> exitProgram();

                default -> System.out.println("WARNING - " + operation + " is not correct operation");

            }

        }
    }

    private void generateReportForRoom() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter RoomType for the report - ");
        String roomType = scanner.nextLine();
        Room selectedRoom = findAvailableRoomByType(roomType);

        if (selectedRoom != null) {
            System.out.println("Enter file Path to save the report - ");
            String filePath = scanner.nextLine();

            try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
                writer.println("Report for Room: " + selectedRoom.getRoomType());
                writer.println("Booking History: ");

                for (Booking booking : bookings) {
                    if (booking.getRoom().equals(selectedRoom)) {
                        String customerName = booking.getCustomer().getName();
                        String startDate = formatDate(booking.getStartDate());
                        String endDate = formatDate(booking.getEndDate());

                        writer.println("Customer: " + customerName + ", Booking Period: " + startDate + " to " + endDate);
                    }
                }
                System.out.println("Report generated successfully. ");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Room not found. ");
        }
    }

    private String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    private void exitProgram() {
        System.out.println("Exiting Booking System. Goodbye!");
        System.exit(0);
    }

    private void loadStateToFile() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter filePath - ");
        String filePath = scanner.nextLine();
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filePath))) {
            rooms = (List<Room>) objectInputStream.readObject();
            customers = (List<Customer>) objectInputStream.readObject();
            bookings = (List<Booking>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveStateToFile() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter FilePath");
        String filePath = scanner.nextLine();
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            outputStream.writeObject(rooms);
            outputStream.writeObject(customers);
            outputStream.writeObject(bookings);
            System.out.println("State saved successfully.");
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addRoom() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String message = """
                    Single Room - enter 1
                    Double Room - enter 2
                    Deluxe Room - enter 3
                    """;
            System.out.println(message);
            String operation = scanner.nextLine();
            switch (operation) {
                case "1" -> {
                    bookingSystem.addRoom(new SingleRoom());
                    System.out.println("Single Room is added");
                    return;
                }
                case "2" -> {
                    bookingSystem.addRoom(new DoubleRoom());
                    System.out.println("Double Room is added");
                    return;
                }
                case "3" -> {
                    bookingSystem.addRoom(new DeluxeRoom());
                    System.out.println("Deluxe Room is added");
                    return;
                }
                default -> System.out.println("WARNING - " + operation + " is not correct operation");
            }
        }
    }

    private void addCustomer() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Here you can register a customer - ");
        System.out.println("Please enter the customers name  - ");
        String name = scanner.nextLine();
        System.out.println("Please enter the customers email  - ");
        String email = scanner.nextLine();
        Customer customer = new Customer(name, email);
        customers.add(customer);
    }

    List<Booking> bookings = new ArrayList<>();

    private void bookRoom() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter customer name - ");
        String customerName = scanner.nextLine();
        Customer customer = findCustomerByName(customerName);
        if (customer == null) {
            System.out.println("No available customer " + customerName);
            return;
        }
        System.out.println("Enter room type - ");
        String roomType = scanner.nextLine();
        Room room = findAvailableRoomByType(roomType);
        if (room == null) {
            System.out.println("No available room of type " + roomType);
            return;
        }

        System.out.println("Enter booking start date (yyyy-MM-dd) - ");
        String startDateStr = scanner.nextLine();
        Date startDate = parseDate(startDateStr);

        System.out.println("Enter booking end date (yyyy-MM-dd) - ");
        String endDateStr = scanner.nextLine();
        Date endDate = parseDate(endDateStr);
        if (startDate == null || endDate == null || endDate.before(startDate)) {
            System.out.println("Invalid date range. Booking failed.");
            return;
        }
        Booking booking = new Booking(room, customer, startDate, endDate);
        bookings.add(booking);
        room.book();
        System.out.println("Booking successful.");

    }

    private Room findAvailableRoomByType(String roomType) {
        for (Room room : rooms) {
            if (room.getRoomType().equalsIgnoreCase(roomType)) {
                return room;
            }
        }
        return null;
    }

    private Date parseDate(String dataStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(dataStr);
        } catch (java.text.ParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd. ");
            return null;
        }
    }

    private Customer findCustomerByName(String name) {
        for (Customer customer : bookingSystem.customers) {
            if (customer.name.equalsIgnoreCase(name)) {
                return customer;
            }
        }
        return null;
    }

    private boolean isAvailable(Room room) {
        return room != null;
    }
}
