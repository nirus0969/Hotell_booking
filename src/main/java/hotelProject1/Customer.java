package hotelProject1;

public class Customer {

    private String givenName;
    private String lastName;
    private String phoneNumber;


    public Customer(String givenName, String lastName, String phoneNumber) {
      setGivenName(givenName);
      setLastName(lastName);
      setPhoneNumber(phoneNumber);
    }

    private static boolean isAlpha(String name) {
        return name.matches("[a-zA-Z]+");
    }

    public void setGivenName(String givenName) {
        String cleanGivenName = givenName.replaceAll("\s", "");
        if (!testName(cleanGivenName)) {
            throw new IllegalArgumentException("The input for the given name is not a valid input");
        }
        this.givenName = cleanGivenName;
    }

    public void setLastName(String lastName) {
        String cleanLastName = lastName.replaceAll("\s", "");
        if (!testName(cleanLastName)) {
            throw new IllegalArgumentException("The input for the last name is not a valid input");
        }
        this.lastName = cleanLastName;
    }

    private boolean testName(String name) {
        if (isAlpha(name) && name.length() > 1) {
            return true;
        }
        return false;
    }

    public void setPhoneNumber(String phoneNumber) {
       if (this.phoneNumber != null) {
           throw new IllegalStateException("Phone number is already registered");
       }
       if (!validPhoneNumber(phoneNumber)) {
           throw new IllegalArgumentException("The number has to start with +45, +46, +479, or +474");
       }
       this.phoneNumber = phoneNumber;
    }

    //inspirert av isValidPhoneNumber(String) fra selfcheckout-example/src/main/java/selfcheckout/SelfCheckout.java
    private boolean validPhoneNumber(String phoneNumber) {
        String cleanPhoneNumber = phoneNumber.replaceAll("\\s", "");
        if (cleanPhoneNumber.startsWith("+479") || cleanPhoneNumber.startsWith("+474") || cleanPhoneNumber.startsWith("+45") || cleanPhoneNumber.startsWith("+46")) {
            if (cleanPhoneNumber.length() != 11) {
                return false;
            }
        } else {
            return false;
        }
        char[] chars = cleanPhoneNumber.substring(1).toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (!Character.isDigit(chars[i])) {
                return false;    
            }  
        }
        return true;
    }

    public String getGivenName() {
        return this.givenName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Customer) {
            Customer otherCustomer = (Customer) other;
            return this.givenName.equals(otherCustomer.getGivenName()) && this.lastName.equals(otherCustomer.getLastName())
                    && this.phoneNumber.equals(otherCustomer.getPhoneNumber());
        }
        return false;
    }

    @Override
    public String toString() {
        return "Customer [givenName=" + givenName + ", lastName=" + lastName + "]";
    }
    
}
