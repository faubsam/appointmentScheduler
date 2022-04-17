package model;

public class FirstLevelDivision {
    /**
     * Division ID
     */
    private int divisionID;
    /**
     * Division name
     */
    private String division;
    /**
     * Division's country Id
     */
    private int countryID;

    /**
     * Constructor
     * @param divID
     * @param div
     * @param cID
     */
    public FirstLevelDivision(int divID, String div, int cID) {
        this.divisionID = divID;
        this.division = div;
        this.countryID = cID;
    }

    /**
     * Getter - division ID
     * @return id
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * Setter - division ID
     * @param divisionID
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * Getter - division name
     * @return name
     */
    public String getDivision() {
        return division;
    }

    /**
     * Setter - division name
     * @param division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Getter - country Id
     * @return country ID
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Setter - country ID
     * @param countryID
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * To String method to display division in the combo box
     * @return name
     */
    public String toString() {
        return division;
    }
}
