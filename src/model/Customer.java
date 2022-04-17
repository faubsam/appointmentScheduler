package model;

public class Customer {
    /**
     * CustomerId: int
     */
    private int customerID;
    /**
     * Name: string
     */
    private String name;
    /**
     * Address: string
     */
    private String address;
    /**
     * Postal code: string
     */
    private String postalCode;
    /**
     * Phone: string
     */
    private String phone;
    /**
     * Division Id: int
     */
    private int divisionID;

    /**
     * Constructor
     * @param id
     * @param name
     * @param address
     * @param postal
     * @param phone
     * @param divID
     */
    public Customer(int id, String name, String address, String postal, String phone, int divID) {
        this.customerID = id;
        this.name = name;
        this.address = address;
        this.postalCode = postal;
        this.phone = phone;
        this.divisionID = divID;
    }

    /**
     * Getter - id
     * @return id
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Setter - id
     * @param customerID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Getter - name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter - name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter - address
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter address
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter postal code
     * @return postal code as string
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Setter postal code
     * @param postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Getter phone
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Setter - phone
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Getter - division id
     * @return division id
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * Setter division
     * @param divisionID
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * Method toString for displaying Customers in the ComboBox
     * @return String customerID + customer name
     */
    public String toString() {
        return this.getCustomerID() + ": " + this.getName();
    }
}
