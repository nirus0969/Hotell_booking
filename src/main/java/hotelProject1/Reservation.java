package hotelProject1;

import java.time.LocalDate;

public class Reservation {

    private HotelRoom hotelRoom;
    private Customer customer;
    private LocalDate startDate;
    private LocalDate endDate;


    public Reservation(HotelRoom hotelRoom, Customer customer, LocalDate startDate, LocalDate endDate) {
        if (startDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("The start date can not be before today");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("End date has to be after the start date of the booking");
        }
        this.hotelRoom = hotelRoom;
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public HotelRoom getHotelRoom() {
        return hotelRoom;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Reservation) {
            Reservation otherReservation = (Reservation) other;
            return this.customer.equals(otherReservation.getCustomer()) && this.hotelRoom.equals(otherReservation.getHotelRoom())
                && this.startDate.equals(otherReservation.startDate) && this.endDate.equals(otherReservation.endDate);

        }
        return false;
    }

    @Override
    public String toString() {
        return "[customer=" + customer.getGivenName() + " " + customer.getLastName() + ", hotelRoom=" + hotelRoom.getRoomId() + ", startDate=" + startDate
                + ", endDate=" + endDate + "]";
    }

    



    

    
}
