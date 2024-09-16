package hotelProject1;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReservationTest {

    private Customer customer;
    private HotelRoom hotelRoom;
    private List<LocalDate> dates;

    @BeforeEach
    public void setup() {
        customer = new Customer("Niru", "Selvaratnam", "+4793070707");
        hotelRoom = new HotelRoom("12A", 2, 2012.50);
        dates = new ArrayList<>(List.of(
            LocalDate.of(2024, 02, 01),
            LocalDate.of(2024, 03, 04),
            LocalDate.of(2024, 04, 05),
            LocalDate.of(2024, 10, 06),
            LocalDate.of(2024, 12, 07),
            LocalDate.of(2024, 12, 24),
            LocalDate.of(2001, 10, 10)
        ));
    }

    @Test
    @DisplayName("Test for expected behaviour for start date and end date")
    public void testReservationDates() {
        new Reservation(hotelRoom, customer, dates.get(0), dates.get(2));
        new Reservation(hotelRoom, customer, dates.get(1), dates.get(4));
        new Reservation(hotelRoom, customer, dates.get(1), dates.get(1));
        assertThrows(IllegalArgumentException.class, () -> {
            new Reservation(hotelRoom, customer, dates.get(3), dates.get(2));
        },"A reservation should not be made if the start date is after the end date");
        assertThrows(IllegalArgumentException.class, () -> {
            new Reservation(hotelRoom, customer, dates.get(5), dates.get(1));
        },"A reservation should not be made if the start date is after the end date");
        assertThrows(IllegalArgumentException.class, () -> {
            new Reservation(hotelRoom, customer, dates.get(6), dates.get(1));
        },"A reservation should not be made if the start date is before today");
    }

    
}
