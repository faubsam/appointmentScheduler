package model;

public class CountryReport {
    /**
     * Country name
     */
    private String country;
    /**
     * Number of appointments per country
     */
    private int num;

    /**
     * Getter - num
     * @return num
     */
    public int getNum() {
        return num;
    }

    /**
     * Setter - num
     * @param num
     */
    public void setNum(int num) {
        this.num = num;
    }

    /**
     * Getter - country
     * @return country name
     */
    public String getCountry() {
        return country;
    }

    /**
     * Setter - country
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Constructor
     * @param country
     * @param num
     */
    public CountryReport (String country, int num) {
        this.country = country;
        this.num = num;
    }
}
