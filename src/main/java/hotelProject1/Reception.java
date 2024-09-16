package hotelProject1;

public class Reception {

    Hotel hotel;
    private boolean adminMode;
    private String password;

    public Reception(String password) {
        setPassword(password);
        this.adminMode = false;
    }

    public void setPassword(String password) {
        if (!validatePassword(password)) {
            throw new IllegalArgumentException("must have a digit, one upper- and lower case letter, no whitespace, and between 8-20 letters");
        }
        this.password = password;
    }

    private boolean validatePassword(String password) {
        return password.matches(
                        "^(?=.*[0-9])" //a digit must occur once
                       + "(?=.*[a-z])(?=.*[A-Z])" //must have one occurence of lower- and upper case letter
                       + "(?=\\S+$).{8,20}$" //no whitespace and has to have between 8-20 letters
        );
    }

    public void activateAdminMode(String password) {
        if (!password.equals(this.password)) {
            throw new IllegalArgumentException("Wrong password");
        }
        this.adminMode = true;
    }

    public void deactivateAdminMode() {
        if (this.adminMode == false) {
            throw new IllegalStateException("Admin mode is already deactivated");
        }
        this.adminMode = false;
    }

    public boolean getAdminMode() {
        return this.adminMode;
    }

    public String getPassword() {
        return password;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
        if (hotel.getReception() != this) {
            hotel.setReception(this);
        }
    }

    public Hotel getHotel() {
        return this.hotel;
    }

    public void makeReservation(Reservation reservation) {
        if (!hotel.containsHotelRoom(reservation.getHotelRoom())) {
            throw new IllegalArgumentException("The hotelroom in the reservation does not exist in this hotel");
        }
        hotel.getHotelReservations().forEach(booking -> {
            if (checkOverlappingReservations(booking, reservation)) {
                throw new IllegalArgumentException("There is already a booking made for the hotelroom in the date range chosen");
            }
        });
        hotel.addHotelReservation(reservation);
    }

    public void cancelReservation(int index) {
        if (hotel.getHotelReservations().isEmpty()) {
            throw new IllegalArgumentException("Can not cancel a reservation when there is no reservations");
        }
        if (index < 0 || index >= hotel.getHotelReservations().size()) {
            throw new IllegalArgumentException("Have to give a valid index");
        }
        else if (!getAdminMode()) {
            throw new IllegalStateException("Can not cancel a reservation when youre not a admin");
        }
        hotel.removeHotelReservation(index);
    }

    private boolean checkOverlappingReservations(Reservation res1, Reservation res2) {
        if (res1.getHotelRoom().equals(res2.getHotelRoom()) && res1.getStartDate().isBefore(res2.getEndDate()) && res1.getEndDate().isAfter(res2.getStartDate())) {
            return true;
        }
        return false;
    }






    
}
