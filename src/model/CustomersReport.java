package model;

public class CustomersReport {

    /**
     * Appointment type
     */
    private String type;
    /**
     * Appointment month
     */
    private String month;
    /**
     * Count of appointments
     */
    private int count;


    /**
     * Getter - type
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * Setter - type
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter - month
     * @return month as String name
     */
    public String getMonth() {
        return month;
    }

    /**
     * Setter - month
     * @param month
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * Getter - count
     * @return int
     */
    public int getCount() {
        return count;
    }

    /**
     * Setter - count
     * @param count
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * Constructor for the customers report
     * @param type
     * @param month
     * @param count
     */
    public CustomersReport(String type, String month, int count) {
        //this.name = name;
        this.type = type;
        this.month = month;
        this.count = count;
    }
}
