package hotelProject1;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Hotel {

    private String name;
    private String location;
    private List<HotelRoom> hotelRooms = new ArrayList<>();
    private List<Reservation> hotelReservations = new ArrayList<>();
    private Reception reception;

    public Hotel(String name, String location, Reception reception) {
        this.name = name;
        this.location = location;
        setReception(reception);
    }

    public void setReception(Reception reception) {
        this.reception = reception;
        if (reception.getHotel() != this) {
            reception.setHotel(this);
        }
    }

    public Reception getReception() {
        return reception;
    }

    public void addHotelReservation(Reservation reservation) {
        hotelReservations.add(reservation);
    }

    public void removeHotelReservation(int index) {
        hotelReservations.remove(index);
    }

    public void addHotelRoom(HotelRoom hotelroom) {
        if (this.containsHotelRoom(hotelroom)) {
            throw new IllegalArgumentException("The hotelroom is already in the hotel");
        }
        if (hotelroom == null) {
            throw new IllegalArgumentException("The hotelroom added to the hotel can't be null");
        }
        hotelRooms.add(hotelroom);
    }

    public boolean containsHotelRoom(HotelRoom hotelRoom) {
        return hotelRooms.contains(hotelRoom);
    }

    public int hotelRoomCount() {
        return hotelRooms.size();
    }

    public String getName() {
        return this.name;
    }

    public String getLocation() {
        return this.location;
    }

    public List<Reservation> getHotelReservations() {
        return new ArrayList<>(hotelReservations);
    }

    public void makeReservation(Reservation reservation) {
        reception.makeReservation(reservation);
    }

    public void cancelReservation(int index) {
        reception.cancelReservation(index);
    }

    public List<HotelRoom> getAvailableHotelRooms(LocalDate startDate, LocalDate endDate) {
        if (startDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("The start date can not be before today");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("End date has to be after the start date of the booking");
        }
        List<HotelRoom> availabeHotelRooms = new ArrayList<>(this.hotelRooms);
        hotelReservations.forEach(reservation -> {
            if (reservation.getStartDate().isBefore(endDate) && reservation.getEndDate().isAfter(startDate)) {
                availabeHotelRooms.remove(reservation.getHotelRoom());
            }
        });
        return availabeHotelRooms;
    }

    public List<HotelRoom> getHotelRooms() {
        return new ArrayList<>(hotelRooms);
    }

    public HotelRoom getHotelRoom(int index) {
        return hotelRooms.get(index);
    }

    public String getCustomerReservationString(Customer customer) {
        List<Reservation> customerReservations = getCustomerReservation(customer);
        StringBuilder builder = new StringBuilder();
        customerReservations.forEach(customerReservation -> {
            builder.append(customerReservation.toString() + "\n");
        });
        return builder.toString();
    }

    public List<Reservation> getCustomerReservation(Customer customer) {
        return hotelReservations.stream().filter(r -> r.getCustomer().equals(customer)).toList();
    }



    
}
