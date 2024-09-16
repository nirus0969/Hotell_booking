package hotelProject1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CustomerTest {

    private Customer customer;

    @BeforeEach
    public void setup() {
        customer = new Customer("Eden", "Hazard", "+4793070707");
    }

    @Test
    @DisplayName("Test for given name and last name")
    public void testName() {
        assertEquals("Eden", customer.getGivenName());
        assertEquals("Hazard", customer.getLastName());
        assertThrows(IllegalArgumentException.class, () -> {
            customer.setGivenName("123");
        },"A name can only have characters");
        assertThrows(IllegalArgumentException.class, () -> {
            customer.setGivenName("a");
        },"A name has to be longer than one character");
    }

    @Test
    @DisplayName("Test for phone number")
    public void testPhoneNumber() {
        customer = new Customer("Lionel", "Messi", "+4793070702");
        assertEquals("+4793070702", customer.getPhoneNumber());
        customer = new Customer("Lionel", "Messi", "+4543070702");
        assertEquals("+4543070702", customer.getPhoneNumber());
        assertThrows(IllegalArgumentException.class, () -> {
            customer = new Customer("Diego", "Maradona", "+454307070A");
        },"The phone number has to be all digits excepts the first character that has to be a +");
        assertThrows(IllegalArgumentException.class, () -> {
            customer = new Customer("Diego", "Maradona", "23466687");
        },"The phone number has to start with +45, +46, +479 or +474");
        assertThrows(IllegalArgumentException.class, () -> {
            customer = new Customer("Diego", "Maradona", "+463466687");
        },"The number excluded the area code has to be 8 digits");
        assertThrows(IllegalStateException.class, () -> {
            customer.setPhoneNumber("+4793070787");
        },"You should not able to change the phonenumber after it is given");
    }
    
    
}
