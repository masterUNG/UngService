package app.ewtc.masterung.ungservice.model;

/**
 * Created by masterung on 14/11/2017 AD.
 */

public class User {

    private String displayName, emailString,
            photoURLString, userIDString;

    public User() {
    }

    public User(String displayName, String emailString,
                String photoURLString, String userIDString) {
        this.displayName = displayName;
        this.emailString = emailString;
        this.photoURLString = photoURLString;
        this.userIDString = userIDString;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmailString() {
        return emailString;
    }

    public void setEmailString(String emailString) {
        this.emailString = emailString;
    }

    public String getPhotoURLString() {
        return photoURLString;
    }

    public void setPhotoURLString(String photoURLString) {
        this.photoURLString = photoURLString;
    }

    public String getUserIDString() {
        return userIDString;
    }

    public void setUserIDString(String userIDString) {
        this.userIDString = userIDString;
    }
}
