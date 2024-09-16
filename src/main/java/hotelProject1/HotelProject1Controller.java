package hotelProject1;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;

public class HotelProject1Controller {

    private Hotel hotel;
    private Reception reception;
    private Customer customer;
    private List<HotelRoom> selectableHotelRooms;
    private IHotelReservationHistory reservationHistory = new HotelReservationHistory();

    @FXML
    private TextArea madeReservations;

    @FXML
    private Button makeReservationButton, seeAvailableRooms, confirmCustomer, changeCustomerInfo, activateAdminButton, deactivateAdminButton, deleteReservationButton;

    @FXML
    private TextField fetchFirstName, fetchLastName, fetchPhoneNumber, fetchStartDate, fetchEndDate;

    @FXML
    private ListView<HotelRoom> hotelRoomView;

    @FXML
    private ListView<Reservation> reservationView;

    @FXML
    private void initialize() {
        initializeSelectableHotelRooms();
        reception = new Reception("Testing123");
        hotel = new Hotel("Clarion", "Oslo", reception);
        selectableHotelRooms.forEach(s -> hotel.addHotelRoom(s));
        try {
            reservationHistory.clearFile(hotel.getName() + "ReservationHistory", hotel);
        } catch (IOException e) {}
    }

    @FXML
    private void handleMakeReservation() {
        try {
            int selectedIndex = hotelRoomView.getSelectionModel().getSelectedIndex();
            LocalDate startDate = LocalDate.parse(fetchStartDate.getText());
            LocalDate endDate = LocalDate.parse(fetchEndDate.getText());
            hotel.makeReservation(new Reservation(hotel.getAvailableHotelRooms(startDate, endDate).get(selectedIndex), customer, startDate, endDate));
            reservationHistory.writeReservationHistory(hotel.getName() + "ReservationHistory", hotel);
            updateMadeReservations();
            updateHotelRoomView();    
        } catch (DateTimeParseException e) {
            showErrorMessage("Wrong input for dates, try YYYY-MM-DD");
        } catch (IllegalArgumentException e) {
            showErrorMessage(e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            showErrorMessage("Have to choose a hotelroom to make a reservation");
        } catch (NullPointerException e) {
            showErrorMessage("Can not make a booking without registering as a customer");
        } catch (IOException e) {}
    }

    @FXML
    private void handleDeleteReservationButton() {
        try {
            int selectedIndex = reservationView.getSelectionModel().getSelectedIndex();
            hotel.cancelReservation(selectedIndex);
            reservationHistory.writeReservationHistory(hotel.getName() + "ReservationHistory", hotel); 
            updateReservationView();     
        } catch (IllegalStateException e) {
            showErrorMessage("Can not delete reservation if not in admin mode");
        } catch (IllegalArgumentException e) {
            showErrorMessage("Have to choose a reservation to delete it");
        } catch (IndexOutOfBoundsException e) {
            showErrorMessage("You have to choose a reservation to delete");
        } catch (IOException e) {}
    }

    private void alterCheckInFields(boolean value) {
        if (value) {
            fetchStartDate.setText("");
            fetchEndDate.setText("");
        }
        fetchStartDate.disableProperty().set(value);
        fetchEndDate.disableProperty().set(value);
    }

    private void alterCustomerRegistrationFields(boolean value) {
        if (value) {
            fetchFirstName.setText("");
            fetchLastName.setText("");
            fetchPhoneNumber.setText("");
        }
        fetchFirstName.disableProperty().set(value);
        fetchLastName.disableProperty().set(value);
        fetchPhoneNumber.disableProperty().set(value);
    }

    @FXML
    private void handleActivateAdminButton() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Admin login");
        dialog.setHeaderText("Write the password to get admin access");
        dialog.setContentText("Password;");
        try {
            String password = dialog.showAndWait().get();
            reception.activateAdminMode(password);
            activateAdminButton.visibleProperty().set(false);
            deactivateAdminButton.visibleProperty().set(true);
            hotelRoomView.visibleProperty().set(false);
            reservationView.visibleProperty().set(true);
            makeReservationButton.visibleProperty().set(false);
            seeAvailableRooms.visibleProperty().set(false);
            confirmCustomer.visibleProperty().set(false);
            changeCustomerInfo.visibleProperty().set(false);
            deleteReservationButton.visibleProperty().set(true);
            madeReservations.setText("");
            madeReservations.disableProperty().set(true);
            hotelRoomView.getItems().clear();
            this.customer = null;
            alterCheckInFields(true);
            alterCustomerRegistrationFields(true);
            updateReservationView();      
        } catch (IllegalArgumentException e) {
            showErrorMessage(e.getMessage());
        } catch (NoSuchElementException e) {}
    }

    @FXML
    private void handleDeactivateAdminButton() {
        try {
            reception.deactivateAdminMode();
            deactivateAdminButton.visibleProperty().set(false);
            activateAdminButton.visibleProperty().set(true);
            hotelRoomView.visibleProperty().set(true);
            reservationView.visibleProperty().set(false);
            seeAvailableRooms.visibleProperty().set(true);
            confirmCustomer.visibleProperty().set(true);
            deleteReservationButton.visibleProperty().set(false);
            madeReservations.disableProperty().set(false);
            alterCheckInFields(false);
            alterCustomerRegistrationFields(false);    
        } catch (IllegalStateException e) {
            showErrorMessage(e.getMessage());
        } 
    }

    @FXML
    private void registerCustomer() {
        try {
            this.customer = new Customer(fetchFirstName.getText(), fetchLastName.getText(), fetchPhoneNumber.getText());
            fetchFirstName.disableProperty().set(true);
            fetchLastName.disableProperty().set(true);
            fetchPhoneNumber.disableProperty().set(true);
            confirmCustomer.visibleProperty().set(false);
            changeCustomerInfo.visibleProperty().set(true);
            makeReservationButton.visibleProperty().set(true);
            updateMadeReservations();    
        } catch (IllegalArgumentException e) {
            showErrorMessage(e.getMessage());
        }
    }

    @FXML
    private void handleChangeCustomerInfo() {
        fetchFirstName.disableProperty().set(false);
        fetchLastName.disableProperty().set(false);
        fetchPhoneNumber.disableProperty().set(false);
        confirmCustomer.visibleProperty().set(true);
        changeCustomerInfo.visibleProperty().set(false);
        makeReservationButton.visibleProperty().set(false);
        madeReservations.setText("");
    }

    @FXML
    private void updateHotelRoomView() {
        try {
            LocalDate startDate = LocalDate.parse(fetchStartDate.getText());
            LocalDate endDate = LocalDate.parse(fetchEndDate.getText());
            hotelRoomView.getItems().setAll(hotel.getAvailableHotelRooms(startDate, endDate));
        } catch (IllegalArgumentException e) {
            hotelRoomView.getItems().clear();
            showErrorMessage(e.getMessage());
        } catch (DateTimeParseException e) {
            hotelRoomView.getItems().clear();
            showErrorMessage("Wrong input for dates, try YYYY-MM-DD");
        }
    }

    @FXML
    private void updateReservationView() {
        try {
            List<Reservation> madeReservations = reservationHistory.readReservationHistory(hotel.getName() + "ReservationHistory", hotel);
            reservationView.getItems().setAll(madeReservations);   
        } catch (IOException e) {}
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText("An error has occurred");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void updateMadeReservations() {
        madeReservations.setText(hotel.getCustomerReservationString(customer));
    }

    private void initializeSelectableHotelRooms() {
        selectableHotelRooms = new ArrayList<>();
        selectableHotelRooms.addAll(List.of(
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

    }
    
}
