package hotelProject1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HotelTest {

    private Reception reception;
    private Hotel hotel;
    private List<HotelRoom> hotelRooms;
    private Customer customer0;
    private Customer customer1;

    @BeforeEach
    public void setup() {
        reception = new Reception("Testing123");
        hotel = new Hotel("Clarion", "Oslo", reception);
        hotelRooms = new ArrayList<>();
        hotelRooms.addAll(List.of(
            new HotelRoom("01A", 2, 2345.22),
            new HotelRoom("01B", 2, 2345.22),
            new HotelRoom("01C", 2, 2345.22),
            new HotelRoom("01D", 2, 2345.22),
            new HotelRoom("02A", 1, 2045.22),
            new HotelRoom("02B", 1, 2045.22),
            new HotelRoom("02C", 2, 2345.22),
            new HotelRoom("02D", 2, 2345.22),
            new HotelRoom("03A", 2, 3345.22)
        ));
        hotelRooms.forEach(hotelRoom -> hotel.addHotelRoom(hotelRoom));   
        customer0 = new Customer("Eric", "Cantona", "+4793474701");
        customer1 = new Customer("Lionel", "Messi", "004793070707");
    }

    @Test
    @DisplayName("Test for making reservation")
    public void testMakeReservation() {
        hotel.makeReservation(new Reservation(hotelRooms.get(0), customer0, LocalDate.of(2024, 10, 11), LocalDate.of(2024, 10, 14)));
        hotel.makeReservation(new Reservation(hotelRooms.get(0), customer1, LocalDate.of(2024, 11, 11), LocalDate.of(2024, 11, 14)));
        hotel.makeReservation(new Reservation(hotelRooms.get(1), customer0, LocalDate.of(2024, 10, 11), LocalDate.of(2024, 10, 14)));
        assertThrows(IllegalArgumentException.class, () -> {
            hotel.makeReservation(new Reservation(hotelRooms.get(0), customer1, LocalDate.of(2024, 10, 12), LocalDate.of(2024, 10, 13)));
        },"Should not make a reservation for a room that is already booked in the time interval");
        assertThrows(IllegalArgumentException.class, () -> {
            hotel.makeReservation(new Reservation(hotelRooms.get(4), customer1, LocalDate.of(2020, 10, 12), LocalDate.of(2024, 10, 13)));
        },"Should not make a reservation for a hotelroom that does not exist");
    }

    @Test
    @DisplayName("Test for getting available Hotel rooms")
    public void testGetAvailableHotelRooms() {
        LocalDate startDate = LocalDate.of(2024, 10, 11);
        LocalDate endDate = LocalDate.of(2024, 10, 14);
        hotel.makeReservation(new Reservation(hotelRooms.get(0), customer0, startDate, endDate));
        hotel.makeReservation(new Reservation(hotelRooms.get(1), customer1, startDate, endDate));
        hotel.makeReservation(new Reservation(hotelRooms.get(2), customer0, startDate, endDate));
        assertEquals(List.of(
            new HotelRoom("01D", 2, 2345.22),
            new HotelRoom("02A", 1, 2045.22),
            new HotelRoom("02B", 1, 2045.22),
            new HotelRoom("02C", 2, 2345.22),
            new HotelRoom("02D", 2, 2345.22),
            new HotelRoom("03A", 2, 3345.22)
        ), hotel.getAvailableHotelRooms(startDate, endDate));      
    }

    @Test
    @DisplayName("Test for checking Customer reservations")
    public void testGetCustomerReservations() {
        Reservation res0 = new Reservation(hotelRooms.get(0), customer0, LocalDate.of(2024, 10, 11), LocalDate.of(2024, 10, 14));
        Reservation res1 = new Reservation(hotelRooms.get(1), customer0, LocalDate.of(2024, 10, 11), LocalDate.of(2024, 10, 14));
        hotel.makeReservation(res0);
        hotel.makeReservation(res1);
        assertEquals(List.of(res0, res1), hotel.getCustomerReservation(customer0));
    }

    @Test
    @DisplayName("Test for checking containg hotel rooms")
    public void testContainingHotelRooms() {
        HotelRoom newRoom0 = new HotelRoom("03B", 2, 2001.2);
        HotelRoom newRoom1 = new HotelRoom("03C", 2, 2001.2);
        hotel.addHotelRoom(newRoom0);
        assertTrue(hotel.containsHotelRoom(newRoom0));
        assertFalse(hotel.containsHotelRoom(newRoom1));  
    }

    @Test
    @DisplayName("Test for checking cancelling reservation")
    public void testCancelReservation() {
        LocalDate startDate = LocalDate.of(2024, 10, 11);
        LocalDate endDate = LocalDate.of(2024, 10, 14);
        hotel.makeReservation(new Reservation(hotelRooms.get(0), customer0, startDate, endDate));
        hotel.makeReservation(new Reservation(hotelRooms.get(1), customer1, startDate, endDate));
        hotel.makeReservation(new Reservation(hotelRooms.get(2), customer0, startDate, endDate));
        assertThrows(IllegalStateException.class, () -> {
            hotel.cancelReservation(0);
        },"Should not be able to cancel reservation when not admin");
        reception.activateAdminMode("Testing123");
        assertThrows(IllegalArgumentException.class, () -> {
            hotel.cancelReservation(3);
        },"Should not be able to cancel a reservation that does not exist");
        hotel.cancelReservation(0);
        assertEquals(2, hotel.getHotelReservations().size());   
        hotel.cancelReservation(0);
        hotel.cancelReservation(0);
        assertThrows(IllegalArgumentException.class, () -> {
            hotel.cancelReservation(0);
        },"Should not be able to cancel a reservation that does not exist"); 
    }

}
