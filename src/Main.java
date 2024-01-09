import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        BookingSystem bookingSystem = new BookingSystem();
        Operator operator = new Operator(bookingSystem);
        operator.startProgram();
    }
}