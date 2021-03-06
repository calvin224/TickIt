package ie.ul.tick_it;

import android.provider.ContactsContract;

import androidx.annotation.NonNull;

import java.time.LocalDateTime;
import java.time.LocalDate;

public class Ticket {
    private String Name;
    private String EventName;
    private String About;
    private String Location;
    private String Image;
    private String Type;
    private String UserID;
    private String Email;
    private String BusinessName;
    private String count;
    private long Date;
    private String TicketID;
    private boolean Scanned;

    public Ticket() {
    }

    public Ticket(String BusinessName,String EventName,String About,String Location,String image,String count, long date) {
        this.Name = BusinessName;
        this.EventName = EventName;
        this.About = About;
        this.Location = Location;
        this.Image = image;
        this.count = count;
        this.Date = date;
    }

    public Ticket(String userID, String businessName, String EventName, String userName, String businessLocation, boolean scanned) {
        this.BusinessName = businessName;
        this.UserID = userID;
        this.Name = userName;
        this.EventName = EventName;
        this.Location = businessLocation;
        this.Scanned = scanned;
    }


    public String getBusinessName() {
        return BusinessName;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        this.Name = name;
    }
    public String getLocation() {
        return Location;
    }
    public void setLocation(String Location) {
        this.Location = Location;
    }
    public String getImage() {
        return Image;
    }
    public void setImage(String Image) {
        this.Image = Image;
    }
    public String getType() {
        return Type;
    }
    public String getEmail() {
        return Email;
    }
    public String getAbout() {
        return About;
    }
    public String getEventName() {
        return EventName;
    }
    public String getCount() {
        return count;
    }
    public String getUserID() {
        return UserID;
    }
    public String getTicketID() {
        return TicketID;
    }
    public void setTicketID(String ID) {
        this.TicketID = ID;
    }
    public void setScanned(boolean scanned) {
        this.Scanned = scanned;
    }
    public boolean getScanned() {
        return Scanned;
    }

    @NonNull
    @Override
    public String toString() {
        return this.Name + " " + this.Location + " " + this.Image;
    }
}
