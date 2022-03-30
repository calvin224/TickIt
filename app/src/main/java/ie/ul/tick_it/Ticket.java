package ie.ul.tick_it;

import androidx.annotation.NonNull;

public class Ticket {
    private String Name;
    private String Location;
    private String Image;
    private String Type;
    private String Index;

    public Ticket() {
    }

    public Ticket(String Name) {
        this.Name = Name;
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
    public String getIndex() {
        return Index;
    }

    @NonNull
    @Override
    public String toString() {
        return this.Name + " " + this.Location + " " + this.Image;
    }
}
