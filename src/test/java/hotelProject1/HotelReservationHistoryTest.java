package hotelProject1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HotelReservationHistoryTest {

    private static final String test_reservation_file_content = """
        Clarion;Oslo
        01A;1;1002.1;Lionel;Messi;+4793070787;2023-10-12;2023-10-15;
        01B;1;1223.2;Lionel;Messi;+4793070787;2023-11-12;2023-11-15;
        01C;1;121.2;Lionel;Messi;+4793070787;2023-12-12;2023-12-15;
        01D;1;2123.2;Lionel;Messi;+4793070787;2023-07-12;2023-07-15;
        02B;2;333.22;Lionel;Messi;+4793070787;2023-08-12;2023-08-15;
        02A;2;2333.3;Lionel;Messi;+4793070787;2023-09-12;2023-09-15;
        """;

    private String password = "Testing123";
    private Reception reception = new Reception(password);
    private Customer customer = new Customer("Lionel", "Messi", "+4793070787");

    private IHotelReservationHistory reservationHistory = new HotelReservationHistory();

    private Hotel getFilledHotelObject() {
        List<HotelRoom> hotelRooms = new ArrayList<>(List.of(
            new HotelRoom("01A", 1, 1002.1),
            new HotelRoom("01B", 1, 1223.2),
            new HotelRoom("01C", 1, 121.2),
            new HotelRoom("01D", 1, 2123.2),
            new HotelRoom("02A", 2, 2333.3),
            new HotelRoom("02B", 2, 333.22)
        ));
        List<Reservation> reservations = new ArrayList<>(List.of(
            new Reservation(hotelRooms.get(0), customer, LocalDate.of(2023, 10, 12), LocalDate.of(2023, 10, 15)),
            new Reservation(hotelRooms.get(1), customer, LocalDate.of(2023, 11, 12), LocalDate.of(2023, 11, 15)),
            new Reservation(hotelRooms.get(2), customer, LocalDate.of(2023, 12, 12), LocalDate.of(2023, 12, 15)),
            new Reservation(hotelRooms.get(3), customer, LocalDate.of(2023, 7, 12), LocalDate.of(2023, 7, 15)),
            new Reservation(hotelRooms.get(5), customer, LocalDate.of(2023, 8, 12), LocalDate.of(2023, 8, 15)),
            new Reservation(hotelRooms.get(4), customer, LocalDate.of(2023, 9, 12), LocalDate.of(2023, 9, 15))
        ));
        Hotel hotel = getEmptyHotelObject();
        hotelRooms.forEach(hotelroom -> hotel.addHotelRoom(hotelroom));
        reservations.forEach(reservation -> hotel.makeReservation(reservation));
        return hotel;
    }

    private Hotel getEmptyHotelObject() {
        return new Hotel("Clarion", "Oslo", reception);
    }

    @BeforeAll
    public void setup() throws IOException {
        Files.write(reservationHistory.getReservationHistoryFile("reservation_test").toPath(), test_reservation_file_content.getBytes());    
    }

    @Test
    public void testReadReservationHistory() throws IOException {
        List<Reservation> expectedReservationHistory = getFilledHotelObject().getHotelReservations();
        List<Reservation> actualReservationHistory = reservationHistory.readReservationHistory("reservation_test", getEmptyHotelObject());

        Iterator<Reservation> expectedReservationIterator = expectedReservationHistory.iterator();
        Iterator<Reservation> actualReservationIterator = actualReservationHistory.iterator();

        while (expectedReservationIterator.hasNext()) {
            try {
                Reservation expectedReservation = expectedReservationIterator.next();
                Reservation actualReservation = actualReservationIterator.next();
                assertEquals(expectedReservation, actualReservation);
            } catch (IndexOutOfBoundsException e) {
                fail("The lists of reservations was not the same");
            }
        }
    }

    @Test 
    public void testWriteReservationHistory() throws IOException {
        reservationHistory.writeReservationHistory("new_reservation_history", getFilledHotelObject());
        Path expectedFile = reservationHistory.getReservationHistoryFile("reservation_test").toPath();
        Path actualFile = reservationHistory.getReservationHistoryFile("new_reservation_history").toPath();
        assertEquals(Files.mismatch(expectedFile, actualFile), -1, "The files does not have to same information about reservations");
    }

    @AfterAll
    public void CleanUp() {
        reservationHistory.getReservationHistoryFile("reservation_test").delete();
        reservationHistory.getReservationHistoryFile("new_reservation_history").delete();
    }



    

    
    
}
