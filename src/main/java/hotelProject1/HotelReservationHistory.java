package hotelProject1;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HotelReservationHistory implements IHotelReservationHistory {

    @Override
    public List<Reservation> readReservationHistory(String filename, Hotel hotel) throws IOException {
        List<Reservation> reservationHistory = new ArrayList<>();
        try (Scanner scanner = new Scanner(getReservationHistoryFile(filename))) {
            String[] hotelInfo = scanner.nextLine().split(";");
            if (hotelInfo[0].equals(hotel.getName()) && hotelInfo[1].equals(hotel.getLocation())) {
                while (scanner.hasNextLine()) {
                    String[] properties = scanner.nextLine().split(";");
                    Reservation res = new Reservation(new HotelRoom(properties[0], Integer.parseInt(properties[1]), Double.parseDouble(properties[2])), new Customer(properties[3], properties[4], properties[5]), LocalDate.parse(properties[6]),LocalDate.parse(properties[7]));
                    reservationHistory.add(res);
                }  
            }
        } 
        return reservationHistory;
    }

    @Override
    public void writeReservationHistory(String filename, Hotel hotel) throws IOException {
        try (PrintWriter writer = new PrintWriter(getReservationHistoryFile(filename))) {
            writer.println(hotel.getName() + ";" + hotel.getLocation());
            for (Reservation reservation : hotel.getHotelReservations()) {
                HotelRoom hotelRoom = reservation.getHotelRoom();
                Customer customer = reservation.getCustomer();
                writer.println(String.format("%s;%s;%s;%s;%s;%s;%s;%s;", hotelRoom.getRoomId(), hotelRoom.getRoomCapacity(), hotelRoom.getPricePerNight(), customer.getGivenName(), customer.getLastName(), customer.getPhoneNumber(), reservation.getStartDate(), reservation.getEndDate()));            
            }  
        }       
    }

    @Override
    public File getReservationHistoryFile(String filename) {
        return new File(HotelReservationHistory.class.getResource("reservationHistory/").getFile() + filename + ".txt");
    }



    @Override
    public void clearFile(String filename, Hotel hotel) throws IOException {
        try (PrintWriter writer = new PrintWriter(getReservationHistoryFile(filename))) {
            writer.println(hotel.getName() + ";" + hotel.getLocation());
        }    
    }
    
}
