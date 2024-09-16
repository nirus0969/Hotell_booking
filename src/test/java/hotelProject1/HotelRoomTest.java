package hotelProject1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HotelRoomTest {

    private HotelRoom hotelRoom;

    @BeforeEach
    public void setup() {
        hotelRoom = new HotelRoom("12A", 2,20212.12);
    }

    @Test
    @DisplayName("Test for RoomId")
    public void testRoomId() {
        hotelRoom.setRoomId("01A");
        assertEquals("01A", hotelRoom.getRoomId(),"The roomId was not changed after calling the method");
        hotelRoom.setRoomId("02D");
        assertEquals("02D", hotelRoom.getRoomId(),"The roomId was not changed after calling the method");
        hotelRoom.setRoomId("22B");
        assertEquals("22B", hotelRoom.getRoomId(),"The roomId was not changed after calling the method");
        hotelRoom.setRoomId("03C");
        assertEquals("03C", hotelRoom.getRoomId(),"The roomId was not changed after calling the method");
        assertThrows(IllegalArgumentException.class, () -> {
            hotelRoom.setRoomId("002A");
        },"There has to be two digits and a character");
        assertThrows(IllegalArgumentException.class, () -> {
            hotelRoom.setRoomId("00A");
        },"the first two digit has to be in the interval 01-99");
        assertThrows(IllegalArgumentException.class, () -> {
            hotelRoom.setRoomId("01E");
        },"The last charater has to be between A-D");
    }

    @Test
    @DisplayName("Test for RoomCapacity")
    public void testRoomCapacity() {
        hotelRoom.setRoomCapacity(1);
        hotelRoom.setRoomCapacity(2);
        hotelRoom.setRoomCapacity(3);
        hotelRoom.setRoomCapacity(4);
        assertEquals(4, hotelRoom.getRoomCapacity(),"The room capacity did not change after calling the method");
        assertThrows(IllegalArgumentException.class, () -> {
            hotelRoom.setRoomCapacity(0);
        },"Can not be zero or lower");
        assertThrows(IllegalArgumentException.class, () -> {
            hotelRoom.setRoomCapacity(-50);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            hotelRoom.setRoomCapacity(100);
        },"Room capacity has to be in the interval 1-4");
    }

    @Test
    @DisplayName("Test for Price per night")
    public void testPricePerNight() {
        hotelRoom.setPricePerNight(1002.2);
        hotelRoom.setPricePerNight(22003.2);
        assertThrows(IllegalArgumentException.class, () -> {
            hotelRoom.setPricePerNight(0);
        },"Cant not have the price for a room as zero");
        assertThrows(IllegalArgumentException.class, () -> {
            hotelRoom.setPricePerNight(-2003.2);
        },"Can not be a negative number");
    }
    
}
