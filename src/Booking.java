import java.io.Serializable;
import java.util.Date;

public class Booking implements Serializable {
    private Room room;
    private Customer customer;
    private Date startDate;
    private Date endDate;
    public Booking(Room room, Customer customer, Date startDate, Date endDate) {
        this.room = room;
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Room getRoom() {
        return room;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
