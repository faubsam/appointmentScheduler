package model;

import com.sun.media.jfxmedia.events.VideoRendererListener;

public class Contact {
    /**
     * ContactID: int
     */
    private int contactID;
    /**
     * Contact name: string
     */
    private String contactName;
    /**
     * Email: string
     */
    private String email;

    /**
     * Constructor
     * @param id
     * @param name
     * @param email
     */
    public Contact(int id, String name, String email) {
        this.contactID = id;
        this.contactName = name;
        this.email = email;
    }

    /**
     * Getter - contact id
     * @return id
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Setter - contact ID
     * @param contactID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * Getter - contact name
     * @return name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Setter - contact name
     * @param contactName
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Getter - email
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter - email
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * To string method to display contact in combo box
     * @return contact name
     */
    public String toString() {
        return this.contactName;
    }
}
