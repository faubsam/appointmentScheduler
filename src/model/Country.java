package model;

public class Country {
    /**
     * country ID
     */
    private int countryID;
    /**
     * country name
     */
    private String country;

    /**
     * constuctor
     * @param id
     * @param name
     */
    public Country(int id, String name){
        this.countryID = id;
        this.country = name;
    }

    /**
     * to string method to display country name in the combo box
     * @return country name
     */
    @Override
    public String toString() {
        return this.country;
    }

    /**
     * getter - country id
     * @return id
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * setter - country id
     * @param countryID
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * getter - country name
     * @return country
     */
    public String getCountry() {
        return country;
    }

    /**
     * setter - country name
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }


}
