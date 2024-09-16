package hotelProject1;

public class HotelRoom {

    private String roomId;
    private int roomCapacity;
    private double pricePerNight;

    public HotelRoom(String roomId, int roomCapacity, double pricePerNight) {
        setRoomId(roomId);
        setPricePerNight(pricePerNight);
        setRoomCapacity(roomCapacity);
     }

     public void setRoomId(String roomId) {
        if (!roomId.matches("[0-9]{1}[1-9]{1}[A-D]{1}")) {
            throw new IllegalArgumentException("RoomId have to be a three character string starting with two digits and ending with a letter A-D");
        }
        this.roomId = roomId;
    }

    public void setPricePerNight(double pricePerNight) {
        if (pricePerNight <= 0) {
            throw new IllegalArgumentException("The price per night has to be bigger than 0");
        }
        this.pricePerNight = pricePerNight;
    }

    public void setRoomCapacity(int roomCapacity) {
        if (roomCapacity <= 0 || roomCapacity >= 5) {
            throw new IllegalArgumentException("The room capacity has to be a number between 1-9");
        }
        this.roomCapacity = roomCapacity;      
    }

    public String getRoomId() {
        return this.roomId;
    }

    public int getRoomCapacity() {
        return this.roomCapacity;
    }

    public double getPricePerNight() {
        return this.pricePerNight;
    }

    @Override
    public boolean equals(Object other) {
        final double delta = .0001;
        if (other instanceof HotelRoom) {
            HotelRoom otherRoom = (HotelRoom) other;
            return this.roomId.equals(otherRoom.getRoomId()) && this.roomCapacity == otherRoom.getRoomCapacity()
                    && (Math.abs(this.pricePerNight - otherRoom.getPricePerNight()) < delta);
        }
        return false;
    }

    @Override
    public String toString() {
        return "[roomId=" + roomId + ", roomCapacity=" + roomCapacity + ", pricePerNight=" + pricePerNight
                + " kr]";
    }

    

   

    



    
}
