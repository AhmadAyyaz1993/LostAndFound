package lost_and_found.projectx.Models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ahmad on 09/06/2017.
 */

public class QRData extends RealmObject {
    @PrimaryKey
    private int id;

    private String name;

    private String address;

    private String email;

    private String phoneNumber;

    private String fbProfileLink;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFbProfileLink() {
        return fbProfileLink;
    }

    public void setFbProfileLink(String fbProfileLink) {
        this.fbProfileLink = fbProfileLink;
    }
}
