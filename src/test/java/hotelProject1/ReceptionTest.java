package hotelProject1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReceptionTest {

    private Reception reception;

    @BeforeEach
    public void setup() {
        reception = new Reception("Testing123");
    }

    @Test
    @DisplayName("Test for checking if admin mode works correctly")
    public void testAdminMode() {
        reception.activateAdminMode("Testing123");
        assertEquals(true, reception.getAdminMode());
        reception.deactivateAdminMode();
        assertEquals(false, reception.getAdminMode());
        assertThrows(IllegalArgumentException.class, () -> {
            reception.activateAdminMode("123");
        },"Should throw exception for wrong password");
        assertEquals(false, reception.getAdminMode(),"The adminMode changed despite giving wrong password");
        assertThrows(IllegalStateException.class, () -> {
            reception.deactivateAdminMode();
        },"Should not deactive something that is already deativated");
    }

    @Test
    @DisplayName("Test for checking if password validation works as intended")
    public void testPassword() {
        reception.setPassword("NorskSommer2021");
        assertEquals("NorskSommer2021", reception.getPassword());
        reception.setPassword("Likerikkefisk1");
        assertEquals("Likerikkefisk1", reception.getPassword());
        assertThrows(IllegalArgumentException.class, () -> {
            reception.setPassword("petternorthug200");
        },"Make sure the password must have a upper-case letter");
        assertThrows(IllegalArgumentException.class, () -> {
            reception.setPassword("Abcdefgh");
        },"Make sure the password has at least one digit");
        assertThrows(IllegalArgumentException.class, () -> {
            reception.setPassword("vinterollillehammer1   ");
        },"Make sure the password does not have whitespace");
    }

    @Test
    @DisplayName("Test for checking if hotel and reception relation works as intended")
    public void testRelationWithHotel() {
        assertEquals(reception.getHotel(), null);
        Hotel hotel = new Hotel("Clarion", "Oslo", reception);
        assertEquals(hotel, reception.getHotel(),"The relation from reception to hotel was not made");
        assertEquals(reception, hotel.getReception(),"The relartion from hotel to reception was not made");
    }


    
}
