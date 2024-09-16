package hotelProject1;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface IHotelReservationHistory {

    List<Reservation> readReservationHistory(String filename, Hotel hotel) throws IOException;

    void writeReservationHistory(String filename, Hotel hotel) throws IOException;

    File getReservationHistoryFile(String filename);

    void clearFile(String filename, Hotel hotel) throws IOException;
}
