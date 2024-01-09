import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookingSystem {
    List<Room> rooms = new ArrayList<>();
    List<Customer> customers = new ArrayList<>();
    List<Booking> bookings = new ArrayList<>();

    public List<Room> getRooms() {
        return rooms;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }
    public static boolean isAvailable(Room room) {
        return room != null;
    }

    public void bookRoom(Room room, Customer customer, Date startDate, Date endDate) {
        if (isAvailable(room)) {
            Booking booking = new Booking(room, customer, startDate, endDate);
            bookings.add(booking);
            System.out.println("Booking successful.");
        } else {
            System.out.println("Room not available for booking.");
        }
    }

    public void saveStateToFile(String filePath) {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            outputStream.writeObject(rooms);
            outputStream.writeObject(customers);
            outputStream.writeObject(bookings);
            System.out.println("State saved successfully.");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @SuppressWarnings("unchecked")
    public void loadStateFromFile(String filePath) {
        try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filePath))){
            rooms = (List<Room>) objectInputStream.readObject();
            customers = (List<Customer>) objectInputStream.readObject();
            bookings = (List<Booking>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
