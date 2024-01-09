import java.io.Serializable;

public abstract class Room  implements Serializable {
    static int idGeneration = 1;
    int id = idGeneration++;
    public abstract String getRoomType();
    private boolean available;
     public Room() {
        this.available = true;
    }

    public void book() {
        this.available = false;
    }
}

class SingleRoom extends Room {
    final int singleBed;
    final int bathroom;
    final int TV;
    final int closed;
    final double perDay = 20;

    public SingleRoom() {
        this.singleBed = 1;
        this.bathroom = 1;
        this.closed = 1;
        this.TV = 1;

    }
    @Override
    public String getRoomType() {
        return "SINGLE";
    }
    public static boolean isAvailable(Room room) {
        return room != null;
    }
}
class DoubleRoom extends Room{
    final int doubleBed;
    final int bathroom;
    final int TV;
    final int closed;
    final double perDay = 35;
    public DoubleRoom() {
        this.doubleBed = 1;
        this.bathroom = 1;
        this.TV = 1;
        this.closed = 1;
    }

    @Override
    public String getRoomType() {
        return "Double";
    }
}

class DeluxeRoom extends Room{
    final int minibar;
    final int bathtub;
    final int kinSizeBed;
    final int sittingArea;
    final double perDay = 55;

    public DeluxeRoom() {
        this.sittingArea = 1;
        this.minibar = 1;
        this.bathtub = 1;
        this.kinSizeBed = 1;
    }

    @Override
    public String getRoomType()
    {
        return "Deluxe";
    }
}


